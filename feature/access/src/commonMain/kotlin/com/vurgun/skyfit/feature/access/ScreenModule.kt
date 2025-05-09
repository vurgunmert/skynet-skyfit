package com.vurgun.skyfit.feature.access

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.access.forgotpassword.ForgotPasswordResetScreen
import com.vurgun.skyfit.feature.access.forgotpassword.ForgotPasswordScreen
import com.vurgun.skyfit.feature.access.forgotpassword.ForgotPasswordVerifyOTPScreen
import com.vurgun.skyfit.feature.access.legal.PrivacyPolicyScreen
import com.vurgun.skyfit.feature.access.legal.TermsAndConditionsScreen
import com.vurgun.skyfit.feature.access.login.AuthFlowScreen
import com.vurgun.skyfit.feature.access.login.LoginScreen
import com.vurgun.skyfit.feature.access.login.LoginVerifyOTPScreen
import com.vurgun.skyfit.feature.access.maintenance.MaintenanceScreen
import com.vurgun.skyfit.feature.access.splash.SplashScreen
import kotlinx.serialization.Serializable

val accessScreenModule = screenModule {
    register<SharedScreen.Splash> { SplashScreen() }
    register<SharedScreen.Maintenance> { MaintenanceScreen() }
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