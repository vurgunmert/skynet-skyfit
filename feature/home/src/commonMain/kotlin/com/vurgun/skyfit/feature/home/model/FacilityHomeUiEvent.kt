package com.vurgun.skyfit.feature.home.model

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed interface FacilityHomeUiEvent {
    data class ShowOverlay(val screen: ScreenProvider) : FacilityHomeUiEvent
    data object DismissOverlay : FacilityHomeUiEvent
    data object NavigateToManageLessons : FacilityHomeUiEvent
}