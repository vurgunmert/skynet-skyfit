package com.vurgun.skyfit.presentation.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.domain.usecase.ChatbotQueryUseCase
import com.vurgun.skyfit.presentation.shared.components.ChatMessageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatbotViewModel(private val useCase: ChatbotQueryUseCase) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessageItem>>(emptyList())
    val messages: StateFlow<List<ChatMessageItem>> get() = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun sendQuery(userInput: String) {
        viewModelScope.launch {
            // Add user message to the list
            addMessage(ChatMessageItem(content = userInput, time = "14:23", isUser = true))
            addMessage(ChatMessageItem(content = "Hey Olivia. Can we get on a quick call?", time = "14:23", isUser = false))

            // Set loading state
            _isLoading.value = true

            try {
                // Query the chatbot and add the bot's response
                val botResponse = useCase.queryChat(userInput)
                addMessage(ChatMessageItem(content = botResponse, time = "14:23", isUser = false))
            } catch (e: Exception) {
                // Add error message
                addMessage(ChatMessageItem(content = "An error occurred: ${e.message}", time = "14:23", isUser = false))
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun addMessage(ChatMessageItem: ChatMessageItem) {
        _messages.value += ChatMessageItem
    }
}