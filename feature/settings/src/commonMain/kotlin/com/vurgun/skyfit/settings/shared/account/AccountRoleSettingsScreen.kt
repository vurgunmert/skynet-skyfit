package com.vurgun.skyfit.settings.shared.account

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.SecondaryLargeButton
import com.vurgun.skyfit.core.ui.components.image.CircleNetworkImage
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.accounts_title
import fiwe.core.ui.generated.resources.add_account_action
import fiwe.core.ui.generated.resources.ic_minus

class AccountRoleSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<AccountRoleSettingsViewModel>()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                AccountRoleSettingsEffect.NavigateBack -> {
                    appNavigator.pop()
                }

                AccountRoleSettingsEffect.NavigateToAddAccount -> {
                    appNavigator.push(SharedScreen.Onboarding)
                }

                is AccountRoleSettingsEffect.ShowDeleteResult -> Unit
            }
        }

        MobileSettingsManageAccountsScreen(viewModel)
    }

}

@Composable
private fun MobileSettingsManageAccountsScreen(viewModel: AccountRoleSettingsViewModel) {
    val registeredAccountTypes by viewModel.registeredAccountTypes.collectAsState()
    val selectedRole by viewModel.selectedRole.collectAsState()

    SkyFitMobileScaffold(
        topBar = {
            CompactTopBar(
                title = stringResource(Res.string.accounts_title),
                onClickBack = { viewModel.onAction(AccountRoleSettingsAction.NavigateBack) })

        }
    ) {

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            registeredAccountTypes.forEach {
                SettingsManageAccountTypeItem(
                    item = it,
                    selected = selectedRole.typeId == it.typeId,
                    onDelete = viewModel::deleteAccountType
                )
            }

            if (registeredAccountTypes.size < 3) {
                SecondaryLargeButton(
                    text = stringResource(Res.string.add_account_action),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onAction(AccountRoleSettingsAction.NavigateToAddNewAccount) }
                )
            }
        }
    }
}

@Composable
private fun SettingsManageAccountTypeItem(
    item: AccountType,
    selected: Boolean,
    onDelete: (type: AccountType) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .border(1.dp, SkyFitColor.border.secondaryButtonDisabled, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleNetworkImage(
            url = item.photoImageUrl.takeUnless { it.isNullOrEmpty() } ?: "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
            size = 40.dp,
            modifier = Modifier
        )
        Spacer(Modifier.width(16.dp))
        BodyMediumSemiboldText(
            text = "${item.fullName} (${item.typeName})",
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(24.dp))
        if (selected) {
            Icon(
                painter = painterResource(Res.drawable.ic_minus),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = SkyFitColor.icon.default
            )
        }
    }
}