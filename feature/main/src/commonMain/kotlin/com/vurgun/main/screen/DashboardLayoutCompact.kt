package com.vurgun.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.main.dashboard.DashboardUiAction
import com.vurgun.main.dashboard.DashboardUiEffect
import com.vurgun.main.dashboard.DashboardViewModel
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.special.ChatBotButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.main.component.ScreenOverlay
import com.vurgun.main.screen.DashboardCompactComponent.BottomBar
import org.jetbrains.compose.resources.DrawableResource
import skyfit.core.ui.generated.resources.*

@Composable
fun DashboardCompact(viewModel: DashboardViewModel) {
    val mainNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val overlayNavigation by viewModel.overlayNavigation.collectAsState()
    val homeScreen = rememberScreen(SharedScreen.Home)

    Navigator(homeScreen) { dashboardNavigator ->

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                DashboardUiEffect.NavigateToHome -> dashboardNavigator.replace(SharedScreen.Home)
                DashboardUiEffect.NavigateToExplore -> dashboardNavigator.replace(SharedScreen.Explore)
                DashboardUiEffect.NavigateToSocial -> dashboardNavigator.replace(SharedScreen.Social)
                DashboardUiEffect.NavigateToNutrition -> dashboardNavigator.replace(SharedScreen.Nutrition)
                DashboardUiEffect.NavigateToProfile -> dashboardNavigator.replace(SharedScreen.Profile)
                DashboardUiEffect.ShowChatBot -> mainNavigator.push(SharedScreen.ChatBot)
                DashboardUiEffect.ShowConversations -> mainNavigator.push(SharedScreen.Conversations)
                DashboardUiEffect.ShowNotifications -> mainNavigator.push(SharedScreen.Notifications)
                else -> Unit
            }
        }

        SkyFitMobileScaffold(
            bottomBar = {
                BottomBar(
                    modifier = Modifier.padding(bottom = 12.dp),
                    activeScreen = dashboardNavigator.lastItem,
                    onClickHome = { viewModel.onAction(DashboardUiAction.OnClickHome) },
                    onClickExplore = { viewModel.onAction(DashboardUiAction.OnClickExplore) },
                    onClickSocial = { viewModel.onAction(DashboardUiAction.OnClickSocial) },
                    onClickProfile = { viewModel.onAction(DashboardUiAction.OnClickProfile) },
                    onClickAIBot = { viewModel.onAction(DashboardUiAction.OnClickChatBot) }
                )
            }
        ) {
            Box(Modifier.fillMaxSize()) {
                CrossfadeTransition(navigator = dashboardNavigator)

                overlayNavigation?.let {
                    ScreenOverlay(it, onDismiss = viewModel::dismissOverlay)
                }
            }
        }
    }
}


private object DashboardCompactComponent {

    @Composable
    fun BottomBar(
        modifier: Modifier = Modifier,
        activeScreen: Screen,
        onClickHome: () -> Unit,
        onClickExplore: () -> Unit,
        onClickSocial: () -> Unit,
        onClickProfile: () -> Unit,
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
                        selected = activeScreen == SharedScreen.Home,
                        selectedIcon = Res.drawable.ic_home_fill,
                        unselectedIcon = Res.drawable.ic_home,
                        onClick = onClickHome
                    )

                    BottomBarItem(
                        selected = activeScreen == SharedScreen.Explore,
                        selectedIcon = Res.drawable.ic_barbell_fill,
                        unselectedIcon = Res.drawable.ic_barbell,
                        onClick = onClickExplore
                    )

                    BottomBarItem(
                        selected = activeScreen == SharedScreen.Social,
                        selectedIcon = Res.drawable.ic_plus,
                        unselectedIcon = Res.drawable.ic_search,
                        onClick = onClickSocial
                    )

                    BottomBarItem(
                        selected = activeScreen == SharedScreen.Profile,
                        selectedIcon = Res.drawable.ic_profile_fill,
                        unselectedIcon = Res.drawable.ic_profile,
                        onClick = onClickProfile
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
    fun BottomBarItem(
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
    fun BlockTouchSpacer(width: Dp) {
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
