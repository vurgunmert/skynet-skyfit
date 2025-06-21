package com.vurgun.skyfit.settings.facility.account

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.ProfileTag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface FacilityAccountSettingsUiState {
    data object Loading : FacilityAccountSettingsUiState
    data class Error(val message: String) : FacilityAccountSettingsUiState
    data class Content(
        val hasMultipleAccounts: Boolean = false,
        val form: FacilityAccountSettingsFormState
    ) : FacilityAccountSettingsUiState
}

data class FacilityAccountSettingsFormState(
    val name: String,
    val biography: String,
    val backgroundImageUrl: String? = null,
    val location: String,
    val profileTags: List<ProfileTag> = emptyList(),
    val isUpdated: Boolean = false
)

sealed class FacilityAccountSettingsUiAction {
    data object NavigateToBack : FacilityAccountSettingsUiAction()
    data object NavigateToChangePassword : FacilityAccountSettingsUiAction()
    data object NavigateToAccounts : FacilityAccountSettingsUiAction()
    data object NavigateToEditProfile : FacilityAccountSettingsUiAction()
    data object DeleteProfile : FacilityAccountSettingsUiAction()
}

sealed interface FacilityAccountSettingsEffect {
    data object NavigateToBack : FacilityAccountSettingsEffect
    data object NavigateToChangePassword : FacilityAccountSettingsEffect
    data object NavigateToAccounts : FacilityAccountSettingsEffect
    data object NavigateToEditProfile : FacilityAccountSettingsEffect
    data class ShowDeleteError(val message: String) : FacilityAccountSettingsEffect
}

class FacilityAccountSettingsViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<FacilityAccountSettingsUiState>(FacilityAccountSettingsUiState.Loading)
    val uiState: StateFlow<FacilityAccountSettingsUiState> = _uiState

    private val _effect = SingleSharedFlow<FacilityAccountSettingsEffect>()
    val effect: SharedFlow<FacilityAccountSettingsEffect> = _effect

    private val facilityUser: FacilityAccount
        get() = userManager.account.value as? FacilityAccount
            ?: error("User is not a Facility")

    fun onAction(action: FacilityAccountSettingsUiAction) {
        when (action) {
            FacilityAccountSettingsUiAction.DeleteProfile -> deleteAccount()
            FacilityAccountSettingsUiAction.NavigateToAccounts ->
                _effect.emitIn(screenModelScope, FacilityAccountSettingsEffect.NavigateToAccounts)

            FacilityAccountSettingsUiAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, FacilityAccountSettingsEffect.NavigateToBack)

            FacilityAccountSettingsUiAction.NavigateToChangePassword ->
                _effect.emitIn(screenModelScope, FacilityAccountSettingsEffect.NavigateToChangePassword)

            FacilityAccountSettingsUiAction.NavigateToEditProfile ->
                _effect.emitIn(screenModelScope, FacilityAccountSettingsEffect.NavigateToEditProfile)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            try {
                val facilityProfile =
                    facilityRepository.getFacilityProfile(facilityId = facilityUser.gymId).getOrThrow()
                val form = FacilityAccountSettingsFormState(
                    name = facilityProfile.facilityName,
                    biography = facilityProfile.bio,
                    profileTags = listOf(), //TODO: GET TAGS API
                    location = facilityProfile.gymAddress,
                    backgroundImageUrl = facilityProfile.backgroundImageUrl
                )
                _uiState.value = FacilityAccountSettingsUiState.Content(
                    hasMultipleAccounts = userManager.accountTypes.value.size > 1,
                    form = form
                )
            } catch (e: Exception) {
                _uiState.value = FacilityAccountSettingsUiState.Error(e.message ?: "Profil getirme hatasi")
            }
        }
    }

    private fun deleteAccount() {
        TODO()
    }
}
