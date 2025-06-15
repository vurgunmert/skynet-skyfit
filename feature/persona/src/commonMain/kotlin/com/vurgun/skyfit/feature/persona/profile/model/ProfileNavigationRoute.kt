package com.vurgun.skyfit.feature.persona.profile.model

sealed interface ProfileNavigationRoute {
    data object Activities: ProfileNavigationRoute
    data object Posts: ProfileNavigationRoute
}