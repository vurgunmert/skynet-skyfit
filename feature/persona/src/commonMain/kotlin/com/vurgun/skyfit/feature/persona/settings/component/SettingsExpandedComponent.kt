package com.vurgun.skyfit.feature.persona.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.feature.persona.settings.model.SettingsNavigationRoute
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

internal object SettingsExpandedComponent {

    @Composable
    fun SideNavigationMenu(
        modifier: Modifier = Modifier,
        routes: List<SettingsNavigationRoute>,
        activeScreen: Screen,
        onClickRoute: (SettingsNavigationRoute) -> Unit = {},
        onClickLogout: () -> Unit = {}
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .width(315.dp)
                .fillMaxHeight()
                .background(
                    color = SkyFitColor.background.surfaceTertiary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            SideMenuNavigationItemColumn(activeScreen, routes, onClickRoute)

            Spacer(Modifier.weight(1f))

            SkyButton(
                label = stringResource(Res.string.logout_action),
                onClick = onClickLogout,
                modifier = Modifier.fillMaxWidth(),
                variant = SkyButtonVariant.Primary,
                size = SkyButtonSize.Large
            )
        }
    }

    @Composable
    fun SideMenuNavigationItemColumn(
        activeScreen: Screen,
        items: List<SettingsNavigationRoute>,
        onClick: (SettingsNavigationRoute) -> Unit
    ) {
        items.forEach { item ->
            when (item) {
                SettingsNavigationRoute.Branches -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.branches_label),
                        selected = item == activeScreen,
                        iconRes = Res.drawable.ic_building,
                        onClick = { onClick(item) }
                    )
                }

                SettingsNavigationRoute.Members -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.members_label),
                        iconRes = Res.drawable.ic_posture_fill,
                        onClick = { onClick(item) }
                    )
                }

                SettingsNavigationRoute.Packages -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.packages_label),
                        iconRes = Res.drawable.ic_package,
                        onClick = { onClick(item) }
                    )
                }

                SettingsNavigationRoute.Trainers -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.trainers_label),
                        iconRes = Res.drawable.ic_athletic_performance,
                        onClick = { onClick(item) }
                    )
                }

                SettingsNavigationRoute.Account -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.settings_account_label),
                        iconRes = Res.drawable.ic_profile,
                        onClick = { onClick(item) }
                    )
                }

                SettingsNavigationRoute.Notifications -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.notifications_label),
                        iconRes = Res.drawable.ic_bell,
                        onClick = { onClick(item) }
                    )
                }

                SettingsNavigationRoute.PaymentHistory -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.settings_payment_history_label),
                        iconRes = Res.drawable.ic_credit_card,
                        onClick = { onClick(item) }
                    )
                }

                SettingsNavigationRoute.Support -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.settings_support_label),
                        iconRes = Res.drawable.ic_question_circle,
                        onClick = { onClick(item) }
                    )
                }
            }
        }
    }

    @Composable
    fun SideMenuNavigationItem(
        text: String,
        selected: Boolean = false,
        iconRes: DrawableResource? = null,
        onClick: () -> Unit = {}
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClick)
                .background(
                    color = if (selected) SkyFitColor.background.fillTransparentSecondary else SkyFitColor.transparent,
                )
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (iconRes != null) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = SkyFitColor.icon.default
                )
            }

            Text(
                text = text,
                modifier = Modifier.weight(1f),
                style = SkyFitTypography.bodyMediumMedium
            )
        }
    }
}