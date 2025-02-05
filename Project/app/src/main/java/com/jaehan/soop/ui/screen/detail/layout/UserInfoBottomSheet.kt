package com.jaehan.soop.ui.screen.detail.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.Typography

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
    bio: List<String>,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onClosedBottomSheet,
        sheetState = bottomSheetState,
        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        dragHandle = null,
    ) {
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
            UserInfoRow(label = "Followers", value = "$followers")
            UserInfoRow(label = "Following", value = "$following")
            UserInfoRow(label = "Language", value = "${language.joinToString(", ")}")
            UserInfoRow(label = "Repositories", value = "$repositories")
            UserInfoRow(label = "Bio", value = "${bio.joinToString(", ")}")
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
            bio = listOf("jaehan4707","android","kotlin","java"),
            language = listOf("kotlin", "java")
        )
    }
}