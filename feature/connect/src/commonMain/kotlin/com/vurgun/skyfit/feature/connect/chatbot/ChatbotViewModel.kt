package com.vurgun.skyfit.feature.connect.chatbot

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class ChatBotAction {
    data object OnClickBack : ChatBotAction()
    data object OnClickStart : ChatBotAction()
    data object OnClickNew : ChatBotAction()
    data class OnClickHistoryItem(val query: String) : ChatBotAction()

    data object OnClickPostureAnalysis : ChatBotAction()
}

sealed class ChatBotEffect {
    data object NavigateBack : ChatBotEffect()
    data object NavigatePostureAnalysis : ChatBotEffect()
    data class NavigateChat(
        val presetQuery: String? = null
    ) : ChatBotEffect()
}

class ChatbotViewModel(
    private val storage: Storage
) : ScreenModel {

    private val _effect = SingleSharedFlow<ChatBotEffect>()
    val effect: SharedFlow<ChatBotEffect> get() = _effect

    val historyItems = listOf<String>(
        "Bana uygun kişisel bir antrenman planı oluştur. Hedefim \"kilo vermek.\"",
        "Günlük beslenme alışkanlıklarımı iyileştirmek için birkaç ipucu verir misin?",
        "Bana üst vücut kası yapmak için bir antrenman programı hazırla."
    )

    val onboardingCompleted: StateFlow<Boolean>
        get() = storage.getAsFlow(ChatBotOnboardingCompleted)
            .mapNotNull { it }
            .stateIn(screenModelScope, SharingStarted.Lazily, false)

    fun onAction(action: ChatBotAction) {
        when (action) {
            ChatBotAction.OnClickBack -> {
                _effect.emitIn(screenModelScope, ChatBotEffect.NavigateBack)
            }

            ChatBotAction.OnClickNew -> {
                _effect.emitIn(screenModelScope, ChatBotEffect.NavigateChat())
            }

            ChatBotAction.OnClickPostureAnalysis -> {
                _effect.emitIn(screenModelScope, ChatBotEffect.NavigatePostureAnalysis)
            }

            ChatBotAction.OnClickStart -> finalizeOnboarding()
            is ChatBotAction.OnClickHistoryItem -> {
                _effect.emitIn(screenModelScope, ChatBotEffect.NavigateChat(action.query))
            }
        }
    }

    private fun finalizeOnboarding() {
        screenModelScope.launch {
            storage.writeValue(ChatBotOnboardingCompleted, true)
        }
    }

    data object ChatBotOnboardingCompleted : Storage.Key.BooleanKey("chatbot_onboarding_completed", false)
}