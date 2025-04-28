package com.vurgun.skyfit.feature.settings.trainer

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
import com.vurgun.skyfit.core.ui.components.special.MobileSettingsDeleteAccountBottomSheet
import com.vurgun.skyfit.core.ui.components.menu.SettingsMenuItem
import com.vurgun.skyfit.feature.settings.component.TrainerAccountSettingsProfileCard
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.accounts_title
import skyfit.core.ui.generated.resources.add_account_action
import skyfit.core.ui.generated.resources.delete_account_action
import skyfit.core.ui.generated.resources.ic_delete
import skyfit.core.ui.generated.resources.ic_lock
import skyfit.core.ui.generated.resources.ic_plus
import skyfit.core.ui.generated.resources.ic_profile
import skyfit.core.ui.generated.resources.settings_account_label
import skyfit.core.ui.generated.resources.settings_change_my_password_label

@Composable
fun MobileTrainerSettingsAccountScreen(
    goToBack: () -> Unit,
    goToEditProfile: () -> Unit,
    goToChangePassword: () -> Unit,
    goToAddAccount: () -> Unit,
    goToManageAccounts: () -> Unit,
    viewModel: TrainerAccountSettingsViewModel = koinViewModel()
) {

    val account by viewModel.accountState.collectAsState()

    var showDeleteConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.settings_account_label), onClickBack = goToBack)
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

            TrainerAccountSettingsProfileCard(
                onClick = goToEditProfile,
                backgroundImageUrl = account.backgroundImageUrl,
                foregroundImageUrl = account.profileImageUrl,
                name = account.firstName.toString(),
                social = account.email?.substringBefore("@").toString(),
                note = account.biography.toString(),
                tags = account.profileTags
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

