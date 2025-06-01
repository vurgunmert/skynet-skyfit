package com.vurgun.skyfit.feature.persona.settings.shared

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.persona.domain.model.UserAccountType
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

sealed class SettingsHomeUiState {
    object Loading : SettingsHomeUiState()
    data class Error(val message: String?) : SettingsHomeUiState()
    object Content : SettingsHomeUiState()
}

sealed interface SettingsMainAction {
    data object OnClickBack : SettingsMainAction
    data object OnClickLogout : SettingsMainAction
    data object OnClickManageProfile : SettingsMainAction
    data object OnClickNotifications : SettingsMainAction
    data object OnClickPaymentHistory : SettingsMainAction
    data object OnClickManageTrainers : SettingsMainAction
    data object OnClickManageMembers : SettingsMainAction
    data object OnClickManageBranches : SettingsMainAction
    data object OnClickSupport : SettingsMainAction
    data object OnClickPackages : SettingsMainAction
}

sealed interface SettingsMainEffect {
    data object NavigateToBack : SettingsMainEffect
    data object NavigateToSplash : SettingsMainEffect
    data object NavigateToManageProfile : SettingsMainEffect
    data object NavigateToNotifications : SettingsMainEffect
    data object NavigateToPaymentHistory : SettingsMainEffect
    data object NavigateToManageTrainers : SettingsMainEffect
    data object NavigateToManageMembers : SettingsMainEffect
    data object NavigateToManageBranches : SettingsMainEffect
    data object NavigateToSupport : SettingsMainEffect
    data object NavigateToPackages : SettingsMainEffect
}

class SettingsHomeViewModel(
    private val userManager: UserManager
) : ScreenModel {

    private val _uiState = UiStateDelegate<SettingsHomeUiState>(SettingsHomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<SettingsMainEffect>()
    val effect: SharedFlow<SettingsMainEffect> = _effect

    val selectedTypeId
        get() = userManager.userRole.value.typeId

    val accountTypes = userManager.accountTypes

    init {
        screenModelScope.launch {
            runCatching {
                userManager.getAccountTypes()
                _uiState.update(SettingsHomeUiState.Content)
            }.onFailure {
                _uiState.update(SettingsHomeUiState.Error(it.message))
            }
        }
    }

    fun onAction(action: SettingsMainAction) {
        when (action) {
            SettingsMainAction.OnClickBack -> emitEffect(SettingsMainEffect.NavigateToBack)
            SettingsMainAction.OnClickLogout -> emitEffect(SettingsMainEffect.NavigateToSplash)
            SettingsMainAction.OnClickManageProfile -> emitEffect(SettingsMainEffect.NavigateToManageProfile)
            SettingsMainAction.OnClickNotifications -> emitEffect(SettingsMainEffect.NavigateToNotifications)
            SettingsMainAction.OnClickPaymentHistory -> emitEffect(SettingsMainEffect.NavigateToPaymentHistory)
            SettingsMainAction.OnClickManageTrainers -> emitEffect(SettingsMainEffect.NavigateToManageTrainers)
            SettingsMainAction.OnClickManageMembers -> emitEffect(SettingsMainEffect.NavigateToManageMembers)
            SettingsMainAction.OnClickManageBranches -> emitEffect(SettingsMainEffect.NavigateToManageBranches)
            SettingsMainAction.OnClickSupport -> emitEffect(SettingsMainEffect.NavigateToSupport)
            SettingsMainAction.OnClickPackages -> emitEffect(SettingsMainEffect.NavigateToPackages)
        }
    }

    fun onLogout() {
        screenModelScope.launch {
            userManager.logout()
            emitEffect(SettingsMainEffect.NavigateToSplash)
        }
    }

    fun selectUserType(userTypeId: Int) {
        if (selectedTypeId == userTypeId) return

        _uiState.update(SettingsHomeUiState.Loading)
        screenModelScope.launch {
            runCatching {
                userManager.updateUserType(userTypeId)
                _uiState.update(SettingsHomeUiState.Content)
            }.onFailure {
                _uiState.update(SettingsHomeUiState.Error(it.message))
            }
        }
    }

    private fun emitEffect(effect: SettingsMainEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}