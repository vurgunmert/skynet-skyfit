package com.vurgun.skyfit.feature_chatbot.di

import com.vurgun.skyfit.feature_chatbot.domain.ChatbotRepository
import com.vurgun.skyfit.feature_chatbot.domain.usecases.ChatbotApiUseCase
import com.vurgun.skyfit.feature_chatbot.ui.ChatbotViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chatBotModule = module {
    single<ChatbotApiUseCase> { ChatbotRepository() }
    viewModel { ChatbotViewModel(get()) }
}