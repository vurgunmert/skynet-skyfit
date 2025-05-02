package com.vurgun.skyfit.feature.onboarding.screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.domain.model.GenderType
import com.vurgun.skyfit.core.ui.components.special.SelectableCardComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.feature.onboarding.component.OnboardingActionGroupComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.gender_female
import skyfit.core.ui.generated.resources.gender_male
import skyfit.core.ui.generated.resources.ic_gender_female
import skyfit.core.ui.generated.resources.ic_gender_male
import skyfit.core.ui.generated.resources.onboarding_gender_message
import skyfit.core.ui.generated.resources.onboarding_gender_title

internal class SelectGenderScreen(private val viewModel: OnboardingViewModel): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileOnboardingGenderSelectionScreen(
            viewModel = viewModel,
            goToWeightSelection = { navigator.push(SelectWeightScreen(viewModel)) }
        )
    }
}

@Composable
internal fun MobileOnboardingGenderSelectionScreen(
    viewModel: OnboardingViewModel,
    goToWeightSelection: () -> Unit,
) {
    val selectedGender = viewModel.uiState.collectAsState().value.gender

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 8, currentStep = 3)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_gender_title),
                subtitle = stringResource(Res.string.onboarding_gender_message)
            )
            Spacer(Modifier.height(16.dp))
            OnboardingGenderSelectorComponent(
                selectedGender = selectedGender,
                onSelected = viewModel::updateGender
            )
            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(onClickContinue = goToWeightSelection)

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun OnboardingGenderSelectorComponent(
    selectedGender: GenderType,
    onSelected: (GenderType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SelectableCardComponent(
                isSelected = selectedGender == GenderType.MALE,
                modifier = Modifier.size(112.dp, 104.dp),
                onClick = { onSelected(GenderType.MALE) }) {
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
            SelectableCardComponent(
                isSelected = selectedGender == GenderType.FEMALE,
                modifier = Modifier.size(112.dp, 104.dp),
                onClick = { onSelected(GenderType.FEMALE) }) {
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
