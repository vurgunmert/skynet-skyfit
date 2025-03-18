package com.vurgun.skyfit.core.di

import com.vurgun.skyfit.core.data.repositories.AppConfigRepositoryImpl
import com.vurgun.skyfit.core.domain.repository.AppConfigRepository
import org.koin.dsl.module

val configModule = module {
    single<AppConfigRepository> { AppConfigRepositoryImpl() }
}