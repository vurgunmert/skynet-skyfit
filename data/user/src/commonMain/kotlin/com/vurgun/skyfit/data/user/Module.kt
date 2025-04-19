package com.vurgun.skyfit.data.user

import com.vurgun.skyfit.data.core.dataCoreModule
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.data.user.repository.UserManagerImpl
import com.vurgun.skyfit.data.user.repository.UserRepository
import com.vurgun.skyfit.data.user.repository.UserRepositoryImpl
import com.vurgun.skyfit.data.user.service.UserApiService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataUserModule = module {
    includes(dataCoreModule)

    single<UserApiService> { UserApiService(get()) }

    single<UserRepository> {
        UserRepositoryImpl(
            apiService = get(),
            dispatchers = get(),
            storage = get(),
            tokenManager = get()
        )
    }

    single<UserManager> {
        UserManagerImpl(
            appScope = get(named("app-scope-coroutine")),
            storage = get(),
            repository = get(),
            tokenManager = get()
        )
    }
}
