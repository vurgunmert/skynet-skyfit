package com.vurgun.skyfit.feature.calendar.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.courses.domain.model.AppointmentDetail
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface UserAppointmentDetailUiState {
    data object Loading : UserAppointmentDetailUiState
    data class Error(val message: String) : UserAppointmentDetailUiState
    data class Content(val appointment: AppointmentDetail) : UserAppointmentDetailUiState
}

sealed interface UserAppointmentDetailAction {
    data object NavigateToBack : UserAppointmentDetailAction
    data object CancelAppointment : UserAppointmentDetailAction
}

sealed interface UserAppointmentDetailEffect {
    data object NavigateToBack : UserAppointmentDetailEffect
}

class UserAppointmentDetailViewModel(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserAppointmentDetailUiState>(UserAppointmentDetailUiState.Loading)
    val uiState: StateFlow<UserAppointmentDetailUiState> get() = _uiState

    private val _effect = MutableSharedFlow<UserAppointmentDetailEffect>()
    val effect: SharedFlow<UserAppointmentDetailEffect> = _effect

    fun onAction(action: UserAppointmentDetailAction) {
        when(action) {
            UserAppointmentDetailAction.NavigateToBack -> emitEffect(UserAppointmentDetailEffect.NavigateToBack)
            UserAppointmentDetailAction.CancelAppointment -> cancelAppointment()
        }
    }

    fun loadAppointment(lpId: Int) {
        viewModelScope.launch {
            try {
                val appointment = courseRepository.getAppointmentDetail(lpId).getOrThrow()
                _uiState.value = UserAppointmentDetailUiState.Content(appointment)
            } catch (e: Exception) {
                _uiState.value = UserAppointmentDetailUiState.Error(e.message ?: "Randevu getirme hatasi")
            }
        }
    }

    fun cancelAppointment() {
        val appointment = (_uiState.value as? UserAppointmentDetailUiState.Content)?.appointment ?: return

        viewModelScope.launch {
            try {
                courseRepository.cancelAppointment(appointment.lessonId, appointment.lpId).getOrThrow()
                loadAppointment(appointment.lpId)
            } catch (e: Exception) {
                _uiState.value = UserAppointmentDetailUiState.Error(e.message ?: "Randevu iptal hatasi")
            }
        }
    }

    private fun emitEffect(effect: UserAppointmentDetailEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}
