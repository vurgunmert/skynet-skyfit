package com.vurgun.skyfit.core.utils

actual fun getPlatform(): Platform {
    return DesktopPlatform()
}

class DesktopPlatform : Platform {
    override val name: String = "Desktop"
    override val clientId: Int = 1
    override val osId: Int = 2
}