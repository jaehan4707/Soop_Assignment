package com.jaehan.soop.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaehan.soop.R
import com.jaehan.soop.domain.model.SearchResult
import com.jaehan.soop.ui.componenet.SearchItem
import com.jaehan.soop.ui.componenet.SearchTextField
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.lightGray

val sampleItem =
    SearchResult(
        id = 1,
        userProfileImage = "https://avatars.githubusercontent.com/u/99114456?v=4",
        userName = "jaehan4707",
        repositoryName = "Soop",
        starCount = 15000,
        description = "description",
        language = "Kotlin"
    )
val sampleData = List(8) { index ->
    sampleItem.copy(id = index + 1)
}

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

        LazyColumn(
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = sampleData,
                key = { _, result ->
                    result.id
                }
            ) { _, result ->
                SearchItem(
                    modifier = Modifier.fillMaxWidth(),
                    userImage = result.userProfileImage,
                    repositoryName = result.repositoryName,
                    description = result.description,
                    star = result.starCount,
                    language = result.language,
                    userName = result.userName,
                )
                Spacer(modifier = Modifier.height(3.dp))
                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun MainScreenPreview() {
    SOOP_Theme {
        MainScreen()
    }
}
