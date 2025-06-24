package com.vurgun.skyfit.profile.user.measurements

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.Measurement
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.MeasurementCategory
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.form.SkyFormInputNumberText
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.my_measurements_label
import skyfit.core.ui.generated.resources.save_action

class UserMeasurementsScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<UserMeasurementsViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.uiEffect) { effect ->
            when (effect) {
                UserMeasurementsUiEffect.NavigateBack -> {
                    navigator.pop()
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (val state = uiState) {
            is UserMeasurementsUiState.Content -> {
                UserMeasurementsComponent.Content(state, viewModel::onAction)
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

internal object UserMeasurementsComponent {

    @Composable
    fun Content(
        content: UserMeasurementsUiState.Content,
        onAction: (UserMeasurementsUiAction) -> Unit,
    ) {
        val windowSize = LocalWindowSize.current

        SkyFitMobileScaffold(
            modifier = Modifier.fillMaxWidth(),
            topBar = {
                if (windowSize != WindowSize.EXPANDED) {
                    CompactTopBar(
                        title = stringResource(Res.string.my_measurements_label),
                        onClickBack = { onAction(UserMeasurementsUiAction.OnClickBack) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MeasurementItemGroup(
                        categories = content.categories,
                        measurements = content.measurements,
                        onAction = onAction
                    )

                    Spacer(modifier = Modifier.height(132.dp))
                }
            }
        )
    }

    @Composable
    fun MeasurementItemGroup(
        categories: List<MeasurementCategory>,
        measurements: List<Measurement>,
        onAction: (UserMeasurementsUiAction) -> Unit
    ) {
        val focusManager = LocalFocusManager.current
        val grouped = remember(categories) { categories.chunked(2) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            grouped.forEach { pair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    pair.forEach { category ->
                        val measurement = measurements.firstOrNull { it.categoryId == category.id }

                        MeasurementItem(
                            category = category,
                            initialValue = measurement?.value?.toString() ?: "",
                            modifier = Modifier.weight(1f),
                            onSave = { newValue ->
                                onAction(
                                    UserMeasurementsUiAction.OnSaveMeasurement(
                                        measurement = measurement,
                                        category = category,
                                        value = newValue.toIntOrNull() ?: 0,
                                        unitId = category.unitId
                                    )
                                )
                                focusManager.clearFocus()
                            }
                        )
                    }

                    // Fill remaining space if only one item in row
                    if (pair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }


    @Composable
    private fun MeasurementItem(
        category: MeasurementCategory,
        initialValue: String,
        onSave: (String) -> Unit,
        imeAction: ImeAction = ImeAction.Next,
        onNext: (() -> Unit)? = null,
        modifier: Modifier = Modifier
    ) {
        var inputValue by rememberSaveable { mutableStateOf(initialValue) }
        val showSaveButton = inputValue.isNotEmpty() && inputValue != initialValue
        val keyboardController = LocalSoftwareKeyboardController.current

        Box(modifier = modifier.fillMaxWidth()) {
            SkyFormInputNumberText(
                title = category.title,
                hint = category.hint,
                value = inputValue,
                onValueChange = { inputValue = it },
                keyboardType = KeyboardType.Number,
                imeAction = imeAction,
                onNext = onNext,
                modifier = Modifier.fillMaxWidth()
            )

            if (showSaveButton) {
                SkyButton(
                    label = stringResource(Res.string.save_action),
                    onClick = {
                        onSave(inputValue)
                        keyboardController?.hide()
                    },
                    size = SkyButtonSize.Micro,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(6.dp)
                )
            }
        }
    }

}