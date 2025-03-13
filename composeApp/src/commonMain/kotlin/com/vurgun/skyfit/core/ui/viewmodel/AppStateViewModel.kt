package com.vurgun.skyfit.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.domain.model.AppState
import com.vurgun.skyfit.core.domain.model.User
import com.vurgun.skyfit.core.domain.model.UserState
import com.vurgun.skyfit.core.domain.repository.AppStateRepository
import com.vurgun.skyfit.core.domain.repository.UserRepository
import com.vurgun.skyfit.core.storage.LocalSettingsStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppStateViewModel(
    private val localSettingsStore: LocalSettingsStore,
    private val appStateRepository: AppStateRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _appState = MutableStateFlow<AppState>(AppState.UNKNOWN)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _userState = MutableStateFlow<UserState>(UserState.UNKNOWN)
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        checkAppStateAndAuth()
    }

    private fun checkAppStateAndAuth() {
        viewModelScope.launch {
            _userState.value = UserState.LOADING

            val fetchedAppState = appStateRepository.getAppState()
            _appState.value = fetchedAppState

            if (fetchedAppState != AppState.NORMAL) {
                _userState.value = UserState.NOT_LOGGED_IN
                return@launch
            }

            val cachedToken = localSettingsStore.getToken()
            if (cachedToken.isNullOrEmpty()) {
                _userState.value = UserState.NOT_LOGGED_IN
                return@launch
            }

            val fetchedUser = userRepository.getUser(cachedToken)
            if (fetchedUser != null) {
                _user.value = fetchedUser
                _userState.value = UserState.READY
            } else {
                _userState.value = UserState.NOT_LOGGED_IN
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clearUserData()
            _user.value = null
            _userState.value = UserState.NOT_LOGGED_IN
        }
    }
}
