package com.vurgun.skyfit.core.ui.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_biceps_force
import skyfit.composeapp.generated.resources.ic_character_carrot
import skyfit.composeapp.generated.resources.ic_character_koala
import skyfit.composeapp.generated.resources.ic_character_panda
import skyfit.composeapp.generated.resources.ic_clock
import skyfit.composeapp.generated.resources.ic_exercises
import skyfit.composeapp.generated.resources.ic_fast_food
import skyfit.composeapp.generated.resources.ic_female_body_type_ecto
import skyfit.composeapp.generated.resources.ic_female_body_type_endo
import skyfit.composeapp.generated.resources.ic_female_body_type_meso
import skyfit.composeapp.generated.resources.ic_high_intensity_training
import skyfit.composeapp.generated.resources.ic_jumping_rope
import skyfit.composeapp.generated.resources.ic_male_body_type_ecto
import skyfit.composeapp.generated.resources.ic_male_body_type_endo
import skyfit.composeapp.generated.resources.ic_male_body_type_meso
import skyfit.composeapp.generated.resources.ic_medal
import skyfit.composeapp.generated.resources.ic_pull_up_bar
import skyfit.composeapp.generated.resources.ic_push_up
import skyfit.composeapp.generated.resources.ic_sit_up
import skyfit.composeapp.generated.resources.ic_sleep
import skyfit.composeapp.generated.resources.ic_smoking
import skyfit.composeapp.generated.resources.ic_trophy
import skyfit.composeapp.generated.resources.ic_yoga
import skyfit.composeapp.generated.resources.logo_skyfit

object SkyFitIcon {

    val idResMap = mapOf(
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
        return idResMap[id]
    }

    @Composable
    fun getIconResourcePainter(id: String?): Painter? {
        return idResMap[id]?.let { painterResource(it) }
    }

    @Composable
    fun getIconResourcePainter(id: String?, defaultRes: DrawableResource): Painter {
        return idResMap[id]?.let { painterResource(it) } ?: painterResource(defaultRes)
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

object SkyFitBodyTypeIcon {
    val maleIconMap = mapOf(
        "ic_male_body_type_ecto" to Res.drawable.ic_male_body_type_ecto,
        "ic_male_body_type_meso" to Res.drawable.ic_male_body_type_meso,
        "ic_male_body_type_endo" to Res.drawable.ic_male_body_type_endo
    )
    val femaleIconMap = mapOf(
        "ic_female_body_type_ecto" to Res.drawable.ic_female_body_type_ecto,
        "ic_female_body_type_meso" to Res.drawable.ic_female_body_type_meso,
        "ic_female_body_type_endo" to Res.drawable.ic_female_body_type_endo
    )

    fun getIconResource(id: String?): DrawableResource? {
        return (maleIconMap[id] ?: femaleIconMap[id])
    }

    @Composable
    fun getIconResourcePainter(id: String?): Painter {
        return (maleIconMap[id] ?: femaleIconMap[id])?.let { painterResource(it) } ?: painterResource(Res.drawable.logo_skyfit)
    }
}

object SkyFitAsset {

    enum class SkyFitIcon(val id: String, val res: DrawableResource) {
        HIGH_INTENSITY_TRAINING("ic_trophy", Res.drawable.ic_high_intensity_training),
        MEDAL("ic_trophy", Res.drawable.ic_medal),
        TROPHY("ic_trophy", Res.drawable.ic_trophy),
        CLOCK("ic_clock", Res.drawable.ic_clock),
        EXERCISES("ic_exercises", Res.drawable.ic_exercises),
        PUSH_UP("ic_push_up", Res.drawable.ic_push_up),
        BICEPS_FORCE("ic_biceps_force", Res.drawable.ic_biceps_force),
        PULL_UP_BAR("ic_pull_up_bar", Res.drawable.ic_pull_up_bar),
        JUMPING_ROPE("ic_jumping_rope", Res.drawable.ic_jumping_rope),
        SIT_UP("ic_sit_up", Res.drawable.ic_sit_up),
        YOGA("ic_yoga", Res.drawable.ic_yoga),
        SLEEP("ic_sleep", Res.drawable.ic_sleep),
        FAST_FOOD("ic_fast_food", Res.drawable.ic_fast_food),
        SMOKING("ic_smoking", Res.drawable.ic_smoking);

        companion object {
            private val map = entries.associateBy { it.id }

            fun fromId(id: String?): SkyFitIcon? = map[id]

            fun fromIdOrDefault(id: String?, default: SkyFitIcon = EXERCISES): SkyFitIcon {
                return map[id] ?: default
            }
        }
    }

    @Composable
    fun getPainter(iconId: String?, defaultIcon: SkyFitIcon = SkyFitIcon.EXERCISES): Painter {
        val icon = SkyFitIcon.fromIdOrDefault(iconId, defaultIcon)
        return painterResource(icon.res)
    }

    /** 🔹 GENERAL ICONS **/
    val Icons = mapOf(
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

    /** 🔹 CHARACTER ICONS **/
    val CharacterIcons = mapOf(
        "ic_character_carrot" to Res.drawable.ic_character_carrot,
        "ic_character_koala" to Res.drawable.ic_character_koala,
        "ic_character_panda" to Res.drawable.ic_character_panda
    )

    enum class CharacterIcon(val id: String, val res: DrawableResource) {
        CARROT("ic_character_carrot", Res.drawable.ic_character_carrot),
        KOALA("ic_character_koala", Res.drawable.ic_character_koala),
        PANDA("ic_character_panda", Res.drawable.ic_character_panda)
    }

    enum class BodyTypeIcon(val id: String, val res: DrawableResource) {
        MALE_ECTO("ic_male_body_type_ecto", Res.drawable.ic_male_body_type_ecto),
        MALE_MESO("ic_male_body_type_meso", Res.drawable.ic_male_body_type_meso),
        MALE_ENDO("ic_male_body_type_endo", Res.drawable.ic_male_body_type_endo),
        FEMALE_ECTO("ic_female_body_type_ecto", Res.drawable.ic_female_body_type_ecto),
        FEMALE_MESO("ic_female_body_type_meso", Res.drawable.ic_female_body_type_meso),
        FEMALE_ENDO("ic_female_body_type_endo", Res.drawable.ic_female_body_type_endo),
    }

}