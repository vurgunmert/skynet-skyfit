package com.vurgun.skyfit.feature.dashboard.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.persona.domain.model.FacilityDetail
import com.vurgun.skyfit.core.data.persona.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.persona.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.schedule.data.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.schedule.data.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

data class DashboardStatCardModel(
    val primaryMetrics: List<DashboardStatMetric>,
    val chartData: Any? = null,
    val timeRange: TimeRange = TimeRange.H
) {
    enum class TimeRange {
        Y, M6, M3, M1, H
    }
}

data class DashboardStatMetric(
    val title: String,
    val value: String,
    val changePercent: Int? = null,
    val changeDirection: ChangeDirection = ChangeDirection.NEUTRAL
) {
    enum class ChangeDirection {
        UP, DOWN, NEUTRAL
    }
}

sealed interface FacilityHomeUiState {
    data object Loading : FacilityHomeUiState
    data class Error(val message: String?) : FacilityHomeUiState
    data class Content(
        val facility: FacilityDetail,
        val profile: FacilityProfile,
        val activeLessons: List<LessonSessionItemViewData> = emptyList(),
        val allLessons: List<LessonSessionItemViewData> = emptyList(),
        val statistics: DashboardStatCardModel
    ) : FacilityHomeUiState
}

sealed interface FacilityHomeAction {
    data object OnClickNotifications : FacilityHomeAction
    data object OnClickConversations : FacilityHomeAction
    data object OnClickLessons : FacilityHomeAction
}

sealed interface FacilityHomeEffect {
    data object NavigateToNotifications : FacilityHomeEffect
    data object NavigateToConversations : FacilityHomeEffect
    data object NavigateToManageLessons : FacilityHomeEffect
}

class FacilityHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val profileRepository: ProfileRepository,
    private val mapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _uiState = UiStateDelegate<FacilityHomeUiState>(FacilityHomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<FacilityHomeEffect>()
    val effect: SharedFlow<FacilityHomeEffect> = _effect

    fun onAction(action: FacilityHomeAction) {
        when (action) {
            FacilityHomeAction.OnClickConversations -> {
                emitEffect(FacilityHomeEffect.NavigateToConversations)
            }

            FacilityHomeAction.OnClickLessons -> {
                emitEffect(FacilityHomeEffect.NavigateToManageLessons)
            }

            FacilityHomeAction.OnClickNotifications -> {
                emitEffect(FacilityHomeEffect.NavigateToNotifications)
            }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val facility = userManager.user.value as FacilityDetail
                val facilityProfile = profileRepository.getFacilityProfile(facility.gymId).getOrThrow()

                val metrics = listOf(
                    DashboardStatMetric("Aktif Üye", "${facilityProfile.memberCount}"),
                    DashboardStatMetric("Aktif Egitmen", "${facilityProfile.trainerCount}"),
                    DashboardStatMetric("Puan", "${facilityProfile.point}")
                )

                val statistics = DashboardStatCardModel(primaryMetrics = metrics)

                val activeLessons = courseRepository.getUpcomingLessonsByFacility(facility.gymId, limit = 5)
                    .getOrNull()?.let { list ->
                        list.map { mapper.map(it) }
                    }.orEmpty()

                val allLessons = courseRepository.getAllLessonsByFacility(facility.gymId, startDate = LocalDate(2025,1,1), null)
                    .getOrNull()?.let { list ->
                        list.map { mapper.map(it) }
                    }.orEmpty()

                _uiState.update(FacilityHomeUiState.Content(facility, facilityProfile, activeLessons, allLessons, statistics))
            }.onFailure {
                _uiState.update(FacilityHomeUiState.Error(it.message))
            }
        }
    }

    private fun emitEffect(effect: FacilityHomeEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}