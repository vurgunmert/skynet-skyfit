package com.vurgun.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.main.component.ExpandedScreenOverlay
import com.vurgun.main.dashboard.DashboardUiAction
import com.vurgun.main.dashboard.DashboardUiEffect
import com.vurgun.main.dashboard.DashboardViewModel
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.FiweLogoDark
import com.vurgun.skyfit.core.ui.components.topbar.ExpandedTopBar
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import com.vurgun.skyfit.core.utils.rememberAccount
import com.vurgun.main.component.ScreenOverlay
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.ui.utils.LocalExpandedOverlayController
import org.jetbrains.compose.resources.DrawableResource
import skyfit.core.ui.generated.resources.*

@Composable
fun DashboardExpanded(viewModel: DashboardViewModel) {

    val mainNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val compactOverlayNavigation by viewModel.compactOverlayNavigation.collectAsState()
    val expandedOverlayNavigation by viewModel.expandedOverlayNavigation.collectAsState()
    val homeScreen = rememberScreen(SharedScreen.Home)

    CompositionLocalProvider(
        LocalCompactOverlayController provides viewModel::setCompactOverlay,
        LocalExpandedOverlayController provides viewModel::setExpandedOverlay,
    ) {

        Navigator(homeScreen, key = "dashboard") { dashboardNavigator ->

            CollectEffect(viewModel.effect) { effect ->
                when (effect) {
                    DashboardUiEffect.NavigateToHome -> {
                        if (dashboardNavigator.lastItem.key != SharedScreen.Home.key) {
                            dashboardNavigator.replace(SharedScreen.Home)
                        }
                    }

                    DashboardUiEffect.NavigateToExplore -> {
                        if (dashboardNavigator.lastItem.key != SharedScreen.Explore.key) {
                            dashboardNavigator.replace(SharedScreen.Explore)
                        }
                    }

                    DashboardUiEffect.NavigateToSocial -> {
                        if (dashboardNavigator.lastItem.key != SharedScreen.Social.key) {
                            dashboardNavigator.replace(SharedScreen.Social)
                        }
                    }

                    DashboardUiEffect.NavigateToNutrition -> {
                        if (dashboardNavigator.lastItem.key != SharedScreen.Nutrition.key) {
                            dashboardNavigator.replace(SharedScreen.Nutrition)
                        }
                    }

                    DashboardUiEffect.NavigateToProfile -> {
                        if (dashboardNavigator.lastItem.key != SharedScreen.Profile.key) {
                            dashboardNavigator.replace(SharedScreen.Profile)
                        }
                    }

                    DashboardUiEffect.NavigateToSettings -> {
                        if (dashboardNavigator.lastItem.key != SharedScreen.Settings.key) {
                            dashboardNavigator.replace(SharedScreen.Settings)
                        }
                    }

                    DashboardUiEffect.ShowChatBot -> dashboardNavigator.replace(SharedScreen.ChatBot)
                    else -> {
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SkyFitColor.background.default)
            ) {
                DashboardExpandedComponents.Sidebar(
                    activeScreen = dashboardNavigator.lastItem,
                    onAction = viewModel::onAction
                )

                Box(Modifier.weight(1f)) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { DashboardExpandedComponents.TopBar(viewModel) },
                        content = { CurrentScreen() }
                    )

                    compactOverlayNavigation?.let { screen ->
                        ScreenOverlay(screen, onDismiss = viewModel::dismissCompactOverlay)
                    }

                    expandedOverlayNavigation?.let { screen ->
                        ExpandedScreenOverlay(screen, onDismiss = viewModel::dismissExpandedOverlay)
                    }
                }
            }
        }
    }

}

private object DashboardExpandedComponents {

