package com.vurgun.skyfit.presentation.shared.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_biceps_force
import skyfit.composeapp.generated.resources.ic_character_carrot
import skyfit.composeapp.generated.resources.ic_character_koala
import skyfit.composeapp.generated.resources.ic_character_panda
import skyfit.composeapp.generated.resources.ic_exercises
import skyfit.composeapp.generated.resources.ic_fast_food
import skyfit.composeapp.generated.resources.ic_jumping_rope
import skyfit.composeapp.generated.resources.ic_pull_up_bar
import skyfit.composeapp.generated.resources.ic_push_up
import skyfit.composeapp.generated.resources.ic_sit_up
import skyfit.composeapp.generated.resources.ic_sleep
import skyfit.composeapp.generated.resources.ic_smoking
import skyfit.composeapp.generated.resources.ic_yoga
import skyfit.composeapp.generated.resources.logo_skyfit

object SkyFitIcon {

    val iconMap = mapOf(
        "ic_exercises" to Res.drawable.ic_exercises,
        "ic_push_up" to Res.drawable.ic_push_up,
        "ic_biceps_force" to Res.drawable.ic_biceps_force,
        "ic_pull_up_bar" to Res.drawable.ic_pull_up_bar,
        "ic_jumping_rope" to Res.drawable.ic_jumping_rope,
        "ic_sit_up" to Res.drawable.ic_sit_up,
        "ic_yoga" to Res.drawable.ic_yoga,
        "ic_sleep" to Res.drawable.ic_sleep,
        "ic_fast_food" to Res.drawable.ic_fast_food,
        "ic_smoking" to Res.drawable.ic_smoking,
    )

    fun getIconResource(id: String?): DrawableResource? {
        return iconMap[id]
    }

    @Composable
    fun getIconResourcePainter(id: String?): Painter? {
        return iconMap[id]?.let { painterResource(it) }
    }

    @Composable
    fun getIconResourcePainter(id: String?, defaultRes: DrawableResource): Painter {
        return iconMap[id]?.let { painterResource(it) } ?: painterResource(defaultRes)
    }
}

object SkyFitCharacterIcon {
    val iconMap = mapOf(
        "ic_character_carrot" to Res.drawable.ic_character_carrot,
        "ic_character_koala" to Res.drawable.ic_character_koala,
        "ic_character_panda" to Res.drawable.ic_character_panda
    )

    fun getIconResource(id: String?): DrawableResource? {
        return iconMap[id]
    }

    @Composable
    fun getIconResourcePainter(id: String?): Painter {
        return iconMap[id]?.let { painterResource(it) } ?: painterResource(Res.drawable.logo_skyfit)
    }
}