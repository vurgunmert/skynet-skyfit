package com.vurgun.skyfit.feature.home.model

import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.feature.home.component.LessonFilterData

sealed interface FacilityHomeUiState {
    data object Loading : FacilityHomeUiState
    data class Error(val message: String?) : FacilityHomeUiState
    data class Content(
        val facility: FacilityAccount,
        val profile: FacilityProfile,
        val upcomingLessons: List<LessonSessionItemViewData> = emptyList(),
        val allLessons: List<LessonSessionItemViewData> = emptyList(),
        val filteredLessons: List<LessonSessionItemViewData> = emptyList(),
        val statistics: DashboardStatCardModel,
        val notificationsEnabled: Boolean = true,
        val conversationsEnabled: Boolean = true,
        val appliedFilter: LessonFilterData = LessonFilterData(),
    ) : FacilityHomeUiState
}