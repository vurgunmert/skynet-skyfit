package com.vurgun.skyfit.core.utils

import android.os.Build
import com.vurgun.skyfit.core.utils.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val clientId: Int = 1
    override val osId: Int = 3
}

actual fun getPlatform(): Platform = AndroidPlatform()
