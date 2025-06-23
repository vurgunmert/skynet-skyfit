package com.vurgun.main.dashboard

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.navigation.SharedScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface DashboardNavigationRoute {
    data object Home : DashboardNavigationRoute
    data object Explore : DashboardNavigationRoute
    data object Social : DashboardNavigationRoute
    data object Nutrition : DashboardNavigationRoute
    data object Profile : DashboardNavigationRoute
    data object Settings : DashboardNavigationRoute
}

sealed interface DashboardUiAction {
    data object OnClickNotifications : DashboardUiAction
    data object OnClickConversations : DashboardUiAction
    data object OnClickChatBot : DashboardUiAction
    data object OnClickHome : DashboardUiAction
    data object OnClickExplore : DashboardUiAction
    data object OnClickSocial : DashboardUiAction
    data object OnClickNutrition : DashboardUiAction
    data object OnClickProfile : DashboardUiAction
    data object OnClickSettings : DashboardUiAction
}

sealed interface DashboardUiEffect {
    data object ShowNotifications : DashboardUiEffect
    data object ShowConversations : DashboardUiEffect
    data object ShowChatBot : DashboardUiEffect
    data object NavigateToHome : DashboardUiEffect
    data object NavigateToExplore : DashboardUiEffect
    data object NavigateToSocial : DashboardUiEffect
    data object NavigateToNutrition : DashboardUiEffect
    data object NavigateToProfile : DashboardUiEffect
    data object NavigateToSettings : DashboardUiEffect
}

class DashboardViewModel(userManager: ActiveAccountManager) : ScreenModel {

    private val _overlayNavigation = MutableStateFlow<ScreenProvider?>(null)
    val compactOverlayNavigation = _overlayNavigation.asStateFlow()

    private val _expandedOverlayNavigation = MutableStateFlow<ScreenProvider?>(null)
    val expandedOverlayNavigation = _expandedOverlayNavigation.asStateFlow()

    private val _effect = SingleSharedFlow<DashboardUiEffect>()
    val effect = _effect.asSharedFlow()

    private val _lastCompactScreen = MutableStateFlow<ScreenProvider>(SharedScreen.Home)
    val lastCompactScreen: StateFlow<ScreenProvider> = _lastCompactScreen

    fun onAction(action: DashboardUiAction) {
        when (action) {
            DashboardUiAction.OnClickNotifications -> {
                _effect.emitIn(screenModelScope, DashboardUiEffect.ShowNotifications)
            }

            DashboardUiAction.OnClickConversations -> {
                _effect.emitIn(screenModelScope, DashboardUiEffect.ShowConversations)
            }

            DashboardUiAction.OnClickChatBot -> {
                _effect.emitIn(screenModelScope, DashboardUiEffect.ShowChatBot)
            }

            DashboardUiAction.OnClickExplore -> {
                _lastCompactScreen.value = SharedScreen.Explore
                _effect.emitIn(screenModelScope, DashboardUiEffect.NavigateToExplore)
            }

            DashboardUiAction.OnClickHome -> {
                _lastCompactScreen.value = SharedScreen.Home
                _effect.emitIn(screenModelScope, DashboardUiEffect.NavigateToHome)
            }

            DashboardUiAction.OnClickNutrition -> {
                _lastCompactScreen.value = SharedScreen.Nutrition
                _effect.emitIn(screenModelScope, DashboardUiEffect.NavigateToNutrition)
            }

            DashboardUiAction.OnClickProfile -> {
                _lastCompactScreen.value = SharedScreen.Profile
                _effect.emitIn(screenModelScope, DashboardUiEffect.NavigateToProfile)
            }

            DashboardUiAction.OnClickSocial -> {
                _lastCompactScreen.value = SharedScreen.Social
                _effect.emitIn(screenModelScope, DashboardUiEffect.NavigateToSocial)
            }

            DashboardUiAction.OnClickSettings -> {
                _lastCompactScreen.value = SharedScreen.Settings
                _effect.emitIn(screenModelScope, DashboardUiEffect.NavigateToSettings)
            }

        }
    }

    fun setCompactOverlay(screen: ScreenProvider?) {
        _overlayNavigation.value = screen
    }

    fun setExpandedOverlay(screen: ScreenProvider?) {
        _expandedOverlayNavigation.value = screen
    }

    fun dismissCompactOverlay() {
        _overlayNavigation.value = null
    }

    fun dismissExpandedOverlay() {
        _expandedOverlayNavigation.value = null
    }
}