package com.vurgun.skyfit.feature.dashboard.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.UserDetail
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.feature.dashboard.component.HomeAppointmentItemViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class UserHomeAction {
    data object NavigateToNotifications : UserHomeAction()
    data object NavigateToConversations : UserHomeAction()
    data object NavigateToAppointments : UserHomeAction()
}

sealed class UserHomeEffect {
    data object NavigateToNotifications : UserHomeEffect()
    data object NavigateToConversations : UserHomeEffect()
    data object NavigateToAppointments : UserHomeEffect()
}


class UserHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository
) : ScreenModel {

    private val _effect = SingleSharedFlow<UserHomeEffect>()
    val effect: SharedFlow<UserHomeEffect> = _effect

    private val user: UserDetail
        get() = userManager.user.value as? UserDetail
            ?: error("❌ current account is not user")

    val characterType = user.characterType

    private val _appointments = MutableStateFlow<List<HomeAppointmentItemViewData>>(emptyList())
    val appointments = _appointments.asStateFlow()

    fun onAction(action: UserHomeAction) {
        when (action) {
            UserHomeAction.NavigateToAppointments ->
                emitEffect(UserHomeEffect.NavigateToAppointments)

            UserHomeAction.NavigateToConversations ->
                emitEffect(UserHomeEffect.NavigateToConversations)

            UserHomeAction.NavigateToNotifications ->
                emitEffect(UserHomeEffect.NavigateToNotifications)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            courseRepository.getUpcomingAppointmentsByUser(user.normalUserId)
                .map { appointments ->
                    appointments.map {
                        HomeAppointmentItemViewData(it.lessonId, it.iconId, it.title, it.startTime.toString(), it.facilityName)
                    }
                }.fold(
                    onSuccess = {
                        _appointments.value = it
                    },
                    onFailure = {
                        print("❌ Appointments failed as you can see ${it.message}")
                    }
                )
        }
    }

    private fun emitEffect(effect: UserHomeEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}