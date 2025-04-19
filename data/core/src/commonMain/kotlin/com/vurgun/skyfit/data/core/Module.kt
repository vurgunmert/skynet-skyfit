package com.vurgun.skyfit.data.core

import com.vurgun.skyfit.data.core.storage.DataStoreStorage
import com.vurgun.skyfit.data.core.storage.Storage
import com.vurgun.skyfit.data.core.storage.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataCoreModule = module {
    includes(platformModule)

    single(named("app-scope-coroutine")) {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    singleOf(::DataStoreStorage) {
        bind<Storage>()
    }

    single {
        TokenManager(
            storage = get(),
            scope = get(named("app-scope-coroutine"))
        )
    }
}

internal expect val platformModule: Module