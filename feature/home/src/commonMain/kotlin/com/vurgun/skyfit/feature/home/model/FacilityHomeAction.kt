package com.vurgun.skyfit.feature.home.model

import cafe.adriel.voyager.core.registry.ScreenProvider
import com.vurgun.skyfit.feature.home.component.LessonFilterData

sealed interface FacilityHomeAction {
    data class OnOverlayRequest(val screen: ScreenProvider) : FacilityHomeAction
    data class ApplyLessonFilter(val filter: LessonFilterData) : FacilityHomeAction
    data class SearchLesson(val query: String? = null) : FacilityHomeAction
    data object OnClickLessons : FacilityHomeAction
    data object OnClickNotifications : FacilityHomeAction
    data object OnClickConversations : FacilityHomeAction
    data object OnClickChatBot : FacilityHomeAction
    data object OnClickFilter : FacilityHomeAction
}