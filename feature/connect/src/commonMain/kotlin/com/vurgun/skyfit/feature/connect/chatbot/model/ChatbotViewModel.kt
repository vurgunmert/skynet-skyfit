package com.vurgun.skyfit.feature.connect.chatbot.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.storage.ChatBotSessionStorage
import com.vurgun.skyfit.core.data.utility.UiEffectDelegate
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotMessage
import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotRepository
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatbotUiEffect.NavigateToChat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class ChatbotUiState {
    data object Loading : ChatbotUiState()
    data class Error(val message: String?) : ChatbotUiState()
    data class Content(
        val onboardingCompleted: Boolean = false,
        val historyMessages: List<ChatbotMessage> = emptyList()
    ) : ChatbotUiState() {
        val isHistoryEmpty: Boolean = historyMessages.isEmpty()
    }
}

sealed class ChatbotUiAction {
    data object OnClickBack : ChatbotUiAction()
    data object OnClickOnboardingStart : ChatbotUiAction()
    data object OnClickPostureAnalysis : ChatbotUiAction()
    data object OnClickMealReport : ChatbotUiAction()
    data class OnClickNewSession(val query: String? = null) : ChatbotUiAction()
    data class OnClickSession(val sessionId: String) : ChatbotUiAction()
}

sealed class ChatbotUiEffect {
    data object Dismiss : ChatbotUiEffect()
    data object NavigateToPostureAnalysis : ChatbotUiEffect()
    data object NavigateToMealReport : ChatbotUiEffect()
    data class NavigateToChat(
        val query: String? = null,
        val sessionId: String? = null
    ) : ChatbotUiEffect()
}

class ChatbotViewModel(
    private val repository: ChatbotRepository,
    private val storage: ChatBotSessionStorage
) : ScreenModel {

    private val _uiState = UiStateDelegate<ChatbotUiState>(ChatbotUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = UiEffectDelegate<ChatbotUiEffect>()
    val effect = _effect.asSharedFlow()

    fun onAction(action: ChatbotUiAction) {
        when (action) {
            ChatbotUiAction.OnClickBack -> {
                _effect.emitIn(screenModelScope, ChatbotUiEffect.Dismiss)
            }

            is ChatbotUiAction.OnClickNewSession -> {
                _effect.emitIn(screenModelScope, NavigateToChat(action.query))
            }

            ChatbotUiAction.OnClickPostureAnalysis -> {
                _effect.emitIn(screenModelScope, ChatbotUiEffect.NavigateToPostureAnalysis)
            }

            ChatbotUiAction.OnClickOnboardingStart -> {
                markOnboardingCompleted()
            }

            is ChatbotUiAction.OnClickSession -> {
                _effect.emitIn(screenModelScope, NavigateToChat(sessionId = action.sessionId))
            }

            ChatbotUiAction.OnClickMealReport -> {
                _effect.emitIn(screenModelScope, ChatbotUiEffect.NavigateToMealReport)
            }
        }
    }

    fun loadData(windowSize: WindowSize) {
        val takeCount = if (windowSize == WindowSize.COMPACT) 3 else 10
        _uiState.update(ChatbotUiState.Loading)

        screenModelScope.launch {

            storage.onboardingCompleted.collect { onboardingCompleted ->

                if (onboardingCompleted) {
                    runCatching {
                        val historyMessages = repository.getLastSessions(takeCount).values
                            .mapNotNull { it.firstOrNull() }

                        _uiState.update(
                            ChatbotUiState.Content(
                                onboardingCompleted = storage.onboardingCompleted.first(),
                                historyMessages = historyMessages
                            )
                        )
                    }.onFailure { error ->
                        _uiState.update(ChatbotUiState.Error(error.message))
                    }
                } else {
                    _uiState.update(ChatbotUiState.Content(onboardingCompleted = false))
                }
            }
        }
    }

    private fun markOnboardingCompleted() {
        screenModelScope.launch {
            storage.markOnboardingCompleted()
        }
    }
}