package com.vurgun.skyfit.profile.model

sealed interface ProfileNavigationRoute {
    data object Activities: ProfileNavigationRoute
    data object Posts: ProfileNavigationRoute
}