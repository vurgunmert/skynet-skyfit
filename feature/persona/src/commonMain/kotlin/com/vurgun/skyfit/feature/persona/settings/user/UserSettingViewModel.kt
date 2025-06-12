package com.vurgun.skyfit.feature.persona.settings.user

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.persona.settings.model.SettingsNavigationRoute

sealed class UserSettingUiState {
    data object Loading : UserSettingUiState()
    data class Content(
        val accountPreviews: List<String> = emptyList(),
    ) : UserSettingUiState()

    data class Error(val message: String) : UserSettingUiState()
}

sealed interface UserSettingUiAction {
    data object OnClickBack : UserSettingUiAction
    data class OnClickLogout(val screen: ScreenProvider) : UserSettingUiAction
    data class OnClickNavigationItem(val screen: ScreenProvider) : UserSettingUiAction
    data class OnRequestOverlayNavigation(val screen: ScreenProvider) : UserSettingUiAction
}

sealed interface UserSettingUiEffect {
    data object NavigateBack : UserSettingUiAction
    data class NavigateToScreen(val screen: ScreenProvider) : UserSettingUiAction
}

class UserSettingViewModel : ScreenModel {

    val settingsRoutes = listOf(
        SettingsNavigationRoute.Account,
        SettingsNavigationRoute.PaymentHistory,
        SettingsNavigationRoute.Notifications,
        SettingsNavigationRoute.Support,
    )
}