package com.vurgun.skyfit.feature_settings.ui.user

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.vurgun.skyfit.core.data.models.BodyType

// Data class to hold all user account state in one place
data class UserAccountState(
    val userName: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val height: Int? = null,
    val heightUnit: String? = null,
    val weight: Int? = null,
    val weightUnit: String? = null,
    val bodyType: BodyType = BodyType.NOT_DEFINED,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val profileTags: List<String> = emptyList(),
    val isUpdated: Boolean = false
)

class SkyFitUserAccountSettingsViewModel : ViewModel() {

    private val _accountState = MutableStateFlow(UserAccountState())
    val accountState: StateFlow<UserAccountState> = _accountState

    private var initialState: UserAccountState? = null

    fun loadData() {
        val initial = UserAccountState(
            userName = "maxjacobson",
            fullName = "Maxine",
            email = "maxine@gmail.com",
            height = 175,
            heightUnit = "cm",
            weight = 75,
            weightUnit = "kg",
            bodyType = BodyType.NOT_DEFINED,
            profileTags = listOf("Kardiyo", "Kas Gelişimi", "Fonksiyonel Antrenman"),
            profileImageUrl = null,
            backgroundImageUrl = null
        )
        _accountState.value = initial
        initialState = initial
    }

    private fun updateState(update: UserAccountState.() -> UserAccountState) {
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

    fun updateHeight(value: Int) {
        updateState { copy(height = value) }
    }

    fun updateHeightUnit(value: String) {
        updateState { copy(heightUnit = value) }
    }

    fun updateWeightUnit(value: String) {
        updateState { copy(weightUnit = value) }
    }

    fun updateWeight(value: Int) {
        updateState { copy(weight = value) }
    }

    fun updateBodyType(value: BodyType) {
        updateState { copy(bodyType = value) }
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
