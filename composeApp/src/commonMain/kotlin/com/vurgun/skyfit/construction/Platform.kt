package com.vurgun.skyfit.construction

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform