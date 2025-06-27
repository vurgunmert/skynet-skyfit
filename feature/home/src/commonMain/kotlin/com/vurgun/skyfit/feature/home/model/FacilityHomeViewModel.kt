package com.vurgun.skyfit.feature.home.model

import androidx.compose.ui.util.fastFilter
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.statistics.front.StatisticCardUiData
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.home.component.LessonFilterData
import com.vurgun.skyfit.feature.home.model.FacilityHomeEffect.NavigateToManageLessons
import com.vurgun.skyfit.feature.home.model.FacilityHomeEffect.ShowOverlay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

data class DashboardStatCardModel(
    val primaryMetrics: List<StatisticCardUiData>,
    val chartData: List<Any> = emptyList(),
    val timeRange: TimeRange = TimeRange.H
) {
    enum class TimeRange {
        Y, M6, M3, M1, H
    }
}

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

sealed interface FacilityHomeEffect {
    data class ShowOverlay(val screen: ScreenProvider) : FacilityHomeEffect
    data object DismissOverlay : FacilityHomeEffect
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

    private val _debouncedActions = MutableSharedFlow<FacilityHomeAction>(extraBufferCapacity = 1)

    init {
        screenModelScope.launch {
            _debouncedActions
                .distinctUntilChanged() // Prevent back-to-back same actions
                .collectLatest { action ->
                    domainHandleAction(action)
                }
        }
    }

    fun onAction(action: FacilityHomeAction) {
        _debouncedActions.tryEmit(action)
    }

    private fun domainHandleAction(action: FacilityHomeAction) {
        when (action) {

            is FacilityHomeAction.OnOverlayRequest ->
                emitEffect(ShowOverlay(action.screen))

            FacilityHomeAction.OnClickLessons -> {
                emitEffect(NavigateToManageLessons)
            }

            FacilityHomeAction.OnClickChatBot ->
                emitEffect(ShowOverlay(SharedScreen.ChatBot))

            FacilityHomeAction.OnClickConversations ->
                emitEffect(ShowOverlay(SharedScreen.Conversations))

            FacilityHomeAction.OnClickNotifications ->
                emitEffect(ShowOverlay(SharedScreen.Notifications))

            is FacilityHomeAction.ApplyLessonFilter ->
                applyLessonFilter(action.filter)

            is FacilityHomeAction.SearchLesson ->
                applyLessonFilter(LessonFilterData(query = action.query))

            FacilityHomeAction.OnClickFilter ->
                emitEffect(
                    ShowOverlay(
                        SharedScreen.LessonFilter(
                            lessons = (uiState.value as? FacilityHomeUiState.Content)?.allLessons ?: emptyList(),
                            onApply = { applyLessonFilter(it as LessonFilterData) }
                        )))
        }
    }

    fun loadData() {
        _uiState.update(FacilityHomeUiState.Loading)

        screenModelScope.launch {
            runCatching {
                val facility = userManager.account.value as FacilityAccount
                val profile = facilityRepository.getFacilityProfile(facility.gymId).getOrThrow()

                val statistics = DashboardStatCardModel(
                    primaryMetrics = listOf(
                        StatisticCardUiData("Aktif Üye", "${profile.memberCount}"),
                        StatisticCardUiData("Aktif Egitmen", "${profile.trainerCount}"),
                        StatisticCardUiData("FIWE Kazancın", "-"),
                        StatisticCardUiData("Puan", "${profile.point}")
                    )
                )

                val activeLessons = facilityRepository.getUpcomingLessonsByFacility(facility.gymId, 5)
                    .getOrNull()?.map(mapper::map).orEmpty()

                val allLessons = facilityRepository.getAllLessonsByFacility(facility.gymId, LocalDate(2025, 1, 1), null)
                    .getOrNull()?.map(mapper::map).orEmpty()

                _uiState.update(
                    FacilityHomeUiState.Content(
                        facility = facility,
                        profile = profile,
                        upcomingLessons = activeLessons,
                        allLessons = allLessons,
                        filteredLessons = allLessons,
                        statistics = statistics
                    )
                )
            }.onFailure { error ->
                _uiState.update(FacilityHomeUiState.Error(error.message))
            }
        }
    }

    private fun emitEffect(effect: FacilityHomeEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }

    private fun resetFilter() {
        val content = (uiState.value as? FacilityHomeUiState.Content) ?: return
        _uiState.update(content.copy(filteredLessons = content.allLessons, appliedFilter = LessonFilterData()))
    }

    private fun applyLessonFilter(filter: LessonFilterData) {
        val content = (uiState.value as? FacilityHomeUiState.Content) ?: return

        val filteredLessons = content.allLessons
            .let { lessons ->
                var result = lessons

                filter.query
                    ?.takeIf { it.isNotBlank() }
                    ?.let { query ->
                        result = result.fastFilter { it.title.contains(query, ignoreCase = true) }
                    }

                if (filter.selectedTitles.isNotEmpty()) {
                    result = result.filter { it.title in filter.selectedTitles }
                }

                if (filter.selectedTrainers.isNotEmpty()) {
                    result = result.filter { it.trainer in filter.selectedTrainers }
                }

                if (filter.selectedHours.isNotEmpty()) {
                    result = result.filter { it.hours in filter.selectedHours }
                }

                if (filter.selectedStatuses.isNotEmpty()) {
                    result = result.filter { it.statusName in filter.selectedStatuses }
                }
                result
            }

        _uiState.update(
            content.copy(
                filteredLessons = filteredLessons,
                appliedFilter = filter
            )
        )
        _effect.emitIn(screenModelScope, FacilityHomeEffect.DismissOverlay)
    }
}