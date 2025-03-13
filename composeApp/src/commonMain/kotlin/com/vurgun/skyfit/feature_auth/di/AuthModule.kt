package com.vurgun.skyfit.feature_auth.di

import androidx.lifecycle.SavedStateHandle
import com.vurgun.skyfit.feature_auth.data.AuthRepositoryImpl
import com.vurgun.skyfit.feature_auth.data.service.AuthApiService
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import com.vurgun.skyfit.feature_auth.ui.viewmodel.CreatePasswordScreenViewModel
import com.vurgun.skyfit.feature_auth.ui.viewmodel.LoginOTPVerificationViewModel
import com.vurgun.skyfit.feature_auth.ui.viewmodel.MobileLoginViewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthApiService(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }

    factory { MobileLoginViewModel(get()) }
    factory { (handle: SavedStateHandle) -> CreatePasswordScreenViewModel(get(), handle) }
    factory { LoginOTPVerificationViewModel(get(), get()) }
}
