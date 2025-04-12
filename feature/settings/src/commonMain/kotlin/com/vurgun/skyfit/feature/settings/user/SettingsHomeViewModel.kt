package com.vurgun.skyfit.feature.settings.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.data.core.storage.LocalSettingsStore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface UserSettingsViewEvent {
    data object GoToLogin : UserSettingsViewEvent
}

class SettingsHomeViewModel(
    private val store: LocalSettingsStore,
    private val userRepository: UserRepository,
    private val userManager: UserManager
) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<UserSettingsViewEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    private val _accountTypes = MutableStateFlow<List<UserAccountType>>(emptyList())
    val accountTypes = _accountTypes.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getUserTypes().getOrNull()?.let {
                _accountTypes.value = it
            }
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            store.clearAll()
            _uiEvents.emit(UserSettingsViewEvent.GoToLogin)
        }
    }

    fun selectUserType(userTypeId: Int) {
        viewModelScope.launch {
            userRepository.selectUserType(userTypeId)
            userManager.getActiveUser(forceRefresh = true)
        }
    }
}