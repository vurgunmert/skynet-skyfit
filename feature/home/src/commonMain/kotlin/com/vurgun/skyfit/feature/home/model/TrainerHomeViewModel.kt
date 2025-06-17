package com.vurgun.skyfit.feature.home.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.TrainerAccount
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.statistics.front.StatisticCardUiData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed interface TrainerHomeUiState {
    data object Loading : TrainerHomeUiState
    data class Error(val message: String?) : TrainerHomeUiState
    data class Content(
        val account: TrainerAccount,
        val profile: TrainerProfile,
        val statistics: DashboardStatCardModel? = null,
        val lessons: List<LessonSessionItemViewData> = emptyList()
    ) : TrainerHomeUiState
}

sealed interface TrainerHomeAction {
    data object OnClickNotifications : TrainerHomeAction
    data object OnClickConversations : TrainerHomeAction
    data object OnClickAppointments : TrainerHomeAction
    data object OnClickChatBot : TrainerHomeAction
}

sealed interface TrainerHomeEffect {
    data class NavigateToVisitFacility(val facilityId: Int) : TrainerHomeEffect
    data object NavigateToNotifications : TrainerHomeEffect
    data object NavigateToConversations : TrainerHomeEffect
    data object NavigateToAppointments : TrainerHomeEffect
    data object NavigateToChatBot : TrainerHomeEffect
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

    fun onAction(action: TrainerHomeAction) {
        when (action) {
            TrainerHomeAction.OnClickAppointments ->
                emitEffect(TrainerHomeEffect.NavigateToAppointments)

            TrainerHomeAction.OnClickConversations ->
                emitEffect(TrainerHomeEffect.NavigateToConversations)

            TrainerHomeAction.OnClickNotifications ->
                emitEffect(TrainerHomeEffect.NavigateToNotifications)

            TrainerHomeAction.OnClickChatBot ->
                emitEffect(TrainerHomeEffect.NavigateToChatBot)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val trainerDetail = userManager.account.value as TrainerAccount
                val trainerProfile = trainerRepository.getTrainerProfile(trainerDetail.trainerId).getOrThrow()

                val lessons = trainerRepository.getUpcomingLessonsByTrainer(trainerDetail.trainerId)
                    .getOrNull()?.let { list ->
                        list.map { mapper.map(it) }
                    }.orEmpty()

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
                        lessons = lessons
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
}