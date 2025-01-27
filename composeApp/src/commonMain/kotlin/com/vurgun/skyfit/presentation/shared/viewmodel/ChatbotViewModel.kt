package com.vurgun.skyfit.presentation.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.domain.usecase.ChatbotQueryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatbotViewModel(private val useCase: ChatbotQueryUseCase) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun sendQuery(userInput: String) {
        viewModelScope.launch {
            // Add user message to the list
            addMessage(Message(text = userInput, isUser = true))

            // Set loading state
            _isLoading.value = true

            try {
                // Query the chatbot and add the bot's response
                val botResponse = useCase.queryChat(userInput)
                addMessage(Message(text = botResponse, isUser = false))
            } catch (e: Exception) {
                // Add error message
                addMessage(Message(text = "An error occurred: ${e.message}", isUser = false))
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun addMessage(message: Message) {
        _messages.value = _messages.value + message
    }
}


data class Message(
    val text: String,
    val isUser: Boolean
)
