package com.vurgun.skyfit.feature_splash.domain.model

sealed class SplashResult {
    data object Maintenance : SplashResult()
    data object UserAvailable : SplashResult()
    data object UserNotFound : SplashResult()
}