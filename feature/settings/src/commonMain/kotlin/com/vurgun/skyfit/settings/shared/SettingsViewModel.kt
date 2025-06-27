package com.vurgun.skyfit.settings.shared

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import com.vurgun.skyfit.settings.model.SettingsDestination
import com.vurgun.skyfit.settings.model.SettingsMenuItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

sealed class SettingsUiState {
    object Loading : SettingsUiState()
    data class Error(val message: String?) : SettingsUiState()
    data class Content(
        val account: Account,
        val accountTypes: List<AccountType>,
        val selectedAccountTypeId: Int,
        val menuItems: List<SettingsMenuItem>,
        val groupedMenuItems: Map<Int, List<SettingsMenuItem>>,
        val destination: SettingsDestination = SettingsDestination.Account,
        val showSave: Boolean = false,
    ) : SettingsUiState()
}

sealed interface SettingsUiAction {
    data object OnClickBack : SettingsUiAction
    data object OnClickLogout : SettingsUiAction
    data class OnAccountRoleChanged(val typeId: Int) : SettingsUiAction
    data class OnDestinationChanged(val destination: SettingsDestination) : SettingsUiAction
}

sealed interface SettingsUiEffect {
    data object NavigateToBack : SettingsUiEffect
    data object NavigateToAuth : SettingsUiEffect
    data object NavigateToAccount : SettingsUiEffect
    data object NavigateToPaymentSettings : SettingsUiEffect
    data object NavigateToNotificationSettings : SettingsUiEffect
    data object NavigateToSupportSettings : SettingsUiEffect
    data object NavigateToBranchSettings : SettingsUiEffect
    data object NavigateToLessonPackageSettings : SettingsUiEffect
    data object NavigateToMemberSettings : SettingsUiEffect
    data object NavigateToTrainerSettings : SettingsUiEffect
}

