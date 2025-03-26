package com.vurgun.skyfit.core.ui.components.text.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography

@Composable
fun CodeTextInput(
    modifier: Modifier = Modifier.fillMaxWidth(),
    codeLength: Int = 4,
    onOtpCompleted: (String) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val boxSize = (maxWidth / (codeLength + 1)).coerceIn(40.dp, 56.dp)

        val otpState = remember { mutableStateListOf(*Array(codeLength) { "" }) }
        val focusRequesters = remember { List(codeLength) { FocusRequester() } }

        Row(
            // Center the row horizontally
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            otpState.forEachIndexed { index, value ->
                CommonOtpTextField(
                    otp = value,
                    boxSize = boxSize, // pass our computed size
                    onOtpChanged = { newValue ->
                        if (newValue.length == 1) {
                            otpState[index] = newValue
                            if (index < otpState.lastIndex) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        } else if (newValue.isEmpty()) {
                            otpState[index] = ""
                        }

                        // If all digits are entered, trigger completion
                        if (otpState.all { it.isNotEmpty() }) {
                            onOtpCompleted(otpState.joinToString(""))
                        }
                    },
                    onDelete = {
                        if (index > 0) {
                            otpState[index] = ""
                            focusRequesters[index - 1].requestFocus()
                        }
                    },
                    focusRequester = focusRequesters[index],
                    nextFocusRequester = focusRequesters.getOrNull(index + 1),
                    previousFocusRequester = focusRequesters.getOrNull(index - 1)
                )
            }
        }

        // Automatically focus on the first field when the screen loads
        LaunchedEffect(Unit) {
            focusRequesters.first().requestFocus()
        }
    }
}

@Composable
private fun CommonOtpTextField(
    otp: String,
    boxSize: Dp,
    onOtpChanged: (String) -> Unit,
    onDelete: () -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester? = null,
    previousFocusRequester: FocusRequester? = null
) {
    OutlinedTextField(
        value = otp,
        singleLine = true,
        onValueChange = { newValue ->
            if (newValue.length <= 1) {
                onOtpChanged(newValue)
                if (newValue.isNotEmpty()) {
                    nextFocusRequester?.requestFocus() // Move to next field
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(
            onNext = { nextFocusRequester?.requestFocus() }
        ),
        shape = RoundedCornerShape(50),
        textStyle = SkyFitTypography.bodyLargeMedium.copy(textAlign = TextAlign.Center),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = SkyFitColor.text.default,
            cursorColor = SkyFitColor.text.default,
            focusedBorderColor = SkyFitColor.border.secondaryButton,
            unfocusedBorderColor = SkyFitColor.border.secondaryButtonDisabled,
            backgroundColor = SkyFitColor.specialty.secondaryButtonRest
        ),
        placeholder = {
            Text(
                "•",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier
            .size(boxSize) // Use our adaptive size
            .focusRequester(focusRequester)
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Backspace && keyEvent.type == KeyEventType.KeyUp) {
                    if (otp.isEmpty()) {
                        previousFocusRequester?.requestFocus() // Move back
                        onDelete()
                    } else {
                        onOtpChanged("") // Clear current field
                    }
                    true
                } else {
                    false
                }
            }
    )
}
