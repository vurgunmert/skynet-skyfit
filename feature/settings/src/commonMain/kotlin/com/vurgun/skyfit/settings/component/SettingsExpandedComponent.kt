package com.vurgun.skyfit.settings.component

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
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.settings.model.SettingsDestination
import com.vurgun.skyfit.settings.model.SettingsMenuItem
import com.vurgun.skyfit.settings.shared.SettingsUiAction
import com.vurgun.skyfit.settings.shared.SettingsUiState
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

internal object SettingsExpandedComponent {

    @Composable
    fun LandingContent(
        content: SettingsUiState.Content,
        onAction: (SettingsUiAction) -> Unit,
        container: @Composable () -> Unit
    ) {
        Row(
            modifier = Modifier.padding(bottom = 16.dp, end = 16.dp).fillMaxSize()
        ) {
            NavigationMenu(
                items = content.menuItems,
                destination = content.destination,
                onItemSelected = { onAction(SettingsUiAction.OnDestinationChanged(it)) },
                accountSelector = {
                    AccountTypeSelector(
                        accounts = content.accountTypes,
                        selectedTypeId = content.selectedAccountTypeId,
                        onSelectType = { onAction(SettingsUiAction.OnAccountRoleChanged(it)) }
                    )
                },
                showSave = content.showSave,
                onClickSave = { TODO() },
                onClickLogout = { onAction(SettingsUiAction.OnClickLogout) },
                modifier = Modifier.width(315.dp).fillMaxHeight()
            )
            Spacer(Modifier.width(16.dp))

            container()
        }
    }

    @Composable
    fun NavigationMenuItem(
        titleRes: StringResource,
        iconRes: DrawableResource,
        selected: Boolean = false,
        onClick: () -> Unit = {},
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier
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
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = SkyFitColor.icon.default
            )

            Text(
                text = stringResource(titleRes),
                modifier = Modifier.weight(1f),
                style = SkyFitTypography.bodyMediumMedium
            )
        }
    }

    @Composable
    fun NavigationMenu(
        items: List<SettingsMenuItem>,
        destination: SettingsDestination,
        onItemSelected: (SettingsDestination) -> Unit = {},
        accountSelector: @Composable (() -> Unit)? = null,
        showSave: Boolean = false,
        onClickSave: () -> Unit = {},
        onClickLogout: () -> Unit = {},
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .width(315.dp)
                .fillMaxHeight()
                .background(
                    color = SkyFitColor.background.surfaceTertiary,
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(16.dp)
        ) {
            items.forEach { item ->
                NavigationMenuItem(
                    titleRes = item.titleRes,
                    iconRes = item.iconRes,
                    selected = destination == item.destination,
                    onClick = { onItemSelected(item.destination) }
                )
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.weight(1f))

            if (accountSelector != null) {
                accountSelector()
                Spacer(Modifier.height(32.dp))
            }

            if (showSave) {
                SkyButton(
                    label = stringResource(Res.string.save_changes_action),
                    onClick = onClickSave,
                    modifier = Modifier.fillMaxWidth(),
                    variant = SkyButtonVariant.Primary,
                    size = SkyButtonSize.Large
                )
                Spacer(Modifier.height(16.dp))
            }

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
    fun SideNavigationMenu(
        modifier: Modifier = Modifier,
        routes: List<SettingsDestination>,
        activeScreen: Screen,
        onClickRoute: (SettingsDestination) -> Unit = {},
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
        items: List<SettingsDestination>,
        onClick: (SettingsDestination) -> Unit
    ) {
        items.forEach { item ->
            when (item) {
                SettingsDestination.Branches -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.branches_label),
                        selected = item == activeScreen,
                        iconRes = Res.drawable.ic_building,
                        onClick = { onClick(item) }
                    )
                }

                SettingsDestination.Members -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.members_label),
                        iconRes = Res.drawable.ic_posture_fill,
                        onClick = { onClick(item) }
                    )
                }

                SettingsDestination.LessonPackages -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.packages_label),
                        iconRes = Res.drawable.ic_package,
                        onClick = { onClick(item) }
                    )
                }

                SettingsDestination.Trainers -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.trainers_label),
                        iconRes = Res.drawable.ic_athletic_performance,
                        onClick = { onClick(item) }
                    )
                }

                SettingsDestination.Account -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.settings_account_label),
                        iconRes = Res.drawable.ic_profile,
                        onClick = { onClick(item) }
                    )
                }

                SettingsDestination.Notifications -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.notifications_label),
                        iconRes = Res.drawable.ic_bell,
                        onClick = { onClick(item) }
                    )
                }

                SettingsDestination.Payment -> {
                    SideMenuNavigationItem(
                        text = stringResource(Res.string.settings_payment_history_label),
                        iconRes = Res.drawable.ic_credit_card,
                        onClick = { onClick(item) }
                    )
                }

                SettingsDestination.Support -> {
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