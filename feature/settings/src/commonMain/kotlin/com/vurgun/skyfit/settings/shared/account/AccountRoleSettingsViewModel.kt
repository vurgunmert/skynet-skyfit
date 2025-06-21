package com.vurgun.skyfit.settings.shared.account

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed interface AccountRoleSettingsAction {
    data object NavigateBack : AccountRoleSettingsAction
    data object NavigateToAddNewAccount : AccountRoleSettingsAction
}

sealed interface AccountRoleSettingsEffect {
    data object NavigateBack : AccountRoleSettingsEffect
    data object NavigateToAddAccount : AccountRoleSettingsEffect
    data class ShowDeleteResult(val success: Boolean, val message: String) : AccountRoleSettingsEffect
}

class AccountRoleSettingsViewModel(
    private val userManager: ActiveAccountManager
) : ScreenModel {

    private val _effect = SingleSharedFlow<AccountRoleSettingsEffect>()
    val effect: SharedFlow<AccountRoleSettingsEffect> = _effect

    val selectedRole = userManager.accountRole
    val registeredAccountTypes = userManager.accountTypes

    fun onAction(action: AccountRoleSettingsAction) {
        when (action) {
            AccountRoleSettingsAction.NavigateBack -> emitEffect(AccountRoleSettingsEffect.NavigateBack)
            AccountRoleSettingsAction.NavigateToAddNewAccount -> emitEffect(AccountRoleSettingsEffect.NavigateToAddAccount)
        }
    }

    fun deleteAccountType(type: AccountType) {
        emitEffect(AccountRoleSettingsEffect.ShowDeleteResult(false, "Hesap silinirken bir hata oluştu."))

//        screenModelScope.launch {
//            try {
//                userManager.deleteAccountType(type)
//                emitEffect(ManageAccountsEffect.ShowDeleteResult(true, "Hesap başarıyla silindi."))
//            } catch (e: Exception) {
//                emitEffect(ManageAccountsEffect.ShowDeleteResult(false, "Hesap silinirken bir hata oluştu."))
//            }
//        }
    }

    private fun emitEffect(effect: AccountRoleSettingsEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}
