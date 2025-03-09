package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.TrainerOnboardingViewModel
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterInputComponent
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterMultilineInputComponent
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.action_continue
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.mandatory_biography
import skyfit.composeapp.generated.resources.mandatory_firstname_lastname
import skyfit.composeapp.generated.resources.mandatory_work_place
import skyfit.composeapp.generated.resources.onboarding_user_select_goal_message
import skyfit.composeapp.generated.resources.onboarding_user_select_goal_title

@Composable
fun MobileOnboardingTrainerDetailsScreen(
    viewModel: TrainerOnboardingViewModel,
    navigator: Navigator
) {
    val trainerAccountState by viewModel.state.collectAsState()

    val isContinueEnabled by remember(trainerAccountState) {
        mutableStateOf(
            trainerAccountState.fullName.isNotBlank() &&
                    trainerAccountState.facilityName.isNotBlank() &&
                    trainerAccountState.biography.isNotBlank()
        )
    }

    SkyFitScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 7, currentStep = 7)

            Spacer(Modifier.weight(1f))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_user_select_goal_title),
                subtitle = stringResource(Res.string.onboarding_user_select_goal_message)
            )

            Spacer(Modifier.height(24.dp))
            SkyFitSelectToEnterInputComponent(
                title = stringResource(Res.string.mandatory_firstname_lastname),
                hint = stringResource(Res.string.mandatory_firstname_lastname),
                value = trainerAccountState.fullName,
                onValueChange = { viewModel.updateFullName(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            Spacer(Modifier.height(24.dp))
            SkyFitSelectToEnterInputComponent(
                title = stringResource(Res.string.mandatory_work_place),
                hint = stringResource(Res.string.mandatory_work_place),
                value = trainerAccountState.facilityName,
                onValueChange = { viewModel.updateFacilityName(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            Spacer(Modifier.height(24.dp))
            SkyFitSelectToEnterMultilineInputComponent(
                title = stringResource(Res.string.mandatory_biography),
                hint = stringResource(Res.string.mandatory_biography),
                value = trainerAccountState.biography,
                onValueChange = { viewModel.updateBiography(it) },
                rightIconRes = Res.drawable.ic_pencil
            )

            Spacer(Modifier.weight(1f))

            SkyFitButtonComponent(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.action_continue),
                onClick = {
                    if (isContinueEnabled) {
                        navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingUserGoalSelection,
                            NavigationRoute.OnboardingCompleted
                        )
                    }
                },
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                state = if (isContinueEnabled) ButtonState.Rest else ButtonState.Disabled
            )

            Spacer(Modifier.height(30.dp))
        }
    }
}
