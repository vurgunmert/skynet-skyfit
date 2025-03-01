package com.vurgun.skyfit.core.utils

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val clientId: Int = 1
    override val osId: Int = 4
}

actual fun getPlatform(): Platform = IOSPlatform()