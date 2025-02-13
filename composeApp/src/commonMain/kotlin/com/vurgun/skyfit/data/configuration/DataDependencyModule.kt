package com.vurgun.skyfit.data.configuration

import com.vurgun.skyfit.data.network.repositories.ChatbotRepository
import com.vurgun.skyfit.data.network.repositories.RemoteDataRepository
import com.vurgun.skyfit.domain.usecase.ChatbotApiUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataDependencyModule = module {
    singleOf(::RemoteDataRepository)
    single<ChatbotApiUseCase> { ChatbotRepository() }
}