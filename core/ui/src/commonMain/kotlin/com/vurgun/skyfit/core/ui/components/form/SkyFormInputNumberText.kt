package com.vurgun.skyfit.core.ui.components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.text.NumberInputFormField
import com.vurgun.skyfit.core.ui.components.text.SkyDigitInputTextField
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun SkyFormInputNumberText(
    title: String,
    hint: String,
    value: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SkyText(
            text = title,
            styleType = TextStyleType.BodyMediumSemibold,
            modifier = Modifier.padding(start = 8.dp)
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
            SkyDigitInputTextField(
                hint = hint,
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SkyFormInputNumberText(
    title: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onNext: (() -> Unit)? = null,
    enabled: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SkyText(
            text = title,
            styleType = TextStyleType.BodyMediumSemibold,
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(Modifier.height(8.dp))

        NumberInputFormField(
            hint = hint,
            value = value,
            onValueChange = onValueChange,
            keyboardType = keyboardType,
            imeAction = imeAction,
            onNext = onNext,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SkyFitColor.background.surfaceSecondary,
                    shape = RoundedCornerShape(50)
                )
        )
    }
}
