package com.vurgun.skyfit.profile.model

sealed interface ProfileDestination {
    data object About: ProfileDestination
    data object Posts: ProfileDestination
    data object Measurements: ProfileDestination
}