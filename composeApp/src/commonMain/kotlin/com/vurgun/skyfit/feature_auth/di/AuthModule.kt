package com.vurgun.skyfit.feature_auth.di

import androidx.lifecycle.SavedStateHandle
import com.vurgun.skyfit.feature_auth.data.repository.AuthRepositoryImpl
import com.vurgun.skyfit.feature_auth.data.service.AuthApiService
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import com.vurgun.skyfit.feature_auth.ui.viewmodel.PasswordCreateViewModel
import com.vurgun.skyfit.feature_auth.ui.viewmodel.ForgotPasswordVerifyOTPViewModel
import com.vurgun.skyfit.feature_auth.ui.viewmodel.ForgotPasswordViewModel
import com.vurgun.skyfit.feature_auth.ui.viewmodel.LoginOTPVerificationViewModel
import com.vurgun.skyfit.feature_auth.ui.viewmodel.LoginViewModel
import com.vurgun.skyfit.feature_auth.ui.viewmodel.PasswordResetViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthApiService(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }

    factory { LoginViewModel(get()) }
    factory { ForgotPasswordViewModel(get()) }
    factory { PasswordCreateViewModel(get()) }
    factory { PasswordResetViewModel(get()) }
    factory { LoginOTPVerificationViewModel(get(), get()) }
    factory { ForgotPasswordVerifyOTPViewModel(get(), get()) }
}
