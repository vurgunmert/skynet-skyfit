package com.vurgun.skyfit.feature.persona.settings.shared

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

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