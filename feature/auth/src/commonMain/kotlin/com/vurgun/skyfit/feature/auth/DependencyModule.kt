package com.vurgun.skyfit.feature.auth

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.auth.forgotpassword.ForgotPasswordVerifyOTPViewModel
import com.vurgun.skyfit.feature.auth.forgotpassword.ForgotPasswordViewModel
import com.vurgun.skyfit.feature.auth.forgotpassword.PasswordResetViewModel
import com.vurgun.skyfit.feature.auth.login.LoginOTPVerificationViewModel
import com.vurgun.skyfit.feature.auth.login.LoginViewModel
import com.vurgun.skyfit.feature.auth.register.PasswordCreateViewModel
import org.koin.dsl.module

val featureAuthModule = module {
    includes(dataCoreModule)

    factory { LoginViewModel(get(), get()) }
    factory { ForgotPasswordViewModel(get()) }
    factory { PasswordCreateViewModel(get()) }
    factory { PasswordResetViewModel(get()) }
    factory { LoginOTPVerificationViewModel(get(), get(), get()) }
    factory { ForgotPasswordVerifyOTPViewModel(get(), get()) }
}