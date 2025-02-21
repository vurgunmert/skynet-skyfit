package com.vurgun.skyfit.di

import com.vurgun.skyfit.data.repository.AuthRepositoryImpl
import com.vurgun.skyfit.domain.repository.AuthRepository
import com.vurgun.skyfit.domain.usecases.auth.AuthenticatePhoneNumberUseCase
import com.vurgun.skyfit.domain.usecases.auth.RegisterUserUseCase
import com.vurgun.skyfit.domain.usecases.auth.ResendOTPUseCase
import com.vurgun.skyfit.domain.usecases.auth.VerifyOTPUseCase
import com.vurgun.skyfit.domain.util.DispatcherProvider
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileLoginViewModel
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileOTPVerificationViewModel
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileRegisterViewModel
import org.koin.dsl.module

val authModule = module {
    single { DispatcherProvider() } // Platform-specific DispatcherProvider
    single<AuthRepository> { AuthRepositoryImpl() }

    factory { AuthenticatePhoneNumberUseCase(get(), get()) }
    factory { RegisterUserUseCase(get(), get()) }
    factory { VerifyOTPUseCase(get(), get()) }
    factory { ResendOTPUseCase(get(), get()) }

    factory { MobileLoginViewModel(get()) }
    factory { MobileRegisterViewModel(get()) }
    factory { MobileOTPVerificationViewModel(get(), get()) }
}
