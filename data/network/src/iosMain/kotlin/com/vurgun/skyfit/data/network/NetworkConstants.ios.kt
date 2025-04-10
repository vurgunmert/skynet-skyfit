package com.vurgun.skyfit.data.network

actual fun getNetworkConstants(): NetworkConstants {
    return IOSNetworkConstants()
}

private class IOSNetworkConstants: NetworkConstants {
    override val clientId: Int = 1
    override val osId: Int = 4
}