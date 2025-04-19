package com.vurgun.skyfit.feature.settings.user

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.data.core.domain.model.BodyType
import com.vurgun.skyfit.data.core.domain.model.HeightUnitType
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.domain.model.WeightUnitType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Data class to hold all user account state in one place
data class UserAccountState(
    val userName: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val height: Int = 170,
    val heightUnit: HeightUnitType = HeightUnitType.CM,
    val weight: Int = 70,
    val weightUnit: WeightUnitType = WeightUnitType.KG,
    val bodyType: BodyType = BodyType.ECTOMORPH,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val isUpdated: Boolean = false
)

class UserAccountSettingsViewModel(
    private val userManager: UserManager
) : ViewModel() {

    private val user: UserDetail
        get() = userManager.user.value as? UserDetail
            ?: error("❌ current account is not user")

    val hasMultipleAccounts = userManager.accountTypes.value.size > 1

    private val _accountState = MutableStateFlow(UserAccountState())
    val accountState: StateFlow<UserAccountState> = _accountState

    private var initialState: UserAccountState? = null

    fun loadData() {
        val initial = UserAccountState(
            userName = user.username,
            firstName = user.firstName,
            lastName = user.lastName,
            height = user.height,
            weight = user.weight,
            bodyType = user.bodyType,
            profileImageUrl = user.profileImageUrl,
            backgroundImageUrl = user.backgroundImageUrl
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
