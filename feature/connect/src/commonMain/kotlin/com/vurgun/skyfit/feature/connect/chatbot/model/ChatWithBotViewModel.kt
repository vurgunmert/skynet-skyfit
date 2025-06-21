package com.vurgun.skyfit.feature.connect.chatbot.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotApiUseCase
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.formatToHHMMTime
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.feature.connect.component.ChatMessageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

sealed class ChatWithBotAction {
    data object OnClickBack : ChatWithBotAction()
    data class OnSubmitMessage(val text: String) : ChatWithBotAction()
}

sealed class ChatWithBotEffect {
    data object NavigateBack : ChatWithBotEffect()
}

class ChatWithBotViewModel(
    private val useCase: ChatbotApiUseCase
) : ScreenModel {

    private val _messages = MutableStateFlow<List<ChatMessageItem>>(emptyList())
    val messages: StateFlow<List<ChatMessageItem>> get() = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _effect = SingleSharedFlow<ChatWithBotEffect>()
    val effect: SharedFlow<ChatWithBotEffect> get() = _effect

    fun onAction(action: ChatWithBotAction) {
        when (action) {
            ChatWithBotAction.OnClickBack -> {
                _effect.emitIn(screenModelScope, ChatWithBotEffect.NavigateBack)
            }

            is ChatWithBotAction.OnSubmitMessage -> sendQuery(action.text)
        }
    }

    fun loadData(chatHistoryId: String?, presetQuery: String?) {
        // TODO: chatHistory
        if (presetQuery != null) {
            sendQuery(presetQuery)
        }
    }

    private fun sendQuery(userInput: String) {
        screenModelScope.launch {
            val requestStartTime = LocalDateTime.now().formatToHHMMTime()
            addMessage(ChatMessageItem(content = userInput, time = requestStartTime, isUser = true))

            _isLoading.value = true

            try {
                val botResponse = useCase.submitChatQuery(userInput)
                val requestEndTime = LocalDateTime.now().formatToHHMMTime()
                addMessage(ChatMessageItem(content = botResponse.text.toString(), time = requestEndTime, isUser = false))

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
}