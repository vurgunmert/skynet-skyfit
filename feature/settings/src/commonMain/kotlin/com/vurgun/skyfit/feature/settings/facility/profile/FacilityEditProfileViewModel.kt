package com.vurgun.skyfit.feature.settings.facility.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.FacilityDetail
import com.vurgun.skyfit.core.data.domain.model.FitnessTagType
import com.vurgun.skyfit.core.data.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface FacilityEditProfileUiState {
    data object Loading : FacilityEditProfileUiState
    data class Error(val message: String) : FacilityEditProfileUiState
    data class Content(val form: FacilityEditProfileFormState) : FacilityEditProfileUiState
}

sealed class FacilityEditProfileAction {
    data object NavigateToBack : FacilityEditProfileAction()
    data class UpdateName(val value: String) : FacilityEditProfileAction()
    data class UpdateBackgroundImage(val value: String) : FacilityEditProfileAction()
    data class UpdateBiography(val value: String) : FacilityEditProfileAction()
    data class UpdateLocation(val value: String) : FacilityEditProfileAction()
    data class UpdateTags(val tags: List<FitnessTagType>) : FacilityEditProfileAction()
    data object SaveChanges : FacilityEditProfileAction()
}

data class FacilityEditProfileFormState(
    val name: String? = null,
    val biography: String? = null,
    val backgroundImageUrl: String? = null,
    val location: String? = null,
    val profileTags: List<FitnessTagType> = emptyList(),
    val isUpdated: Boolean = false
)

sealed class FacilityEditProfileEffect {
    data object NavigateToBack : FacilityEditProfileEffect()
    data class ShowSaveError(val message: String) : FacilityEditProfileEffect()
}

class FacilityEditProfileViewModel(
    private val userManager: UserManager,
    private val profileRepository: ProfileRepository,
) : ScreenModel {

    private val _uiState = MutableStateFlow<FacilityEditProfileUiState>(FacilityEditProfileUiState.Loading)
    val uiState: StateFlow<FacilityEditProfileUiState> = _uiState

    private val _effect = SingleSharedFlow<FacilityEditProfileEffect>()
    val effect: SharedFlow<FacilityEditProfileEffect> = _effect

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility")

    private var initialState: FacilityEditProfileFormState? = null

    fun onAction(action: FacilityEditProfileAction) {
        when (action) {
            FacilityEditProfileAction.NavigateToBack -> emitEffect(FacilityEditProfileEffect.NavigateToBack)
            FacilityEditProfileAction.SaveChanges -> saveChanges()
            is FacilityEditProfileAction.UpdateBackgroundImage -> updateForm { copy(backgroundImageUrl = action.value) }
            is FacilityEditProfileAction.UpdateBiography -> updateForm { copy(biography = action.value) }
            is FacilityEditProfileAction.UpdateLocation -> updateForm { copy(location = action.value) }
            is FacilityEditProfileAction.UpdateName -> updateForm { copy(name = action.value) }
            is FacilityEditProfileAction.UpdateTags -> updateForm { copy(profileTags = action.tags) }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            try {
                val facilityProfile = profileRepository.getFacilityProfile(facilityUser.gymId).getOrThrow()

                val formState = FacilityEditProfileFormState(
                    name = facilityProfile.facilityName,
                    biography = facilityProfile.bio,
                    backgroundImageUrl = facilityProfile.backgroundImageUrl,
                    location = facilityProfile.gymAddress,
                    profileTags = emptyList(),
                    isUpdated = false
                )

                initialState = formState
                _uiState.value = FacilityEditProfileUiState.Content(formState)

            } catch (e: Exception) {
                _uiState.value = FacilityEditProfileUiState.Error(e.message ?: "Profile getirme hatasi")
            }
        }
    }

    private fun updateForm(update: FacilityEditProfileFormState.() -> FacilityEditProfileFormState) {
        val current = (_uiState.value as? FacilityEditProfileUiState.Content)?.form ?: return
        val updated = update(current).copy(isUpdated = current != initialState)
        _uiState.value = FacilityEditProfileUiState.Content(updated)
    }

    private fun saveChanges() {
        //TODO: UPADTE PROFILE UNDER SCOPE
        val form = (_uiState.value as? FacilityEditProfileUiState.Content)?.form ?: return
        initialState = form.copy(isUpdated = false)
        _uiState.value = FacilityEditProfileUiState.Content(initialState!!)
        emitEffect(FacilityEditProfileEffect.NavigateToBack)
    }

    private fun emitEffect(effect: FacilityEditProfileEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}
