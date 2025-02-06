package com.jaehan.soop.ui.screen.detail.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jaehan.soop.R
import com.jaehan.soop.ui.componenet.LoadingDialog
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.Typography

/**
 * TODO
 *
 * @param modifier
 * @param onClosedBottomSheet : BottomSheet 닫는 람다식
 * @param userProfileImage : 유저 프로필 이미지
 * @param userName : 유저 이름
 * @param followers : 팔로워 수
 * @param following : 팔로잉 수
 * @param language : 언어
 * @param repositories : 레포지토리 수
 * @param bio : bio
 * @param isLoading : 데이터 로딩 여부
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun UserInfoBottomSheet(
    modifier: Modifier,
    onClosedBottomSheet: () -> Unit,
    userProfileImage: String,
    userName: String,
    followers: Long,
    following: Long,
    language: List<String>,
    repositories: Long,
    bio: String,
    isLoading: Boolean,
) {

    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = modifier.fillMaxSize(),
        onDismissRequest = onClosedBottomSheet,
        sheetState = bottomSheetState,
        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        dragHandle = null,
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxHeight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                LoadingDialog(modifier = Modifier)
            }
        } else {
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp,
                    bottom = 40.dp
                ),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    GlideImage(
                        model = userProfileImage,
                        contentDescription = "user_profile_image",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                    Text(text = userName, style = Typography.bodyLarge)
                }
                UserInfoRow(label = stringResource(R.string.followers), value = "$followers")
                UserInfoRow(label = stringResource(R.string.following), value = "$following")
                UserInfoRow(
                    label = stringResource(R.string.language),
                    value = "${language.joinToString(", ")}"
                )
                UserInfoRow(label = stringResource(R.string.repositories), value = "$repositories")
                UserInfoRow(label = stringResource(R.string.bio), value = bio)
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun UserInfoBottomSheetPreview() {
    SOOP_Theme {
        UserInfoBottomSheet(
            onClosedBottomSheet = {}, modifier = Modifier,
            userProfileImage = "https://avatars.githubusercontent.com/u/99114456?v=4",
            followers = 85,
            following = 92,
            repositories = 27,
            userName = "jaehan4707",
            bio = "",
            language = listOf("kotlin", "java"),
            isLoading = true
        )
    }
}
