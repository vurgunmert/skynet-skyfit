package com.vurgun.skyfit.core.navigation

import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.navigator.Navigator

/**
 * Push a screen based on a ScreenProvider.
 */
fun Navigator.navigateTo(provider: ScreenProvider) {
    push(ScreenRegistry.get(provider))
}

/**
 * Replace the current screen with a new one based on a ScreenProvider.
 */
fun Navigator.replaceWith(provider: ScreenProvider) {
    replace(ScreenRegistry.get(provider))
}

/**
 * Replace the current screen with a new one based on a ScreenProvider.
 */
fun Navigator.replaceAllWith(provider: ScreenProvider) {
    replaceAll(ScreenRegistry.get(provider))
}

/**
 * Optionally, pop back to a specific screen (later if you want more control).
 */
fun Navigator.popTo(provider: ScreenProvider) {
    val screen = ScreenRegistry.get(provider)
    popUntil { it == screen }
}
