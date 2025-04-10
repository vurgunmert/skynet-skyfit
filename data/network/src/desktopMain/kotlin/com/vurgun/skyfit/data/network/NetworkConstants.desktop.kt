package com.vurgun.skyfit.data.network

actual fun getNetworkConstants(): NetworkConstants {
    return DesktopNetworkConstants()
}

private class DesktopNetworkConstants : NetworkConstants {
    override val clientId: Int = 1
    override val osId: Int = 2
}