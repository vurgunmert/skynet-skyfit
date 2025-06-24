package com.vurgun.skyfit.profile.user.measurements


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.HealthUtils
import com.vurgun.skyfit.core.data.utility.UiEffectDelegate
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.HealthMetricResult
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.Measurement
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.MeasurementCategory
import com.vurgun.skyfit.core.data.v1.domain.measurement.repository.MeasurementRepository
import kotlinx.coroutines.launch

sealed class UserMeasurementsUiState {
    data object Loading : UserMeasurementsUiState()
    data class Error(val message: String?) : UserMeasurementsUiState()
    data class Content(
        val categories: List<MeasurementCategory>,
        val measurements: List<Measurement>,
        val healthResults: List<HealthMetricResult>
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
                val allCategories = MeasurementCategory.bodyMeasurements() + MeasurementCategory.fitnessTests()
                val measurements = measurementRepository.getLatestMeasurements().getOrDefault(emptyList())
                val healthResults = calculateRates(measurements)
                _uiState.update(UserMeasurementsUiState.Content(allCategories, measurements, healthResults))
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

    fun calculateRates(
        measurements: List<Measurement>
    ): List<HealthMetricResult> {

        val categoryValues: Map<MeasurementCategory, Float> = measurements.mapNotNull { m ->
            MeasurementCategory.entries.find { it.id == m.categoryId }?.let { category ->
                category to m.value.toFloat()
            }
        }.toMap()

        val results = mutableListOf<HealthMetricResult>()

        // BMI
        val bmi = categoryValues[MeasurementCategory.WEIGHT]?.let { weight ->
            categoryValues[MeasurementCategory.HEIGHT]?.let { height ->
                HealthUtils.calculateBMI(weightKg = weight, heightCm = height)
            }
        }
        if (bmi != null) results.add(HealthMetricResult.BMI(bmi))

        // WHR
        val whr = categoryValues[MeasurementCategory.WAIST]?.let { waist ->
            categoryValues[MeasurementCategory.HIP]?.let { hip ->
                HealthUtils.calculateWHR(waist, hip)
            }
        }
        if (whr != null) results.add(HealthMetricResult.WHR(whr))

        // Arm Symmetry
        val armSymmetry = categoryValues[MeasurementCategory.RIGHT_ARM]?.let { right ->
            categoryValues[MeasurementCategory.LEFT_ARM]?.let { left ->
                HealthUtils.calculateArmSymmetry(right, left)
            }
        }
        if (armSymmetry != null) results.add(HealthMetricResult.ArmSymmetry(armSymmetry))

        // Leg Symmetry
        val legSymmetry = categoryValues[MeasurementCategory.RIGHT_LEG]?.let { right ->
            categoryValues[MeasurementCategory.LEFT_LEG]?.let { left ->
                HealthUtils.calculateLegSymmetry(right, left)
            }
        }
        if (legSymmetry != null) results.add(HealthMetricResult.LegSymmetry(legSymmetry))

        // Chest/Waist Ratio
        val chestWaistRatio = categoryValues[MeasurementCategory.CHEST]?.let { chest ->
            categoryValues[MeasurementCategory.WAIST]?.let { waist ->
                HealthUtils.calculateChestWaistRatio(chest, waist)
            }
        }
        if (chestWaistRatio != null) results.add(HealthMetricResult.ChestWaistRatio(chestWaistRatio))

        // Flexibility
        categoryValues[MeasurementCategory.SIT_AND_REACH]?.let { flexibility ->
            results.add(HealthMetricResult.Flexibility(flexibility))
        }

        // Push-up
        categoryValues[MeasurementCategory.PUSH_UP]?.let { pushUpCount ->
            results.add(HealthMetricResult.PushUp(pushUpCount))
        }

        // HLS – depends on calculated values
        if (bmi != null && whr != null && armSymmetry != null && categoryValues[MeasurementCategory.PUSH_UP] != null) {
            val hls = HealthUtils.calculateHLS(
                bmi = bmi,
                whr = whr,
                pushUp = categoryValues[MeasurementCategory.PUSH_UP]?.toInt(),
                symmetry = armSymmetry
            ).toFloat()
            results.add(HealthMetricResult.HLS(hls))
        }

        return results
    }

}