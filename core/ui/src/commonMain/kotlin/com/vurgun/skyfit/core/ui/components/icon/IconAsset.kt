package com.vurgun.skyfit.core.ui.components.icon

import org.jetbrains.compose.resources.DrawableResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_calendar_dots
import skyfit.core.ui.generated.resources.ic_chevron_down
import skyfit.core.ui.generated.resources.ic_chevron_left
import skyfit.core.ui.generated.resources.ic_chevron_right
import skyfit.core.ui.generated.resources.ic_minus
import skyfit.core.ui.generated.resources.ic_plus

//TODO: May or maynot - better to remove?
sealed class IconAsset(val id: Int, val resource: DrawableResource) {
    data object ChevronLeft: IconAsset(1, Res.drawable.ic_chevron_left)
    data object ChevronRight: IconAsset(2, Res.drawable.ic_chevron_right)
    data object ChevronDown: IconAsset(3, Res.drawable.ic_chevron_down)
    data object CalendarDots: IconAsset(4, Res.drawable.ic_calendar_dots)
    data object Minus: IconAsset(5, Res.drawable.ic_minus)
    data object Plus: IconAsset(6, Res.drawable.ic_plus)
}