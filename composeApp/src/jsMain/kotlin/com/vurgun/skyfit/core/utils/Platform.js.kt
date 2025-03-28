package com.vurgun.skyfit.core.utils

actual fun getPlatform(): Platform = WebPlatform()

class WebPlatform : Platform {
    override val name: String = "Web"
    override val clientId: Int = 2
    override val osId: Int = 2
}