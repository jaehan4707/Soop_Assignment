package com.jaehan.soop.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaehan.soop.domain.model.ApiResponse
import com.jaehan.soop.domain.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun updateSearchText(query: String) {
        _uiState.update { it.copy(query = query) }
    }


    fun searchRepositories(query: String) {
        viewModelScope.launch {
            repoRepository.searchRepository(query).onStart {
                _uiState.update { it.copy(isLoading = true) }
            }.collectLatest { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiEvent.emit(HomeUiEvent.ShowError(response.errorMessage))
                    }

                    is ApiResponse.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false, results = response.data)
                        }
                    }
                }
            }
        }
    }
}