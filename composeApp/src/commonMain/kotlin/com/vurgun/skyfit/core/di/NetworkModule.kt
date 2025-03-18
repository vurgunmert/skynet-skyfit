package com.vurgun.skyfit.core.di

import com.vurgun.skyfit.core.network.client.ApiClient
import com.vurgun.skyfit.core.network.client.commonHttpClient
import com.vurgun.skyfit.core.utils.DispatcherProvider
import org.koin.dsl.module

val networkModule = module {
    single { DispatcherProvider() }
    single { ApiClient(commonHttpClient) }
}