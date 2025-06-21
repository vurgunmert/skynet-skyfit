package com.vurgun.skyfit.feature.connect.chatbot.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatBotEffect.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class ChatBotAction {
    data object OnClickBack : ChatBotAction()
    data object OnClickOnboardingStart : ChatBotAction()
    data object OnClickNewChat : ChatBotAction()
    data object OnClickPostureAnalysis : ChatBotAction()
    data object OnClickMealReport : ChatBotAction()
    data class OnSubmitNewQuery(val query: String) : ChatBotAction()
    data class OnClickHistoryItem(val query: String) : ChatBotAction()

}

sealed class ChatBotEffect {
    data object Dismiss : ChatBotEffect()
    data object NavigateToPostureAnalysis : ChatBotEffect()
    data object NavigateToMealReport : ChatBotEffect()
    data class NavigateToChat(
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
                _effect.emitIn(screenModelScope, ChatBotEffect.Dismiss)
            }

            ChatBotAction.OnClickNewChat -> {
                _effect.emitIn(screenModelScope, NavigateToChat())
            }

            ChatBotAction.OnClickPostureAnalysis -> {
                _effect.emitIn(screenModelScope, ChatBotEffect.NavigateToPostureAnalysis)
            }

            ChatBotAction.OnClickOnboardingStart -> finalizeOnboarding()
            is ChatBotAction.OnClickHistoryItem -> {
                _effect.emitIn(screenModelScope, NavigateToChat(action.query))
            }

            ChatBotAction.OnClickMealReport -> {
                _effect.emitIn(screenModelScope, ChatBotEffect.NavigateToMealReport)
            }
            is ChatBotAction.OnSubmitNewQuery -> {
                _effect.emitIn(screenModelScope, NavigateToChat(action.query))
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