class SettingsViewModel(
    private val userManager: ActiveAccountManager
) : ScreenModel {

    private val _uiState = UiStateDelegate<SettingsUiState>(SettingsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<SettingsUiEffect>()
    val effect: SharedFlow<SettingsUiEffect> = _effect

    private val _debouncedActions = MutableSharedFlow<SettingsUiAction>(extraBufferCapacity = 1)

    init {
        screenModelScope.launch {
            _debouncedActions
                .distinctUntilChanged()
                .collectLatest { action ->
                    domainHandleAction(action)
                }
        }
    }

    fun onAction(action: SettingsUiAction) {
        _debouncedActions.tryEmit(action)
    }

    fun domainHandleAction(action: SettingsUiAction) {
        when (action) {
            SettingsUiAction.OnClickBack -> emitEffect(SettingsUiEffect.NavigateToBack)
            SettingsUiAction.OnClickLogout -> logoutAccount()
            is SettingsUiAction.OnAccountRoleChanged -> changeAccountRole(action.typeId)
            is SettingsUiAction.OnDestinationChanged -> changeDestination(action.destination)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val account = userManager.account.value ?: error("Account not found.")
                val accountType = userManager.getAccountTypes()
                _uiState.update(
                    SettingsUiState.Content(
                        account = account,
                        accountTypes = accountType,
                        selectedAccountTypeId = account.accountRole.typeId,
                        menuItems = getMenuItems(account.accountRole),
                        groupedMenuItems = getGroupedMenuItems(account.accountRole)
                    )
                )
            }.onFailure {
                _uiState.update(SettingsUiState.Error(it.message))
            }
        }
    }

    private fun changeDestination(destination: SettingsDestination) {
        val currentContent = uiState.value as? SettingsUiState.Content ?: return

        _uiState.update(
            currentContent.copy(
                destination = destination,
            )
        )

        when (destination) {
            SettingsDestination.Account -> emitEffect(SettingsUiEffect.NavigateToAccount)
            SettingsDestination.Branches -> emitEffect(SettingsUiEffect.NavigateToBranchSettings)
            SettingsDestination.LessonPackages -> emitEffect(SettingsUiEffect.NavigateToLessonPackageSettings)
            SettingsDestination.Members -> emitEffect(SettingsUiEffect.NavigateToMemberSettings)
            SettingsDestination.Notifications -> emitEffect(SettingsUiEffect.NavigateToNotificationSettings)
            SettingsDestination.Payment -> emitEffect(SettingsUiEffect.NavigateToPaymentSettings)
            SettingsDestination.Support -> emitEffect(SettingsUiEffect.NavigateToSupportSettings)
            SettingsDestination.Trainers -> emitEffect(SettingsUiEffect.NavigateToTrainerSettings)
        }
    }

    fun logoutAccount() {
        screenModelScope.launch {
            userManager.logout()
            emitEffect(SettingsUiEffect.NavigateToAuth)
        }
    }

    private fun changeAccountRole(userTypeId: Int) {
        val currentContent = uiState.value as? SettingsUiState.Content ?: return

        _uiState.update(SettingsUiState.Loading)
        screenModelScope.launch {
            runCatching {
                userManager.updateUserType(userTypeId)
                _uiState.update(currentContent.copy(selectedAccountTypeId = userTypeId))
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

    private fun getMenuItems(accountRole: AccountRole): List<SettingsMenuItem> {
        return when (accountRole) {
            AccountRole.User -> getUserMenuItems()
            AccountRole.Trainer -> getTrainerMenuItems()
            AccountRole.Facility -> getFacilityMenuItems()
            AccountRole.Guest -> emptyList()
        }
    }

    private fun getGroupedMenuItems(accountRole: AccountRole): Map<Int, List<SettingsMenuItem>> {
        return when (accountRole) {
            AccountRole.User -> getUserMenuGroupedItems()
            AccountRole.Trainer -> getTrainerMenuGroupedItems()
            AccountRole.Facility -> getFacilityMenuGroupedItems()
            AccountRole.Guest -> emptyMap()
        }
    }

    private fun getUserMenuItems(): List<SettingsMenuItem> = listOf(
        SettingsMenuItem.Account,
//        SettingsMenuItem.Notifications,
//        SettingsMenuItem.Payment,
        SettingsMenuItem.Support,
    )

    private fun getTrainerMenuItems(): List<SettingsMenuItem> = listOf(
        SettingsMenuItem.Account,
//        SettingsMenuItem.Notifications,
//        SettingsMenuItem.Payment,
        SettingsMenuItem.Support,
    )

    private fun getFacilityMenuItems(): List<SettingsMenuItem> = listOf(
        SettingsMenuItem.Account,
//        SettingsMenuItem.Notifications,
//        SettingsMenuItem.Payment,
        SettingsMenuItem.LessonPackages,
        SettingsMenuItem.Members,
        SettingsMenuItem.Trainers,
        SettingsMenuItem.Support,
//        SettingsMenuItem.Branches,
    )

    private fun getUserMenuGroupedItems(): Map<Int, List<SettingsMenuItem>> =
        listOf(
            1 to SettingsMenuItem.Account,
//            1 to SettingsMenuItem.Payment,
//            2 to SettingsMenuItem.Notifications,
            3 to SettingsMenuItem.Support,
        ).groupBy { it.first }
            .mapValues { entry -> entry.value.map { it.second } }

    private fun getTrainerMenuGroupedItems(): Map<Int, List<SettingsMenuItem>> =
        getUserMenuGroupedItems()

    private fun getFacilityMenuGroupedItems(): Map<Int, List<SettingsMenuItem>> =
        listOf(
            1 to SettingsMenuItem.Account,
//            1 to SettingsMenuItem.Payment,
            1 to SettingsMenuItem.LessonPackages,
//            2 to SettingsMenuItem.Notifications,
            3 to SettingsMenuItem.Members,
            3 to SettingsMenuItem.Trainers,
//            3 to SettingsMenuItem.Branches,
            4 to SettingsMenuItem.Support,
        ).groupBy { it.first }
            .mapValues { entry -> entry.value.map { it.second } }
}