package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.button.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_search
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun SkyFitSearchTextInputComponent(
    modifier: Modifier = Modifier,
    hint: String = "Ara",
    value: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    onSearch: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, shape = RoundedCornerShape(20.dp))
            .clickable { focusRequester.requestFocus() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_search),
            contentDescription = "Search",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )

        Spacer(Modifier.width(8.dp))

        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            enabled = true,
            value = value.orEmpty(),
            onValueChange = { onValueChange?.invoke(it) },
            textStyle = SkyFitTypography.bodyMediumRegular.copy(
                color = SkyFitColor.text.default
            ),
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search // Sets the search action on keyboard
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide() // Hides the keyboard when search is triggered
                    onSearch?.invoke()
                }
            ),
            cursorBrush = SolidColor(SkyFitColor.specialty.buttonBgRest),
            decorationBox = { innerTextField ->
                if (value.isNullOrBlank()) {
                    Text(text = hint, style = SkyFitTypography.bodyMediumRegular, color = SkyFitColor.text.secondary)
                } else {
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun SkyFitSearchFilterBarComponent(onEnableSearch: (Boolean) -> Unit) {
    var filterItems = listOf("Kardiyo", "Pilates", "Kondisyon", "Beslenme", "CrossFit", "Yoga")
    var selectedItems by remember { mutableStateOf(setOf<String>()) }
    var isSearchVisible by remember { mutableStateOf(false) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // First icon for showing search
        item {
            SkyFitIconButton(
                painter = painterResource(Res.drawable.logo_skyfit),
                onClick = {
                    isSearchVisible = !isSearchVisible
                    onEnableSearch(isSearchVisible)
                }
            )
        }

        // Second icon for deselecting all filters
        item {
            SkyFitIconButton(
                painter = painterResource(Res.drawable.logo_skyfit),
                onClick = { selectedItems = emptySet() }
            )
        }

        // Filter items
        items(filterItems) { filter ->
            SkyFitSearchFilter(
                text = filter,
                selected = selectedItems.contains(filter),
                onClick = {
                    selectedItems = if (selectedItems.contains(filter)) {
                        selectedItems - filter
                    } else {
                        selectedItems + filter
                    }
                }
            )
        }
    }
}

@Composable
private fun SkyFitSearchFilter(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (selected) SkyFitColor.text.inverse else SkyFitColor.text.default
    val borderColor = SkyFitColor.border.secondaryButton
    val backgroundColor = if (selected) SkyFitColor.specialty.buttonBgRest else SkyFitColor.background.surfaceSecondary
    Box(
        Modifier
            .wrapContentSize()
            .background(backgroundColor, CircleShape)
            .border(2.dp, color = borderColor, shape = CircleShape)
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            style = SkyFitTypography.bodyMediumRegular,
            color = textColor
        )
    }
}
