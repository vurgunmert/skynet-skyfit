package com.vurgun.skyfit.core.di

import com.vurgun.skyfit.core.domain.repository.AppStateRepository
import com.vurgun.skyfit.core.domain.repository.AppStateRepositoryImpl
import com.vurgun.skyfit.core.domain.repository.UserRepository
import com.vurgun.skyfit.core.domain.repository.UserRepositoryImpl
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.commonHttpClient
import com.vurgun.skyfit.core.ui.viewmodel.AppStateViewModel
import com.vurgun.skyfit.core.utils.DispatcherProvider
import org.koin.dsl.module

val appModule = module {
    //Network
    single { DispatcherProvider() }
    single { ApiClient(commonHttpClient) }

    single<AppStateRepository> { AppStateRepositoryImpl() }

    single<UserRepository> { UserRepositoryImpl(get()) }

    single { AppStateViewModel(get(), get(), get()) }
}
