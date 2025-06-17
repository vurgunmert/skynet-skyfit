package com.vurgun.skyfit.core.data.v1.domain.profile

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
