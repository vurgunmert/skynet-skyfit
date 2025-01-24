package com.vurgun.skyfit.presentation.mobile.features.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitLogoComponent
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileForgotPasswordCodeScreen(navigator: Navigator) {
    var codeReady by remember { mutableStateOf(false) }
    var enteredCode by remember { mutableStateOf("") }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize().imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(36.dp))
            SkyFitLogoComponent()
            Spacer(Modifier.height(48.dp))
            MobileForgotPasswordCodeScreenTitleComponent("john@example.com")
            Spacer(Modifier.height(48.dp))
            SkyFitCodeInputComponent(onCodeComplete = { isComplete, code ->
                codeReady = isComplete
                enteredCode = code
            })
            Spacer(Modifier.weight(1f))
            MobileForgotPasswordCodeScreenActionsComponent(
                submitEnabled = codeReady,
                onClickSubmit = {
                    navigator.jumpAndStay(SkyFitNavigationRoute.ForgotPasswordReset)
                },
                onClickResend = {}
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun MobileForgotPasswordCodeScreenTitleComponent(email: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Şifremi Unuttum",
            style = SkyFitTypography.heading3
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "$email adresine onay kodu gönderdik",
            style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
        )
    }
}

@Composable
private fun SkyFitCodeInputComponent(
    digitCount: Int = 4,
    onCodeComplete: (Boolean, String) -> Unit
) {
    val digits = remember { mutableStateListOf(*Array(digitCount) { "" }) }
    val focusRequesters = List(digitCount) { FocusRequester() }
    var isReady by remember { mutableStateOf(false) }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        digits.forEachIndexed { index, value ->
            BasicTextField(
                value = value,
                onValueChange = { input ->
                    if (input.length <= 1) {
                        // Update the current digit
                        digits[index] = input

                        // Move focus to the next field if input is valid and not the last field
                        if (input.isNotEmpty() && index < digitCount - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        // Check if all fields are filled
                        isReady = digits.all { it.isNotEmpty() }
                        onCodeComplete(isReady, digits.joinToString(""))
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .focusRequester(focusRequesters[index])
                    .background(
                        color = if (isReady) Color.Green else Color.DarkGray,
                        shape = CircleShape
                    )
                    .clickable { focusRequesters[index].requestFocus() }
                    .padding(12.dp),
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (index == digitCount - 1) {
                            isReady = digits.all { it.isNotEmpty() }
                            onCodeComplete(isReady, digits.joinToString(""))
                        }
                    }
                ),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = "•",
                            color = Color.Gray,
                            style = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }

    // Focus handling for deletion (right to left)
    LaunchedEffect(digits) {
        focusRequesters.forEachIndexed { index, focusRequester ->
            focusRequester.freeFocus()
            if (digits[index].isEmpty() && index > 0) {
                focusRequesters[index - 1].requestFocus()
            }
        }
    }
}

@Composable
private fun MobileForgotPasswordCodeScreenActionsComponent(
    submitEnabled: Boolean = false,
    onClickSubmit: () -> Unit,
    onClickResend: () -> Unit
) {
    Column {
        SkyFitButtonComponent(
            Modifier.fillMaxWidth(), text = "Onayla",
            onClick = onClickSubmit,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest,
            isEnabled = submitEnabled
        )
        Spacer(Modifier.height(14.dp))
        Row {
            Text("Herhangi bir kod iletilmedi mi?", style = SkyFitTypography.bodyMediumRegular.copy(SkyFitColor.text.secondary))
            Spacer(Modifier.width(4.dp))
            Text(
                "Tekrar göder", style = SkyFitTypography.bodyMediumUnderlined.copy(SkyFitColor.text.secondary),
                modifier = Modifier.clickable(onClick = onClickResend)
            )
        }
        Spacer(Modifier.height(44.dp))
    }
}

