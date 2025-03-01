package com.vurgun.skyfit.core.utils

interface Platform {
    val name: String
    val clientId: Int // 1 - Mobil, 2- Web, 3 - Server
    val osId: Int // 1- Windows, 2 - MacOs, 3 - Android, 4 - Ios
}

expect fun getPlatform(): Platform