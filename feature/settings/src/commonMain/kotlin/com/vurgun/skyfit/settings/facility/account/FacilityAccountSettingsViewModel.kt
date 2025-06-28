package com.vurgun.skyfit.settings.facility.account

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.UiEventDelegate
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.ProfileTag
import kotlinx.coroutines.flow.MutableStateFlow
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

sealed interface FacilityAccountSettingsEvent {
    data object NavigateToBack : FacilityAccountSettingsEvent
    data object NavigateToChangePassword : FacilityAccountSettingsEvent
    data object NavigateToAccounts : FacilityAccountSettingsEvent
    data object NavigateToEditProfile : FacilityAccountSettingsEvent
    data class ShowDeleteError(val message: String) : FacilityAccountSettingsEvent
}

class FacilityAccountSettingsViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<FacilityAccountSettingsUiState>(FacilityAccountSettingsUiState.Loading)
    val uiState: StateFlow<FacilityAccountSettingsUiState> = _uiState

    private val _eventDelegate = UiEventDelegate<FacilityAccountSettingsEvent>()
    val eventFlow = _eventDelegate.eventFlow

    private val facilityUser: FacilityAccount
        get() = userManager.account.value as? FacilityAccount
            ?: error("User is not a Facility")

    fun onAction(action: FacilityAccountSettingsUiAction) {
        when (action) {
            FacilityAccountSettingsUiAction.DeleteProfile -> deleteAccount()
            FacilityAccountSettingsUiAction.NavigateToAccounts ->
                _eventDelegate.sendIn(screenModelScope, FacilityAccountSettingsEvent.NavigateToAccounts)

            FacilityAccountSettingsUiAction.NavigateToBack ->
                _eventDelegate.sendIn(screenModelScope, FacilityAccountSettingsEvent.NavigateToBack)

            FacilityAccountSettingsUiAction.NavigateToChangePassword ->
                _eventDelegate.sendIn(screenModelScope, FacilityAccountSettingsEvent.NavigateToChangePassword)

            FacilityAccountSettingsUiAction.NavigateToEditProfile ->
                _eventDelegate.sendIn(screenModelScope, FacilityAccountSettingsEvent.NavigateToEditProfile)
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
