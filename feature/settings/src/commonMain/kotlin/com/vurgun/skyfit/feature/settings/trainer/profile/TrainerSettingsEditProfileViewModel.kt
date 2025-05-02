package com.vurgun.skyfit.feature.settings.trainer.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.FitnessTagType
import com.vurgun.skyfit.core.data.domain.model.TrainerDetail
import com.vurgun.skyfit.core.data.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
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
    val userName: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val biography: String? = null,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val profileTags: List<FitnessTagType> = emptyList(),
    val isUpdated: Boolean = false
)

sealed class TrainerEditProfileAction {
    data object NavigateToBack : TrainerEditProfileAction()
    data class UpdateProfileImage(val value: String) : TrainerEditProfileAction()
    data class UpdateBackgroundImage(val value: String) : TrainerEditProfileAction()
    data class UpdateUserName(val value: String) : TrainerEditProfileAction()
    data class UpdateFirstName(val value: String) : TrainerEditProfileAction()
    data class UpdateLastName(val value: String) : TrainerEditProfileAction()
    data class UpdateBiography(val value: String) : TrainerEditProfileAction()
    data class UpdateTags(val tags: List<FitnessTagType>) : TrainerEditProfileAction()
    data object SaveChanges : TrainerEditProfileAction()
}

sealed class TrainerEditProfileEffect {
    data object NavigateToBack : TrainerEditProfileEffect()
    data class ShowSaveError(val message: String) : TrainerEditProfileEffect()
}

class TrainerSettingsEditProfileViewModel(
    private val userManager: UserManager,
    private val profileRepository: ProfileRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<TrainerEditProfileUiState>(TrainerEditProfileUiState.Loading)
    val uiState: StateFlow<TrainerEditProfileUiState> = _uiState

    private val _effect = SingleSharedFlow<TrainerEditProfileEffect>()
    val effect: SharedFlow<TrainerEditProfileEffect> = _effect

    private val trainerUser: TrainerDetail
        get() = userManager.user.value as? TrainerDetail
            ?: error("❌ User is not a Trainer")

    private var initialState: TrainerEditProfileFormState? = null

    fun onAction(action: TrainerEditProfileAction) {
        when (action) {
            is TrainerEditProfileAction.UpdateProfileImage -> updateForm { copy(profileImageUrl = action.value) }
            is TrainerEditProfileAction.UpdateBackgroundImage -> updateForm { copy(backgroundImageUrl = action.value) }
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
                val trainerProfile = profileRepository.getTrainerProfile(trainerUser.trainerId).getOrThrow()

                val formState = TrainerEditProfileFormState(
                    userName = trainerProfile.username,
                    firstName = trainerProfile.firstName,
                    lastName = trainerProfile.lastName,
                    biography = trainerProfile.bio,
                    profileImageUrl = trainerProfile.profileImageUrl,
                    backgroundImageUrl = trainerProfile.backgroundImageUrl,
                    profileTags = emptyList(), //TODO: TAGS
                    isUpdated = false
                )

                initialState = formState
                _uiState.value = TrainerEditProfileUiState.Content(formState)

            } catch (e: Exception) {
                _uiState.value = TrainerEditProfileUiState.Error(e.message ?: "Profile getirme hatasi")
            }
        }
    }

    private fun updateForm(update: TrainerEditProfileFormState.() -> TrainerEditProfileFormState) {
        val current = (_uiState.value as? TrainerEditProfileUiState.Content)?.form ?: return
        val updated = update(current).copy(isUpdated = current != initialState)
        _uiState.value = TrainerEditProfileUiState.Content(updated)
    }

    private fun saveChanges() {
        val form = (_uiState.value as? TrainerEditProfileUiState.Content)?.form ?: return
        initialState = form.copy(isUpdated = false)
        _uiState.value = TrainerEditProfileUiState.Content(initialState!!)
        emitEffect(TrainerEditProfileEffect.NavigateToBack)
    }

    private fun emitEffect(effect: TrainerEditProfileEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}
