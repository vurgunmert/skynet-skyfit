package com.vurgun.skyfit.feature_settings.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsDeleteAccountBottomSheet
import com.vurgun.skyfit.feature_settings.ui.SettingsMenuItem
import com.vurgun.skyfit.feature_settings.ui.component.UserAccountSettingsProfileCard
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.delete_account_action
import skyfit.composeapp.generated.resources.ic_delete
import skyfit.composeapp.generated.resources.ic_lock
import skyfit.composeapp.generated.resources.settings_account_label
import skyfit.composeapp.generated.resources.settings_change_my_password_label

@Composable
fun MobileUserSettingsAccountScreen(navigator: Navigator) {

    val viewModel: SkyFitUserAccountSettingsViewModel = koinInject()

    val account by viewModel.accountState.collectAsState()

    var showDeleteConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.settings_account_label), onClickBack = { navigator.popBackStack() })
        },
        bottomBar = {
            if (showDeleteConfirm) {
                MobileSettingsDeleteAccountBottomSheet(
                    onCancelClicked = { showDeleteConfirm = false },
                    onDeleteClicked = viewModel::deleteAccount
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            UserAccountSettingsProfileCard(
                backgroundImageUrl = account.backgroundImageUrl,
                foregroundImageUrl = account.profileImageUrl,
                name = account.firstName.toString(),
                social = account.email?.substringBefore("@").toString(),
                height = account.height.toString(),
                weight = account.weight.toString(),
                bodyType = account.bodyType.turkishShort,
                onClick = { navigator.jumpAndStay(MobileNavRoute.Settings.User.EditProfile) },
            )

            SettingsMenuItem(
                iconRes = Res.drawable.ic_lock,
                text = stringResource(Res.string.settings_change_my_password_label),
                onClick = { navigator.jumpAndStay(MobileNavRoute.Settings.User.ChangePassword) })

            PrimaryLargeButton(
                text = stringResource(Res.string.delete_account_action),
                leftIconPainter = painterResource(Res.drawable.ic_delete),
                modifier = Modifier.fillMaxWidth(),
                onClick = { showDeleteConfirm = true }
            )

            Spacer(Modifier.height(124.dp))
        }
    }
}