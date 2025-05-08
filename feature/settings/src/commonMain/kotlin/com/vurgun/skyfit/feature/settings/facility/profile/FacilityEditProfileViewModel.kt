package com.vurgun.skyfit.feature.settings.facility.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.FacilityDetail
import com.vurgun.skyfit.core.data.domain.model.WorkoutTag
import com.vurgun.skyfit.core.data.domain.repository.AuthRepository
import com.vurgun.skyfit.core.data.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.async
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
    data class UpdateBackgroundImage(val value: ByteArray?) : FacilityEditProfileAction()
    data object DeleteBackgroundImage : FacilityEditProfileAction()
    data class UpdateBiography(val value: String) : FacilityEditProfileAction()
    data class UpdateLocation(val value: String) : FacilityEditProfileAction()
    data class UpdateTags(val tags: List<WorkoutTag>) : FacilityEditProfileAction()
    data object SaveChanges : FacilityEditProfileAction()
}

data class FacilityEditProfileFormState(
    val name: String,
    val biography: String,
    val backgroundImageUrl: String? = null,
    val backgroundImageBytes: ByteArray? = null,
    val location: String,
    val profileTags: List<WorkoutTag> = emptyList(),
    val allTags: List<WorkoutTag> = emptyList(),
    val isUpdated: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FacilityEditProfileFormState) return false

        return name == other.name &&
                biography == other.biography &&
                backgroundImageUrl == other.backgroundImageUrl &&
                backgroundImageBytes contentEquals other.backgroundImageBytes &&
                location == other.location &&
                profileTags == other.profileTags &&
                isUpdated == other.isUpdated
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + biography.hashCode()
        result = 31 * result + (backgroundImageUrl?.hashCode() ?: 0)
        result = 31 * result + (backgroundImageBytes?.contentHashCode() ?: 0)
        result = 31 * result + location.hashCode()
        result = 31 * result + profileTags.hashCode()
        result = 31 * result + isUpdated.hashCode()
        return result
    }
}

sealed class FacilityEditProfileEffect {
    data object NavigateToBack : FacilityEditProfileEffect()
    data class ShowSaveError(val message: String) : FacilityEditProfileEffect()
}

class FacilityEditProfileViewModel(
    private val userManager: UserManager,
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
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
            is FacilityEditProfileAction.UpdateBackgroundImage -> updateForm { copy(backgroundImageBytes = action.value) }
            is FacilityEditProfileAction.DeleteBackgroundImage ->
                updateForm { copy(backgroundImageBytes = null, backgroundImageUrl = null) }

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
                val backgroundImageDeferred = facilityProfile.backgroundImageUrl?.let { async { profileRepository.fetchImageBytes(it) } }
                val allTagsDeferred = async { authRepository.getTags().getOrDefault(emptyList()) }

                val formState = FacilityEditProfileFormState(
                    name = facilityProfile.facilityName,
                    biography = facilityProfile.bio,
                    backgroundImageUrl = facilityProfile.backgroundImageUrl,
                    backgroundImageBytes = backgroundImageDeferred?.await(),
                    location = facilityProfile.gymAddress,
                    profileTags = emptyList(),
                    allTags = allTagsDeferred.await(),
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
        val newForm = update(current)
        val updated = newForm.copy(isUpdated = newForm != initialState)
        _uiState.value = FacilityEditProfileUiState.Content(updated)
    }

    private fun saveChanges() {
        val form = (_uiState.value as? FacilityEditProfileUiState.Content)?.form ?: return

        screenModelScope.launch {
            _uiState.value = FacilityEditProfileUiState.Loading

            try {
                profileRepository.updateFacilityProfile(
                    gymId = facilityUser.gymId,
                    backgroundImageBytes = form.backgroundImageBytes,
                    name = form.name,
                    address = form.location,
                    bio = form.biography,
                    profileTags = form.profileTags.map { it.tagId }
                )

                initialState = form.copy(isUpdated = false)
                _uiState.value = FacilityEditProfileUiState.Content(initialState!!)
                emitEffect(FacilityEditProfileEffect.NavigateToBack)
            } catch (e: Exception) {
                _effect.emitIn(screenModelScope, FacilityEditProfileEffect.ShowSaveError(e.message ?: "Kaydetme hatasi"))
            }
        }
    }

    private fun emitEffect(effect: FacilityEditProfileEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}
