package com.vurgun.skyfit.feature.settings.facility.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.FacilityDetail
import com.vurgun.skyfit.core.data.domain.model.FitnessTagType
import com.vurgun.skyfit.core.data.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface FacilityManageProfileUiState {
    data object Loading : FacilityManageProfileUiState
    data class Error(val message: String) : FacilityManageProfileUiState
    data class Content(val form: FacilityManageAccountFormState) : FacilityManageProfileUiState
}

data class FacilityManageAccountFormState(
    val name: String? = null,
    val biography: String? = null,
    val backgroundImageUrl: String? = null,
    val location: String? = null,
    val profileTags: List<FitnessTagType> = emptyList(),
    val isUpdated: Boolean = false
)

sealed class FacilityManageProfileAction {
    data object NavigateToBack : FacilityManageProfileAction()
    data object NavigateToChangePassword : FacilityManageProfileAction()
    data object NavigateToAccounts : FacilityManageProfileAction()
    data object NavigateToEditProfile : FacilityManageProfileAction()
    data object DeleteProfile : FacilityManageProfileAction()
}

sealed interface FacilityManageProfileEffect {
    data object NavigateToBack : FacilityManageProfileEffect
    data object NavigateToChangePassword : FacilityManageProfileEffect
    data object NavigateToAccounts : FacilityManageProfileEffect
    data object NavigateToEditProfile : FacilityManageProfileEffect
    data class ShowDeleteError(val message: String) : FacilityManageProfileEffect
}

class FacilityManageProfileViewModel(
    private val userManager: UserManager,
    private val profileRepository: ProfileRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<FacilityManageProfileUiState>(FacilityManageProfileUiState.Loading)
    val uiState: StateFlow<FacilityManageProfileUiState> = _uiState

    private val _effect = SingleSharedFlow<FacilityManageProfileEffect>()
    val effect: SharedFlow<FacilityManageProfileEffect> = _effect

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility")

    val hasMultipleAccounts = userManager.accountTypes.value.size > 1

    fun onAction(action: FacilityManageProfileAction) {
        when (action) {
            FacilityManageProfileAction.DeleteProfile -> deleteCurentProfile()
            FacilityManageProfileAction.NavigateToAccounts ->
                _effect.emitIn(screenModelScope, FacilityManageProfileEffect.NavigateToAccounts)

            FacilityManageProfileAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, FacilityManageProfileEffect.NavigateToBack)

            FacilityManageProfileAction.NavigateToChangePassword ->
                _effect.emitIn(screenModelScope, FacilityManageProfileEffect.NavigateToChangePassword)

            FacilityManageProfileAction.NavigateToEditProfile ->
                _effect.emitIn(screenModelScope, FacilityManageProfileEffect.NavigateToEditProfile)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            try {
                val facilityProfile = profileRepository.getFacilityProfile(facilityId = facilityUser.gymId).getOrThrow()
                val form = FacilityManageAccountFormState(
                    name = facilityProfile.facilityName,
                    biography = facilityProfile.bio,
                    profileTags = listOf(), //TODO: GET TAGS API
                    location = facilityProfile.gymAddress,
                    backgroundImageUrl = facilityProfile.backgroundImageUrl
                )
                _uiState.value = FacilityManageProfileUiState.Content(form)
            } catch (e: Exception) {
                _uiState.value = FacilityManageProfileUiState.Error(e.message ?: "Profil getirme hatasi")
            }
        }
    }

    fun deleteCurentProfile() {
        // TODO: Implement account deletion logic
    }
}
