package com.jaehan.soop.ui.screen.detail.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaehan.soop.R
import com.jaehan.soop.ui.theme.SOOP_Theme

@Composable
fun RepositoryStatsRow(modifier: Modifier, star: Long, watchers: Long, fork: Long) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        StatColumn(modifier = Modifier, topic = stringResource(R.string.star), count = star)
        Spacer(modifier = Modifier.weight(1f))
        StatColumn(modifier = Modifier, topic = stringResource(R.string.watchers), count = watchers)
        Spacer(modifier = Modifier.weight(1f))
        StatColumn(modifier = Modifier, topic = stringResource(R.string.forks), count = fork)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview(showSystemUi = true)
fun RepositoryStatsRowPreview() {
    SOOP_Theme {
        RepositoryStatsRow(
            modifier = Modifier.fillMaxHeight(0.15f),
            star = 1900,
            watchers = 1500,
            fork = 1500
        )
    }
}