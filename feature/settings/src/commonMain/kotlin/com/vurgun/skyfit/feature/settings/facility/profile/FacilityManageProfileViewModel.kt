package com.vurgun.skyfit.feature.settings.facility.profile

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.data.core.domain.model.FacilityDetail
import com.vurgun.skyfit.data.core.domain.model.FitnessTagType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class FacilityAccountState(
    val name: String? = null,
    val biography: String? = null,
    val backgroundImageUrl: String? = null,
    val location: String? = null,
    val profileTags: List<FitnessTagType> = emptyList(),
    val isUpdated: Boolean = false
)

class FacilityManageProfileViewModel(
    private val userManager: UserManager
) : ViewModel() {

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility")

    val hasMultipleAccounts = userManager.accountTypes.value.size > 1

    private val _accountState = MutableStateFlow(FacilityAccountState())
    val accountState: StateFlow<FacilityAccountState> = _accountState

    private var initialState: FacilityAccountState? = null

    fun loadData() {
        val initial = FacilityAccountState(
            name = facilityUser.gymName,
            biography = facilityUser.bio,
            profileTags = listOf(), //TODO: GET TAGS API
            location = facilityUser.gymAddress,
            backgroundImageUrl = facilityUser.backgroundImageUrl
        )
        _accountState.value = initial
        initialState = initial
    }

    private fun updateState(update: FacilityAccountState.() -> FacilityAccountState) {
        _accountState.update {
            val newState = it.update().copy(isUpdated = it != initialState)
            newState
        }
    }

    fun updateName(value: String) {
        updateState { copy(name = value) }
    }

    fun updateBiography(value: String) {
        updateState { copy(biography = value) }
    }

    fun updateLocation(value: String) {
        updateState { copy(location = value) }
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
