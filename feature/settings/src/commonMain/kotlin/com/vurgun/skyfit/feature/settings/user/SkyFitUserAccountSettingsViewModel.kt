package com.vurgun.skyfit.feature.settings.user

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.core.domain.model.BodyType
import com.vurgun.skyfit.data.core.domain.model.HeightUnitType
import com.vurgun.skyfit.data.core.domain.model.WeightUnitType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Data class to hold all user account state in one place
data class UserAccountState(
    val userName: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val height: Int = 170,
    val heightUnit: HeightUnitType = HeightUnitType.CM,
    val weight: Int = 70,
    val weightUnit: WeightUnitType = WeightUnitType.KG,
    val bodyType: BodyType = BodyType.ECTOMORPH,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val isUpdated: Boolean = false
)

class SkyFitUserAccountSettingsViewModel : ViewModel() {

    private val _accountState = MutableStateFlow(UserAccountState())
    val accountState: StateFlow<UserAccountState> = _accountState

    private var initialState: UserAccountState? = null

    fun loadData() {
        val initial = UserAccountState(
            userName = "maxjacobson",
            firstName = "Maxine",
            lastName = "Jacobson",
            email = "maxine@gmail.com",
            height = 175,
            weight = 75,
            bodyType = BodyType.ECTOMORPH,
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

    fun updateFirstName(value: String) {
        updateState { copy(firstName = value) }
    }

    fun updateLastName(value: String) {
        updateState { copy(lastName = value) }
    }

    fun updateEmail(value: String) {
        updateState { copy(email = value) }
    }

    fun updateHeight(value: Int) {
        updateState { copy(height = value) }
    }

    fun updateHeightUnit(value: HeightUnitType) {
        updateState { copy(heightUnit = value) }
    }

    fun updateWeightUnit(value: WeightUnitType) {
        updateState { copy(weightUnit = value) }
    }

    fun updateWeight(value: Int) {
        updateState { copy(weight = value) }
    }

    fun updateBodyType(value: BodyType) {
        updateState { copy(bodyType = value) }
    }

    fun saveChanges() {
        updateState { copy(isUpdated = false) }
        initialState = _accountState.value
    }

    fun deleteAccount() {
        // TODO: Implement account deletion logic
    }
}
