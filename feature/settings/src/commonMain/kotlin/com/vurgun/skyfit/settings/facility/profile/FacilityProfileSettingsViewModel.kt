package com.vurgun.skyfit.settings.facility.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.ProfileTag
import com.vurgun.skyfit.core.data.v1.domain.global.repository.GlobalRepository
import com.vurgun.skyfit.core.network.RemoteImageDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface FacilityProfileSettingsUiState {
    data object Loading : FacilityProfileSettingsUiState
    data class Error(val message: String) : FacilityProfileSettingsUiState
    data class Content(val form: FacilityProfileSettingsFormState) : FacilityProfileSettingsUiState
}

sealed class FacilityProfileSettingsUiAction {
    data object NavigateToBack : FacilityProfileSettingsUiAction()
    data class UpdateName(val value: String) : FacilityProfileSettingsUiAction()
    data class UpdateBackgroundImage(val value: ByteArray?) : FacilityProfileSettingsUiAction()
    data object DeleteBackgroundImage : FacilityProfileSettingsUiAction()
    data class UpdateBiography(val value: String) : FacilityProfileSettingsUiAction()
    data class UpdateLocation(val value: String) : FacilityProfileSettingsUiAction()
    data class UpdateTags(val tags: List<ProfileTag>) : FacilityProfileSettingsUiAction()
    data object SaveChanges : FacilityProfileSettingsUiAction()
}

data class FacilityProfileSettingsFormState(
    val name: String,
    val biography: String,
    val backgroundImageUrl: String? = null,
    val backgroundImageBytes: ByteArray? = null,
    val location: String,
    val profileTags: List<ProfileTag> = emptyList(),
    val allTags: List<ProfileTag> = emptyList(),
    val isUpdated: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FacilityProfileSettingsFormState) return false

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

sealed class FacilityProfileSettingsEffect {
    data object NavigateToBack : FacilityProfileSettingsEffect()
    data class ShowSaveError(val message: String) : FacilityProfileSettingsEffect()
}

class FacilityProfileSettingsViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository,
    private val globalRepository: GlobalRepository,
    private val remoteImageDataSource: RemoteImageDataSource,
) : ScreenModel {

    private val _uiState = MutableStateFlow<FacilityProfileSettingsUiState>(FacilityProfileSettingsUiState.Loading)
    val uiState: StateFlow<FacilityProfileSettingsUiState> = _uiState

    private val _effect = SingleSharedFlow<FacilityProfileSettingsEffect>()
    val effect: SharedFlow<FacilityProfileSettingsEffect> = _effect

    private val facilityUser: FacilityAccount
        get() = userManager.account.value as? FacilityAccount
            ?: error("User is not a Facility")

    private var initialState: FacilityProfileSettingsFormState? = null

    fun onAction(action: FacilityProfileSettingsUiAction) {
        when (action) {
            FacilityProfileSettingsUiAction.NavigateToBack -> emitEffect(FacilityProfileSettingsEffect.NavigateToBack)
            FacilityProfileSettingsUiAction.SaveChanges -> saveChanges()
            is FacilityProfileSettingsUiAction.UpdateBackgroundImage -> updateForm { copy(backgroundImageBytes = action.value) }
            is FacilityProfileSettingsUiAction.DeleteBackgroundImage ->
                updateForm { copy(backgroundImageBytes = null, backgroundImageUrl = null) }

            is FacilityProfileSettingsUiAction.UpdateBiography -> updateForm { copy(biography = action.value) }
            is FacilityProfileSettingsUiAction.UpdateLocation -> updateForm { copy(location = action.value) }
            is FacilityProfileSettingsUiAction.UpdateName -> updateForm { copy(name = action.value) }
            is FacilityProfileSettingsUiAction.UpdateTags -> updateForm { copy(profileTags = action.tags) }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            try {
                val facilityProfile = facilityRepository.getFacilityProfile(facilityUser.gymId).getOrThrow()
                val backgroundImageDeferred = facilityProfile.backgroundImageUrl?.let { async { remoteImageDataSource.getImageBytes(it) } }
                val allTagsDeferred = async { globalRepository.getProfileTags().getOrDefault(emptyList()) }

                val formState = FacilityProfileSettingsFormState(
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
                _uiState.value = FacilityProfileSettingsUiState.Content(formState)

            } catch (e: Exception) {
                _uiState.value = FacilityProfileSettingsUiState.Error(e.message ?: "Profile getirme hatasi")
            }
        }
    }

    private fun updateForm(update: FacilityProfileSettingsFormState.() -> FacilityProfileSettingsFormState) {
        val current = (_uiState.value as? FacilityProfileSettingsUiState.Content)?.form ?: return
        val newForm = update(current)
        val updated = newForm.copy(isUpdated = newForm != initialState)
        _uiState.value = FacilityProfileSettingsUiState.Content(updated)
    }

    private fun saveChanges() {
        val form = (_uiState.value as? FacilityProfileSettingsUiState.Content)?.form ?: return

        screenModelScope.launch {
            _uiState.value = FacilityProfileSettingsUiState.Loading

            try {
                facilityRepository.updateFacilityProfile(
                    gymId = facilityUser.gymId,
                    backgroundImageBytes = form.backgroundImageBytes,
                    name = form.name,
                    address = form.location,
                    bio = form.biography,
                    profileTags = form.profileTags.mapNotNull { it.id.toIntOrNull() }
                )

                initialState = form.copy(isUpdated = false)
                _uiState.value = FacilityProfileSettingsUiState.Content(initialState!!)
                emitEffect(FacilityProfileSettingsEffect.NavigateToBack)
            } catch (e: Exception) {
                _effect.emitIn(screenModelScope, FacilityProfileSettingsEffect.ShowSaveError(e.message ?: "Kaydetme hatasi"))
            }
        }
    }

    private fun emitEffect(effect: FacilityProfileSettingsEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}
