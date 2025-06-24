package com.vurgun.skyfit.feature.connect.chatbot.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.randomUUID
import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotMessage
import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ChatWithBotAction {
    data object OnClickBack : ChatWithBotAction()
    data class OnSendQuery(val query: String) : ChatWithBotAction()
}

sealed class ChatWithBotEffect {
    data object NavigateBack : ChatWithBotEffect()
}

class ChatWithBotViewModel(
    private val repository: ChatbotRepository
) : ScreenModel {

    private val _messages = MutableStateFlow<List<ChatbotMessage>>(emptyList())
    val messages: StateFlow<List<ChatbotMessage>> get() = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _effect = SingleSharedFlow<ChatWithBotEffect>()
    val effect: SharedFlow<ChatWithBotEffect> get() = _effect

    private var activeSessionId: String = randomUUID()

    fun onAction(action: ChatWithBotAction) {
        when (action) {
            ChatWithBotAction.OnClickBack -> {
                _effect.emitIn(screenModelScope, ChatWithBotEffect.NavigateBack)
            }

            is ChatWithBotAction.OnSendQuery -> sendQuery(action.query)
        }
    }

    fun loadData(query: String? = null, sessionId: String? = null) {
        if (query != null) {
            sendQuery(query)
        } else if (sessionId != null) {
            loadSession(sessionId)
        } else {
            //TODO: Session Error
        }
    }

    private fun loadSession(sessionId: String) {
        activeSessionId = sessionId

        screenModelScope.launch {
            _isLoading.value = true
            try {
                _messages.value = repository.getSessionHistory(sessionId)
            } catch (error: Exception) {
                print(error.message)
            } finally {
                _isLoading.value = false
            }
            _messages.value = repository.getSessionHistory(sessionId)
        }
    }

    private fun sendQuery(query: String) {
        val message = ChatbotMessage.user(query, activeSessionId)
        addMessage(message)

        screenModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.sendQuery(message)
                addMessage(response)
            } catch (e: Exception) {
                e.printStackTrace()
                addMessage(
                    ChatbotMessage.bot(
                        "Mesajınızı anlayamadım. Lütfen düzenleyip tekrar göndermeyi deneyin.",
                        sessionId = activeSessionId,
                        isError = true
                    )
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun addMessage(item: ChatbotMessage) {
        _messages.value += item
    }
}