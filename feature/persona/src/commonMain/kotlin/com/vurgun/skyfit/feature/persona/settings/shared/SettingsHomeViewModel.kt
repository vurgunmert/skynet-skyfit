package com.vurgun.skyfit.feature.persona.settings.shared

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed interface SettingsMainAction {
    data object NavigateToBack : SettingsMainAction
    data object NavigateToSplash : SettingsMainAction
    data object NavigateToManageProfile : SettingsMainAction
    data object NavigateToNotifications : SettingsMainAction
    data object NavigateToPaymentHistory : SettingsMainAction
    data object NavigateToManageTrainers : SettingsMainAction
    data object NavigateToManageMembers : SettingsMainAction
    data object NavigateToManageBranches : SettingsMainAction
    data object NavigateToSupport : SettingsMainAction
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
}

class SettingsHomeViewModel(
    private val userManager: UserManager
) : ScreenModel {

    private val _effect = SingleSharedFlow<SettingsMainEffect>()
    val effect: SharedFlow<SettingsMainEffect> = _effect

    val selectedTypeId
        get() = userManager.userRole.value.typeId

    val accountTypes = userManager.accountTypes

    init {
        screenModelScope.launch {
            userManager.getAccountTypes()
        }
    }

    fun onAction(action: SettingsMainAction) {
        when (action) {
            SettingsMainAction.NavigateToBack -> {
                emitEffect(SettingsMainEffect.NavigateToBack)
            }

            SettingsMainAction.NavigateToSplash -> {
                emitEffect(SettingsMainEffect.NavigateToSplash)
            }

            SettingsMainAction.NavigateToManageProfile -> {
                emitEffect(SettingsMainEffect.NavigateToManageProfile)
            }

            SettingsMainAction.NavigateToNotifications -> {
                emitEffect(SettingsMainEffect.NavigateToNotifications)
            }

            SettingsMainAction.NavigateToPaymentHistory -> {
                emitEffect(SettingsMainEffect.NavigateToPaymentHistory)
            }

            SettingsMainAction.NavigateToManageTrainers -> {
                emitEffect(SettingsMainEffect.NavigateToManageTrainers)
            }

            SettingsMainAction.NavigateToManageMembers -> {
                emitEffect(SettingsMainEffect.NavigateToManageMembers)
            }

            SettingsMainAction.NavigateToManageBranches -> {
                emitEffect(SettingsMainEffect.NavigateToManageBranches)
            }

            SettingsMainAction.NavigateToSupport -> {
                emitEffect(SettingsMainEffect.NavigateToSupport)
            }
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
        screenModelScope.launch {
            userManager.updateUserType(userTypeId)
        }
    }

    private fun emitEffect(effect: SettingsMainEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}