package com.vurgun.skyfit.feature.settings.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.user.repository.UserManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed interface UserSettingsViewEvent {
    data object GoToLogin : UserSettingsViewEvent
}

class SettingsHomeViewModel(
    private val userManager: UserManager
) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<UserSettingsViewEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    val selectedRole = userManager.userRole
    val accountTypes = userManager.accountTypes

    init {
        viewModelScope.launch {
            userManager.getAccountTypes()
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            userManager.logout()
        }
        viewModelScope.launch {
            _uiEvents.emit(UserSettingsViewEvent.GoToLogin)
        }
    }

    fun selectUserType(userTypeId: Int) {
        if (selectedRole.value.typeId == userTypeId) return
        viewModelScope.launch {
            userManager.updateUserType(userTypeId)
        }
    }
}