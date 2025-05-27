package com.vurgun.skyfit.core.ui.components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.text.SkyInputTextField
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SkyFormTextField(
    title: String,
    hint: String,
    value: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    enabled: Boolean = true,
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
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = SkyFitColor.background.surfaceSecondary,
                    shape = RoundedCornerShape(50)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            SkyInputTextField(
                enabled = enabled,
                hint = hint,
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f)
            )
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