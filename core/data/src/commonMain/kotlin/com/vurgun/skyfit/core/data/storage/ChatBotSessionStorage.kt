package com.vurgun.skyfit.core.data.storage

import com.vurgun.skyfit.core.data.utility.randomUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class ChatBotSessionStorage(
    private val storage: Storage
) {

    suspend fun saveSession(id: String) {
        val current = getSessionIds().toMutableSet()
        current.add(id)
        val joined = current.joinToString(",")
        storage.writeValue(SessionList, joined)
    }

    suspend fun getSessionIds(): List<String> {
        val raw = storage.get(SessionList)
        return if (raw.isNullOrBlank()) {
            val newSessionId = randomUUID()
            storage.writeValue(SessionList, newSessionId)
            listOf(newSessionId)
        } else {
            raw.split(",").filter { it.isNotBlank() }
        }
    }

    fun observeSessionIds(): Flow<List<String>> {
        return storage.getAsFlow(SessionList)
            .map { raw ->
                raw?.split(",")?.filter { it.isNotBlank() } ?: emptyList()
            }
            .distinctUntilChanged()
    }


    suspend fun markOnboardingCompleted() {
        storage.writeValue(OnboardingCompleted, true)
    }

    suspend fun isOnboardingCompleted(): Boolean = storage.get(OnboardingCompleted) ?: false

    val onboardingCompleted: Flow<Boolean> = storage.getAsFlow(OnboardingCompleted).mapNotNull { it }

    private data object OnboardingCompleted : Storage.Key.BooleanKey("chatbot_onboarding_completed", false)
    private data object SessionList : Storage.Key.StringKey("chatbot_session_id_list", null)
}
