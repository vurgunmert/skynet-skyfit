package com.vurgun.skyfit.feature.dashboard.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.LocalOverlayController
import com.vurgun.skyfit.feature.dashboard.DashboardScreen
import com.vurgun.skyfit.feature.dashboard.component.ScreenOverlay
import com.vurgun.skyfit.feature.dashboard.dashboard.DashboardViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.*

@Composable
fun DashboardExpanded(viewModel: DashboardViewModel) {

    val activeNavigation by viewModel.activeNavigation.collectAsState()
    val overlayNavigation by viewModel.overlayNavigation.collectAsState()
    val homeScreen = rememberScreen(DashboardScreen.Home)

    CompositionLocalProvider(LocalOverlayController provides viewModel::setOverlay) {

        Navigator(homeScreen, key = "dashboard") { dashboardNavigator ->

            LaunchedEffect(activeNavigation) {
                dashboardNavigator.replaceAll(activeNavigation)
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SkyFitColor.background.default)
            ) {
                DashboardExpandedComponents.Sidebar(
                    activeNavigation = activeNavigation,
                    onNavigate = { dashboardNavigator.replaceAll(it) },
                    modifier = Modifier
                )

                Box(Modifier.weight(1f)) {
                    CurrentScreen()

                    overlayNavigation?.let {
                        ScreenOverlay(it, onDismiss = viewModel::dismissOverlay)
                    }
                }
            }
        }
    }
}

private object DashboardExpandedComponents {

    @Composable
    fun Sidebar(
        activeNavigation: ScreenProvider,
        onNavigate: (ScreenProvider) -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painterResource(Res.drawable.logo_skyfit),
                null,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Inside
            )

            Spacer(Modifier.height(4.dp))

            SidebarNavigationTopGroup(
                activeNavigation = activeNavigation,
                onNavigate = onNavigate
            )

            Spacer(Modifier.weight(1f))

            SidebarBottomNavigationGroup(
                activeNavigation = activeNavigation,
                onNavigate = onNavigate
            )
        }
    }


    @Composable
    fun SidebarNavigationTopGroup(
        activeNavigation: ScreenProvider,
        onNavigate: (ScreenProvider) -> Unit
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
                selected = activeNavigation is SharedScreen.Home,
                selectedIcon = Res.drawable.ic_home_fill,
                unselectedIcon = Res.drawable.ic_home,
                onClick = { onNavigate(SharedScreen.Home) }
            )

            SidebarNavigationItem(
                selected = activeNavigation is SharedScreen.Explore,
                selectedIcon = Res.drawable.ic_barbell_fill,
                unselectedIcon = Res.drawable.ic_barbell,
                onClick = { onNavigate(SharedScreen.Explore) }
            )

            SidebarNavigationItem(
                selected = activeNavigation is SharedScreen.Social,
                selectedIcon = Res.drawable.ic_search,
                unselectedIcon = Res.drawable.ic_search,
                onClick = { onNavigate(SharedScreen.Social) }
            )

            SidebarNavigationItem(
                selected = activeNavigation is SharedScreen.Nutrition,
                selectedIcon = Res.drawable.ic_coffee_fill,
                unselectedIcon = Res.drawable.ic_coffee,
                onClick = { onNavigate(SharedScreen.Nutrition) }
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
        activeNavigation: ScreenProvider,
        onNavigate: (ScreenProvider) -> Unit
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
                selected = activeNavigation is SharedScreen.Profile,
                onClick = { onNavigate(SharedScreen.Profile) }
            )

            SidebarNavigationItem(
                selected = activeNavigation is SharedScreen.Settings,
                selectedIcon = Res.drawable.ic_settings,
                unselectedIcon = Res.drawable.ic_settings,
                onClick = { onNavigate(SharedScreen.Settings) }
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