package com.vurgun.skyfit.feature.dashboard.dashboard

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.screen.Screen
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.global.model.UserRole
import com.vurgun.skyfit.core.navigation.SharedScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

enum class DashboardNavigation {
    HOME, EXPLORE, SOCIAL, PROFILE
}

data class OverlayState(val screen: Screen? = null)

data class DashboardUiState(
    val overlayState: OverlayState? = null,
)

class DashboardViewModel(userManager: ActiveAccountManager) : ScreenModel {

    private val _activeNavigation = MutableStateFlow<ScreenProvider>(SharedScreen.Home)
    val activeNavigation = _activeNavigation.asStateFlow()

    private val _overlayNavigation = MutableStateFlow<ScreenProvider?>(null)
    val overlayNavigation = _overlayNavigation.asStateFlow()

    val account = userManager.user
    val userRole = userManager.userRole.map { UserRole.fromId(it.typeId) }

    fun onNavigate(to: ScreenProvider) {
        _overlayNavigation.value = null
        _activeNavigation.value = to
    }

    fun setOverlay(screen: ScreenProvider?) {
        _overlayNavigation.value = screen
    }

    fun dismissOverlay() {
        _overlayNavigation.value = null
    }

    init {
    }
}