package com.vurgun.skyfit.feature.home.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.feature.home.component.HomeTopBarState
import com.vurgun.skyfit.feature.home.model.FacilityHomeEffect.NavigateToManageLessons
import com.vurgun.skyfit.feature.home.model.FacilityHomeEffect.ShowOverlay
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
        val topBarState: HomeTopBarState = HomeTopBarState(),
        val facility: FacilityAccount,
        val profile: FacilityProfile,
        val activeLessons: List<LessonSessionItemViewData> = emptyList(),
        val allLessons: List<LessonSessionItemViewData> = emptyList(),
        val statistics: DashboardStatCardModel,
        val notificationsEnabled: Boolean = true,
        val conversationsEnabled: Boolean = true,
    ) : FacilityHomeUiState
}

sealed interface FacilityHomeAction {
    data class OnOverlayRequest(val screen: ScreenProvider) : FacilityHomeAction
    data object OnClickLessons : FacilityHomeAction
}

sealed interface FacilityHomeEffect {
    data class ShowOverlay(val screen: ScreenProvider) : FacilityHomeEffect
    data object NavigateToManageLessons : FacilityHomeEffect
}

class FacilityHomeViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository,
    private val mapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _uiState = UiStateDelegate<FacilityHomeUiState>(FacilityHomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<FacilityHomeEffect>()
    val effect: SharedFlow<FacilityHomeEffect> = _effect

    fun onAction(action: FacilityHomeAction) {
        when (action) {
            is FacilityHomeAction.OnOverlayRequest ->
                emitEffect(ShowOverlay(action.screen))

            FacilityHomeAction.OnClickLessons -> {
                emitEffect(NavigateToManageLessons)
            }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val facility = userManager.user.value as FacilityAccount
                val facilityProfile = facilityRepository.getFacilityProfile(facility.gymId).getOrThrow()

                val metrics = listOf(
                    DashboardStatMetric("Aktif Üye", "${facilityProfile.memberCount}"),
                    DashboardStatMetric("Aktif Egitmen", "${facilityProfile.trainerCount}"),
                    DashboardStatMetric("Puan", "${facilityProfile.point}")
                )

                val statistics = DashboardStatCardModel(primaryMetrics = metrics)

                val activeLessons = facilityRepository.getUpcomingLessonsByFacility(facility.gymId, limit = 5)
                    .getOrNull()?.let { list ->
                        list.map { mapper.map(it) }
                    }.orEmpty()

                val allLessons =
                    facilityRepository.getAllLessonsByFacility(facility.gymId, startDate = LocalDate(2025, 1, 1), null)
                        .getOrNull()?.let { list ->
                            list.map { mapper.map(it) }
                        }.orEmpty()

                _uiState.update(
                    newState = FacilityHomeUiState.Content(
                        topBarState = HomeTopBarState(),
                        facility = facility,
                        profile = facilityProfile,
                        activeLessons = activeLessons,
                        allLessons = allLessons,
                        statistics = statistics
                    )
                )
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