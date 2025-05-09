package com.vurgun.skyfit.feature.access

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.access.domain.SplashUseCase
import com.vurgun.skyfit.feature.access.forgotpassword.ForgotPasswordVerifyOTPViewModel
import com.vurgun.skyfit.feature.access.forgotpassword.ForgotPasswordViewModel
import com.vurgun.skyfit.feature.access.forgotpassword.PasswordResetViewModel
import com.vurgun.skyfit.feature.access.login.LoginOTPVerificationViewModel
import com.vurgun.skyfit.feature.access.login.LoginViewModel
import com.vurgun.skyfit.feature.access.register.PasswordCreateViewModel
import com.vurgun.skyfit.feature.access.splash.SplashViewModel
import org.koin.dsl.module

val featureAccessModule = module {
    includes(dataCoreModule)

    factory { LoginViewModel(get(), get()) }
    factory { ForgotPasswordViewModel(get()) }
    factory { PasswordCreateViewModel(get()) }
    factory { PasswordResetViewModel(get()) }
    factory { LoginOTPVerificationViewModel(get(), get(), get()) }
    factory { ForgotPasswordVerifyOTPViewModel(get(), get()) }

    factory { SplashUseCase(get(), get()) }
    factory { SplashViewModel(get()) }
}