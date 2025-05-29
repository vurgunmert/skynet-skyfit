package com.vurgun.skyfit.feature.dashboard.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import cafe.adriel.voyager.navigator.Navigator
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.ChatBotButtonComponent
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.feature.dashboard.DashboardScreen
import com.vurgun.skyfit.feature.dashboard.explore.ExploreScreen
import com.vurgun.skyfit.feature.dashboard.home.HomeScreen
import com.vurgun.skyfit.feature.persona.profile.ProfileScreen
import com.vurgun.skyfit.feature.persona.settings.SettingsHostScreen
import com.vurgun.skyfit.feature.persona.social.SocialMediaScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_barbell
import skyfit.core.ui.generated.resources.ic_barbell_fill
import skyfit.core.ui.generated.resources.ic_bell
import skyfit.core.ui.generated.resources.ic_chat
import skyfit.core.ui.generated.resources.ic_coffee
import skyfit.core.ui.generated.resources.ic_coffee_fill
import skyfit.core.ui.generated.resources.ic_home
import skyfit.core.ui.generated.resources.ic_home_fill
import skyfit.core.ui.generated.resources.ic_search
import skyfit.core.ui.generated.resources.ic_settings
import skyfit.core.ui.generated.resources.logo_skyfit

internal object DashboardLayoutExpanded {

    data class TopBarState(
        val firstName: String = "",
        val notificationHighlighted: Boolean = false,
        val conversationsHighlighted: Boolean = false,
    )

    @Composable
    fun Screen(viewModel: DashboardViewModel) {
        val topBarState by viewModel.topBarState.collectAsState()

        var overlayScreen by remember { mutableStateOf<ScreenProvider?>(null) }
        val homeScreen = rememberScreen(DashboardScreen.Home)
        val exploreScreen = rememberScreen(DashboardScreen.Explore)
        val socialMediaScreen = rememberScreen(SharedScreen.UnderDevelopment) //DashboardScreen.Social
        val nutritionScreen = rememberScreen(SharedScreen.UnderDevelopment) //DashboardScreen.Nutrition
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
                    onClickNutrition = { dashboardNavigator.replace(nutritionScreen) },
                    onClickProfile = { dashboardNavigator.replace(profileScreen) },
                    onClickSettings = { dashboardNavigator.replace(settingsScreen) },
                    modifier = Modifier
                )

                Box(Modifier.weight(1f)) {
                    Column(modifier = Modifier.fillMaxSize()) {

                        TopBar(
                            state = topBarState,
                            onClickNotifications = {
                                overlayScreen = SharedScreen.UnderDevelopment // SharedScreen.NotificationsExpanded { overlayScreen = null }

                            },
                            onClickConversations = {
                                overlayScreen = SharedScreen.UnderDevelopment // SharedScreen.ConversationsExpanded { overlayScreen = null }
                            },
                            onClickAppAction = {
                                overlayScreen = SharedScreen.ChatBot
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
        onClickNutrition: () -> Unit,
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
                onClickSocial = onClickSocial,
                onClickNutrition = onClickNutrition,
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
        state: TopBarState,
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
            TopBarInfoGroup(state.firstName)
            Spacer(Modifier.weight(1f))
            TopBarNavigationGroup(
                notificationHighlighted = state.notificationHighlighted,
                conversationsHighlighted = state.conversationsHighlighted,
                onClickNotifications = onClickNotifications,
                onClickConversations = onClickConversations,
                onClickAppAction = onClickAppAction
            )
        }
    }

    @Composable
    private fun TopBarInfoGroup(firstName: String) {
        Column {
            SkyText(
                text = "Merhaba $firstName!",
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
        notificationHighlighted: Boolean,
        conversationsHighlighted: Boolean,
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
                highlighted = notificationHighlighted,
                icon = Res.drawable.ic_bell,
                onClick = onClickNotifications
            )

            TopBarNavigationItem(
                selected = false,
                highlighted = conversationsHighlighted,
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

            SidebarNavigationItem(
                selected = currentScreen is SocialMediaScreen,
                selectedIcon = Res.drawable.ic_coffee_fill,
                unselectedIcon = Res.drawable.ic_coffee,
                onClick = onClickNutrition
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