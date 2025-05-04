package com.vurgun.skyfit.feature.calendar.screen.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.data.courses.domain.model.AppointmentDetail
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UserAppointmentDetailUiState {
    data object Loading : UserAppointmentDetailUiState()
    data class Error(val message: String) : UserAppointmentDetailUiState()
    data class Content(val appointment: AppointmentDetail) : UserAppointmentDetailUiState()
}

sealed class UserAppointmentDetailAction {
    data object NavigateToBack : UserAppointmentDetailAction()
    data object CancelAppointment : UserAppointmentDetailAction()
}

sealed class UserAppointmentDetailEffect {
    data object NavigateToBack : UserAppointmentDetailEffect()
    data class ShowCancelError(val message: String) : UserAppointmentDetailEffect()
}

class UserAppointmentDetailViewModel(
    private val courseRepository: CourseRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<UserAppointmentDetailUiState>(UserAppointmentDetailUiState.Loading)
    val uiState: StateFlow<UserAppointmentDetailUiState> get() = _uiState

    private val _effect = MutableSharedFlow<UserAppointmentDetailEffect>()
    val effect: SharedFlow<UserAppointmentDetailEffect> = _effect

    fun onAction(action: UserAppointmentDetailAction) {
        when (action) {
            UserAppointmentDetailAction.NavigateToBack -> emitEffect(UserAppointmentDetailEffect.NavigateToBack)
            UserAppointmentDetailAction.CancelAppointment -> cancelAppointment()
        }
    }

    fun loadAppointment(lpId: Int) {
        screenModelScope.launch {
            try {
                val appointment = courseRepository.getAppointmentDetail(lpId).getOrThrow()
                _uiState.value = UserAppointmentDetailUiState.Content(appointment)
            } catch (e: Exception) {
                _uiState.value = UserAppointmentDetailUiState.Error(e.message ?: "Randevu getirme hatasi")
            }
        }
    }

    private fun cancelAppointment() {
        val appointment = (_uiState.value as? UserAppointmentDetailUiState.Content)?.appointment ?: return

        screenModelScope.launch {
            try {
                courseRepository.cancelAppointment(appointment.lessonId, appointment.lpId).getOrThrow()
                loadAppointment(appointment.lpId)
            } catch (e: Exception) {
                emitEffect(UserAppointmentDetailEffect.ShowCancelError(e.message ?: "Randevu iptal hatasi"))
            }
        }
    }

    private fun emitEffect(effect: UserAppointmentDetailEffect) {
        screenModelScope.launch {
            _effect.emit(effect)
        }
    }
}
