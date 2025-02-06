package com.jaehan.soop.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jaehan.soop.domain.model.Repo
import com.jaehan.soop.domain.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    private val _repositories = MutableStateFlow<PagingData<Repo>>(PagingData.empty())
    val repositories = _repositories.asStateFlow()

    fun updateSearchText(query: String) {
        _query.value = query
    }

    fun searchRepositories(query: String) {
        viewModelScope.launch {
            repoRepository.searchRepository(query)
                .cachedIn(viewModelScope)
                .catch {
                    _uiEvent.emit(HomeUiEvent.ShowError(it.message ?: ""))
                }
                .collectLatest { pagingData ->
                    _repositories.value = pagingData
                }
        }
    }
}
