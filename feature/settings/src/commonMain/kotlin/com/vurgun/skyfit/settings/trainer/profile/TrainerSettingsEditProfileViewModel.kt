package com.vurgun.skyfit.settings.trainer.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.TrainerAccount
import com.vurgun.skyfit.core.data.v1.domain.global.model.ProfileTag
import com.vurgun.skyfit.core.data.v1.domain.global.repository.GlobalRepository
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import com.vurgun.skyfit.core.network.RemoteImageDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface TrainerEditProfileUiState {
    data object Loading : TrainerEditProfileUiState
    data class Error(val message: String) : TrainerEditProfileUiState
    data class Content(val form: TrainerEditProfileFormState) : TrainerEditProfileUiState
}

data class TrainerEditProfileFormState(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val biography: String,
    val profileImageUrl: String? = null,
    val profileImageBytes: ByteArray? = null,
    val backgroundImageUrl: String? = null,
    val backgroundImageBytes: ByteArray? = null,
    val availableTags: List<ProfileTag> = emptyList(),
    val profileTags: List<ProfileTag> = emptyList(),
    val isUpdated: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TrainerEditProfileFormState) return false

        return userName == other.userName &&
                firstName == other.firstName &&
                lastName == other.lastName &&
                biography == other.biography &&
                profileImageUrl == other.profileImageUrl &&
                profileImageBytes contentEquals other.profileImageBytes &&
                backgroundImageUrl == other.backgroundImageUrl &&
                backgroundImageBytes contentEquals other.backgroundImageBytes &&
                profileTags == other.profileTags &&
                isUpdated == other.isUpdated
    }

    override fun hashCode(): Int {
        var result = userName.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + biography.hashCode()
        result = 31 * result + (profileImageUrl?.hashCode() ?: 0)
        result = 31 * result + (profileImageBytes?.contentHashCode() ?: 0)
        result = 31 * result + (backgroundImageUrl?.hashCode() ?: 0)
        result = 31 * result + (backgroundImageBytes?.contentHashCode() ?: 0)
        result = 31 * result + profileTags.hashCode()
        result = 31 * result + isUpdated.hashCode()
        return result
    }
}

sealed class TrainerEditProfileAction {
    data object NavigateToBack : TrainerEditProfileAction()
    data object SaveChanges : TrainerEditProfileAction()
    data class UpdateProfileImage(val value: ByteArray?) : TrainerEditProfileAction()
    data class UpdateBackgroundImage(val value: ByteArray?) : TrainerEditProfileAction()
    data object DeleteProfileImage : TrainerEditProfileAction()
    data object DeleteBackgroundImage : TrainerEditProfileAction()
    data class UpdateUserName(val value: String) : TrainerEditProfileAction()
    data class UpdateFirstName(val value: String) : TrainerEditProfileAction()
    data class UpdateLastName(val value: String) : TrainerEditProfileAction()
    data class UpdateBiography(val value: String) : TrainerEditProfileAction()
    data class UpdateTags(val tags: List<ProfileTag>) : TrainerEditProfileAction()
}

sealed class TrainerEditProfileEffect {
    data object NavigateToBack : TrainerEditProfileEffect()
    data class ShowSaveError(val message: String) : TrainerEditProfileEffect()
}

