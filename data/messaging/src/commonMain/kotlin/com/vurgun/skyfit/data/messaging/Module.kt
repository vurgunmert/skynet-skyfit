package com.vurgun.skyfit.data.messaging

import org.koin.dsl.module

val dataMessagingModule = module {

    single<ChatbotApiUseCase> { ChatbotRepository() }
}