package com.vurgun.skyfit.core.utils

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform