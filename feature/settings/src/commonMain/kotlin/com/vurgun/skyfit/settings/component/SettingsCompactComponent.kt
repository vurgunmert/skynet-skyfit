package com.vurgun.skyfit.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyPageScaffold
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
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
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_chevron_right
import fiwe.core.ui.generated.resources.logout_action
import fiwe.core.ui.generated.resources.settings_title

internal object SettingsCompactComponent {

    @Composable
    fun LandingContent(
        content: SettingsUiState.Content,
        onAction: (SettingsUiAction) -> Unit,
    ) {
        Layout(
            topbar = {
                CompactTopBar(
                    title = stringResource(Res.string.settings_title),
                    onClickBack = { onAction(SettingsUiAction.OnClickBack) },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            content = {
                NavigationMenu(
                    groupedItems = content.groupedMenuItems,
                    onItemSelected = { onAction(SettingsUiAction.OnDestinationChanged(it)) },
                    accountSelector = {
                        AccountTypeSelector(
                            accounts = content.accountTypes,
                            selectedTypeId = content.selectedAccountTypeId,
                            onSelectType = { onAction(SettingsUiAction.OnAccountRoleChanged(it)) }
                        )
                    },
                    onClickLogout = { onAction(SettingsUiAction.OnClickLogout) },
                    modifier = Modifier
                )
            }
        )
    }

    @Composable
    fun NavigationMenu(
        groupedItems: Map<Int, List<SettingsMenuItem>>,
        onItemSelected: (SettingsDestination) -> Unit = {},
        accountSelector: @Composable (() -> Unit)? = null,
        onClickLogout: () -> Unit = {},
        modifier: Modifier = Modifier,
    ) {
        val lastGroupIndex = groupedItems.keys.maxOrNull()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = {
                Column(
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {
                    Spacer(Modifier.height(24.dp))

                    groupedItems.forEach { (groupIndex, items) ->
                        items.forEach { item ->
                            NavigationMenuItem(
                                titleRes = item.titleRes,
                                iconRes = item.iconRes,
                                onClick = { onItemSelected(item.destination) }
                            )
                            Spacer(Modifier.height(32.dp))
                        }

                        if (groupIndex != lastGroupIndex) {
                            NavigationMenuItemGroupDivider()
                            Spacer(Modifier.height(32.dp))
                        }
                    }

                    if (accountSelector != null) {
                        NavigationMenuItemGroupDivider()
                        Spacer(Modifier.height(32.dp))
                        accountSelector()
                        Spacer(Modifier.height(32.dp))
                    }

                    Spacer(Modifier.height(96.dp))
                }
            },
            bottomBar = {
                SkyButton(
                    label = stringResource(Res.string.logout_action),
                    onClick = onClickLogout,
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    variant = SkyButtonVariant.Primary,
                    size = SkyButtonSize.Large
                )
            }
        )
    }

    @Composable
    private fun NavigationMenuItem(
        titleRes: StringResource,
        iconRes: DrawableResource,
        onClick: () -> Unit
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val isHovered by interactionSource.collectIsHoveredAsState()

        val backgroundColor = when {
            isPressed -> Color(0xFFDDDDDD)
            isHovered -> Color(0xFFF5F5F5)
            else -> Color.Transparent
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null, // disables ripple
                    onClick = onClick
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SkyIcon(
                res = iconRes,
                size = SkyIconSize.Medium
            )

            SkyText(
                text = stringResource(titleRes),
                modifier = Modifier.weight(1f),
                styleType = TextStyleType.BodyMediumMedium,
            )

            SkyIcon(
                res = Res.drawable.ic_chevron_right,
                size = SkyIconSize.Medium
            )
        }
    }


    @Composable
    private fun NavigationMenuItemGroupDivider() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(SkyFitColor.border.default)
        )
    }

    @Composable
    fun Layout(
        topbar: @Composable () -> Unit,
        content: @Composable () -> Unit
    ) {
        SkyPageScaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { topbar() },
            content = { content() }
        )
    }
}