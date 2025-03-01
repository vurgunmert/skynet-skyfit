package com.vurgun.skyfit.feature_auth.di

import com.vurgun.skyfit.core.network.commonHttpClient
import com.vurgun.skyfit.feature_auth.data.AuthRepositoryImpl
import com.vurgun.skyfit.feature_auth.data.service.AuthApiService
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import com.vurgun.skyfit.feature_auth.domain.usecases.AuthLoginUseCase
import com.vurgun.skyfit.feature_auth.domain.usecases.AuthRegisterUseCase
import com.vurgun.skyfit.feature_auth.domain.usecases.AuthRequestOTPCodeUseCase
import com.vurgun.skyfit.feature_auth.ui.viewmodel.MobileLoginViewModel
import com.vurgun.skyfit.feature_auth.ui.viewmodel.MobileOTPVerificationViewModel
import com.vurgun.skyfit.feature_auth.ui.viewmodel.MobileRegisterViewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthApiService(commonHttpClient) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    factory { AuthRegisterUseCase(get(), get()) }
    factory { AuthLoginUseCase(get(), get()) }
    factory { AuthRequestOTPCodeUseCase(get(), get()) }

    factory { MobileLoginViewModel(get(), get()) }
    factory { MobileRegisterViewModel(get()) }
    factory { MobileOTPVerificationViewModel(get(), get()) }
}
