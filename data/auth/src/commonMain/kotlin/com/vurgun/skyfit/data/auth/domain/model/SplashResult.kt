package com.vurgun.skyfit.data.auth.domain.model

sealed class SplashResult {
    data object Maintenance : SplashResult()
    data object UserAvailable : SplashResult()
    data object UserNotFound : SplashResult()
}