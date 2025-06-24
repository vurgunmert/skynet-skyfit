package com.vurgun.skyfit.profile.user.measurements


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.UiEffectDelegate
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.Measurement
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.MeasurementCategory
import com.vurgun.skyfit.core.data.v1.domain.measurement.repository.MeasurementRepository
import kotlinx.coroutines.launch

sealed class UserMeasurementsUiState {
    data object Loading : UserMeasurementsUiState()
    data class Error(val message: String?) : UserMeasurementsUiState()
    data class Content(
        val categories: List<MeasurementCategory>,
        val measurements: List<Measurement>
    ) : UserMeasurementsUiState()
}
sealed interface UserMeasurementsUiAction {
    data object OnClickBack: UserMeasurementsUiAction
    data class OnSaveMeasurement(
        val measurement: Measurement? = null,
        val category: MeasurementCategory,
        val value: Int,
        val unitId: Int? = null
    ) : UserMeasurementsUiAction
}

sealed interface UserMeasurementsUiEffect {
    data object NavigateBack : UserMeasurementsUiEffect
}

class UserMeasurementsViewModel(
    private val measurementRepository: MeasurementRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<UserMeasurementsUiState>(UserMeasurementsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = UiEffectDelegate<UserMeasurementsUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onAction(action: UserMeasurementsUiAction) {
        when (action) {
            UserMeasurementsUiAction.OnClickBack ->
                _uiEffect.emitIn(screenModelScope, UserMeasurementsUiEffect.NavigateBack)
            is UserMeasurementsUiAction.OnSaveMeasurement ->
                saveMeasurement(action.measurement, action.category, action.value, action.unitId)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val allCategories = MeasurementCategory.bodyMeasurements()
                val measurements = measurementRepository.getLatestMeasurements().getOrDefault(emptyList())
                _uiState.update(UserMeasurementsUiState.Content(allCategories, measurements))
            }.onFailure {
                _uiState.update(UserMeasurementsUiState.Error(it.message))
            }
        }
    }

    fun saveMeasurement(measurement: Measurement?, category: MeasurementCategory, value: Int, unitId: Int? = null) {
        screenModelScope.launch {
            runCatching {
                if (measurement == null) {
                    measurementRepository.addMeasurement(category.id, value, unitId)
                } else {
                    measurementRepository.updateMeasurement(measurement.measurementId, value, unitId)
                }
                loadData()
            }.onFailure { error ->
                print(error)
            }
        }
    }
}