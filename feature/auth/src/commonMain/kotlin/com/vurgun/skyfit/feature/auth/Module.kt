package com.vurgun.skyfit.feature.auth

import com.vurgun.skyfit.data.auth.dataAuthModule
import com.vurgun.skyfit.feature.auth.forgotpassword.ForgotPasswordVerifyOTPViewModel
import com.vurgun.skyfit.feature.auth.forgotpassword.ForgotPasswordViewModel
import com.vurgun.skyfit.feature.auth.register.PasswordCreateViewModel
import com.vurgun.skyfit.feature.auth.forgotpassword.PasswordResetViewModel
import com.vurgun.skyfit.feature.auth.login.LoginOTPVerificationViewModel
import com.vurgun.skyfit.feature.auth.login.LoginViewModel
import com.vurgun.skyfit.feature.auth.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureAuthModule = module {
    includes(dataAuthModule)

    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { PasswordCreateViewModel(get()) }
    viewModel { PasswordResetViewModel(get()) }
    viewModel { LoginOTPVerificationViewModel(get(), get(), get()) }
    viewModel { ForgotPasswordVerifyOTPViewModel(get(), get()) }
}