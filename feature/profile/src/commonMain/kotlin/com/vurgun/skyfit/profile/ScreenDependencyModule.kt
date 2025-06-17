package com.vurgun.skyfit.profile

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.profile.screen.ProfileScreen

val screenProfileModule = screenModule {
    register<SharedScreen.Profile> { ProfileScreen() }
}