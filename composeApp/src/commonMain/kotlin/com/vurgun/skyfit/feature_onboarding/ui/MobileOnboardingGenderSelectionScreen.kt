package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
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
import com.vurgun.skyfit.core.ui.components.SkyFitSelectableCardComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.BaseOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.TrainerOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.UserOnboardingViewModel
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.gender_female
import skyfit.composeapp.generated.resources.gender_male
import skyfit.composeapp.generated.resources.ic_gender_female
import skyfit.composeapp.generated.resources.ic_gender_male
import skyfit.composeapp.generated.resources.onboarding_select_gender_message
import skyfit.composeapp.generated.resources.onboarding_select_gender_title

@Composable
fun MobileOnboardingGenderSelectionScreen(
    viewModel: BaseOnboardingViewModel,
    navigator: Navigator
) {
    val cachedGender by remember(viewModel) {
        derivedStateOf {
            when (viewModel) {
                is UserOnboardingViewModel -> viewModel.state.value.gender
                is TrainerOnboardingViewModel -> viewModel.state.value.gender
                else -> "male"
            }
        }
    }

    var selectedGender by remember { mutableStateOf(cachedGender ?: "male") }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 7, currentStep = 3)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_select_gender_title),
                subtitle = stringResource(Res.string.onboarding_select_gender_message)
            )
            Spacer(Modifier.height(16.dp))
            OnboardingGenderSelectorComponent(
                selectedGender = selectedGender,
                onSelected = { selectedGender = it }
            )
            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(
                onClickContinue = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> viewModel.updateGender(selectedGender)
                        is TrainerOnboardingViewModel -> viewModel.updateGender(selectedGender)
                    }
                    navigator.jumpAndStay(NavigationRoute.OnboardingWeightSelection)
                },
                onClickSkip = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingGenderSelection,
                            NavigationRoute.OnboardingCompleted
                        )

                        is TrainerOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingGenderSelection,
                            NavigationRoute.OnboardingTrainerDetails
                        )
                    }
                }
            )
            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
fun OnboardingGenderSelectorComponent(
    selectedGender: String,
    onSelected: (String) -> Unit
) {
    val genderMale = "male"
    val genderFemale = "female"

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SkyFitSelectableCardComponent(
                isSelected = selectedGender == genderMale,
                modifier = Modifier.size(112.dp, 104.dp),
                onClick = { onSelected(genderMale) }) {
                Image(
                    painter = painterResource(Res.drawable.ic_gender_male),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }
            Text(
                text = stringResource(Res.string.gender_male),
                style = SkyFitTypography.bodyMediumRegular
            )
        }

        Spacer(Modifier.width(24.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SkyFitSelectableCardComponent(
                isSelected = selectedGender == genderFemale,
                modifier = Modifier.size(112.dp, 104.dp),
                onClick = { onSelected(genderFemale) }) {
                Image(
                    painter = painterResource(Res.drawable.ic_gender_female), // Replace with your image resource
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }
            Text(
                text = stringResource(Res.string.gender_female),
                style = SkyFitTypography.bodyMediumRegular
            )
        }
    }
}
