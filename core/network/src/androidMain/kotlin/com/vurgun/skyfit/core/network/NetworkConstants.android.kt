package com.vurgun.skyfit.core.network

actual fun getNetworkConstants(): NetworkConstants {
    return AndroidNetworkConstants()
}

private class AndroidNetworkConstants: NetworkConstants {
    override val clientId: Int = 1
    override val osId: Int = 3
}