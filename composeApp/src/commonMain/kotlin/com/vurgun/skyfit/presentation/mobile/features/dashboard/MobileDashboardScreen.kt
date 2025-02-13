package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.mobile.features.user.messages.ChatBotButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.compose_multiplatform
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileDashboardWithNavigation(
    rootNavigator: Navigator,
    dashboardNavigator: Navigator,
    initialRoute: SkyFitNavigationRoute = SkyFitNavigationRoute.DashboardHome
) {
    NavHost(
        navigator = dashboardNavigator,
        initialRoute = initialRoute.route
    ) {
        scene(SkyFitNavigationRoute.DashboardHome.route) { MobileDashboardHomeScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardExplore.route) { MobileDashboardExploreScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardSocial.route) { MobileDashboardSocialScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardNutrition.route) { MobileDashboardNutritionScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardProfile.route) { MobileDashboardProfileScreen(rootNavigator) }
    }
}


@Composable
fun MobileDashboardScreen(
    rootNavigator: Navigator,
    initialRoute: SkyFitNavigationRoute = SkyFitNavigationRoute.DashboardHome
) {
    val dashboardNavigator = rememberNavigator()
    // Collect the current navigation entry as state
    val currentBackStackEntry by dashboardNavigator.currentEntry.collectAsState(initial = null)
    val currentRoute: String = currentBackStackEntry?.route?.route ?: initialRoute.route


    SkyFitScaffold(
        bottomBar = {
            MobileDashboardBottomBar(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
                currentRoute = currentRoute,
                onClickHome = { dashboardNavigator.takeover(SkyFitNavigationRoute.DashboardHome) },
                onClickExplore = { dashboardNavigator.takeover(SkyFitNavigationRoute.DashboardExplore) },
                onClickSocial = { dashboardNavigator.takeover(SkyFitNavigationRoute.DashboardSocial) },
                onClickAddPost = { rootNavigator.jumpAndStay(SkyFitNavigationRoute.UserSocialMediaPostAdd) },
                onClickNutrition = { dashboardNavigator.takeover(SkyFitNavigationRoute.DashboardNutrition) },
                onClickProfile = { dashboardNavigator.takeover(SkyFitNavigationRoute.DashboardProfile) },
                onClickChatBot = { rootNavigator.jumpAndStay(SkyFitNavigationRoute.UserChatBot) },
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
    modifier: Modifier,
    currentRoute: String,
    onClickHome: () -> Unit,
    onClickExplore: () -> Unit,
    onClickSocial: () -> Unit,
    onClickAddPost: () -> Unit,
    onClickNutrition: () -> Unit,
    onClickProfile: () -> Unit,
    onClickChatBot: () -> Unit
) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier
                .background(SkyFitColor.specialty.buttonBgRest, RoundedCornerShape(percent = 50))
                .padding(vertical = 24.dp, horizontal = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Home",
                tint = SkyFitColor.text.default,
                modifier = Modifier.size(20.dp).clickable(onClick = onClickHome)
            )
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Explore",
                tint = SkyFitColor.text.default,
                modifier = Modifier.size(20.dp).clickable(onClick = onClickExplore)
            )
            Icon(
                painter = painterResource(
                    if (currentRoute == SkyFitNavigationRoute.DashboardSocial.route) Res.drawable.compose_multiplatform
                    else Res.drawable.logo_skyfit
                ),
                contentDescription = if (currentRoute == SkyFitNavigationRoute.DashboardSocial.route) "Add Post" else "Social",
                tint = SkyFitColor.text.default,
                modifier = Modifier.size(20.dp).clickable(
                    onClick = if (currentRoute == SkyFitNavigationRoute.DashboardSocial.route) onClickAddPost else onClickSocial
                )
            )
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Nutrition",
                tint = SkyFitColor.text.default,
                modifier = Modifier.size(20.dp).clickable(onClick = onClickNutrition)
            )
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Profile",
                tint = SkyFitColor.text.default,
                modifier = Modifier.size(20.dp).clickable(onClick = onClickProfile)
            )
        }

        ChatBotButtonComponent(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 94.dp, end = 20.dp),
            onClick = onClickChatBot
        )
    }
}


private fun Navigator.takeover(to: SkyFitNavigationRoute) {
    navigate(
        route = to.route,
        options = NavOptions(
            popUpTo = PopUpTo(SkyFitNavigationRoute.DashboardHome.route, inclusive = false),
            launchSingleTop = true
        )
    )
}
