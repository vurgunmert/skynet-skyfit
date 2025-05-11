package com.vurgun.skyfit.core.data.utility

enum class Platform {
    IOS, ANDROID, DESKTOP, OTHER
}

expect val platform: Platform

object PlatformType {
    val isIos: Boolean get() = platform == Platform.IOS
    val isAndroid: Boolean get() = platform == Platform.ANDROID
    val isDesktop: Boolean get() = platform == Platform.DESKTOP
}
