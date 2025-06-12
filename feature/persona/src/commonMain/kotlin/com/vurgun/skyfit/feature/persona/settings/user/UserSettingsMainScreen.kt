package com.vurgun.skyfit.feature.persona.settings.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.menu.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.core.ui.components.menu.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsHomeUiState
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsHomeViewModel
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsMainAction
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsMainEffect
import com.vurgun.skyfit.feature.persona.settings.shared.component.SettingsHomeAccountTypesColumn
import com.vurgun.skyfit.feature.persona.settings.shared.helpsupport.SettingsSupportHelpScreen
import com.vurgun.skyfit.feature.persona.settings.user.notification.UserSettingsNotificationsScreen
import com.vurgun.skyfit.feature.persona.settings.user.payment.UserSettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature.persona.settings.user.profile.UserSettingsManageProfileScreen
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class UserSettingsMainScreen : Screen {


}

