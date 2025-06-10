package com.vurgun.skyfit.core.data.v1.domain.auth.model

sealed class SplashResult {
    data object Maintenance : SplashResult()
    data object UserAvailable : SplashResult()
    data object UserNotFound : SplashResult()
}