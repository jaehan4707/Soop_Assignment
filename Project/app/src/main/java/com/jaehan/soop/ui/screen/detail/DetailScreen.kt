package com.jaehan.soop.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jaehan.soop.R
import com.jaehan.soop.ui.componenet.LoadingDialog
import com.jaehan.soop.ui.screen.detail.layout.RepositoryStatsRow
import com.jaehan.soop.ui.screen.detail.layout.TopicChips
import com.jaehan.soop.ui.screen.detail.layout.UserInfoBottomSheet
import com.jaehan.soop.ui.screen.detail.layout.UserProfile
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.Typography
import com.jaehan.soop.ui.theme.lightGray
import kotlinx.coroutines.flow.collectLatest


@Composable
fun DetailRoute(
    modifier: Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    onShowError: (String) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState().value
    val uiEvent = viewModel.uiEvent
    val bottomDetailUiState = viewModel.bottomDetailUiState.collectAsStateWithLifecycle().value
    LaunchedEffect(uiEvent) {
        uiEvent.collectLatest { event ->
            when (event) {
                is DetailUiEvent.ShowError -> onShowError(event.errorMessage)
            }
        }
    }

    if (uiState.isLoading) {
        LoadingDialog(modifier = modifier)
    } else {
        DetailScreen(
            modifier = modifier,
            repositoryName = uiState.repositoryName,
            topics = uiState.topics,
            star = uiState.starCount,
            watchers = uiState.watchers,
            fork = uiState.forks,
            description = uiState.description,
            userProfileImage = uiState.userProfileImage,
            userName = uiState.userName,
            getUserInfoAndRepositories = viewModel::getUserInfoAndRepositories,
            following = bottomDetailUiState.following,
            followers = bottomDetailUiState.followers,
            repositories = bottomDetailUiState.repositoryCount,
            bio = bottomDetailUiState.bio,
            language = bottomDetailUiState.language,
            isBottomLoading = bottomDetailUiState.isLoading,
            isLoaded = bottomDetailUiState.userName.isNotBlank(),
        )
    }
}


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    repositoryName: String,
    topics: List<String>,
    star: Long,
    watchers: Long,
    fork: Long,
    description: String,
    userProfileImage: String,
    userName: String,
    getUserInfoAndRepositories: (String) -> Unit,
    followers: Long,
    following: Long,
    language: List<String>,
    repositories: Long,
    bio: String,
    isBottomLoading: Boolean,
    isLoaded: Boolean,
) {
    var isOpenBottomSheet by remember { mutableStateOf(false) }

    if (isOpenBottomSheet && !isLoaded) {
        LaunchedEffect(userName) {
            getUserInfoAndRepositories(userName)
        }
    }

    if (isOpenBottomSheet) {
        UserInfoBottomSheet(
            modifier = modifier,
            onClosedBottomSheet = { isOpenBottomSheet = false },
            userProfileImage = userProfileImage,
            userName = userName,
            followers = followers,
            following = following,
            language = language,
            repositories = repositories,
            bio = bio,
            isLoading = isBottomLoading,
        )
    }

    Column(
        modifier =
        modifier
            .fillMaxSize()
            .background(color = lightGray)
            .padding(vertical = 20.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(text = repositoryName, style = Typography.titleLarge)
        TopicChips(modifier = Modifier, topics = topics)
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        RepositoryStatsRow(Modifier.fillMaxHeight(0.15f), star, watchers, fork)
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.weight(0.05f))
        UserProfile(
            modifier = Modifier,
            userProfileImage = userProfileImage,
            userName = userName,
            onClickedMore = {
                isOpenBottomSheet = true
            },
        )
        Spacer(modifier = Modifier.weight(0.05f))
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Text(text = stringResource(R.string.description), style = Typography.titleLarge)
        Text(text = description, style = Typography.labelLarge, color = Color.Gray)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview(showSystemUi = true)
fun DetailScreenPreview() {
    SOOP_Theme {
        DetailScreen(
            repositoryName = "android",
            topics = listOf(
                "android",
                "kotlin",
                "owncloud",
                "1234",
                "12314125",
                "1234",
                "12345",
                "12314123",
                "123123"
            ),
            star = 3900,
            watchers = 3900,
            fork = 3100,
            description = "asdasedadsadsad",
            userProfileImage = "https://avatars.githubusercontent.com/u/99114456?v=4",
            userName = "jaehan4707",
            getUserInfoAndRepositories = {},
            followers = 0,
            following = 0,
            language = listOf(),
            bio = "",
            repositories = 0,
            isBottomLoading = false,
            isLoaded = false,
        )
    }
}
