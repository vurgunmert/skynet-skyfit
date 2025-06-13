package com.vurgun.skyfit.feature.connect.conversation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class UserConversationItem(
    val avatarUrl: String = "",
    val title: String = "Julia Heosten",
    val message: String = "It was amazing! Really helped me stretch out and relax. Have you tried yoga before?",
    val time: String = "13:55",
    val unreadCount: Int = 0
)

sealed interface ConversationsUiState {
    object Loading : ConversationsUiState
    data class Content(
        val previews: List<UserConversationItem> = emptyList()
    ) : ConversationsUiState
    data class Error(val message: String?) : ConversationsUiState
}

sealed interface ConversationsAction {
    data object OnClickBack : ConversationsAction
    data object OnClickConversation : ConversationsAction
}

sealed interface ConversationsEffect {
    data object NavigateBack : ConversationsEffect
    data object NavigateToChat : ConversationsEffect
}

class ConversationsViewModel : ScreenModel {

    private val _uiState = UiStateDelegate<ConversationsUiState>(ConversationsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<ConversationsEffect>()
    val effect: SharedFlow<ConversationsEffect> = _effect

    fun loadData() {
        screenModelScope.launch {
            runCatching {


                val previews = listOf(
                    UserConversationItem(),
                    UserConversationItem(
                        avatarUrl = "",
                        title = "John Doe",
                        message = "Let’s reschedule to tomorrow at 5?",
                        time = "11:20",
                        unreadCount = 2
                    )
                )
                _uiState.update(ConversationsUiState.Content(previews))
            }.onFailure {
                _uiState.update(ConversationsUiState.Error(it.message))
            }
        }
    }

    fun onAction(action: ConversationsAction) {
        when (action) {
            ConversationsAction.OnClickBack -> emitEffect(ConversationsEffect.NavigateBack)
            ConversationsAction.OnClickConversation -> emitEffect(ConversationsEffect.NavigateToChat)
        }
    }

    private fun emitEffect(effect: ConversationsEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}