class TrainerSettingsEditProfileViewModel(
    private val userManager: ActiveAccountManager,
    private val trainerRepository: TrainerRepository,
    private val globalRepository: GlobalRepository,
    private val remoteImageDataSource: RemoteImageDataSource
) : ScreenModel {

    private val _uiState = MutableStateFlow<TrainerEditProfileUiState>(TrainerEditProfileUiState.Loading)
    val uiState: StateFlow<TrainerEditProfileUiState> = _uiState

    private val _effect = SingleSharedFlow<TrainerEditProfileEffect>()
    val effect: SharedFlow<TrainerEditProfileEffect> = _effect

    private val trainerUser: TrainerAccount
        get() = userManager.account.value as? TrainerAccount
            ?: error("❌ User is not a Trainer")

    private var initialState: TrainerEditProfileFormState? = null

    fun onAction(action: TrainerEditProfileAction) {
        when (action) {
            is TrainerEditProfileAction.UpdateProfileImage ->
                updateForm { copy(profileImageBytes = action.value) }

            is TrainerEditProfileAction.DeleteProfileImage ->
                updateForm { copy(profileImageBytes = null, profileImageUrl = null) }

            is TrainerEditProfileAction.UpdateBackgroundImage ->
                updateForm { copy(backgroundImageBytes = action.value) }

            is TrainerEditProfileAction.DeleteBackgroundImage ->
                updateForm { copy(backgroundImageBytes = null, backgroundImageUrl = null) }

            is TrainerEditProfileAction.UpdateUserName -> updateForm { copy(userName = action.value) }
            is TrainerEditProfileAction.UpdateFirstName -> updateForm { copy(firstName = action.value) }
            is TrainerEditProfileAction.UpdateLastName -> updateForm { copy(lastName = action.value) }
            is TrainerEditProfileAction.UpdateBiography -> updateForm { copy(biography = action.value) }
            is TrainerEditProfileAction.UpdateTags -> updateForm { copy(profileTags = action.tags) }
            TrainerEditProfileAction.SaveChanges -> saveChanges()
            TrainerEditProfileAction.NavigateToBack -> emitEffect(TrainerEditProfileEffect.NavigateToBack)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            try {
                val trainerProfile = trainerRepository.getTrainerProfile(trainerUser.trainerId).getOrThrow()

                val profileImageUrl = trainerProfile.profileImageUrl
                val backgroundImageUrl = trainerProfile.backgroundImageUrl

                // Load image bytes in parallel
                val profileImageDeferred = profileImageUrl?.let { async { remoteImageDataSource.getImageBytes(it) } }
                val backgroundImageDeferred = backgroundImageUrl?.let { async { remoteImageDataSource.getImageBytes(it) } }
                val allTagsDeferred = async { globalRepository.getProfileTags().getOrDefault(emptyList()) }

                val formState = TrainerEditProfileFormState(
                    userName = trainerProfile.username,
                    firstName = trainerProfile.firstName,
                    lastName = trainerProfile.lastName,
                    biography = trainerProfile.bio,
                    profileImageUrl = profileImageUrl,
                    profileImageBytes = profileImageDeferred?.await(),
                    backgroundImageUrl = backgroundImageUrl,
                    backgroundImageBytes = backgroundImageDeferred?.await(),
                    profileTags = emptyList(),
                    availableTags = allTagsDeferred.await(),
                    isUpdated = false
                )

                initialState = formState
                _uiState.value = TrainerEditProfileUiState.Content(formState)

            } catch (e: Exception) {
                _uiState.value = TrainerEditProfileUiState.Error(e.message ?: "Profil getirme hatasi")
            }
        }
    }

    private fun updateForm(update: TrainerEditProfileFormState.() -> TrainerEditProfileFormState) {
        val current = (_uiState.value as? TrainerEditProfileUiState.Content)?.form ?: return
        val newForm = update(current)
        val updated = newForm.copy(isUpdated = newForm != initialState)
        _uiState.value = TrainerEditProfileUiState.Content(updated)
    }

    private fun saveChanges() {
        val form = (_uiState.value as? TrainerEditProfileUiState.Content)?.form ?: return

        screenModelScope.launch {
            _uiState.value = TrainerEditProfileUiState.Loading

            try {
                trainerRepository.updateTrainerProfile(
                    trainerId = trainerUser.trainerId,
                    username = form.userName,
                    profileImageBytes = form.profileImageBytes,
                    backgroundImageBytes = form.backgroundImageBytes,
                    firstName = form.firstName,
                    lastName = form.lastName,
                    bio = form.biography,
                    profileTags = form.profileTags.map { it.id.toIntOrNull() ?: 0 }.filter { it != 0}
                )

                initialState = form.copy(isUpdated = false)
                _uiState.value = TrainerEditProfileUiState.Content(initialState!!)
                _effect.emitIn(screenModelScope, TrainerEditProfileEffect.NavigateToBack)

            } catch (e: Exception) {
                _effect.emitIn(screenModelScope, TrainerEditProfileEffect.ShowSaveError(e.message ?: "Kaydetme hatasi"))
            }
        }
    }

    private fun emitEffect(effect: TrainerEditProfileEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}
