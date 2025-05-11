package com.vurgun.skyfit.feature.persona.settings.trainer.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.persona.domain.model.TrainerDetail
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutTag
import com.vurgun.skyfit.core.data.persona.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface TrainerManageProfileUiState {
    data object Loading : TrainerManageProfileUiState
    data class Error(val message: String) : TrainerManageProfileUiState
    data class Content(
        val form: TrainerManageProfileFormState,
        val hasMultipleProfiles: Boolean
    ) : TrainerManageProfileUiState
}

data class TrainerManageProfileFormState(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val biography: String,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val profileTags: List<WorkoutTag> = emptyList()
)

sealed class TrainerManageProfileAction {
    data object NavigateToBack : TrainerManageProfileAction()
    data object NavigateToChangePassword : TrainerManageProfileAction()
    data object NavigateToManageAccounts : TrainerManageProfileAction()
    data object NavigateToEditProfile : TrainerManageProfileAction()
    data object DeleteAccount : TrainerManageProfileAction()
}

sealed class TrainerManageProfileEffect {
    data object NavigateToBack : TrainerManageProfileEffect()
    data object NavigateToChangePassword : TrainerManageProfileEffect()
    data object NavigateToManageAccounts : TrainerManageProfileEffect()
    data object NavigateToEditProfile : TrainerManageProfileEffect()
    data class ShowDeleteError(val message: String) : TrainerManageProfileEffect()
}

class TrainerSettingsManageProfileViewModel(
    private val userManager: UserManager,
    private val profileRepository: ProfileRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<TrainerManageProfileUiState>(TrainerManageProfileUiState.Loading)
    val uiState: StateFlow<TrainerManageProfileUiState> = _uiState

    private val _effect = SingleSharedFlow<TrainerManageProfileEffect>()
    val effect: SharedFlow<TrainerManageProfileEffect> = _effect

    private val trainerUser: TrainerDetail
        get() = userManager.user.value as? TrainerDetail
            ?: error("❌ User is not a Trainer")

    fun onAction(action: TrainerManageProfileAction) {
        when (action) {
            TrainerManageProfileAction.DeleteAccount -> deleteAccount()
            TrainerManageProfileAction.NavigateToBack -> emitEffect(TrainerManageProfileEffect.NavigateToBack)
            TrainerManageProfileAction.NavigateToChangePassword -> emitEffect(TrainerManageProfileEffect.NavigateToChangePassword)
            TrainerManageProfileAction.NavigateToManageAccounts -> emitEffect(TrainerManageProfileEffect.NavigateToManageAccounts)
            TrainerManageProfileAction.NavigateToEditProfile -> emitEffect(TrainerManageProfileEffect.NavigateToEditProfile)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            try {
                val trainerProfile = profileRepository.getTrainerProfile(trainerUser.trainerId).getOrThrow()

                val formState = TrainerManageProfileFormState(
                    userName = trainerProfile.username,
                    firstName = trainerProfile.firstName,
                    lastName = trainerProfile.lastName,
                    biography = trainerProfile.bio,
                    profileImageUrl = trainerProfile.profileImageUrl,
                    backgroundImageUrl = trainerProfile.backgroundImageUrl,
                    profileTags = emptyList(), //TODO: TAGS
                )
                _uiState.value = TrainerManageProfileUiState.Content(
                    form = formState,
                    hasMultipleProfiles = userManager.accountTypes.value.size > 1
                )
            } catch (e: Exception) {
                _uiState.value = TrainerManageProfileUiState.Error(e.message ?: "Profil getirme hatasi")
            }
        }
    }

    private fun deleteAccount() {
        emitEffect(TrainerManageProfileEffect.ShowDeleteError("Hesap silme işlemi desteklenmiyor."))
    }

    private fun emitEffect(effect: TrainerManageProfileEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}
