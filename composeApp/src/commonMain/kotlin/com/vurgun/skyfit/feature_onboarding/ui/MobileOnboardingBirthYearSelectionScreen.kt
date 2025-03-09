package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.YearPicker
import com.vurgun.skyfit.core.utils.now
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.BaseOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.TrainerOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.UserOnboardingViewModel
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import kotlinx.datetime.LocalDate
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_select_date_of_birth_message
import skyfit.composeapp.generated.resources.onboarding_select_date_of_birth_title

@Composable
fun MobileOnboardingBirthYearSelectionScreen(
    viewModel: BaseOnboardingViewModel,
    navigator: Navigator
) {
    val currentYear = LocalDate.now().year
    val minBirthYear = currentYear - 80  // Oldest users (80 years old)
    val maxBirthYear = currentYear - 13  // Youngest allowed users (13 years old)
    val defaultBirthYear = currentYear - 16  // Default to 16-year-olds (active age group)

    val cachedYear by remember(viewModel) {
        derivedStateOf {
            when (viewModel) {
                is UserOnboardingViewModel -> viewModel.state.value.birthYear
                is TrainerOnboardingViewModel -> viewModel.state.value.birthYear
                else -> defaultBirthYear
            }
        }
    }
    var selectedYear by remember { mutableStateOf(cachedYear ?: defaultBirthYear) }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 7, currentStep = 2)
            Spacer(Modifier.weight(1f))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_select_date_of_birth_title),
                subtitle = stringResource(Res.string.onboarding_select_date_of_birth_message)
            )
            Spacer(Modifier.height(16.dp))
            YearPicker(
                selectedYear = selectedYear,
                onYearSelected = { selectedYear = it },
                startYear = minBirthYear,
                endYear = maxBirthYear
            )
            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(
                onClickContinue = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> viewModel.updateBirthYear(selectedYear)
                        is TrainerOnboardingViewModel -> viewModel.updateBirthYear(selectedYear)
                    }
                    navigator.jumpAndStay(NavigationRoute.OnboardingGenderSelection)
                },
                onClickSkip = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingBirthYearSelection,
                            NavigationRoute.OnboardingCompleted
                        )

                        is TrainerOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingBirthYearSelection,
                            NavigationRoute.OnboardingTrainerDetails
                        )
                    }
                }
            )
            Spacer(Modifier.height(30.dp))
        }
    }
}