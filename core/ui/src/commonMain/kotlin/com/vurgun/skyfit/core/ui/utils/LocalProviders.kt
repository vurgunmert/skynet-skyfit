package com.vurgun.skyfit.core.ui.utils

import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.core.registry.ScreenProvider

val LocalCompactOverlayController = staticCompositionLocalOf<((ScreenProvider?) -> Unit)?> { null }
val LocalExpandedOverlayController = staticCompositionLocalOf<((ScreenProvider?) -> Unit)?> { null }