package com.jaehan.soop.ui.screen.detail.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.Typography

@Composable
fun UserInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(text = label, style = Typography.bodyLarge)
        Text(text = value, style = Typography.bodyLarge, color = Color.Gray)
    }
}

@Composable
@Preview
fun UserInfoRowPreview(){
    SOOP_Theme {
        UserInfoRow(label = "Followers", value = "12345")
    }
}