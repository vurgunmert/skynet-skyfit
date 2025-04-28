package com.vurgun.skyfit.feature.splash.domain

sealed class SplashResult {
    data object Maintenance : SplashResult()
    data object UserAvailable : SplashResult()
    data object UserNotFound : SplashResult()
}