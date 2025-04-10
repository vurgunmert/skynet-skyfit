package com.vurgun.skyfit.data.network

import org.koin.dsl.module

val dataNetworkModule = module {
    single { DispatcherProvider() }
    single { ApiClient(commonHttpClient) }
}