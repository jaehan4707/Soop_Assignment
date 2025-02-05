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
import androidx.compose.foundation.lazy.itemsIndexed
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
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        modifier = modifier,
        onClickedSearch = viewModel::searchRepositories,
        uiState = uiState.value,
        uiEvent = viewModel.uiEvent,
        updateQuery = viewModel::updateSearchText,
        onShowError = onShowError,
    )
}

@Composable
fun HomeContent(
    modifier: Modifier,
    onClickedSearch: (String) -> Unit,
    uiState: HomeUiState,
    uiEvent: SharedFlow<HomeUiEvent>,
    updateQuery: (String) -> Unit,
    onShowError: (String) -> Unit = {},
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

    MainScreen(
        modifier = modifier,
        onClickedSearch = onClickedSearch,
        results = uiState.results,
        updateQuery = updateQuery,
        query = uiState.query,
        isLoading = uiState.isLoading,
    )
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    results: List<Repo> = listOf(),
    onClickedSearch: (String) -> Unit = {},
    updateQuery: (String) -> Unit = {},
    query: String = "",
    isLoading: Boolean = false,
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
        if (isLoading) {
            LoadingDialog(modifier = Modifier)
        } else {
            LazyColumn(
                contentPadding = PaddingValues(all = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(
                    items = results,
                    key = { _, result ->
                        result.id
                    }
                ) { _, result ->
                    SearchItem(
                        modifier = Modifier.fillMaxWidth(),
                        userImage = result.userProfileImage,
                        repositoryName = result.repositoryName,
                        description = result.description,
                        star = result.starCount,
                        language = result.language,
                        userName = result.userName,
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

@Composable
@Preview(showSystemUi = true)
fun MainScreenPreview() {
    SOOP_Theme {
        MainScreen()
    }
}
