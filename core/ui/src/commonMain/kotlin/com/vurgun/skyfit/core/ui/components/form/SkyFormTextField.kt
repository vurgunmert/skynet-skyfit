package com.vurgun.skyfit.core.ui.components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.ActionIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.text.SkyInputTextField
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_pencil

@Composable
fun SkyFormTextField(
    title: String,
    hint: String,
    value: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    enabled: Boolean = true,
    rightIconRes: DrawableResource? = null,
    focusRequester: FocusRequester = remember { FocusRequester() },
    nextFocusRequester: FocusRequester? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SkyText(
            text = title,
            styleType = TextStyleType.BodyMediumSemibold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50))
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            SkyInputTextField(
                enabled = enabled,
                hint = hint,
                value = value,
                onValueChange = onValueChange,
                focusRequester = focusRequester,
                nextFocusRequester = nextFocusRequester,
                modifier = Modifier.weight(1f)
            )

            if (rightIconRes != null) {
                Spacer(Modifier.width(8.dp))
                SkyIcon(rightIconRes, size = SkyIconSize.Small)
            }
        }
    }
}

@Preview
@Composable
fun SkyFormTextFieldPreview_Empty() {
    SkyFormTextField(
        title = "Antrenman Başlığı",
        hint = "Shoulders and Abs",
        value = null,
        onValueChange = { },
        enabled = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun SkyFormTextFieldPreview_Value() {
    SkyFormTextField(
        title = "Antrenman Başlığı",
        hint = "Shoulders and Abs",
        value = "Pilates",
        onValueChange = { },
        enabled = true,
        modifier = Modifier.fillMaxWidth()
    )
}