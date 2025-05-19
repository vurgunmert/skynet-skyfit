package com.vurgun.skyfit.feature.dashboard.dashboard

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.ChatBotButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.dashboard.DashboardScreen
import com.vurgun.skyfit.feature.dashboard.component.BottomNavigationBar
import com.vurgun.skyfit.feature.dashboard.explore.ExploreScreen
import com.vurgun.skyfit.feature.dashboard.home.HomeScreen
import com.vurgun.skyfit.feature.persona.profile.ProfileScreen
import com.vurgun.skyfit.feature.persona.settings.SettingsHostScreen
import com.vurgun.skyfit.feature.persona.social.SocialMediaScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.*

class DashboardMainScreen : Screen {
    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current

        if (windowSize == WindowSize.EXPANDED) {
            ExpandedDashboardComponent.Screen()
        } else {
            CompactDashboardComponent.Screen()
        }
    }
}


private object CompactDashboardComponent {
    @Composable
    fun Screen() {
        val appNavigator = LocalNavigator.currentOrThrow
        val homeScreen = rememberScreen(DashboardScreen.Home)
        val profileScreen = rememberScreen(DashboardScreen.Profile)

        Navigator(homeScreen) { dashboardNavigator ->
            SkyFitScaffold(
                bottomBar = {
                    BottomNavigationBar(
                        modifier = Modifier.padding(bottom = 12.dp),
                        currentScreen = dashboardNavigator.lastItem,
                        onClickHome = { dashboardNavigator.replace(homeScreen) },
                        onClickProfile = { dashboardNavigator.replace(profileScreen) },
                        onClickAppAction = { appNavigator.push(SharedScreen.PostureAnalysis) }
                    )
                }
            ) {
                CurrentScreen()
            }
        }
    }
}

private object ExpandedDashboardComponent {

    @Composable
    fun Screen() {
        var overlayScreen by remember { mutableStateOf<ScreenProvider?>(null) }
        val homeScreen = rememberScreen(DashboardScreen.Home)
        val exploreScreen = rememberScreen(DashboardScreen.Explore)
        val socialMediaScreen = rememberScreen(DashboardScreen.Social)

        val profileScreen = rememberScreen(DashboardScreen.Profile)
        val settingsScreen = rememberScreen(SharedScreen.Settings)

        Navigator(homeScreen) { dashboardNavigator ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SkyFitColor.background.default)
            ) {
                Sidebar(
                    currentScreen = dashboardNavigator.lastItem,
                    onClickHome = { dashboardNavigator.replace(homeScreen) },
                    onClickExplore = { dashboardNavigator.replace(exploreScreen) },
                    onClickSocial = { dashboardNavigator.replace(socialMediaScreen) },
                    onClickProfile = { dashboardNavigator.replace(profileScreen) },
                    onClickSettings = { dashboardNavigator.replace(settingsScreen) },
                    modifier = Modifier
                )

                Box(Modifier.weight(1f)) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopBar(
                            onClickNotifications = {
                                overlayScreen = SharedScreen.NotificationsExpanded(
                                    onDismiss = { overlayScreen = null },
                                )
                            },
                            onClickConversations = {
                                overlayScreen = SharedScreen.ConversationsExpanded(
                                    onDismiss = { overlayScreen = null },
                                )
                            },
                            onClickAppAction = {
                                overlayScreen = SharedScreen.PostureAnalysis
                            }
                        )
                        Content(modifier = Modifier.weight(1f)) {
                            CurrentScreen()
                        }
                    }

                    overlayScreen?.let {
                        ContentOverlay(it, onDismiss = { overlayScreen = null })
                    }
                }
            }
        }
    }


    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        content: @Composable BoxScope.() -> Unit
    ) {
        Box(modifier.fillMaxSize()) {
            content()
        }
    }

    @Composable
    fun ContentOverlay(
        screenProvider: ScreenProvider,
        onDismiss: () -> Unit,
    ) {
        val root = rememberScreen(screenProvider)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onDismiss
                )
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(420.dp)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    )
            ) {
                Navigator(screen = root) { navigator ->
                    CurrentScreen()
                }
            }
        }
    }

    @Composable
    private fun Sidebar(
        currentScreen: Screen,
        onClickHome: () -> Unit,
        onClickExplore: () -> Unit,
        onClickSocial: () -> Unit,
        onClickProfile: () -> Unit,
        onClickSettings: () -> Unit,
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

            SidebarNavigationGroup(
                currentScreen = currentScreen,
                onClickHome = onClickHome,
                onClickExplore = onClickExplore,
                onClickSocial = onClickSocial
            )

            Spacer(Modifier.weight(1f))

            SidebarProfileNavigationGroup(
                currentScreen = currentScreen,
                onClickProfile = onClickProfile,
                onClickSettings = onClickSettings
            )
        }
    }

    @Composable
    private fun TopBar(
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickAppAction: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            TopBarInfoGroup()
            Spacer(Modifier.weight(1f))
            TopBarNavigationGroup(
                onClickNotifications = onClickNotifications,
                onClickConversations = onClickConversations,
                onClickAppAction = onClickAppAction
            )
        }
    }

    @Composable
    private fun TopBarInfoGroup() {
        Column {
            SkyText(
                text = "Merhaba Maxine!",
                styleType = TextStyleType.BodyMediumSemibold
            )
            SkyText(
                text = "Bugünün meydan okumalarına hazır mısın?",
                styleType = TextStyleType.BodySmall,
                color = SkyFitColor.text.secondary
            )
        }
    }

    @Composable
    private fun TopBarNavigationGroup(
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickAppAction: () -> Unit,
    ) {
        Row(
            Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TopBarTrophiesNavigationItem()

            TopBarNavigationItem(
                selected = false,
                highlighted = true,
                icon = Res.drawable.ic_bell,
                onClick = onClickNotifications
            )

            TopBarNavigationItem(
                selected = false,
                highlighted = true,
                icon = Res.drawable.ic_chat,
                onClick = onClickConversations
            )

            ChatBotButtonComponent(
                modifier = Modifier,
                onClick = onClickAppAction
            )
        }
    }

    @Composable
    private fun TopBarTrophiesNavigationItem() {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .wrapContentWidth()
                .padding(4.dp)
        ) {
            NetworkImage(
                imageUrl = "https://picsum.photos/400/400",
                modifier = Modifier.size(48.dp)
            )
        }
    }

    @Composable
    private fun TopBarNavigationItem(
        selected: Boolean,
        highlighted: Boolean,
        icon: DrawableResource,
        size: Dp = 56.dp,
        onClick: () -> Unit
    ) {
        val background = when {
            selected -> Modifier.background(SkyFitColor.border.secondaryButton, CircleShape)
            else -> Modifier.background(SkyFitColor.background.fillTransparentSecondary, CircleShape)
        }

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
                tint = if (selected) SkyIconTint.Inverse else SkyIconTint.Default
            )
            if (highlighted) {
                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(19.dp)
                        .clip(CircleShape)
                        .size(6.dp)
                        .background(SkyFitColor.icon.critical)
                )
            }
        }
    }

    @Composable
    private fun SidebarNavigationGroup(
        currentScreen: Screen,
        onClickHome: () -> Unit,
        onClickExplore: () -> Unit,
        onClickSocial: () -> Unit
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
                selected = currentScreen is HomeScreen,
                selectedIcon = Res.drawable.ic_home_fill,
                unselectedIcon = Res.drawable.ic_home,
                onClick = onClickHome
            )

            SidebarNavigationItem(
                selected = currentScreen is ExploreScreen,
                selectedIcon = Res.drawable.ic_barbell_fill,
                unselectedIcon = Res.drawable.ic_barbell,
                onClick = onClickExplore
            )

            SidebarNavigationItem(
                selected = currentScreen is SocialMediaScreen,
                selectedIcon = Res.drawable.ic_search,
                unselectedIcon = Res.drawable.ic_search,
                onClick = onClickSocial
            )
        }
    }

    @Composable
    private fun SidebarNavigationItem(
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
    private fun SidebarProfileNavigationGroup(
        currentScreen: Screen,
        onClickProfile: () -> Unit,
        onClickSettings: () -> Unit
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
                selected = currentScreen is ProfileScreen,
                onClick = onClickProfile
            )

            SidebarNavigationItem(
                selected = currentScreen is SettingsHostScreen,
                selectedIcon = Res.drawable.ic_settings,
                unselectedIcon = Res.drawable.ic_settings,
                onClick = onClickSettings
            )
        }
    }

    @Composable
    private fun SidebarProfileNavigationItem(
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
