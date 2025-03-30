package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitWheelPickerComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.utils.getDaysInMonth
import com.vurgun.skyfit.core.utils.now
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewModel
import kotlinx.datetime.LocalDate
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_birth_date_message
import skyfit.composeapp.generated.resources.onboarding_birth_date_title

@Composable
fun MobileOnboardingBirthdaySelectionScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
) {
    val currentYear = LocalDate.now().year
    val selectedYear = viewModel.uiState.collectAsState().value.birthYear ?: (currentYear - 16)
    val selectedMonth = viewModel.uiState.collectAsState().value.birthMonth ?: 1
    val selectedDay = viewModel.uiState.collectAsState().value.birthDay ?: 1

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 8, currentStep = 2)
            Spacer(Modifier.weight(1f))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_birth_date_title),
                subtitle = stringResource(Res.string.onboarding_birth_date_message)
            )
            Spacer(Modifier.height(16.dp))

            // Date Picker Row
            Box {
                Row(Modifier.align(Alignment.Center)) {
                    MonthPicker(selectedMonth) { newMonth ->
                        viewModel.updateMonth(newMonth)
                    }
                    Spacer(Modifier.width(16.dp))
                    DayPicker(selectedMonth, selectedYear, selectedDay) { newDay ->
                        viewModel.updateDay(newDay)
                    }
                    Spacer(Modifier.width(16.dp))
                    YearPicker(selectedYear, onYearSelected = { newYear ->
                        viewModel.updateYear(newYear)
                    })
                }

                Box(
                    Modifier
                        .align(Alignment.Center)
                        .width(213.dp)
                        .height(43.dp)
                        .background(color = SkyFitColor.background.fillTransparentSecondary, shape = RoundedCornerShape(size = 16.dp))
                )
            }

            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(
                onClickContinue = {
                    navigator.jumpAndStay(NavigationRoute.OnboardingGenderSelection)
                }
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}


@Composable
private fun YearPicker(
    selectedYear: Int,
    onYearSelected: (Int) -> Unit,
    startYear: Int = LocalDate.now().year - 80,
    endYear: Int = LocalDate.now().year - 6
) {
    val years = (startYear..endYear).toList()

    SkyFitWheelPickerComponent(
        items = years,
        selectedItem = selectedYear,
        onItemSelected = onYearSelected,
        itemText = { it.toString() },
        visibleItemCount = 5,
        modifier = Modifier.width(36.dp)
    )
}


@Composable
private fun MonthPicker(
    selectedMonth: Int,
    onMonthSelected: (Int) -> Unit
) {
    val months = listOf(
        "Oca", "Şub", "Mar", "Nis", "May", "Haz",
        "Tem", "Ağu", "Eyl", "Eki", "Kas", "Ara"
    )

    SkyFitWheelPickerComponent(
        items = months.indices.map { it + 1 },
        selectedItem = selectedMonth,
        onItemSelected = onMonthSelected,
        itemText = { months[it - 1] },
        visibleItemCount = 5,
        modifier = Modifier.width(36.dp)
    )
}


@Composable
private fun DayPicker(
    selectedMonth: Int,
    selectedYear: Int,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    val daysInMonth = getDaysInMonth(selectedMonth, selectedYear)

    SkyFitWheelPickerComponent(
        items = (1..daysInMonth).toList(),
        selectedItem = selectedDay.coerceAtMost(daysInMonth),
        onItemSelected = onDaySelected,
        itemText = { it.toString() },
        visibleItemCount = 5,
        modifier = Modifier.width(36.dp)
    )
}