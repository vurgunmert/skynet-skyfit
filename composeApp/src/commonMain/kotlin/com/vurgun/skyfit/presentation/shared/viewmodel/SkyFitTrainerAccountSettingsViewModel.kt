package com.vurgun.skyfit.presentation.shared.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class TrainerAccountState(
    val userName: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val biography: String? = null,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val profileTags: List<String> = emptyList(),
    val isUpdated: Boolean = false
)

class SkyFitTrainerAccountSettingsViewModel : ViewModel() {

    private val _accountState = MutableStateFlow(TrainerAccountState())
    val accountState: StateFlow<TrainerAccountState> = _accountState

    private var initialState: TrainerAccountState? = null

    fun loadData() {
        val initial = TrainerAccountState(
            userName = "maxjacobson",
            fullName = "Maxine",
            email = "maxine@gmail.com",
            biography = "Whether you're a beginner or looking to advance, My balanced approach will guide you every step of the way. \uD83C\uDFC3\uD83C\uDFFD\u200D♂\uFE0F\n" +
                    "@ironstudio",
            profileTags = listOf("Kardiyo", "Kas Gelişimi", "Fonksiyonel Antrenman"),
            profileImageUrl = null,
            backgroundImageUrl = null
        )
        _accountState.value = initial
        initialState = initial
    }

    private fun updateState(update: TrainerAccountState.() -> TrainerAccountState) {
        _accountState.update {
            val newState = it.update().copy(isUpdated = it != initialState)
            newState
        }
    }

    fun updateUserName(value: String) {
        updateState { copy(userName = value) }
    }

    fun updateFullName(value: String) {
        updateState { copy(fullName = value) }
    }

    fun updateEmail(value: String) {
        updateState { copy(email = value) }
    }

    fun updateBiography(value: String) {
        updateState { copy(biography = value) }
    }

    fun updateTags(tags: List<String>) {
        updateState { copy(profileTags = tags) }
    }

    fun saveChanges() {
        updateState { copy(isUpdated = false) }
        initialState = _accountState.value
    }

    fun deleteAccount() {
        // TODO: Implement account deletion logic
    }
}
