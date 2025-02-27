package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.resources.SkyFitCharacterIcon
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.BaseOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.FacilityOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.TrainerOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.UserOnboardingViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_lets_get_started
import skyfit.composeapp.generated.resources.onboarding_ready_message
import skyfit.composeapp.generated.resources.onboarding_ready_title

@Composable
fun MobileOnboardingCompletedScreen(
    viewModel: BaseOnboardingViewModel,
    onClickContinue: () -> Unit
) {
    var characterIconId: String? = ""

    LaunchedEffect(viewModel) {
        when (viewModel) {
            is UserOnboardingViewModel -> {
                viewModel.saveUserData()
                characterIconId = viewModel.state.value.characterId
            }

            is TrainerOnboardingViewModel -> {
                viewModel.saveTrainerData()
                characterIconId = viewModel.state.value.characterId
            }

            is FacilityOnboardingViewModel -> viewModel.saveFacilityData()
        }
    }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(68.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_ready_title),
                subtitle = stringResource(Res.string.onboarding_ready_message)
            )
            Spacer(Modifier.height(24.dp))
            OnboardingCharacterComponent(characterIconId)
            Spacer(Modifier.weight(1f))
            SkyFitButtonComponent(
                text = stringResource(Res.string.onboarding_lets_get_started),
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickContinue,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                state = ButtonState.Rest
            )
            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
fun OnboardingCharacterComponent(iconId: String?) {
    Image(
        painter = SkyFitCharacterIcon.getIconResourcePainter(iconId),
        contentDescription = null,
        modifier = Modifier.size(386.dp, 378.dp)
    )
}