package com.vurgun.skyfit.feature.settings.facility.account

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
import com.vurgun.skyfit.feature.settings.component.FacilityAccountSettingsProfileCard
import com.vurgun.skyfit.feature.settings.facility.profile.FacilityManageProfileViewModel
import com.vurgun.skyfit.ui.core.components.button.PrimaryLargeButton
import com.vurgun.skyfit.ui.core.components.menu.SettingsMenuItem
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.accounts_title
import skyfit.ui.core.generated.resources.add_account_action
import skyfit.ui.core.generated.resources.delete_account_action
import skyfit.ui.core.generated.resources.ic_delete
import skyfit.ui.core.generated.resources.ic_lock
import skyfit.ui.core.generated.resources.ic_plus
import skyfit.ui.core.generated.resources.ic_profile
import skyfit.ui.core.generated.resources.settings_account_label
import skyfit.ui.core.generated.resources.settings_change_my_password_label

@Composable
fun MobileFacilitySettingsAccountScreen(
    goToBack: () -> Unit,
    goToEditProfile: () -> Unit,
    goToChangePassword: () -> Unit,
    goToAddAccount: () -> Unit,
    goToManageAccounts: () -> Unit,
    viewModel: FacilityManageProfileViewModel = koinViewModel()
) {
    val accountState by viewModel.accountState.collectAsState()
    val scrollState = rememberScrollState()

    var showDeleteConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.settings_account_label), onClickBack = goToBack)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            FacilityAccountSettingsProfileCard(
                backgroundImageUrl = accountState.backgroundImageUrl,
                name = accountState.name.toString(),
                address = accountState.location.toString(),
                note = accountState.biography.toString(),
                tags = accountState.profileTags,
                onClick = goToEditProfile
            )

            SettingsMenuItem(
                iconRes = Res.drawable.ic_lock,
                text = stringResource(Res.string.settings_change_my_password_label),
                onClick = goToChangePassword
            )

            if (viewModel.hasMultipleAccounts) {
                SettingsMenuItem(
                    iconRes = Res.drawable.ic_profile,
                    text = stringResource(Res.string.accounts_title),
                    onClick = goToManageAccounts
                )
            } else {
                SettingsMenuItem(
                    iconRes = Res.drawable.ic_plus,
                    text = stringResource(Res.string.add_account_action),
                    onClick = goToManageAccounts
                )
            }

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

