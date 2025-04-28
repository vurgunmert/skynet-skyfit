package com.vurgun.skyfit.core.network

actual fun getNetworkConstants(): NetworkConstants {
    return DesktopNetworkConstants()
}

private class DesktopNetworkConstants : NetworkConstants {
    override val clientId: Int = 1
    override val osId: Int = 2
}