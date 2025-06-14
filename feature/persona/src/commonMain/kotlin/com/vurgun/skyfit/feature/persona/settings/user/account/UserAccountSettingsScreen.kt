package com.vurgun.skyfit.feature.persona.settings.user.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
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
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.dialog.rememberErrorDialogState
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.menu.SettingsMenuItem
import com.vurgun.skyfit.core.ui.components.special.MobileSettingsDeleteAccountBottomSheet
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.persona.settings.shared.account.ManageAccountsScreen
import com.vurgun.skyfit.feature.persona.settings.shared.changepassword.ChangePasswordScreen
import com.vurgun.skyfit.feature.persona.settings.shared.component.UserAccountSettingsProfileCard
import com.vurgun.skyfit.feature.persona.settings.user.profile.UserProfileSettingsScreen
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

class UserAccountSettingsScreen : Screen {

    override val key: ScreenKey
        get() = "settings:user:account"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<UserAccountSettingsViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val deleteProfileErrorDialogState = rememberErrorDialogState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                UserManageProfileEffect.NavigateToAccounts -> {
                    navigator.push(ManageAccountsScreen())
                }

                UserManageProfileEffect.NavigateToBack -> {
                    navigator.pop()
                }

                UserManageProfileEffect.NavigateToChangePassword -> {
                    navigator.push(ChangePasswordScreen())
                }

                UserManageProfileEffect.NavigateToEdit -> {
                    navigator.push(UserProfileSettingsScreen())
                }

                is UserManageProfileEffect.ShowDeleteProfileError -> deleteProfileErrorDialogState.show(effect.message)
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            UserManageProfileUiState.Loading -> FullScreenLoaderContent()
            is UserManageProfileUiState.Error -> {
                val message = (uiState as UserManageProfileUiState.Error).message
                ErrorScreen(message = message, onConfirm = { viewModel.onAction(UserManageProfileAction.NavigateToBack) })
            }

            is UserManageProfileUiState.Content -> {
                val content = (uiState as UserManageProfileUiState.Content)

                UserAccountSettingsCompact(
                    content = content,
                    onAction = viewModel::onAction
                )
            }
        }
    }
}

@Composable
private fun UserAccountSettingsCompact(
    content: UserManageProfileUiState.Content,
    onAction: (UserManageProfileAction) -> Unit
) {
    val account = content.form
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SkyFitScreenHeader(
                stringResource(Res.string.settings_account_label),
                onClickBack = { onAction(UserManageProfileAction.NavigateToBack) })
        },
        bottomBar = {
            if (showDeleteConfirm) {
                MobileSettingsDeleteAccountBottomSheet(
                    onCancelClicked = { showDeleteConfirm = false },
                    onDeleteClicked = { onAction(UserManageProfileAction.DeleteProfile) }
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
                name = account.firstName,
                username = account.userName,
                height = "${account.height} ${account.heightUnit.label}",
                weight = "${account.weight} ${account.weightUnit.shortLabel}",
                bodyType = account.bodyType.turkishShort,
                onClick = { onAction(UserManageProfileAction.NavigateToEdit) },
            )

            SettingsMenuItem(
                iconRes = Res.drawable.ic_lock,
                text = stringResource(Res.string.settings_change_my_password_label),
                onClick = { onAction(UserManageProfileAction.NavigateToChangePassword) }
            )

            if (content.hasMultipleProfiles) {
                SettingsMenuItem(
                    iconRes = Res.drawable.ic_profile,
                    text = stringResource(Res.string.accounts_title),
                    onClick = { onAction(UserManageProfileAction.NavigateToAccounts) }
                )
            } else {
                SettingsMenuItem(
                    iconRes = Res.drawable.ic_plus,
                    text = stringResource(Res.string.add_account_action),
                    onClick = { onAction(UserManageProfileAction.NavigateToAccounts) }
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


@Composable
private fun UserAccountSettingsCompact2(
    content: UserManageProfileUiState.Content,
    onAction: (UserManageProfileAction) -> Unit
) {
    val account = content.form
    var showDeleteConfirm by remember { mutableStateOf(false) }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                stringResource(Res.string.settings_account_label),
                onClickBack = { onAction(UserManageProfileAction.NavigateToBack) })
        },
        bottomBar = {
            if (showDeleteConfirm) {
                MobileSettingsDeleteAccountBottomSheet(
                    onCancelClicked = { showDeleteConfirm = false },
                    onDeleteClicked = { onAction(UserManageProfileAction.DeleteProfile) }
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
                name = account.firstName,
                username = account.userName,
                height = "${account.height} ${account.heightUnit.label}",
                weight = "${account.weight} ${account.weightUnit.shortLabel}",
                bodyType = account.bodyType.turkishShort,
                onClick = { onAction(UserManageProfileAction.NavigateToEdit) },
            )

            SettingsMenuItem(
                iconRes = Res.drawable.ic_lock,
                text = stringResource(Res.string.settings_change_my_password_label),
                onClick = { onAction(UserManageProfileAction.NavigateToChangePassword) }
            )

            if (content.hasMultipleProfiles) {
                SettingsMenuItem(
                    iconRes = Res.drawable.ic_profile,
                    text = stringResource(Res.string.accounts_title),
                    onClick = { onAction(UserManageProfileAction.NavigateToAccounts) }
                )
            } else {
                SettingsMenuItem(
                    iconRes = Res.drawable.ic_plus,
                    text = stringResource(Res.string.add_account_action),
                    onClick = { onAction(UserManageProfileAction.NavigateToAccounts) }
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