package com.vurgun.skyfit.feature.auth

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.auth.forgotpassword.ForgotPasswordResetScreen
import com.vurgun.skyfit.feature.auth.forgotpassword.ForgotPasswordScreen
import com.vurgun.skyfit.feature.auth.forgotpassword.ForgotPasswordVerifyOTPScreen
import com.vurgun.skyfit.feature.auth.legal.PrivacyPolicyScreen
import com.vurgun.skyfit.feature.auth.legal.TermsAndConditionsScreen
import com.vurgun.skyfit.feature.auth.login.AuthFlowScreen
import com.vurgun.skyfit.feature.auth.login.LoginScreen
import com.vurgun.skyfit.feature.auth.login.LoginVerifyOTPScreen
import kotlinx.serialization.Serializable

val authScreenModule = screenModule {
    register<SharedScreen.Authorization> { AuthFlowScreen() }
}

internal val authScreenFlowModule = screenModule {
    register<AuthFlow.Login> { LoginScreen() }
    register<AuthFlow.LoginVerifyOTP> { LoginVerifyOTPScreen() }
    register<AuthFlow.ForgotPassword> { ForgotPasswordScreen() }
    register<AuthFlow.ForgotPasswordVerifyOTP> { ForgotPasswordVerifyOTPScreen() }
    register<AuthFlow.ResetPassword> { ForgotPasswordResetScreen() }
    register<AuthFlow.PrivacyPolicy> { PrivacyPolicyScreen() }
    register<AuthFlow.TermsAndConditions> { TermsAndConditionsScreen() }
}

@Serializable
internal sealed class AuthFlow : ScreenProvider {
    data object Login : AuthFlow()
    data object LoginVerifyOTP : AuthFlow()
    data object ForgotPassword : AuthFlow()
    data object ForgotPasswordVerifyOTP : AuthFlow()
    data object ResetPassword : AuthFlow()
    data object PrivacyPolicy : AuthFlow()
    data object TermsAndConditions : AuthFlow()
}