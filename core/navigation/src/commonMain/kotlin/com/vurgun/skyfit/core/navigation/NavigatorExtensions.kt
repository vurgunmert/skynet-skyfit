package com.vurgun.skyfit.core.navigation

import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator

fun Navigator.push(provider: ScreenProvider) {
    push(ScreenRegistry.get(provider))
}

fun Navigator.replace(provider: ScreenProvider) {
    replace(ScreenRegistry.get(provider))
}

fun Navigator.replaceAll(provider: ScreenProvider) {
    replaceAll(ScreenRegistry.get(provider))
}
fun Navigator.popUntil(provider: ScreenProvider) {
    val target = ScreenRegistry.get(provider)
    popUntil { it == target }
}

fun Navigator.findRootNavigator(): Navigator {
    var root = this
    while (root.parent != null) {
        root = root.parent!!
    }
    return root
}