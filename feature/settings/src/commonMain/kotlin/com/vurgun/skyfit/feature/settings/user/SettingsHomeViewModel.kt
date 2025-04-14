package com.vurgun.skyfit.feature.settings.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.data.core.storage.Storage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed interface UserSettingsViewEvent {
    data object GoToLogin : UserSettingsViewEvent
}

class SettingsHomeViewModel(
    private val storage: Storage,
    private val userManager: UserManager
) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<UserSettingsViewEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    val selectedRole = userManager.userRole
    val accountTypes = userManager.accountTypes

    fun onLogout() {
        viewModelScope.launch {
            storage.clearValue(UserRepository.UserAuthToken)
            _uiEvents.emit(UserSettingsViewEvent.GoToLogin)
        }
    }

    fun selectUserType(userTypeId: Int) {
        viewModelScope.launch {
            userManager.updateUserType(userTypeId)
        }
    }
}