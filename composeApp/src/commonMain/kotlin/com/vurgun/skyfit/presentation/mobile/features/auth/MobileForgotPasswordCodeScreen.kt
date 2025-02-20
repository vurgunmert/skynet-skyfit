package com.vurgun.skyfit.presentation.mobile.features.auth

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitLogoComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.utils.keyboardAsState
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileForgotPasswordCodeScreen(navigator: Navigator) {
    val viewModel = remember { MobileForgotPasswordCodeScreenViewModel() }
    val keyboardState by keyboardAsState()
    val otpState by viewModel.otpState.collectAsState()

    val animatedBottomOffset by animateDpAsState(
        targetValue = keyboardState.heightDp,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
    )

    SkyFitScaffold {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = animatedBottomOffset)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            SkyFitLogoComponent()
            MobileForgotPasswordCodeScreenTitleComponent("john@example.com")
            OtpInputRow(viewModel)

            MobileForgotPasswordCodeScreenActionsComponent(
                modifier = Modifier,
                submitEnabled = otpState.isCodeReady,
                onClickSubmit = {
                    viewModel.submitCode()
                    navigator.jumpAndStay(SkyFitNavigationRoute.ForgotPasswordReset)
                },
                onClickResend = {}
            )
        }
    }
}


@Composable
private fun MobileForgotPasswordCodeScreenActionsComponent(
    modifier: Modifier,
    submitEnabled: Boolean = false,
    onClickSubmit: () -> Unit,
    onClickResend: () -> Unit
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SkyFitButtonComponent(
            modifier = modifier.fillMaxWidth(),
            text = "Onayla",
            onClick = onClickSubmit,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest,
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
    }
}

@Composable
fun OtpInputRow(viewModel: MobileForgotPasswordCodeScreenViewModel) {
    val otpState by viewModel.otpState.collectAsState()
    val focusRequesters = List(4) { FocusRequester() }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        otpState.otp.forEachIndexed { index, value ->
            CommonOtpTextField(
                otp = value,
                onOtpChanged = { viewModel.updateOtp(index, it) },
                onDelete = { viewModel.deleteOtp(index - 1) },
                focusRequester = focusRequesters[index],
                nextFocusRequester = focusRequesters.getOrNull(index + 1),
                previousFocusRequester = focusRequesters.getOrNull(index - 1)
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}


@Composable
fun CommonOtpTextField(
    otp: MutableState<String>,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester? = null // Next field to focus on
) {
    val maxChars = 1

    OutlinedTextField(
        value = otp.value,
        singleLine = true,
        onValueChange = {
            if (it.length <= maxChars) {
                otp.value = it
                if (it.length == maxChars) {
                    nextFocusRequester?.requestFocus() // Auto move to next field
                }
            }
        },
        placeholder = { Text("•", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(50),
        textStyle = SkyFitTypography.bodyLargeMedium.copy(textAlign = TextAlign.Center),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = SkyFitColor.text.default,
            cursorColor = SkyFitColor.text.default,
            focusedBorderColor = SkyFitColor.border.secondaryButton,
            unfocusedBorderColor = SkyFitColor.border.secondaryButtonDisabled,
            backgroundColor = SkyFitColor.specialty.secondaryButtonRest
        ),
        modifier = Modifier
            .size(72.dp, 48.dp)
            .padding(horizontal = 4.dp)
            .focusRequester(focusRequester)
    )
}


@Composable
fun CommonOtpTextField(
    otp: String,
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
                    nextFocusRequester?.requestFocus() // Move focus to next
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
        placeholder = { Text("•", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center) },
        modifier = Modifier
            .size(72.dp, 48.dp)
            .focusRequester(focusRequester)
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Backspace && keyEvent.type == KeyEventType.KeyUp) {
                    if (otp.isEmpty()) {
                        previousFocusRequester?.requestFocus() // Move focus back
                        onDelete()
                    } else {
                        onOtpChanged("") // Clear the field
                    }
                    true
                } else {
                    false
                }
            }
    )
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