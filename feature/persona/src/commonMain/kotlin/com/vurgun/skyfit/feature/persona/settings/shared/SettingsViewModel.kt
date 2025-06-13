package com.vurgun.skyfit.feature.persona.settings.shared

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
import com.vurgun.skyfit.feature.persona.settings.model.SettingsNavigationRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SettingsUiState {
    object Loading : SettingsUiState()
    data class Error(val message: String?) : SettingsUiState()
    data class Content(
        val account: Account,
        val accountTypes: List<AccountType>,
        val selectedAccountTypeId: Int

    ) : SettingsUiState()
}

sealed interface SettingsUiAction {
    data object OnClickBack : SettingsUiAction
    data object OnClickLogout : SettingsUiAction
    data class ChangeUserType(val typeId: Int) : SettingsUiAction
    data class OnChangeRoute(val route: SettingsNavigationRoute) : SettingsUiAction
}

sealed interface SettingsUiEffect {
    data object NavigateToBack : SettingsUiEffect
    data object NavigateToSplash : SettingsUiEffect
    data class NavigateToRoute(val route: SettingsNavigationRoute): SettingsUiEffect
}

class SettingsViewModel(
    private val userManager: ActiveAccountManager
) : ScreenModel {

    private val _uiState = UiStateDelegate<SettingsUiState>(SettingsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<SettingsUiEffect>()
    val effect: SharedFlow<SettingsUiEffect> = _effect

    val selectedTypeId
        get() = userManager.userRole.value.typeId

    val accountTypes = userManager.accountTypes

    private val _currentRoute = MutableStateFlow<SettingsNavigationRoute>(SettingsNavigationRoute.Account)
    val currentRoute = _currentRoute.asStateFlow()

    init {
        screenModelScope.launch {
            runCatching {
                userManager.getAccountTypes()
                _uiState.update(
                    SettingsUiState.Content(
                        account = userManager.user.value!!,
                        accountTypes = userManager.accountTypes.value,
                        selectedAccountTypeId = selectedTypeId
                    )
                )
            }.onFailure {
                _uiState.update(SettingsUiState.Error(it.message))
            }
        }
    }

    fun onAction(action: SettingsUiAction) {
        when (action) {
            SettingsUiAction.OnClickBack -> emitEffect(SettingsUiEffect.NavigateToBack)
            SettingsUiAction.OnClickLogout -> emitEffect(SettingsUiEffect.NavigateToSplash)
            is SettingsUiAction.ChangeUserType -> selectUserType(action.typeId)
            is SettingsUiAction.OnChangeRoute -> emitEffect(SettingsUiEffect.NavigateToRoute(action.route))
        }
    }

    fun onLogout() {
        screenModelScope.launch {
            userManager.logout()
            emitEffect(SettingsUiEffect.NavigateToSplash)
        }
    }

    fun selectUserType(userTypeId: Int) {
        if (selectedTypeId == userTypeId) return

        _uiState.update(SettingsUiState.Loading)
        screenModelScope.launch {
            runCatching {
                userManager.updateUserType(userTypeId)
                _uiState.update(
                    SettingsUiState.Content(
                        account = userManager.user.value!!,
                        accountTypes = userManager.accountTypes.value,
                        selectedAccountTypeId = selectedTypeId
                    )
                )
            }.onFailure {
                _uiState.update(SettingsUiState.Error(it.message))
            }
        }
    }

    private fun emitEffect(effect: SettingsUiEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }

    fun getUserRoutes(): List<SettingsNavigationRoute> {
        return listOf(
            SettingsNavigationRoute.Account,
            SettingsNavigationRoute.PaymentHistory,
            SettingsNavigationRoute.Notifications,
            SettingsNavigationRoute.Support
        )
    }

    fun getTrainerRoutes(): List<SettingsNavigationRoute> {
        return listOf(
            SettingsNavigationRoute.Account,
            SettingsNavigationRoute.PaymentHistory,
            SettingsNavigationRoute.Notifications,
            SettingsNavigationRoute.Support
        )
    }

    fun getFacilityRoutes(): List<SettingsNavigationRoute> {
        return listOf(
            SettingsNavigationRoute.Account,
            SettingsNavigationRoute.PaymentHistory,
            SettingsNavigationRoute.Notifications,
            SettingsNavigationRoute.Members,
            SettingsNavigationRoute.Trainers,
            SettingsNavigationRoute.Branches,
            SettingsNavigationRoute.Packages,
            SettingsNavigationRoute.Support
        )
    }
}