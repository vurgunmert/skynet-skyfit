package com.vurgun.skyfit.feature.persona.settings.shared.account

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed interface ManageAccountsAction {
    data object NavigateBack : ManageAccountsAction
    data object NavigateToAddNewAccount : ManageAccountsAction
}

sealed interface ManageAccountsEffect {
    data object NavigateBack : ManageAccountsEffect
    data object NavigateToAddAccount : ManageAccountsEffect
    data class ShowDeleteResult(val success: Boolean, val message: String) : ManageAccountsEffect
}

class ManageAccountsViewModel(
    private val userManager: ActiveAccountManager
) : ScreenModel {

    private val _effect = SingleSharedFlow<ManageAccountsEffect>()
    val effect: SharedFlow<ManageAccountsEffect> = _effect

    val selectedRole = userManager.userRole
    val registeredAccountTypes = userManager.accountTypes

    fun onAction(action: ManageAccountsAction) {
        when (action) {
            ManageAccountsAction.NavigateBack -> emitEffect(ManageAccountsEffect.NavigateBack)
            ManageAccountsAction.NavigateToAddNewAccount -> emitEffect(ManageAccountsEffect.NavigateToAddAccount)
        }
    }

    fun deleteAccountType(type: AccountType) {
        emitEffect(ManageAccountsEffect.ShowDeleteResult(false, "Hesap silinirken bir hata oluştu."))

//        screenModelScope.launch {
//            try {
//                userManager.deleteAccountType(type)
//                emitEffect(ManageAccountsEffect.ShowDeleteResult(true, "Hesap başarıyla silindi."))
//            } catch (e: Exception) {
//                emitEffect(ManageAccountsEffect.ShowDeleteResult(false, "Hesap silinirken bir hata oluştu."))
//            }
//        }
    }

    private fun emitEffect(effect: ManageAccountsEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}
