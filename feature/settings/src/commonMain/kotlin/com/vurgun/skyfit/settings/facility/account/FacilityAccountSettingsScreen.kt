package com.vurgun.skyfit.settings.facility.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
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
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.MobileSettingsDeleteAccountBottomSheet
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.persona.settings.shared.component.FacilityAccountSettingsProfileCard
import com.vurgun.skyfit.settings.facility.profile.FacilityProfileSettingsScreen
import com.vurgun.skyfit.settings.facility.profile.FacilityProfileSettingsUiAction
import com.vurgun.skyfit.settings.shared.account.AccountRoleSettingsScreen
import com.vurgun.skyfit.settings.shared.changepassword.PasswordSettingsScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class FacilityAccountSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityAccountSettingsViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                FacilityAccountSettingsEffect.NavigateToBack -> {
                    navigator.pop()
                }

                FacilityAccountSettingsEffect.NavigateToAccounts -> {
                    navigator.push(AccountRoleSettingsScreen())
                }

                FacilityAccountSettingsEffect.NavigateToChangePassword -> {
                    navigator.push(PasswordSettingsScreen())
                }

                FacilityAccountSettingsEffect.NavigateToEditProfile -> {
                    navigator.push(FacilityProfileSettingsScreen())
                }

                is FacilityAccountSettingsEffect.ShowDeleteError -> {
                    // TODO()
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            FacilityAccountSettingsUiState.Loading -> FullScreenLoaderContent()
            is FacilityAccountSettingsUiState.Error -> {
                val message = (uiState as FacilityAccountSettingsUiState.Error).message
                ErrorScreen(
                    message = message,
                    onConfirm = {
                        viewModel.onAction(FacilityAccountSettingsUiAction.NavigateToBack)
                    })
            }

            is FacilityAccountSettingsUiState.Content -> {
                val content = (uiState as FacilityAccountSettingsUiState.Content)
                MobileFacilitySettingsAccountScreen(content, viewModel::onAction)
            }
        }
    }
}

@Composable
private fun MobileFacilitySettingsAccountScreen(
    content: FacilityAccountSettingsUiState.Content,
    onAction: (FacilityAccountSettingsUiAction) -> Unit
) {
    val windowSize = LocalWindowSize.current
    val scrollState = rememberScrollState()
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val accountState = content.form

    Scaffold(
        topBar = {
            if (windowSize != WindowSize.EXPANDED) {
                CompactTopBar(
                    stringResource(Res.string.settings_account_label),
                    onClickBack = { onAction(FacilityAccountSettingsUiAction.NavigateToBack) })
            }
        },
        bottomBar = {
            if (showDeleteConfirm) {
                MobileSettingsDeleteAccountBottomSheet(
                    onCancelClicked = { showDeleteConfirm = false },
                    onDeleteClicked = { onAction(FacilityAccountSettingsUiAction.DeleteProfile) }
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

            FacilityAccountSettingsProfileCard(
                backgroundImageUrl = accountState.backgroundImageUrl,
                name = accountState.name,
                address = accountState.location,
                note = accountState.biography,
                tags = accountState.profileTags,
                onClick = { onAction(FacilityAccountSettingsUiAction.NavigateToEditProfile) }
            )

            SettingsMenuItem(
                iconRes = Res.drawable.ic_lock,
                text = stringResource(Res.string.settings_change_my_password_label),
                onClick = { onAction(FacilityAccountSettingsUiAction.NavigateToChangePassword) }
            )

            if (content.hasMultipleAccounts) {
                SettingsMenuItem(
                    iconRes = Res.drawable.ic_profile,
                    text = stringResource(Res.string.accounts_title),
                    onClick = { onAction(FacilityAccountSettingsUiAction.NavigateToAccounts) }
                )
            } else {
                SettingsMenuItem(
                    iconRes = Res.drawable.ic_plus,
                    text = stringResource(Res.string.add_account_action),
                    onClick = { onAction(FacilityAccountSettingsUiAction.NavigateToAccounts) }
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

