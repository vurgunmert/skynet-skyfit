package com.vurgun.skyfit.navigation

import com.vurgun.skyfit.core.domain.models.UserRole
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo

fun Navigator.jumpAndTakeover(from: NavigationRoute, to: NavigationRoute) {
    navigate(
        route = to.route,
        options = NavOptions(popUpTo = PopUpTo(from.route, inclusive = true), launchSingleTop = true)
    )
}

fun Navigator.jumpAndTakeover(from: String, to: String) {
    navigate(
        route = to,
        options = NavOptions(popUpTo = PopUpTo(from, inclusive = true), launchSingleTop = true)
    )
}

fun Navigator.jumpAndStay(into: NavigationRoute) {
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
    screen: NavigationRoute,
    userRole: UserRole,
    vararg args: Pair<NavigationRoute.Param, String?>
) {
    if (userRole in screen.roles) {
        navigate(screen.createRoute(*args))
    } else {
        println("Access Denied: User role $userRole cannot access ${screen.route}")
    }
}