    @Composable
    fun TopBar(viewModel: DashboardViewModel) {
        val activeAccount: Account? = rememberAccount()

        activeAccount?.let { account ->
            ExpandedTopBar.TopBarWithAccountAndNavigation(
                account = account,
                onClickNotifications = { viewModel.onAction(DashboardUiAction.OnClickNotifications) },
                onClickConversations = { viewModel.onAction(DashboardUiAction.OnClickConversations) },
                onClickChatBot = { viewModel.onAction(DashboardUiAction.OnClickChatBot) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun Sidebar(
        activeScreen: Screen,
        onAction: (DashboardUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            FiweLogoDark(modifier = Modifier.size(60.dp))

            Spacer(Modifier.height(4.dp))

            SidebarNavigationTopGroup(
                activeScreen = activeScreen,
                onClickHome = { onAction(DashboardUiAction.OnClickHome) },
                onClickExplore = { onAction(DashboardUiAction.OnClickExplore) },
                onClickSocial = { onAction(DashboardUiAction.OnClickSocial) },
                onClickNutrition = { onAction(DashboardUiAction.OnClickNutrition) },
            )

            Spacer(Modifier.weight(1f))

            SidebarBottomNavigationGroup(
                activeScreen = activeScreen,
                onClickProfile = { onAction(DashboardUiAction.OnClickProfile) },
                onClickSettings = { onAction(DashboardUiAction.OnClickSettings) },
            )
        }
    }


    @Composable
    fun SidebarNavigationTopGroup(
        activeScreen: Screen,
        onClickHome: () -> Unit,
        onClickExplore: () -> Unit,
        onClickSocial: () -> Unit,
        onClickNutrition: () -> Unit,
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(SkyFitColor.background.surfaceSecondary)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {

            SidebarNavigationItem(
                selected = activeScreen.key == SharedScreen.Home.key,
                selectedIcon = Res.drawable.ic_home_fill,
                unselectedIcon = Res.drawable.ic_home,
                onClick = onClickHome
            )

            SidebarNavigationItem(
                selected = activeScreen.key == SharedScreen.Explore.key,
                selectedIcon = Res.drawable.ic_barbell_fill,
                unselectedIcon = Res.drawable.ic_barbell,
                onClick = onClickExplore
            )

            SidebarNavigationItem(
                selected = activeScreen.key == SharedScreen.Social.key,
                selectedIcon = Res.drawable.ic_search,
                unselectedIcon = Res.drawable.ic_search,
                onClick = onClickSocial
            )

            SidebarNavigationItem(
                selected = activeScreen.key == SharedScreen.Nutrition.key,
                selectedIcon = Res.drawable.ic_coffee_fill,
                unselectedIcon = Res.drawable.ic_coffee,
                onClick = onClickNutrition
            )
        }
    }

    @Composable
    fun SidebarNavigationItem(
        selected: Boolean,
        selectedIcon: DrawableResource,
        unselectedIcon: DrawableResource,
        size: Dp = 44.dp,
        onClick: () -> Unit
    ) {
        val icon = if (selected) selectedIcon else unselectedIcon
        val tint = if (selected) SkyIconTint.Inverse else SkyIconTint.Default
        val background =
            if (selected) Modifier.background(SkyFitColor.border.secondaryButton, CircleShape) else Modifier

        Box(
            modifier = Modifier
                .then(background)
                .size(size)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            SkyIcon(
                res = icon,
                size = SkyIconSize.Normal,
                tint = tint
            )
        }
    }

    @Composable
    fun SidebarBottomNavigationGroup(
        activeScreen: Screen,
        onClickProfile: () -> Unit,
        onClickSettings: () -> Unit,
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(SkyFitColor.background.surfaceSecondary)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SidebarProfileNavigationItem(
                selected = activeScreen.key == SharedScreen.Profile.key,
                onClick = onClickProfile
            )

            SidebarNavigationItem(
                selected = activeScreen.key == SharedScreen.Settings.key,
                selectedIcon = Res.drawable.ic_settings,
                unselectedIcon = Res.drawable.ic_settings,
                onClick = onClickSettings
            )
        }
    }

    @Composable
    fun SidebarProfileNavigationItem(
        selected: Boolean,
        onClick: () -> Unit
    ) {
        val modifier =
            if (selected) Modifier.border(2.dp, SkyFitColor.border.secondaryButton, CircleShape) else Modifier

        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(48.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            NetworkImage(
                imageUrl = "https://picsum.photos/400/400",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}