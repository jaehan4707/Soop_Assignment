package com.jaehan.soop.ui.screen.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaehan.soop.ui.screen.detail.layout.RepositoryStatsRow
import com.jaehan.soop.ui.screen.detail.layout.UserProfile
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.Typography
import com.jaehan.soop.ui.theme.lightGray

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
) {
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .background(color = lightGray)
            .padding(vertical = 20.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(text = repositoryName, style = Typography.titleLarge)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            items(topics.size) { index ->
                SuggestionChip(
                    onClick = { },
                    label = {
                        Text(
                            text = topics[index],
                            style = Typography.bodyMedium
                        )
                    },
                    border = BorderStroke(width = 1.dp, color = Color.Transparent),
                    shape = CircleShape,
                    colors = SuggestionChipDefaults.suggestionChipColors()
                        .copy(containerColor = Color.LightGray),
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        RepositoryStatsRow(Modifier.fillMaxHeight(0.15f), star, watchers, fork)
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.weight(0.05f))
        UserProfile(
            modifier = Modifier,
            userProfileImage = userProfileImage,
            userName = userName,
            onClickedMore = {}
        )
        Spacer(modifier = Modifier.weight(0.05f))
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
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
            userName = "jaehan4707"
        )
    }
}