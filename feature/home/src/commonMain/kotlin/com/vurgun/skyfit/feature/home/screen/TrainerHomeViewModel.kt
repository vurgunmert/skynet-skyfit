package com.vurgun.skyfit.feature.home.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.TrainerDetail
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface TrainerHomeAction {
    data object NavigateToNotifications : TrainerHomeAction
    data object NavigateToConversations : TrainerHomeAction
    data object NavigateToAppointments : TrainerHomeAction
}

sealed interface TrainerHomeEffect {
    data object NavigateToNotifications : TrainerHomeEffect
    data object NavigateToConversations : TrainerHomeEffect
    data object NavigateToAppointments : TrainerHomeEffect
}

class TrainerHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val mapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _effect = SingleSharedFlow<TrainerHomeEffect>()
    val effect: SharedFlow<TrainerHomeEffect> = _effect

    private val trainerUser: TrainerDetail
        get() = userManager.user.value as? TrainerDetail
            ?: error("❌ User is not a Trainer")

    val characterType = trainerUser.characterType

    private val _appointments = MutableStateFlow<List<LessonSessionItemViewData>>(emptyList())
    val appointments = _appointments.asStateFlow()

    init {
        loadData()
    }

    fun onAction(action: TrainerHomeAction) {
        when (action) {
            TrainerHomeAction.NavigateToAppointments ->
                emitEffect(TrainerHomeEffect.NavigateToAppointments)

            TrainerHomeAction.NavigateToConversations ->
                emitEffect(TrainerHomeEffect.NavigateToConversations)

            TrainerHomeAction.NavigateToNotifications ->
                emitEffect(TrainerHomeEffect.NavigateToNotifications)
        }
    }

    private fun loadData() {
        screenModelScope.launch {
            _appointments.value = courseRepository.getUpcomingLessonsByTrainer(trainerUser.trainerId)
                .getOrNull()?.let { list ->
                    list.map { mapper.map(it) }
                }.orEmpty()
        }
    }

    private fun emitEffect(effect: TrainerHomeEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}