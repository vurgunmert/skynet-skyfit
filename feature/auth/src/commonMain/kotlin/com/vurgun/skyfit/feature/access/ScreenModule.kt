package com.vurgun.skyfit.feature.access

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.access.forgotpassword.ForgotPasswordResetScreen
import com.vurgun.skyfit.feature.access.forgotpassword.ForgotPasswordScreen
import com.vurgun.skyfit.feature.access.forgotpassword.ForgotPasswordVerifyOTPScreen
import com.vurgun.skyfit.feature.access.legal.PrivacyPolicyScreen
import com.vurgun.skyfit.feature.access.legal.TermsAndConditionsScreen
import com.vurgun.skyfit.feature.access.login.AuthorizationScreen
import com.vurgun.skyfit.feature.access.login.LoginScreen
import com.vurgun.skyfit.feature.access.login.LoginVerifyOTPScreen
import com.vurgun.skyfit.feature.access.maintenance.MaintenanceScreen
import com.vurgun.skyfit.feature.access.splash.SplashScreen
import kotlinx.serialization.Serializable

val screenAuthModule = screenModule {
    register<SharedScreen.Splash> { SplashScreen() }
    register<SharedScreen.Maintenance> { MaintenanceScreen() }
    register<SharedScreen.Authorization> { AuthorizationScreen() }
}

internal val authScreenFlowModule = screenModule {
    register<AuthScreen.Login> { LoginScreen() }
    register<AuthScreen.LoginVerifyOTP> { LoginVerifyOTPScreen() }
    register<AuthScreen.ForgotPassword> { ForgotPasswordScreen() }
    register<AuthScreen.ForgotPasswordVerifyOTP> { ForgotPasswordVerifyOTPScreen() }
    register<AuthScreen.ResetPassword> { ForgotPasswordResetScreen() }
    register<AuthScreen.PrivacyPolicy> { PrivacyPolicyScreen() }
    register<AuthScreen.TermsAndConditions> { TermsAndConditionsScreen() }
}

@Serializable
internal sealed class AuthScreen : ScreenProvider {
    data object Login : AuthScreen()
    data object LoginVerifyOTP : AuthScreen()
    data object ForgotPassword : AuthScreen()
    data object ForgotPasswordVerifyOTP : AuthScreen()
    data object ResetPassword : AuthScreen()
    data object PrivacyPolicy : AuthScreen()
    data object TermsAndConditions : AuthScreen()
}