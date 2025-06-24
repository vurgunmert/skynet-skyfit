package com.vurgun.skyfit.profile.user.measurements

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.Measurement
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.MeasurementCategory
import com.vurgun.skyfit.core.data.v1.domain.measurement.repository.MeasurementRepository
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.form.SkyFormInputNumberText
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.save_action

sealed class UserMeasurementsUiState {
    data object Loading : UserMeasurementsUiState()
    data class Error(val message: String?) : UserMeasurementsUiState()
    data class Content(
        val categories: List<MeasurementCategory>,
        val measurements: List<Measurement>
    ) : UserMeasurementsUiState()
}

sealed interface UserMeasurementsUiAction {
    data class OnSaveMeasurement(
        val measurement: Measurement? = null,
        val category: MeasurementCategory,
        val value: Int,
        val unitId: Int? = null
    ) : UserMeasurementsUiAction
}

class UserMeasurementsViewModel(
    private val measurementRepository: MeasurementRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<UserMeasurementsUiState>(UserMeasurementsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun onAction(action: UserMeasurementsUiAction) {
        when (action) {
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

class UserMeasurementsScreen : Screen {
    @Composable
    override fun Content() {

        val viewModel = koinScreenModel<UserMeasurementsViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (val state = uiState) {
            is UserMeasurementsUiState.Content -> {
                MeasurementForm(state, viewModel::onAction)
            }

            is UserMeasurementsUiState.Error -> {
                ErrorScreen(message = state.message) { viewModel.loadData() }
            }

            UserMeasurementsUiState.Loading -> {
                FullScreenLoaderContent()
            }
        }
    }
}

@Composable
private fun MeasurementForm(
    content: UserMeasurementsUiState.Content,
    onAction: (UserMeasurementsUiAction) -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
                .widthIn(max = 680.dp)
        ) {
            content.categories.forEach { category ->
                Spacer(Modifier.height(16.dp))

                content.measurements.firstOrNull { it.categoryId == category.id }?.let { measurement ->
                    MeasurementItemComponent(
                        title = category.title,
                        initialValue = measurement.value.toString(),
                        onSave = {
                            onAction(
                                UserMeasurementsUiAction.OnSaveMeasurement(
                                    measurement = measurement,
                                    category = category,
                                    value = it.toInt(),
                                    unitId = category.unitId
                                )
                            )
                        }
                    )
                } ?: run {
                    MeasurementItemComponent(
                        title = category.title,
                        initialValue = "",
                        onSave = {
                            onAction(
                                UserMeasurementsUiAction.OnSaveMeasurement(
                                    measurement = null,
                                    category = category,
                                    value = it.toInt(),
                                    unitId = category.unitId
                                )
                            )
                        }
                    )
                }
            }

            Spacer(Modifier.height(132.dp))
        }
    }
}

@Composable
private fun MeasurementItemComponent(
    title: String,
    initialValue: String,
    onSave: (String) -> Unit
) {
    var tempValue by remember { mutableStateOf(initialValue) }
    Box(modifier = Modifier.fillMaxWidth()) {

        SkyFormInputNumberText(
            title = title,
            hint = "Olcumunuzu giriniz.",
            value = tempValue,
            onValueChange = { tempValue = it },
            modifier = Modifier
        )

        SkyButton(
            label = stringResource(Res.string.save_action),
            onClick = { onSave(tempValue) },
            enabled = tempValue.isNotEmpty() && tempValue != initialValue,
            size = SkyButtonSize.Micro,
            modifier = Modifier.align(Alignment.BottomEnd).padding(6.dp)
        )
    }
}