package com.vurgun.skyfit.feature.persona.settings.user.account

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.global.model.BodyType
import com.vurgun.skyfit.core.data.v1.domain.global.model.HeightUnitType
import com.vurgun.skyfit.core.data.v1.domain.global.model.WeightUnitType
import com.vurgun.skyfit.core.data.v1.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface UserManageProfileUiState {
    data object Loading : UserManageProfileUiState
    data class Error(val message: String) : UserManageProfileUiState
    data class Content(
        val form: UserSettingsManageProfileFormState,
        val hasMultipleProfiles: Boolean = false
    ) : UserManageProfileUiState
}

data class UserSettingsManageProfileFormState(
    val userName: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val height: Int = 170,
    val heightUnit: HeightUnitType = HeightUnitType.CM,
    val weight: Int = 70,
    val weightUnit: WeightUnitType = WeightUnitType.KG,
    val bodyType: BodyType = BodyType.ECTOMORPH,
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
)

sealed class UserManageProfileAction {
    data object NavigateToBack : UserManageProfileAction()
    data object NavigateToEdit : UserManageProfileAction()
    data object NavigateToChangePassword : UserManageProfileAction()
    data object NavigateToAccounts : UserManageProfileAction()
    data object DeleteProfile : UserManageProfileAction()
}

sealed class UserManageProfileEffect {
    data object NavigateToBack : UserManageProfileEffect()
    data object NavigateToEdit : UserManageProfileEffect()
    data object NavigateToChangePassword : UserManageProfileEffect()
    data object NavigateToAccounts : UserManageProfileEffect()
    data class ShowDeleteProfileError(val message: String) : UserManageProfileEffect()
}

class UserAccountSettingsViewModel(
    private val userManager: ActiveAccountManager,
    private val userRepository: UserRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<UserManageProfileUiState>(UserManageProfileUiState.Loading)
    val uiState: StateFlow<UserManageProfileUiState> = _uiState

    private val _effect = SingleSharedFlow<UserManageProfileEffect>()
    val effect: SharedFlow<UserManageProfileEffect> = _effect

    private val user: UserAccount
        get() = userManager.user.value as? UserAccount
            ?: error("❌ current account is not user")

    fun onAction(action: UserManageProfileAction) {
        when (action) {
            UserManageProfileAction.DeleteProfile -> deleteAccount()
            UserManageProfileAction.NavigateToAccounts ->
                _effect.emitIn(screenModelScope, UserManageProfileEffect.NavigateToAccounts)

            UserManageProfileAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, UserManageProfileEffect.NavigateToBack)

            UserManageProfileAction.NavigateToChangePassword ->
                _effect.emitIn(screenModelScope, UserManageProfileEffect.NavigateToChangePassword)

            UserManageProfileAction.NavigateToEdit ->
                _effect.emitIn(screenModelScope, UserManageProfileEffect.NavigateToEdit)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            try {
                val userProfile = userRepository.getUserProfile(user.normalUserId).getOrThrow()

                val formState = UserSettingsManageProfileFormState(
                    userName = userProfile.username,
                    firstName = userProfile.firstName,
                    lastName = userProfile.lastName,
                    height = userProfile.height,
                    weight = userProfile.weight,
                    bodyType = userProfile.bodyType,
                    profileImageUrl = userProfile.profileImageUrl,
                    backgroundImageUrl = userProfile.backgroundImageUrl,
                )
                val hasMultipleProfiles = userManager.accountTypes.value.size > 1

                _uiState.value = UserManageProfileUiState.Content(formState, hasMultipleProfiles)

            } catch (e: Exception) {
                _uiState.value = UserManageProfileUiState.Error(e.message ?: "Profil getirme hatası")
            }
        }
    }

    private fun deleteAccount() {
       _effect.emitIn(screenModelScope, UserManageProfileEffect.ShowDeleteProfileError("TODO"))
    }
}
