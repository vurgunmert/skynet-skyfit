package com.vurgun.skyfit.feature.connect.chatbot

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.connect.domain.repository.ChatbotApiUseCase
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.feature.connect.component.ChatMessageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed class ChatBotAction {
    data object OnClickBack : ChatBotAction()
    data object OnClickStart : ChatBotAction()
    data object OnClickNew: ChatBotAction()
    data object OnClickPostureAnalysis: ChatBotAction()
    data class OnSubmitMessage(val text: String) : ChatBotAction()
}

sealed class ChatBotEffect {
    data object NavigateBack: ChatBotEffect()
    data object NavigatePostureAnalysis: ChatBotEffect()
    data object NavigateMessages: ChatBotEffect()
}

class ChatbotViewModel(
    private val useCase: ChatbotApiUseCase,
    private val storage: Storage
) : ScreenModel {

    private val _messages = MutableStateFlow<List<ChatMessageItem>>(emptyList())
    val messages: StateFlow<List<ChatMessageItem>> get() = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _effect = SingleSharedFlow<ChatBotEffect>()
    val effect: SharedFlow<ChatBotEffect> get() = _effect

    val onboardingCompleted: StateFlow<Boolean> get() = storage.getAsFlow(ChatBotOnboardingCompleted)
        .mapNotNull { it }
        .stateIn(screenModelScope, SharingStarted.Lazily, false)

    fun onAction(action: ChatBotAction) {
        when (action) {
            ChatBotAction.OnClickBack -> {
                _effect.emitIn(screenModelScope, ChatBotEffect.NavigateBack)
            }
            ChatBotAction.OnClickNew -> {
                _effect.emitIn(screenModelScope, ChatBotEffect.NavigateMessages)
            }
            ChatBotAction.OnClickPostureAnalysis -> {
                _effect.emitIn(screenModelScope, ChatBotEffect.NavigatePostureAnalysis)
            }
            ChatBotAction.OnClickStart -> finalizeOnboarding()
            is ChatBotAction.OnSubmitMessage -> sendQuery(action.text)
        }
    }

    fun sendQuery(userInput: String) {
        screenModelScope.launch {
            addMessage(ChatMessageItem(content = userInput, time = LocalDate.now().toString(), isUser = true))

            _isLoading.value = true

            try {
                val botResponse = useCase.submitChatQuery(userInput)
                addMessage(ChatMessageItem(content = botResponse.text.toString(), time = "14:23", isUser = false))

            } catch (e: Exception) {
                addMessage(ChatMessageItem(content = "An error occurred: ${e.message}", time = "14:23", isUser = false))

            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun addMessage(item: ChatMessageItem) {
        _messages.value += item
    }

    fun finalizeOnboarding() {
       screenModelScope.launch {
           storage.writeValue(ChatBotOnboardingCompleted, true)
       }
    }

    data object ChatBotOnboardingCompleted : Storage.Key.BooleanKey("chatbot_onboarding_completed", false)
}