package com.vurgun.skyfit.presentation.shared.features.auth

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox

@Composable
fun AuthLogoComponent() {
    TodoBox("AuthLogoComponent", Modifier.size(108.dp, 118.dp))
}

@Composable
fun AuthTitleGroupComponent() {
    TodoBox("AuthTitleGroupComponent", Modifier.size(382.dp, 75.dp))
}

@Composable
fun AuthActionGroupComponent() {
    TodoBox("AuthActionGroupComponent", Modifier.size(382.dp, 111.dp))
}
@Composable
fun AuthActionInfoGroupComponent() {
    TodoBox("AuthActionInfoGroupComponent", Modifier.size(382.dp, 111.dp))
}

@Composable
fun AuthLoginInputGroupComponent() {
    TodoBox("LoginInputGroupComponent", Modifier.size(382.dp, 396.dp))
}

@Composable
fun AuthRegisterInputGroupComponent() {
    TodoBox("RegisterInputGroupComponent", Modifier.size(382.dp, 256.dp))
}

@Composable
fun AuthRegisterActionGroupComponent() {
    TodoBox("RegisterActionGroupComponent", Modifier.size(382.dp, 231.dp))
}

@Composable
fun AuthForgotPasswordInputGroupComponent() {
    TodoBox("ForgotPasswordInputGroupComponent", Modifier.size(382.dp, 52.dp))
}

@Composable
fun AuthValidateCodeInputGroupComponent() {
    TodoBox("ValidateCodeInputGroupComponent", Modifier.size(430.dp, 48.dp))
}

@Composable
fun AuthResetPasswordInputGroupComponent() {
    TodoBox("ResetPasswordInputGroupComponent", Modifier.size(382.dp, 120.dp))
}