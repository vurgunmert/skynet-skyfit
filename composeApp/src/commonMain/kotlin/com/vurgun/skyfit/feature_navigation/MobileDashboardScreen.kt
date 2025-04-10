package com.vurgun.skyfit.feature_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.explore.screen.MobileExploreScreen
import com.vurgun.skyfit.ui.core.components.special.ChatBotButtonComponent
import com.vurgun.skyfit.feature.social.screen.MobileSocialMediaScreen
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.DashboardHome
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_barbell
import skyfit.ui.core.generated.resources.ic_barbell_fill
import skyfit.ui.core.generated.resources.ic_home
import skyfit.ui.core.generated.resources.ic_home_fill
import skyfit.ui.core.generated.resources.ic_plus
import skyfit.ui.core.generated.resources.ic_profile
import skyfit.ui.core.generated.resources.ic_profile_fill
import skyfit.ui.core.generated.resources.ic_search

@Composable
fun MobileDashboardWithNavigation(
    rootNavigator: Navigator,
    dashboardNavigator: Navigator,
    initialRoute: MobileNavRoute = DashboardHome
) {
    NavHost(
        navigator = dashboardNavigator,
        initialRoute = initialRoute.route
    ) {
//        scene(DashboardHome.route) { MobileHomeRootScreen(rootNavigator) }
        scene(MobileNavRoute.DashboardExplore.route) {
            MobileExploreScreen(
                goToExercise = { },
                goToVisitTrainer = { },
                goToVisitFacility = { },
                goToExploreCommunities = { },
                goToExploreChallenges = { }
            )
        }
        scene(MobileNavRoute.DashboardSocial.route) { MobileSocialMediaScreen(
            goToCreatePost = {  }
        ) }
//        scene(MobileNavRoute.DashboardProfile.route) { MobileDashboardProfileScreen(rootNavigator) }
    }
}


@Composable
fun MobileDashboardScreen(
    rootNavigator: Navigator,
    initialRoute: MobileNavRoute = DashboardHome
) {
    val dashboardNavigator = rememberNavigator()
    // Collect the current navigation entry as state
    val currentBackStackEntry by dashboardNavigator.currentEntry.collectAsState(initial = null)
    val currentRoute: String = currentBackStackEntry?.route?.route ?: initialRoute.route


    SkyFitMobileScaffold(
        bottomBar = {
            MobileDashboardBottomBar(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
                currentRoute = currentRoute,
                onClickHome = { dashboardNavigator.takeover(DashboardHome) },
                onClickExplore = { dashboardNavigator.takeover(MobileNavRoute.DashboardExplore) },
                onClickSocial = { dashboardNavigator.takeover(MobileNavRoute.DashboardSocial) },
                onClickAddPost = { rootNavigator.jumpAndStay(MobileNavRoute.UserSocialMediaPostAdd) },
                onClickProfile = { dashboardNavigator.takeover(MobileNavRoute.DashboardProfile) },
                onClickChatBot = { rootNavigator.jumpAndStay(MobileNavRoute.UserChatBot) },
            )
        }
    ) {
        MobileDashboardWithNavigation(
            rootNavigator = rootNavigator,
            dashboardNavigator = dashboardNavigator,
            initialRoute = initialRoute
        )
    }
}


@Composable
private fun MobileDashboardBottomBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    onClickHome: () -> Unit,
    onClickExplore: () -> Unit,
    onClickSocial: () -> Unit,
    onClickAddPost: () -> Unit,
    onClickProfile: () -> Unit,
    onClickChatBot: () -> Unit
) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Additional shadow box placed slightly above the bottom bar
        Box(
            modifier = Modifier
                .size(332.dp, 68.dp)
                .offset(y = (-10).dp) // Moves it slightly up
                .shadow(4.dp, shape = RoundedCornerShape(50), ambientColor = Color.Black, spotColor = Color.Black)
        )

        // Bottom bar container
        Box(
            modifier = Modifier
                .shadow(8.dp, shape = RoundedCornerShape(50), ambientColor = Color.Black, spotColor = Color.Black)
                .background(SkyFitColor.specialty.buttonBgRest, RoundedCornerShape(50))
                .padding(horizontal = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                val homeIconRes = if (currentRoute == DashboardHome.route) Res.drawable.ic_home_fill else Res.drawable.ic_home
                Box(Modifier.width(32.dp).height(68.dp).clickable(onClick = onClickHome), contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(homeIconRes),
                        contentDescription = "Home",
                        tint = SkyFitColor.text.inverse,
                        modifier = Modifier.size(24.dp)
                    )
                }

                val exploreIconRes =
                    if (currentRoute == MobileNavRoute.DashboardExplore.route) Res.drawable.ic_barbell_fill else Res.drawable.ic_barbell
                Box(Modifier.width(32.dp).height(68.dp).clickable(onClick = onClickHome), contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(exploreIconRes),
                        contentDescription = "Explore",
                        tint = SkyFitColor.text.inverse,
                        modifier = Modifier.size(24.dp).clickable(onClick = onClickExplore)
                    )
                }

                val socialIconRes =
                    if (currentRoute == MobileNavRoute.DashboardSocial.route) Res.drawable.ic_plus else Res.drawable.ic_search
                Box(Modifier.width(32.dp).height(68.dp).clickable(onClick = onClickHome), contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(socialIconRes),
                        contentDescription = if (currentRoute == MobileNavRoute.DashboardSocial.route) "Add Post" else "Social",
                        tint = SkyFitColor.text.inverse,
                        modifier = Modifier.size(24.dp).clickable(
                            onClick = if (currentRoute == MobileNavRoute.DashboardSocial.route) onClickAddPost else onClickSocial
                        )
                    )
                }

                val profileIconRes =
                    if (currentRoute == MobileNavRoute.DashboardProfile.route) Res.drawable.ic_profile_fill else Res.drawable.ic_profile
                Box(Modifier.width(32.dp).height(68.dp).clickable(onClick = onClickHome), contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(profileIconRes),
                        contentDescription = "Profile",
                        tint = SkyFitColor.text.inverse,
                        modifier = Modifier.size(24.dp).clickable(onClick = onClickProfile)
                    )
                }
            }
        }

        ChatBotButtonComponent(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 112.dp, end = 20.dp),
            onClick = onClickChatBot
        )
    }
}


private fun Navigator.takeover(to: MobileNavRoute) {
    navigate(
        route = to.route,
        options = NavOptions(
            popUpTo = PopUpTo(DashboardHome.route, inclusive = false),
            launchSingleTop = true
        )
    )
}
