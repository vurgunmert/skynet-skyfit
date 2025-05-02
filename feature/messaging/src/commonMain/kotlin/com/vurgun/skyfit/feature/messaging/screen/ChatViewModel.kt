package com.vurgun.skyfit.feature.messaging.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.feature.messaging.component.ChatMessageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ScreenModel {

    private val _messages = MutableStateFlow<List<ChatMessageItem>>(emptyList())
    val messages: StateFlow<List<ChatMessageItem>> get() = _messages

    fun sendMessage(userInput: String) {
        screenModelScope.launch {
            addMessage(ChatMessageItem(content = userInput, time = "14:23", isUser = true))
            addMessage(ChatMessageItem(content = "Hey Olivia. Can we get on a quick call?", time = "14:23", isUser = false))
        }
    }

    private fun addMessage(ChatMessageItem: ChatMessageItem) {
        _messages.value += ChatMessageItem
    }
}