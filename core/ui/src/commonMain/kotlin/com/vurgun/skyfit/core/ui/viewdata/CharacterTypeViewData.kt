package com.vurgun.skyfit.core.ui.viewdata

import com.vurgun.skyfit.core.data.domain.model.CharacterType
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset

sealed class CharacterTypeViewData(val id: Int, val icon: SkyFitAsset.CharacterIcon) {
    data object Carrot : CharacterTypeViewData(1, SkyFitAsset.CharacterIcon.CARROT)
    data object Koala : CharacterTypeViewData(2, SkyFitAsset.CharacterIcon.KOALA)
    data object Panda : CharacterTypeViewData(3, SkyFitAsset.CharacterIcon.PANDA)

    companion object {
        fun from(type: CharacterType): CharacterTypeViewData {
            return when (type) {
                CharacterType.Carrot -> Carrot
                CharacterType.Koala -> Koala
                CharacterType.Panda -> Panda
            }
        }
    }
}
