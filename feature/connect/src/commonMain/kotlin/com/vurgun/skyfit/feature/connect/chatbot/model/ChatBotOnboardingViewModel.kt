package com.vurgun.skyfit.feature.connect.chatbot.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ChatBotOnboardingPage(
    val title: String,
    val message: String,
    val buttonLabel: String
)

sealed class ChatBotShortCut {
    object BodyAnalysis : ChatBotShortCut()
    object NutritionReport : ChatBotShortCut()
}

class ChatBotOnboardingViewModel(private val onboardCompleted: () -> Unit) {

    val pages = listOf(
        ChatBotOnboardingPage(
            title = "Fitness asistanınız",
            message = "Yapay zeka asistanı FIWE'e hoşgeldin 👋 Fitness ve egzersiz yaşamınızı geliştirmek için tek tıkla düşüncelerinizi değiştirmeye hazır olun 🚀",
            buttonLabel = "İleri"
        ),
        ChatBotOnboardingPage(
            title = "Kişiselleştirilmiş Antrenmanlar",
            message = "Hedeflerinize ve tercihlerinize göre antrenman planları oluşturun 📝 Hedefinizi belirleyin: Kilo verme, kas yapma, dayanıklılık artırma ve daha fazlası...",
            buttonLabel = "İleri"
        ),
        ChatBotOnboardingPage(
            title = "Takip ve Destek",
            message = "Günlük aktivitelerinizi ve antrenmanlarınızı takip ederek ihtiyacın olan programa ve diyete kolayca ulaş!",
            buttonLabel = "Bașla"
        )
    )

    var currentPage: MutableState<Int> = mutableStateOf(0)
        private set

    val currentPageData: ChatBotOnboardingPage
        get() = pages[currentPage.value]

    val isLastPage: Boolean
        get() = currentPage.value == pages.size - 1

    fun nextPage() {
        if (!isLastPage) {
            currentPage.value = currentPage.value + 1
        } else {
            endOnboarding()
        }
    }

    private fun endOnboarding() {
        onboardCompleted.invoke()
    }
}
