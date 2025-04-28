package com.vurgun.skyfit.feature.messaging.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.data.messaging.ChatbotApiUseCase
import com.vurgun.skyfit.feature.messaging.component.ChatMessageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ChatbotViewModel(private val useCase: ChatbotApiUseCase) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessageItem>>(emptyList())
    val messages: StateFlow<List<ChatMessageItem>> get() = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isIntroEnabled = MutableStateFlow(true)
    val isIntroEnabled: StateFlow<Boolean> get() = _isIntroEnabled

    fun sendQuery(userInput: String) {
        viewModelScope.launch {
            addMessage(ChatMessageItem(content = userInput, time = LocalDate.now().toString(), isUser = true))

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

    fun completeIntro() {
        _isIntroEnabled.value = false
    }
}