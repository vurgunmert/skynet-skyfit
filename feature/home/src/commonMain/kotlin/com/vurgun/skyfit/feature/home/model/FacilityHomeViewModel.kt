package com.vurgun.skyfit.feature.home.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.UiEventDelegate
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.statistics.front.StatisticCardUiData
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.home.component.LessonFilterData
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

/**
 * ViewModel for the Facility Home screen.
 * Handles user actions, manages UI state, and coordinates data operations.
 */
class FacilityHomeViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository,
    private val mapper: LessonSessionItemViewDataMapper,
    private val filterProcessor: LessonFilterProcessor = LessonFilterProcessor()
) : ScreenModel {

    private val _uiState = UiStateDelegate<FacilityHomeUiState>(FacilityHomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _eventDelegate = UiEventDelegate<FacilityHomeUiEvent>()
    val eventFlow = _eventDelegate.eventFlow

    /**
     * Handles user actions and updates the UI state or emits events accordingly.
     */
    fun onAction(action: FacilityHomeAction) {
        when (action) {
            is FacilityHomeAction.OnOverlayRequest -> 
                _eventDelegate.sendIn(screenModelScope, FacilityHomeUiEvent.ShowOverlay(action.screen))

            FacilityHomeAction.OnClickLessons -> 
                _eventDelegate.sendIn(screenModelScope, FacilityHomeUiEvent.NavigateToManageLessons)

            FacilityHomeAction.OnClickChatBot -> 
                _eventDelegate.sendIn(screenModelScope, FacilityHomeUiEvent.ShowOverlay(SharedScreen.ChatBot))

            FacilityHomeAction.OnClickConversations -> 
                _eventDelegate.sendIn(screenModelScope, FacilityHomeUiEvent.ShowOverlay(SharedScreen.Conversations))

            FacilityHomeAction.OnClickNotifications -> 
                _eventDelegate.sendIn(screenModelScope, FacilityHomeUiEvent.ShowOverlay(SharedScreen.Notifications))

            is FacilityHomeAction.ApplyLessonFilter -> 
                applyLessonFilter(action.filter)

            is FacilityHomeAction.SearchLesson -> 
                applyLessonFilter(LessonFilterData(query = action.query))

            FacilityHomeAction.OnClickFilter -> {
                val content = uiState.value as? FacilityHomeUiState.Content ?: return
                _eventDelegate.sendIn(
                    screenModelScope,
                    FacilityHomeUiEvent.ShowOverlay(
                        SharedScreen.LessonFilter(
                            lessons = content.allLessons,
                            onApply = { applyLessonFilter(it as LessonFilterData) }
                        )
                    )
                )
            }
        }
    }

    /**
     * Loads facility data, including profile, lessons, and statistics.
     */
    fun loadData() {
        _uiState.update(FacilityHomeUiState.Loading)

        screenModelScope.launch {
            runCatching {
                // Get facility account and profile
                val facility = userManager.account.value as? FacilityAccount 
                    ?: throw IllegalStateException("Current account is not a facility account")

                val profileResult = facilityRepository.getFacilityProfile(facility.gymId)
                val profile = profileResult.getOrThrow()

                // Create statistics model
                val statistics = createStatisticsModel(profile)

                // Get lessons data
                val activeLessons = facilityRepository.getUpcomingLessonsByFacility(facility.gymId, 5)
                    .getOrNull()?.map(mapper::map).orEmpty()

                val allLessons = facilityRepository.getAllLessonsByFacility(facility.gymId, LocalDate(2025, 1, 1), null)
                    .getOrNull()?.map(mapper::map).orEmpty()

                // Update UI state with loaded data
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

    /**
     * Resets the applied filter and shows all lessons.
     */
    private fun resetFilter() {
        val content = uiState.value as? FacilityHomeUiState.Content ?: return
        _uiState.update(
            content.copy(
                filteredLessons = content.allLessons, 
                appliedFilter = LessonFilterData()
            )
        )
    }

    /**
     * Applies filter to lessons and updates the UI state.
     */
    private fun applyLessonFilter(filter: LessonFilterData) {
        val content = uiState.value as? FacilityHomeUiState.Content ?: return

        val filteredLessons = filterProcessor.applyFilter(content.allLessons, filter)

        _uiState.update(
            content.copy(
                filteredLessons = filteredLessons,
                appliedFilter = filter
            )
        )
        _eventDelegate.sendIn(screenModelScope, FacilityHomeUiEvent.DismissOverlay)
    }

    /**
     * Creates a statistics model from facility profile data.
     */
    private fun createStatisticsModel(profile: com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile): DashboardStatCardModel {
        return DashboardStatCardModel(
            primaryMetrics = listOf(
                StatisticCardUiData("Aktif Üye", "${profile.memberCount}"),
                StatisticCardUiData("Aktif Egitmen", "${profile.trainerCount}"),
                StatisticCardUiData("FIWE Kazancın", "-"),
                StatisticCardUiData("Puan", "${profile.point}")
            )
        )
    }
}
