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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitSelectableCardComponent
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_gender_female
import skyfit.composeapp.generated.resources.ic_gender_male

@Composable
fun MobileOnboardingGenderSelectionScreen(
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent()
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = "Cinsiyet Seçiniz",
                subtitle = "Seçiminiz profilinizde görüntülenecek"
            )
            Spacer(Modifier.height(16.dp))
            OnboardingGenderSelectorComponent()
            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(
                onClickContinue = onNext,
                onClickSkip = onSkip
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
fun OnboardingGenderSelectorComponent() {
    var isMaleSelected by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SkyFitSelectableCardComponent(
            isSelected = isMaleSelected,
            modifier = Modifier.size(112.dp, 104.dp),
            onClick = { isMaleSelected = !isMaleSelected }) {
            Image(
                painter = painterResource(Res.drawable.ic_gender_male),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(Modifier.width(24.dp))

        SkyFitSelectableCardComponent(
            isSelected = !isMaleSelected,
            modifier = Modifier.size(112.dp, 104.dp),
            onClick = { isMaleSelected = !isMaleSelected }) {
            Image(
                painter = painterResource(Res.drawable.ic_gender_female), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }
    }
}
