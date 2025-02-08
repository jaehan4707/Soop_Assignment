package com.jaehan.soop.ui.screen.detail.layout

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.Typography

/**
 * TODO
 *
 * @param modifier
 * @param topics : topic 리스트
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopicChips(
    modifier: Modifier, topics: List<String>
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 130.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        topics.forEach { topic ->
            SuggestionChip(
                onClick = { },
                label = {
                    Text(
                        text = topic, style = Typography.bodyMedium,
                        maxLines = 1, overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.background
                    )
                },
                modifier = Modifier.widthIn(min = 50.dp, max = 110.dp),
                shape = CircleShape,
                border = BorderStroke(width = 1.dp, color = Color.Transparent),
                colors = SuggestionChipDefaults.suggestionChipColors()
                    .copy(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
@Preview
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
fun TopicChipsPreview() {
    SOOP_Theme {
        TopicChips(
            modifier = Modifier,
            topics = listOf("android", "kotlin", "owncloud", "123412312312312312")
        )
    }
}
