package com.vurgun.skyfit.core.navigation

import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator

/**
 * Pushes a screen from a [ScreenProvider] using the global [ScreenRegistry].
 */
inline fun Navigator.push(provider: ScreenProvider) {
    push(ScreenRegistry.get(provider))
}

/**
 * Replaces the current screen with one from a [ScreenProvider].
 */
inline fun Navigator.replace(provider: ScreenProvider) {
    replace(ScreenRegistry.get(provider))
}

/**
 * Replaces the entire back stack with a single screen from a [ScreenProvider].
 */
inline fun Navigator.replaceAll(provider: ScreenProvider) {
    replaceAll(ScreenRegistry.get(provider))
}

/**
 * Pops the back stack until the target screen from a [ScreenProvider] is at the top.
 * If the screen is not found in the back stack, no-op.
 */
inline fun Navigator.popUntil(provider: ScreenProvider) {
    val target = ScreenRegistry.get(provider)
    popUntil { it == target }
}

/**
 * Recursively walks up the navigator hierarchy and returns the top-most (app) navigator.
 */
fun Navigator.findRootNavigator(): Navigator {
    var root = this
    while (root.parent != null) {
        root = root.parent!!
    }
    return root
}


@OptIn(InternalVoyagerApi::class)
fun Navigator?.findParentByKey(key: String): Navigator? {
    var current = this
    while (current != null) {
        if (current.key == key) return current
        current = current.parent
    }
    return null
}
