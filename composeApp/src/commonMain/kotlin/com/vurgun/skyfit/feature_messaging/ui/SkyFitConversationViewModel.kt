package com.vurgun.skyfit.feature_messaging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SkyFitConversationViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessageItem>>(emptyList())
    val messages: StateFlow<List<ChatMessageItem>> get() = _messages

    fun sendMessage(userInput: String) {
        viewModelScope.launch {
            addMessage(ChatMessageItem(content = userInput, time = "14:23", isUser = true))
            addMessage(ChatMessageItem(content = "Hey Olivia. Can we get on a quick call?", time = "14:23", isUser = false))
        }
    }

    private fun addMessage(ChatMessageItem: ChatMessageItem) {
        _messages.value += ChatMessageItem
    }
}