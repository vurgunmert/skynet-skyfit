package com.vurgun.skyfit.core.ui.viewdata

import com.vurgun.skyfit.core.data.persona.domain.model.GenderType
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset

sealed class BodyTypeViewData(val id: Int, val name: String, val icon: SkyFitAsset.BodyTypeIcon) {
    data object MaleEctomorph : BodyTypeViewData(1, "Ectomorph", SkyFitAsset.BodyTypeIcon.MALE_ECTO)
    data object MaleMesomorph : BodyTypeViewData(2, "Mesomorph", SkyFitAsset.BodyTypeIcon.MALE_MESO)
    data object MaleEndomorph : BodyTypeViewData(3, "Endomorph", SkyFitAsset.BodyTypeIcon.MALE_ENDO)

    data object FemaleEctomorph : BodyTypeViewData(1, "Ectomorph", SkyFitAsset.BodyTypeIcon.FEMALE_ECTO)
    data object FemaleMesomorph : BodyTypeViewData(2, "Mesomorph", SkyFitAsset.BodyTypeIcon.FEMALE_MESO)
    data object FemaleEndomorph : BodyTypeViewData(3, "Endomorph", SkyFitAsset.BodyTypeIcon.FEMALE_ENDO)

    companion object {
        fun from(gender: GenderType): List<BodyTypeViewData> {
            return when (gender) {
                GenderType.MALE -> listOf(MaleEctomorph, MaleMesomorph, MaleEndomorph)
                GenderType.FEMALE -> listOf(FemaleEctomorph, FemaleMesomorph, FemaleEndomorph)
            }
        }
    }
}
