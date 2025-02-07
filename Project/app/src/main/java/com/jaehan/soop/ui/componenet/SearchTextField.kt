package com.jaehan.soop.ui.componenet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaehan.soop.R
import com.jaehan.soop.ui.theme.SOOP_Theme
import com.jaehan.soop.ui.theme.Typography

/**
 * TODO
 *
 * @param modifier
 * @param searchText : 검색어
 * @param onTextChanged : 값이 변할 경우의 람다식
 * @param onClickedClear : x 버튼을 누를 경우 람다식
 * @param onClickedSearch : 키보드 완료 버튼을 누를 경우 람다식
 * @param placeHolder : placeHolder
 * @param focusManager : focusManager
 */
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchText: String = "",
    onTextChanged: (String) -> Unit = {},
    onClickedClear: () -> Unit = {},
    onClickedSearch: (String) -> Unit = {},
    placeHolder: String = "",
    focusManager: FocusManager,
) {
    OutlinedTextField(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = searchText,
        onValueChange = { onTextChanged(it) },
        trailingIcon = {
            IconButton(onClick = onClickedClear) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = "clear_button",
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions =
        KeyboardActions(onSearch = {
            onClickedSearch(searchText)
            focusManager.clearFocus()
        }),
        singleLine = true,
        placeholder = {
            Text(
                text = placeHolder,
                color = Color.Gray,
                style = Typography.labelSmall,
            )
        },
    )
}

@Composable
@Preview(showSystemUi = true, name = "SearchTextField")
fun SearchTextFieldPreview() {
    SOOP_Theme {
        SearchTextField(
            focusManager = LocalFocusManager.current
        )
    }
}
