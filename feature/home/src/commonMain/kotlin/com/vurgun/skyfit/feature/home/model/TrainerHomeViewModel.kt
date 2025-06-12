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
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed interface TrainerHomeUiState {
    data object Loading : TrainerHomeUiState
    data class Error(val message: String?) : TrainerHomeUiState
    data class Content(
        val trainer: TrainerAccount,
        val profile: TrainerProfile,
        val appointments: List<LessonSessionItemViewData> = emptyList()
    ) : TrainerHomeUiState
}

sealed interface TrainerHomeAction {
    data object OnClickNotifications : TrainerHomeAction
    data object OnClickConversations : TrainerHomeAction
    data object OnClickAppointments : TrainerHomeAction
}

sealed interface TrainerHomeEffect {
    data class NavigateToVisitFacility(val facilityId: Int) : TrainerHomeEffect
    data object NavigateToNotifications : TrainerHomeEffect
    data object NavigateToConversations : TrainerHomeEffect
    data object NavigateToAppointments : TrainerHomeEffect
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
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val trainerDetail = userManager.user.value as TrainerAccount
                val trainerProfile = trainerRepository.getTrainerProfile(trainerDetail.trainerId).getOrThrow()

                val appointments = trainerRepository.getUpcomingLessonsByTrainer(trainerDetail.trainerId)
                    .getOrNull()?.let { list ->
                        list.map { mapper.map(it) }
                    }.orEmpty()

                _uiState.update(TrainerHomeUiState.Content(trainerDetail, trainerProfile, appointments))
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
}