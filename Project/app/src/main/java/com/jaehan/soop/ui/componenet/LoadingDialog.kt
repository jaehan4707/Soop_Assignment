package com.jaehan.soop.ui.componenet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.skyBlue

@Composable
fun LoadingDialog(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = skyBlue,
            strokeWidth = 7.dp
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun LoadingDialogPreview() {
    SOOP_Theme {
        LoadingDialog(modifier = Modifier)
    }
}
