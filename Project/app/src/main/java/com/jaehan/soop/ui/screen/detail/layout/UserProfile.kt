package com.jaehan.soop.ui.screen.detail.layout

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jaehan.soop.R
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.Typography
import com.jaehan.soop.ui.theme.skyBlue

/**
 * TODO
 *
 * @param modifier
 * @param userProfileImage : 유저 프로필 이미지
 * @param userName : 유저 이름
 * @param onClickedMore : More 버튼 클릭 람다식
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserProfile(
    modifier: Modifier,
    userProfileImage: String,
    userName: String,
    onClickedMore: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        GlideImage(
            model = userProfileImage,
            contentDescription = "user_profile_image",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Text(text = userName, color = Color.Gray, style = Typography.labelLarge)
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onClickedMore(userName) },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors().copy(containerColor = skyBlue)
        ) {
            Text(text = stringResource(R.string.more_button_message), style = Typography.bodyMedium)
        }
    }
}


@Composable
@Preview(showSystemUi = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true, name = "lightMode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "darkMode",
    backgroundColor = 0xFF000000
)
fun UserProfilePreview() {
    SOOP_Theme {
        UserProfile(
            modifier = Modifier,
            userProfileImage = "https://avatars.githubusercontent.com/u/99114456?v=4",
            userName = "jaehan4707",
            onClickedMore = {}
        )
    }
}
