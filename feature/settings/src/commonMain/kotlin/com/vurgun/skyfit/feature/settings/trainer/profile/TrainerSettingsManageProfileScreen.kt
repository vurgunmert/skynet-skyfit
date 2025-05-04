package com.vurgun.skyfit.feature.settings.trainer.profile

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.menu.SettingsMenuItem
import com.vurgun.skyfit.core.ui.components.special.MobileSettingsDeleteAccountBottomSheet
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.settings.shared.account.ManageAccountsScreen
import com.vurgun.skyfit.feature.settings.shared.changepassword.ChangePasswordScreen
import com.vurgun.skyfit.feature.settings.shared.component.TrainerAccountSettingsProfileCard
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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

class TrainerSettingsManageProfileScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<TrainerSettingsManageProfileViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                TrainerManageProfileEffect.NavigateToBack -> {
                    navigator.pop()
                }

                TrainerManageProfileEffect.NavigateToChangePassword -> {
                    navigator.push(ChangePasswordScreen())
                }

                TrainerManageProfileEffect.NavigateToManageAccounts -> {
                    navigator.push(ManageAccountsScreen())
                }

                TrainerManageProfileEffect.NavigateToEditProfile -> {
                    navigator.push(TrainerSettingsEditProfileScreen())
                }

                is TrainerManageProfileEffect.ShowDeleteError -> {
//                    TODO()
                }
            }
        }
        LaunchedEffect(Unit) {
            viewModel.loadData()
        }


        when (uiState) {
            is TrainerManageProfileUiState.Loading -> FullScreenLoaderContent()

            is TrainerManageProfileUiState.Error -> ErrorScreen(
                message = (uiState as TrainerManageProfileUiState.Error).message,
                onConfirm = { navigator.pop() }
            )

            is TrainerManageProfileUiState.Content -> {
                val content = (uiState as TrainerManageProfileUiState.Content)
                MobileTrainerSettingsAccountScreen(content, viewModel::onAction)
            }
        }
    }

}

@Composable
private fun MobileTrainerSettingsAccountScreen(
    content: TrainerManageProfileUiState.Content,
    onAction: (TrainerManageProfileAction) -> Unit
) {
    val account = content.form
    var showDeleteConfirm by remember { mutableStateOf(false) }


    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = stringResource(Res.string.settings_account_label),
                onClickBack = { onAction(TrainerManageProfileAction.NavigateToBack) })
        },
        bottomBar = {
            if (showDeleteConfirm) {
                MobileSettingsDeleteAccountBottomSheet(
                    onCancelClicked = { showDeleteConfirm = false },
                    onDeleteClicked = { onAction(TrainerManageProfileAction.DeleteAccount) }
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
                onClick = { onAction(TrainerManageProfileAction.NavigateToEditProfile) },
                backgroundImageUrl = account.backgroundImageUrl,
                foregroundImageUrl = account.profileImageUrl,
                name = account.firstName,
                username = account.userName,
                biography = account.biography,
                tags = account.profileTags
            )

            SettingsMenuItem(
                iconRes = Res.drawable.ic_lock,
                text = stringResource(Res.string.settings_change_my_password_label),
                onClick = { onAction(TrainerManageProfileAction.NavigateToChangePassword) }
            )

            if (content.hasMultipleProfiles) {
                SettingsMenuItem(
                    iconRes = Res.drawable.ic_profile,
                    text = stringResource(Res.string.accounts_title),
                    onClick = { onAction(TrainerManageProfileAction.NavigateToManageAccounts) }
                )
            } else {
                SettingsMenuItem(
                    iconRes = Res.drawable.ic_plus,
                    text = stringResource(Res.string.add_account_action),
                    onClick = { onAction(TrainerManageProfileAction.NavigateToManageAccounts) }
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

