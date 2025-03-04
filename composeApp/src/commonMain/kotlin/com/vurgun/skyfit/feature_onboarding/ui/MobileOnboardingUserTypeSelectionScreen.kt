package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.domain.models.UserType
import com.vurgun.skyfit.core.ui.components.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitTextButton
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.facility
import skyfit.composeapp.generated.resources.onboarding_select_user_type_message
import skyfit.composeapp.generated.resources.onboarding_select_user_type_title
import skyfit.composeapp.generated.resources.trainer
import skyfit.composeapp.generated.resources.user

//TODO: While in pursue to mobile->desktop I will remove asap after desktop setup
@Composable
fun MobileOnboardingUserTypeSelectionScreen(onSelected: (UserType) -> Unit) {

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(48.dp))
            SkyFitLogoComponent()
            Spacer(Modifier.height(56.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_select_user_type_title),
                subtitle = stringResource(Res.string.onboarding_select_user_type_message)
            )
            Spacer(Modifier.height(64.dp))
            MobileOnboardingUserTypeSelectionComponent(
                onSelectUser = { onSelected(UserType.USER) },
                onSelectTrainer = { onSelected(UserType.TRAINER) },
                onSelectFacility = { onSelected(UserType.FACILITY_MANAGER) },
            )
        }
    }
}

@Composable
fun OnboardingUserTypeSelectionScreen(onSelected: (UserType) -> Unit) {
    SkyFitScaffold {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val screenWidth = maxWidth

            Column(
                modifier = Modifier
                    .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(48.dp))
                SkyFitLogoComponent()
                Spacer(Modifier.height(56.dp))
                OnboardingTitleGroupComponent(
                    title = stringResource(Res.string.onboarding_select_user_type_title),
                    subtitle = stringResource(Res.string.onboarding_select_user_type_message)
                )
                Spacer(Modifier.height(64.dp))
                MobileOnboardingUserTypeSelectionComponent(
                    onSelectUser = { onSelected(UserType.USER) },
                    onSelectTrainer = { onSelected(UserType.TRAINER) },
                    onSelectFacility = { onSelected(UserType.FACILITY_MANAGER) },
                )
            }
        }
    }
}



@Composable
private fun MobileOnboardingUserTypeSelectionComponent(
    onSelectUser: () -> Unit,
    onSelectTrainer: () -> Unit,
    onSelectFacility: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SkyFitTextButton(
            text = stringResource(Res.string.user),
            onClick = onSelectUser
        )
        Spacer(Modifier.height(24.dp))
        SkyFitTextButton(
            text = stringResource(Res.string.trainer),
            onClick = onSelectTrainer
        )
        Spacer(Modifier.height(24.dp))
        SkyFitTextButton(
            text = stringResource(Res.string.facility),
            onClick = onSelectFacility
        )
    }
}