package com.jaehan.soop.ui.screen.home.layout

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jaehan.soop.R
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.Typography
import com.jaehan.soop.ui.theme.getLanguageColor
import com.jaehan.soop.ui.util.toKFormat

/**
 * TODO
 *
 * @param modifier
 * @param userImage : 유저 프로필 이미지
 * @param repositoryName : 레포지토리 이름
 * @param description : 레포지토리 설명
 * @param star : 스타 개수
 * @param language : 대표 언어
 * @param userName : 유저 이름
 * @param onClick : 클릭 람다식
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    userImage: String,
    repositoryName: String,
    description: String,
    star: Long,
    language: String,
    userName: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 15.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            GlideImage(
                model = userImage,
                contentDescription = "user_image",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Text(
                text = userName,
                color = Color.Gray,
                style = Typography.labelLarge,
            )
        }
        Text(text = repositoryName, style = Typography.bodyMedium)
        if (description.isNotBlank()) {
            Text(text = description, style = Typography.labelSmall)
        }
        Row(
            modifier = Modifier, verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(id = R.drawable.ic_star), contentDescription = "star")
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = star.toKFormat(),
                color = Color.Gray,
                style = Typography.labelSmall
            )
            Spacer(modifier = Modifier.width(10.dp))
            if (language.isNotBlank()) {
                Spacer(
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(getLanguageColor(language))
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = language, color = Color.Gray, style = Typography.labelSmall)
            }
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
fun SearchItemPreview() {
    SOOP_Theme {
        SearchItem(
            userImage = "https://avatars.githubusercontent.com/u/99114456?v=4",
            repositoryName = "open-android",
            description = "description",
            star = 14557,
            language = "kotlin",
            userName = "android",
            onClick = {},
        )
    }
}
