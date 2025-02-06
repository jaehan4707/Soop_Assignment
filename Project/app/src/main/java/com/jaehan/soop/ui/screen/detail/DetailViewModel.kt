package com.jaehan.soop.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaehan.soop.domain.model.ApiResponse
import com.jaehan.soop.domain.repository.RepoRepository
import com.jaehan.soop.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<DetailUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()
    private val _bottomDetailUiState = MutableStateFlow(BottomDetailUiState())
    val bottomDetailUiState = _bottomDetailUiState.asStateFlow()

    init {
        val owner = savedStateHandle["owner"] ?: ""
        val repositoryName = savedStateHandle["repositoryName"] ?: ""
        getRepositoryInfo(owner, repositoryName)
    }

    fun getRepositoryInfo(owner: String, repositoryName: String) {
        viewModelScope.launch {
            repoRepository.getRepositoryInfo(owner = owner, repositoryName = repositoryName)
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }.collectLatest { response ->
                    when (response) {
                        is ApiResponse.Error -> {
                            _uiEvent.emit(DetailUiEvent.ShowError(response.errorMessage))
                        }

                        is ApiResponse.Success -> {
                            val repo = response.data
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    userName = repo.userName,
                                    repositoryName = repo.repositoryName,
                                    starCount = repo.starCount,
                                    watchers = repo.watchers,
                                    forks = repo.forks,
                                    description = repo.description,
                                    userProfileImage = repo.userProfileImage,
                                    topics = repo.topics,
                                )
                            }
                        }
                    }
                }
        }
    }

    fun getUserInfoAndRepositories(userName: String) {
        viewModelScope.launch {
            val userInfoFlow = userRepository.getUserInfo(userName)
            val userReposFlow = userRepository.getUserRepositories(userName)
            userInfoFlow
                .combine(userReposFlow) { userInfoResponse, userReposResponse ->
                    Pair(userInfoResponse, userReposResponse)
                }.onStart {
                    _bottomDetailUiState.update { it.copy(isLoading = true) }
                }.collectLatest { (userInfoResponse, userReposResponse) ->
                    var state = when (userInfoResponse) {
                        is ApiResponse.Error -> {
                            _uiEvent.emit(DetailUiEvent.ShowError(userInfoResponse.errorMessage))
                            BottomDetailUiState()
                        }

                        is ApiResponse.Success -> {
                            val user = userInfoResponse.data
                            BottomDetailUiState().copy(
                                followers = user.followers,
                                following = user.following,
                                repositoryCount = user.repositoryCount,
                                bio = user.bio,
                                userName = userName,
                                userProfileImage = user.userProfileImage,
                            )
                        }
                    }

                    when (userReposResponse) {
                        is ApiResponse.Error -> {
                            _uiEvent.emit(DetailUiEvent.ShowError(userReposResponse.errorMessage))
                        }

                        is ApiResponse.Success -> {
                            state = state.copy(language = userReposResponse.data)
                        }
                    }
                    _bottomDetailUiState.value = state.copy(isLoading = false)
                }
        }
    }
}
