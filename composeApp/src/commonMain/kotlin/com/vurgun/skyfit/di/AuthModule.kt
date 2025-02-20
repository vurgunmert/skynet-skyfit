package com.vurgun.skyfit.di

import com.vurgun.skyfit.data.repository.AuthRepositoryImpl
import com.vurgun.skyfit.domain.repository.AuthRepository
import com.vurgun.skyfit.domain.usecases.auth.AuthenticatePhoneNumberUseCase
import com.vurgun.skyfit.domain.util.DispatcherProvider
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileLoginViewModel
import org.koin.dsl.module

val authModule = module {
    single { DispatcherProvider() } // Platform-specific DispatcherProvider
    single<AuthRepository> { AuthRepositoryImpl() }
    factory { AuthenticatePhoneNumberUseCase(get(), get()) }
    factory { MobileLoginViewModel(get()) }
}
