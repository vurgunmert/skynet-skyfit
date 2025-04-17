package com.vurgun.skyfit.feature.settings.account

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.domain.model.UserAccountType

class SettingsManageAccountsViewModel(
    userManager: UserManager
) : ViewModel() {
    val selectedRole = userManager.userRole
    val registeredAccountTypes = userManager.accountTypes

    fun deleteAccountType(type: UserAccountType) {
        //TODO:
    }
}
