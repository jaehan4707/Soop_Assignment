package com.jaehan.soop.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jaehan.soop.R
import com.jaehan.soop.domain.model.Repo
import com.jaehan.soop.ui.componenet.LoadingDialog
import com.jaehan.soop.ui.componenet.SearchTextField
import com.jaehan.soop.ui.screen.home.layout.SearchItem
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.lightGray
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onShowError: (String) -> Unit = {},
) {
    val query = viewModel.query.collectAsStateWithLifecycle()
    val pageRepositories = viewModel.repositories.collectAsLazyPagingItems()
    HomeContent(
        modifier = modifier,
        onClickedSearch = viewModel::searchRepositories,
        query = query.value,
        uiEvent = viewModel.uiEvent,
        updateQuery = viewModel::updateSearchText,
        onShowError = onShowError,
        pageRepositories = pageRepositories,
    )
}

@Composable
fun HomeContent(
    modifier: Modifier,
    onClickedSearch: (String) -> Unit,
    query: String,
    uiEvent: SharedFlow<HomeUiEvent>,
    updateQuery: (String) -> Unit,
    onShowError: (String) -> Unit = {},
    pageRepositories: LazyPagingItems<Repo>,
) {
    LaunchedEffect(uiEvent) {
        uiEvent.collectLatest { event ->
            when (event) {
                is HomeUiEvent.ShowError -> {
                    onShowError(event.errorMessage)
                }
            }
        }
    }

    HomeScreen(
        modifier = modifier,
        onClickedSearch = onClickedSearch,
        pageRepositories = pageRepositories,
        updateQuery = updateQuery,
        query = query,
        onShowError = onShowError,
    )
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    pageRepositories: LazyPagingItems<Repo>? = null,
    onClickedSearch: (String) -> Unit = {},
    updateQuery: (String) -> Unit = {},
    query: String = "",
    onShowError: (String) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier =
        modifier
            .fillMaxSize()
            .background(color = lightGray)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        SearchTextField(
            modifier = Modifier,
            searchText = query,
            onTextChanged = { updateQuery(it) },
            onClickedClear = { updateQuery("") },
            placeHolder = stringResource(id = R.string.search_placeholder),
            focusManager = focusManager,
            onClickedSearch = onClickedSearch,
        )
        pageRepositories?.let { repositories ->
            when (repositories.loadState.refresh) {
                is LoadState.Loading -> {
                    LoadingDialog(modifier = Modifier.fillMaxSize())
                }

                is LoadState.Error -> {
                    onShowError("로딩 오류!")
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(all = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(repositories.itemCount) { index ->
                            val item = repositories[index]
                            item?.let { repo ->
                                SearchItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    userImage = repo.userProfileImage,
                                    repositoryName = repo.repositoryName,
                                    description = repo.description,
                                    star = repo.starCount,
                                    language = repo.language,
                                    userName = repo.userName,
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                HorizontalDivider(
                                    modifier = Modifier,
                                    thickness = 1.dp,
                                    color = Color.Gray
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview() {
    SOOP_Theme {
        HomeScreen()
    }
}
