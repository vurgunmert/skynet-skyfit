package com.vurgun.skyfit.feature_profile.ui.components.viewdata

data class LifestyleActionRowViewData(
    val iconId: String,
    val title: String,
    val items: List<LifestyleActionItemViewData>,
    val iconSizePx: Int = 32
)

data class LifestyleActionItemViewData(
    val iconId: String,
    val label: String
)
