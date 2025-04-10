package com.vurgun.skyfit.data.user

import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.data.user.repository.UserManagerImpl
import com.vurgun.skyfit.data.user.repository.UserRepositoryImpl
import com.vurgun.skyfit.data.user.service.UserApiService
import org.koin.dsl.module

val dataUserModule = module {
    single<UserApiService> { UserApiService(get()) }

    single<UserRepository> {
        UserRepositoryImpl(
            apiService = get(),
            settingsStore = get(),
            dispatchers = get()
        )
    }

    single<UserManager> { UserManagerImpl(get()) }

}
