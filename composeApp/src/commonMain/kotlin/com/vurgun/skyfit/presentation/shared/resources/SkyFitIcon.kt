package com.vurgun.skyfit.presentation.shared.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

object SkyFitIcon {

    val iconMap = mapOf(
        "push_up" to Res.drawable.logo_skyfit,
        "yoga" to Res.drawable.logo_skyfit,
        "barbell" to Res.drawable.logo_skyfit,
        "barbell_fill" to Res.drawable.logo_skyfit
    )

    fun getIconResource(name: String?): DrawableResource? {
        return iconMap[name]
    }

    @Composable
    fun getIconResourcePainter(name: String?): Painter? {
        return iconMap[name]?.let { painterResource(it) }
    }
}