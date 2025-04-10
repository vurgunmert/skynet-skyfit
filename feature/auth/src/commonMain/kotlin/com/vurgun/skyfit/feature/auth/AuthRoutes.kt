package com.vurgun.skyfit.feature.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.vurgun.skyfit.feature.auth.register.MobileCreatePasswordScreen
import com.vurgun.skyfit.feature.auth.forgotpassword.MobileForgotPasswordResetScreen
import com.vurgun.skyfit.feature.auth.forgotpassword.MobileForgotPasswordScreen
import com.vurgun.skyfit.feature.auth.forgotpassword.MobileForgotPasswordVerifyOTPScreen
import com.vurgun.skyfit.feature.auth.legal.MobilePrivacyPolicyScreen
import com.vurgun.skyfit.feature.auth.legal.MobileTermsAndConditionsScreen
import com.vurgun.skyfit.feature.auth.login.MobileLoginScreen
import com.vurgun.skyfit.feature.auth.login.MobileLoginVerifyOTPScreen
import com.vurgun.skyfit.feature.auth.maintenance.MaintenanceScreen
import com.vurgun.skyfit.feature.auth.splash.SplashScreen
import kotlinx.serialization.Serializable

sealed interface AuthRoute {

    @Serializable
    data object Splash : AuthRoute

    @Serializable
    data object Maintenance : AuthRoute

    @Serializable
    data object Login : AuthRoute

    @Serializable
    data object LoginOtpVerify : AuthRoute

    @Serializable
    data object CreatePassword : AuthRoute

    @Serializable
    data object ForgotPassword : AuthRoute

    @Serializable
    data object ForgotPasswordOtpVerify : AuthRoute

    @Serializable
    data object ResetPassword : AuthRoute

    @Serializable
    data object PrivacyPolicy : AuthRoute

    @Serializable
    data object TermsAndConditions : AuthRoute
}

fun NavGraphBuilder.authRoutes(
    navController: NavHostController,
    goToDashboard: () -> Unit,
    goToOnboarding: () -> Unit,
) {

    composable<AuthRoute.Splash> {
        SplashScreen(
            goToMaintenance = { navController.navigate(AuthRoute.Maintenance) },
            goToLogin = { navController.navigate(AuthRoute.Login) },
            goToDashboard = goToDashboard
        )
    }

    composable<AuthRoute.Maintenance> {
        MaintenanceScreen()
    }

    composable<AuthRoute.Login> {
        MobileLoginScreen(
            goToOtp = { navController.navigate(AuthRoute.LoginOtpVerify) },
            goToOnboarding = goToOnboarding,
            goToDashboard = goToDashboard,
            goToForgotPassword = { navController.navigate(AuthRoute.ForgotPassword) },
            goToPrivacyPolicy = { navController.navigate(AuthRoute.PrivacyPolicy) },
            goToTermsAndConditions = { navController.navigate(AuthRoute.TermsAndConditions) }
        )
    }

    composable<AuthRoute.LoginOtpVerify> {
        MobileLoginVerifyOTPScreen(
            goToCreatePassword = { navController.navigate(AuthRoute.CreatePassword) },
            goToDashboard = goToDashboard,
            goToOnboarding = goToOnboarding
        )
    }

    composable<AuthRoute.CreatePassword> {
        MobileCreatePasswordScreen(
            goToOnboarding = goToOnboarding,
            goToTermsAndConditions = { navController.navigate(AuthRoute.TermsAndConditions) },
            goToPrivacyPolicy = { navController.navigate(AuthRoute.PrivacyPolicy) },
        )
    }

    composable<AuthRoute.ForgotPassword> {
        MobileForgotPasswordScreen(
            goToBack = { navController.popBackStack() },
            goToVerify = { navController.navigate(AuthRoute.ForgotPasswordOtpVerify) }
        )
    }

    composable<AuthRoute.ForgotPasswordOtpVerify> {
        MobileForgotPasswordVerifyOTPScreen(
            goToReset = { navController.navigate(AuthRoute.ResetPassword) }
        )
    }

    composable<AuthRoute.ResetPassword> {
        MobileForgotPasswordResetScreen(
            goToLogin = { navController.navigate(AuthRoute.Login) },
            goToDashboard = goToDashboard
        )
    }

    composable<AuthRoute.PrivacyPolicy> {
        MobilePrivacyPolicyScreen(
            goToBack = { navController.navigate(AuthRoute.PrivacyPolicy) }
        )
    }

    composable<AuthRoute.TermsAndConditions> {
        MobileTermsAndConditionsScreen(
            goToBack = { navController.popBackStack() }
        )
    }
}