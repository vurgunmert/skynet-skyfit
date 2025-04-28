package com.vurgun.skyfit.feature.auth

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.auth.login.LoginScreen

val authScreenModule = screenModule {
    register<SharedScreen.Login> { LoginScreen() }
}