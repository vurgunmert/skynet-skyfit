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
import com.vurgun.skyfit.core.data.v1.domain.account.model.TrainerAccount
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.statistics.front.StatisticCardUiData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.home.component.LessonFilterData
import com.vurgun.skyfit.feature.home.model.FacilityHomeEffect.ShowOverlay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed interface TrainerHomeUiState {
    data object Loading : TrainerHomeUiState
    data class Error(val message: String?) : TrainerHomeUiState
    data class Content(
        val account: TrainerAccount,
        val profile: TrainerProfile,
        val statistics: DashboardStatCardModel? = null,
        val allLessons: List<LessonSessionItemViewData> = emptyList(),
        val filteredLessons: List<LessonSessionItemViewData> = emptyList(),
        val upcomingLessons: List<LessonSessionItemViewData> = emptyList(),
        val appliedFilter: LessonFilterData = LessonFilterData(),
        val notificationsEnabled: Boolean = false,
        val conversationsEnabled: Boolean = false,
    ) : TrainerHomeUiState
}

sealed interface TrainerHomeAction {
    data object OnClickNotifications : TrainerHomeAction
    data object OnClickConversations : TrainerHomeAction
    data object OnClickAppointments : TrainerHomeAction
    data class OnClickAppointment(val lessonId: Int) : TrainerHomeAction
    data object OnClickChatBot : TrainerHomeAction
    data object OnClickFilter : TrainerHomeAction
    data class ApplyLessonFilter(val filter: LessonFilterData) : TrainerHomeAction
}

sealed interface TrainerHomeEffect {
    data class NavigateToVisitFacility(val facilityId: Int) : TrainerHomeEffect
    data object NavigateToNotifications : TrainerHomeEffect
    data object NavigateToConversations : TrainerHomeEffect
    data object NavigateToAppointments : TrainerHomeEffect
    data object NavigateToChatBot : TrainerHomeEffect
    data class ShowLessonFilter(val lessons: List<LessonSessionItemViewData>) : TrainerHomeEffect
    data class NavigateToAppointment(val lessonId: Int) : TrainerHomeEffect
    data class ShowOverlay(val screen: ScreenProvider) : TrainerHomeEffect
    data object DismissOverlay : TrainerHomeEffect
}

class TrainerHomeViewModel(
    private val userManager: ActiveAccountManager,
    private val trainerRepository: TrainerRepository,
    private val mapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _uiState = UiStateDelegate<TrainerHomeUiState>(TrainerHomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<TrainerHomeEffect>()
    val effect: SharedFlow<TrainerHomeEffect> = _effect

    private val _debouncedActions = MutableSharedFlow<TrainerHomeAction>(extraBufferCapacity = 1)

    init {
        screenModelScope.launch {
            _debouncedActions
                .debounce(500L)
                .distinctUntilChanged()
                .collectLatest { action ->
                    domainHandleAction(action)
                }
        }
    }

    fun onAction(action: TrainerHomeAction) {
        _debouncedActions.tryEmit(action)
    }

    private fun domainHandleAction(action: TrainerHomeAction) {
        when (action) {
            TrainerHomeAction.OnClickAppointments ->
                emitEffect(TrainerHomeEffect.NavigateToAppointments)

            TrainerHomeAction.OnClickConversations ->
                emitEffect(TrainerHomeEffect.NavigateToConversations)

            TrainerHomeAction.OnClickNotifications ->
                emitEffect(TrainerHomeEffect.NavigateToNotifications)

            TrainerHomeAction.OnClickChatBot ->
                emitEffect(TrainerHomeEffect.NavigateToChatBot)

            TrainerHomeAction.OnClickFilter ->
                emitEffect(
                    TrainerHomeEffect.ShowOverlay(
                        SharedScreen.LessonFilter(
                            lessons = (uiState.value as? TrainerHomeUiState.Content)?.allLessons ?: emptyList(),
                            onApply = { applyLessonFilter(it as LessonFilterData) }
                        )))

            is TrainerHomeAction.ApplyLessonFilter -> applyLessonFilter(action.filter)
            is TrainerHomeAction.OnClickAppointment ->
                emitEffect(TrainerHomeEffect.NavigateToAppointment(action.lessonId.toInt()))
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val trainerDetail = userManager.account.value as TrainerAccount
                val trainerProfile = trainerRepository.getTrainerProfile(trainerDetail.trainerId).getOrThrow()

                val allLessons = trainerRepository.getLessonsByTrainer(
                    trainerId = trainerDetail.trainerId,
                    startDate = LocalDate(2025, 1, 1),
                    endDate = null
                ).getOrNull()?.let { list -> list.map { mapper.map(it) } }.orEmpty()

                val upcomingLessons = trainerRepository.getUpcomingLessonsByTrainer(trainerDetail.trainerId)
                    .getOrNull()?.let { list -> list.map { mapper.map(it) } }.orEmpty()

                val metrics = listOf(
                    StatisticCardUiData("Video", "${trainerProfile.videoCount}"),
                    StatisticCardUiData("Aktif Dersler", "${trainerProfile.lessonCount}"),
                    StatisticCardUiData("Puan", "${trainerProfile.point}")
                )

                val statistics = DashboardStatCardModel(primaryMetrics = metrics)

                _uiState.update(
                    TrainerHomeUiState.Content(
                        account = trainerDetail,
                        profile = trainerProfile,
                        statistics = statistics,
                        allLessons = allLessons,
                        filteredLessons = allLessons,
                        upcomingLessons = upcomingLessons
                    )
                )
            }.onFailure {
                _uiState.update(TrainerHomeUiState.Error(it.message))
            }
        }
    }

    private fun emitEffect(effect: TrainerHomeEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }

    private fun stats() {
        val statList = listOf(
            StatisticCardUiData(
                title = "Takipçi",
                value = "327",
                changePercent = 53,
                changeDirection = StatisticCardUiData.ChangeDirection.UP
            ),
            StatisticCardUiData(
                title = "Aktif Dersler",
                value = "12",
                changePercent = 2,
                changeDirection = StatisticCardUiData.ChangeDirection.UP
            ),
            StatisticCardUiData(
                title = "SkyFit Kazancı",
                value = "$1327",
                changePercent = 53,
                changeDirection = StatisticCardUiData.ChangeDirection.UP
            ),
            StatisticCardUiData(
                title = "Profil Görüntülenmesi",
                value = "211",
                changePercent = 12,
                changeDirection = StatisticCardUiData.ChangeDirection.DOWN
            )
        )
    }

    private fun resetFilter() {
        val content = (uiState.value as? TrainerHomeUiState.Content) ?: return
        _uiState.update(content.copy(filteredLessons = content.allLessons, appliedFilter = LessonFilterData()))
    }

    private fun applyLessonFilter(filter: LessonFilterData) {
        val content = (uiState.value as? TrainerHomeUiState.Content) ?: return

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
        _effect.emitIn(screenModelScope, TrainerHomeEffect.DismissOverlay)
    }
}