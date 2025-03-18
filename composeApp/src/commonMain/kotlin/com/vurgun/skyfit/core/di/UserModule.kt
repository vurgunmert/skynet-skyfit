package com.vurgun.skyfit.core.di

import com.vurgun.skyfit.core.data.repositories.UserRepositoryImpl
import com.vurgun.skyfit.core.data.service.UserApiService
import com.vurgun.skyfit.core.domain.repository.UserRepository
import org.koin.dsl.module

val userModule = module {
    single<UserApiService> { UserApiService(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
}