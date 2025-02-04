package com.jaehan.soop.ui.screen.detail.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaehan.soop.R
import com.jaehan.soop.ui.theme.SOOP_Theme

@Composable
fun StatColumn(modifier: Modifier, topic: String, count: Long) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Text(text = topic)
        Spacer(modifier = Modifier.weight(0.1f))
        Text(
            text = stringResource(id = R.string.start_count, count / 1000.0),
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.weight(0.5f))
    }
}


@Composable
@Preview(showBackground = true)
fun StatColumnPreview() {
    SOOP_Theme {
        StatColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            topic = "Star",
            count = 3900
        )
    }
}