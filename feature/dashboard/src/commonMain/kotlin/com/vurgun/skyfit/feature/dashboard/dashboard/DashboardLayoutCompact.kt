package com.vurgun.skyfit.feature.dashboard.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.special.ChatBotButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.feature.dashboard.DashboardScreen
import org.jetbrains.compose.resources.DrawableResource
import skyfit.core.ui.generated.resources.*

internal object DashboardLayoutCompact {

    enum class DashboardTab {
        HOME, EXPLORE, SOCIAL, PROFILE
    }

    @Composable
    fun Screen(viewModel: DashboardViewModel) {
        val mainNavigator = LocalNavigator.currentOrThrow
        var selectedTab by rememberSaveable { mutableStateOf(DashboardTab.HOME) }

        val screens = mapOf(
            DashboardTab.HOME to rememberScreen(DashboardScreen.Home),
            DashboardTab.EXPLORE to rememberScreen(DashboardScreen.Explore),
            DashboardTab.SOCIAL to rememberScreen(DashboardScreen.Social),
            DashboardTab.PROFILE to rememberScreen(DashboardScreen.Profile),
        )

        val currentScreen = screens[selectedTab] ?: error("Invalid tab")

        Navigator(currentScreen) { dashboardNavigator ->
            CrossfadeTransition(navigator = dashboardNavigator)

            SkyFitMobileScaffold(
                bottomBar = {
                    CompactDashboardBottomTabBar(
                        modifier = Modifier.padding(bottom = 12.dp),
                        selectedTab = selectedTab,
                        onTabSelected = { tab ->
                            selectedTab = tab
                            dashboardNavigator.replace(screens[tab]!!)
                        },
                        onClickAIBot = {
                            mainNavigator.push(SharedScreen.ChatBot)
                        }
                    )
                }
            ) {
                CurrentScreen()
            }
        }
    }

    @Composable
    private fun CompactDashboardBottomTabBar(
        modifier: Modifier = Modifier,
        selectedTab: DashboardTab,
        onTabSelected: (DashboardTab) -> Unit,
        onClickAIBot: () -> Unit
    ) {
        Box(
            modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 24.dp)
                        .wrapContentWidth()
                        .shadow(
                            8.dp,
                            shape = RoundedCornerShape(50),
                            ambientColor = Color.Black,
                            spotColor = Color.Black
                        )
                        .background(SkyFitColor.specialty.buttonBgRest, RoundedCornerShape(50)),
                    horizontalArrangement = Arrangement.spacedBy(0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BlockTouchSpacer(width = 16.dp)

                    BottomBarItem(
                        selected = selectedTab == DashboardTab.HOME,
                        selectedIcon = Res.drawable.ic_home_fill,
                        unselectedIcon = Res.drawable.ic_home,
                        onClick = { onTabSelected(DashboardTab.HOME) }
                    )

                    BottomBarItem(
                        selected = selectedTab == DashboardTab.EXPLORE,
                        selectedIcon = Res.drawable.ic_barbell_fill,
                        unselectedIcon = Res.drawable.ic_barbell,
                        onClick = { onTabSelected(DashboardTab.EXPLORE) }
                    )

                    BottomBarItem(
                        selected = selectedTab == DashboardTab.SOCIAL,
                        selectedIcon = Res.drawable.ic_plus,
                        unselectedIcon = Res.drawable.ic_search,
                        onClick = { onTabSelected(DashboardTab.SOCIAL) }
                    )

                    BottomBarItem(
                        selected = selectedTab == DashboardTab.PROFILE,
                        selectedIcon = Res.drawable.ic_profile_fill,
                        unselectedIcon = Res.drawable.ic_profile,
                        onClick = { onTabSelected(DashboardTab.PROFILE) }
                    )

                    BlockTouchSpacer(width = 16.dp)
                }
            }

            ChatBotButtonComponent(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 112.dp, end = 20.dp),
                onClick = onClickAIBot
            )
        }
    }

    @Composable
    private fun BottomBarItem(
        selected: Boolean,
        selectedIcon: DrawableResource,
        unselectedIcon: DrawableResource,
        onClick: () -> Unit
    ) {
        val iconRes = if (selected) selectedIcon else unselectedIcon

        Box(
            modifier = Modifier
                .size(width = 52.dp, height = 68.dp) // 20 icon + 16 side spacing on both
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            SkyIcon(
                res = iconRes,
                size = SkyIconSize.Normal,
                tint = SkyIconTint.Inverse
            )
        }
    }

    @Composable
    private fun BlockTouchSpacer(width: Dp) {
        Box(
            modifier = Modifier
                .width(width)
                .height(68.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    // no-op, blocks click-through
                }
        )
    }

}
