package com.vurgun.skyfit.core.ui.styling

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_biceps_force
import skyfit.core.ui.generated.resources.ic_character_carrot
import skyfit.core.ui.generated.resources.ic_character_koala
import skyfit.core.ui.generated.resources.ic_character_panda
import skyfit.core.ui.generated.resources.ic_clock
import skyfit.core.ui.generated.resources.ic_exercises
import skyfit.core.ui.generated.resources.ic_fast_food
import skyfit.core.ui.generated.resources.ic_female_body_type_ecto
import skyfit.core.ui.generated.resources.ic_female_body_type_endo
import skyfit.core.ui.generated.resources.ic_female_body_type_meso
import skyfit.core.ui.generated.resources.ic_high_intensity_training
import skyfit.core.ui.generated.resources.ic_jumping_rope
import skyfit.core.ui.generated.resources.ic_male_body_type_ecto
import skyfit.core.ui.generated.resources.ic_male_body_type_endo
import skyfit.core.ui.generated.resources.ic_male_body_type_meso
import skyfit.core.ui.generated.resources.ic_medal
import skyfit.core.ui.generated.resources.ic_pull_up_bar
import skyfit.core.ui.generated.resources.ic_push_up
import skyfit.core.ui.generated.resources.ic_sit_up
import skyfit.core.ui.generated.resources.ic_sleep
import skyfit.core.ui.generated.resources.ic_smoking
import skyfit.core.ui.generated.resources.ic_trophy
import skyfit.core.ui.generated.resources.ic_yoga

object SkyFitAsset {

    enum class SkyFitIcon(val id: Int, val resId: String, val res: DrawableResource) {
        HIGH_INTENSITY_TRAINING(1, "ic_high_intensity_training", Res.drawable.ic_high_intensity_training),
        MEDAL(2, "ic_medal", Res.drawable.ic_medal),
        TROPHY(3, "ic_trophy", Res.drawable.ic_trophy),
        CLOCK(4, "ic_clock", Res.drawable.ic_clock),
        EXERCISES(5, "ic_exercises", Res.drawable.ic_exercises),
        PUSH_UP(6, "ic_push_up", Res.drawable.ic_push_up),
        BICEPS_FORCE(7, "ic_biceps_force", Res.drawable.ic_biceps_force),
        PULL_UP_BAR(8, "ic_pull_up_bar", Res.drawable.ic_pull_up_bar),
        JUMPING_ROPE(9, "ic_jumping_rope", Res.drawable.ic_jumping_rope),
        SIT_UP(10, "ic_sit_up", Res.drawable.ic_sit_up),
        YOGA(11, "ic_yoga", Res.drawable.ic_yoga),
        SLEEP(12, "ic_sleep", Res.drawable.ic_sleep),
        FAST_FOOD(13, "ic_fast_food", Res.drawable.ic_fast_food),
        SMOKING(14, "ic_smoking", Res.drawable.ic_smoking);

        companion object {
            private val resIdMap = entries.associateBy { it.resId }
            val intIdMap = entries.associateBy { it.id }

            fun fromId(id: String?): SkyFitIcon? = resIdMap[id]

            fun fromResIdOrDefault(id: String?, default: SkyFitIcon = EXERCISES): SkyFitIcon {
                return resIdMap[id] ?: default
            }

            fun fromIntId(id: Int?): SkyFitIcon? = intIdMap[id]

            fun fromIdOrDefault(id: Int?, default: SkyFitIcon = EXERCISES): SkyFitIcon {
                return intIdMap[id] ?: default
            }

        }
    }

    @Composable
    fun getPainter(iconId: String?, defaultIcon: SkyFitIcon = SkyFitIcon.EXERCISES): Painter {
        val icon = SkyFitIcon.fromResIdOrDefault(iconId, defaultIcon)
        return painterResource(icon.res)
    }

    @Composable
    fun getPainter(iconId: Int?, defaultIcon: SkyFitIcon = SkyFitIcon.EXERCISES): Painter {
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

    enum class CharacterIcon(val id: String, val res: DrawableResource) {
        CARROT("ic_character_carrot", Res.drawable.ic_character_carrot),
        KOALA("ic_character_koala", Res.drawable.ic_character_koala),
        PANDA("ic_character_panda", Res.drawable.ic_character_panda);

        companion object {
            fun findById(id: String): CharacterIcon? {
                return entries.firstOrNull { it.id == id }
            }
        }
    }

    enum class BodyTypeIcon(val id: String, val res: DrawableResource) {
        MALE_ECTO("ic_male_body_type_ecto", Res.drawable.ic_male_body_type_ecto),
        MALE_MESO("ic_male_body_type_meso", Res.drawable.ic_male_body_type_meso),
        MALE_ENDO("ic_male_body_type_endo", Res.drawable.ic_male_body_type_endo),
        FEMALE_ECTO("ic_female_body_type_ecto", Res.drawable.ic_female_body_type_ecto),
        FEMALE_MESO("ic_female_body_type_meso", Res.drawable.ic_female_body_type_meso),
        FEMALE_ENDO("ic_female_body_type_endo", Res.drawable.ic_female_body_type_endo);

        companion object {
            fun findById(id: String): BodyTypeIcon? {
                return entries.firstOrNull { it.id == id }
            }
        }
    }

}