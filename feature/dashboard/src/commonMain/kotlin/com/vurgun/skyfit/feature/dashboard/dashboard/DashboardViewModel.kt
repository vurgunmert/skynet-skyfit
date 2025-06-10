package com.vurgun.skyfit.feature.dashboard.dashboard

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.global.model.UserRole
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.feature.dashboard.dashboard.DashboardLayoutExpanded.TopBarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DashboardViewModel(userManager: ActiveAccountManager) : ScreenModel {

    val account = userManager.user
    val userRole = userManager.userRole.map { UserRole.fromId(it.typeId) }

    private val _topBarState = MutableStateFlow(TopBarState())
    val topBarState = _topBarState.asStateFlow()

    init {
        screenModelScope.launch {
            _topBarState.update {
                it.copy(
                    firstName = account.value?.username.orEmpty(),
                    notificationHighlighted = true,
                    conversationsHighlighted = true
                )
            }
        }
    }
}