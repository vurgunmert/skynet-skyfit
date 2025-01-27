package com.vurgun.skyfit.presentation.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.presentation.shared.components.ChatMessageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SkyFitConversationViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessageItem>>(emptyList())
    val messages: StateFlow<List<ChatMessageItem>> get() = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun sendMessage(userInput: String) {
        viewModelScope.launch {
            addMessage(ChatMessageItem(text = userInput, isUser = true))
            _isLoading.value = true

            try {
                addMessage(ChatMessageItem(text = "Hey Olivia. Can we get on a quick call?", isUser = false))
            } catch (e: Exception) {
                addMessage(ChatMessageItem(text = "An error occurred: ${e.message}", isUser = false))
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun addMessage(ChatMessageItem: ChatMessageItem) {
        _messages.value = _messages.value + ChatMessageItem
    }
}