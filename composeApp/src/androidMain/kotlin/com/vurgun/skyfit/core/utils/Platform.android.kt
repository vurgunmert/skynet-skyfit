package com.vurgun.skyfit.core.utils

import android.os.Build
import com.vurgun.skyfit.core.utils.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
