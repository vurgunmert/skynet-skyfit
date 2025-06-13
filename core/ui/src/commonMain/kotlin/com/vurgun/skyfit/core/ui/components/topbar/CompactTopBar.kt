package com.vurgun.skyfit.core.ui.components.topbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_left

object CompactTopBar {

    @Composable
    fun TopBarWithTitle(
        title: String,
        onClickBack: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            IconButton(
                onClick = onClickBack,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_chevron_left),
                    contentDescription = "Back",
                    tint = SkyFitColor.text.default,
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = title,
                style = SkyFitTypography.bodyLargeSemibold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun TopbarWithEndAction(
        title: String,
        actionLabel: String,
        onClickBack: () -> Unit,
        onClickAction: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier.fillMaxWidth().padding(vertical = 12.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            TopBarWithTitle(title, onClickBack = onClickBack)

            SkyButton(
                label = actionLabel,
                variant = SkyButtonVariant.Primary,
                size = SkyButtonSize.Micro,
                onClick = onClickAction,
                modifier = Modifier.padding(end = 24.dp)
            )
        }
    }

    // Default: Profile + Actions
    @Composable
    fun TopBarWithAccountAndNavigation(
        actions: @Composable () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 36.dp, max = 64.dp)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            actions.invoke()
        }
    }
}