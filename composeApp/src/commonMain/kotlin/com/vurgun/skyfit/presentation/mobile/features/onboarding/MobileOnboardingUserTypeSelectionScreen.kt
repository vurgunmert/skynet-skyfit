package com.vurgun.skyfit.presentation.mobile.features.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitLogoComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitTextButton
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingStepProgressComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingTitleGroupComponent

@Composable
fun MobileOnboardingUserTypeSelectionScreen(onClickUser: () -> Unit,
                                            onClickTrainer: () -> Unit,
                                            onClickFacility: () -> Unit) {
    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 6, currentStep = 1)
            Spacer(Modifier.height(47.dp))
            SkyFitLogoComponent()
            Spacer(Modifier.height(56.dp))
            OnboardingTitleGroupComponent(
                title = "Skyfit’e Hoş Geldin!",
                subtitle = "Uygulamaya nasıl devam etmek istersin"
            )
            Spacer(Modifier.height(64.dp))
            MobileOnboardingUserTypeSelectionComponent(
                onSelectUser = onClickUser,
                onSelectTrainer = onClickTrainer,
                onSelectFacility = onClickFacility
            )
        }
    }
}

@Composable
private fun MobileOnboardingUserTypeSelectionComponent(onSelectUser: () -> Unit,
                                                       onSelectTrainer: () -> Unit,
                                                       onSelectFacility: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SkyFitTextButton(
            text = "Kullanıcı",
            onClick = onSelectUser
        )
        Spacer(Modifier.height(24.dp))
        SkyFitTextButton(
            text = "Antrenör",
            onClick = onSelectTrainer
        )
        Spacer(Modifier.height(24.dp))
        SkyFitTextButton(
            text = "İşletme",
            onClick = onSelectFacility
        )
    }
}