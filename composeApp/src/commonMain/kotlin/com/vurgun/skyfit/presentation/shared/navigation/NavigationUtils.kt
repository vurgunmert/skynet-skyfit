package com.vurgun.skyfit.presentation.shared.navigation

import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo

fun Navigator.jumpAndTakeover(from: SkyFitNavigationRoute, to: SkyFitNavigationRoute) {
    navigate(
        route = to.route,
        options = NavOptions(popUpTo = PopUpTo(from.route, inclusive = true), launchSingleTop = true)
    )
}

fun Navigator.jumpAndStay(into: SkyFitNavigationRoute) {
    navigate(
        route = into.route,
        options = NavOptions(launchSingleTop = false) // Ensures a new instance is added to the stack
    )
}

fun Navigator.jumpAndStay(route: String) {
    navigate(
        route = route,
        options = NavOptions(launchSingleTop = false) // Ensures a new instance is added to the stack
    )
}

// Extensions for Navigator to handle role-based navigation
fun Navigator.navigateToScreen(
    screen: SkyFitNavigationRoute,
    userRole: Role,
    vararg args: Pair<SkyFitNavigationRoute.Param, String?>
) {
    if (userRole in screen.roles) {
        navigate(screen.createRoute(*args))
    } else {
        println("Access Denied: User role $userRole cannot access ${screen.route}")
    }
}