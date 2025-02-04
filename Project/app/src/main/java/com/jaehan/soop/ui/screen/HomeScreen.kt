package com.jaehan.soop.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaehan.soop.R
import com.jaehan.soop.ui.componenet.SearchTextField
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.lightGray

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val (searchText, setSearchText) =
        remember {
            mutableStateOf("")
        }
    val focusManager = LocalFocusManager.current
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .background(color = lightGray)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        SearchTextField(
            modifier = Modifier,
            searchText = searchText,
            onTextChanged = { setSearchText(it) },
            onClickedClear = { setSearchText("") },
            placeHolder = stringResource(id = R.string.search_placeholder),
            focusManager = focusManager,
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun MainScreenPreview() {
    SOOP_Theme {
        MainScreen()
    }
}
