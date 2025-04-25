package com.vurgun.skyfit.feature.profile.components.viewdata

data class LifestyleActionRowViewData(
    val iconId: Int,
    val title: String,
    val items: List<LifestyleActionItemViewData>,
    val iconSizePx: Int = 32
)

data class LifestyleActionItemViewData(
    val iconId: Int,
    val label: String
)
