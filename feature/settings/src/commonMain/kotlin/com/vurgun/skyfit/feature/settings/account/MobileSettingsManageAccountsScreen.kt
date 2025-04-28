package com.vurgun.skyfit.feature.settings.account

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vurgun.skyfit.core.data.domain.model.UserAccountType
import com.vurgun.skyfit.core.ui.components.button.SecondaryLargeButton
import com.vurgun.skyfit.core.ui.components.image.CircleNetworkImage
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.accounts_title
import skyfit.core.ui.generated.resources.add_account_action
import skyfit.core.ui.generated.resources.ic_minus

@Composable
fun MobileSettingsManageAccountsScreen(
    goToBack: () -> Unit,
    goToAddAccount: () -> Unit,
    viewModel: SettingsManageAccountsViewModel = koinViewModel()
) {
    val registeredAccountTypes by viewModel.registeredAccountTypes.collectAsStateWithLifecycle()
    val selectedRole by viewModel.selectedRole.collectAsStateWithLifecycle()

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.accounts_title), onClickBack = goToBack)
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
                    onClick = goToAddAccount
                )
            }
        }
    }
}

@Composable
private fun SettingsManageAccountTypeItem(
    item: UserAccountType,
    selected: Boolean,
    onDelete: (type: UserAccountType) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .border(1.dp, SkyFitColor.border.secondaryButtonDisabled, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleNetworkImage(
            url = item.profilePhoto.takeUnless { it.isNullOrEmpty() } ?: "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
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