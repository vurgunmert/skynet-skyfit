package com.vurgun.skyfit.core.ui.utils

import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.core.registry.ScreenProvider

val LocalOverlayController = staticCompositionLocalOf<((ScreenProvider?) -> Unit)?> { null }