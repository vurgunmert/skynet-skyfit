package com.vurgun.skyfit.feature_settings.ui.trainer

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.core.domain.models.FitnessTagType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class TrainerAccountState(
    val userName: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val biography: String? = null,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val profileTags: List<FitnessTagType> = emptyList(),
    val isUpdated: Boolean = false
)

class TrainerAccountSettingsViewModel : ViewModel() {

    private val _accountState = MutableStateFlow(TrainerAccountState())
    val accountState: StateFlow<TrainerAccountState> = _accountState

    private var initialState: TrainerAccountState? = null

    fun loadData() {
        val initial = TrainerAccountState(
            userName = "maxjacobson",
            firstName = "Maxine",
            lastName = "Jacombzi",
            email = "maxinetrainer@gmail.com",
            biography = "Whether you're a beginner or looking to advance, My balanced approach will guide you every step of the way. \uD83C\uDFC3\uD83C\uDFFD\u200D♂\uFE0F\n" +
                    "@ironstudio",
            profileTags = listOf(FitnessTagType.CARDIO, FitnessTagType.FLEXIBILITY),
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

    fun updateFirstName(value: String) {
        updateState { copy(firstName = value) }
    }

    fun updateLastName(value: String) {
        updateState { copy(lastName = value) }
    }

    fun updateEmail(value: String) {
        updateState { copy(email = value) }
    }

    fun updateBiography(value: String) {
        updateState { copy(biography = value) }
    }

    fun updateTags(tags: List<FitnessTagType>) {
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
