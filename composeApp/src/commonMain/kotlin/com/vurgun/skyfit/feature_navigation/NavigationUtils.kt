package com.vurgun.skyfit.feature_navigation

import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo

fun Navigator.jumpAndTakeover(from: MobileNavRoute, to: MobileNavRoute) {
    navigate(
        route = to.route,
        options = NavOptions(popUpTo = PopUpTo(from.route, inclusive = true), launchSingleTop = true)
    )
}

fun Navigator.jumpAndTakeover(to: MobileNavRoute) {
    navigate(
        route = to.route,
        options = NavOptions(
            popUpTo = PopUpTo("", inclusive = true), // 🔥 Clears entire stack
            launchSingleTop = true
        )
    )
}

fun Navigator.jumpAndTakeover(from: String, to: String) {
    navigate(
        route = to,
        options = NavOptions(popUpTo = PopUpTo(from, inclusive = true), launchSingleTop = true)
    )
}

fun Navigator.jumpAndStay(into: MobileNavRoute) {
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