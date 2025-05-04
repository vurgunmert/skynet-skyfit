package com.vurgun.skyfit.core.network

import org.koin.dsl.module

val dataNetworkModule = module {
    single { DispatcherProvider() }
    single { ApiClient(commonHttpClient) }
    single { RemoteImageDataSource(commonHttpClient) }
}