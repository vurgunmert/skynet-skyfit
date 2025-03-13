package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.OnboardingViewModel
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterInputComponent
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterMultilineInputComponent
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.action_continue
import skyfit.composeapp.generated.resources.biography_hint
import skyfit.composeapp.generated.resources.first_name_hint
import skyfit.composeapp.generated.resources.first_name_mandatory
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.last_name_hint
import skyfit.composeapp.generated.resources.last_name_mandatory
import skyfit.composeapp.generated.resources.mandatory_biography
import skyfit.composeapp.generated.resources.onboarding_trainer_complete_profile_message
import skyfit.composeapp.generated.resources.onboarding_trainer_complete_profile_title

@Composable
fun MobileOnboardingTrainerDetailsScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
) {
    val firstName = viewModel.state.collectAsState().value.firstName
    val lastName = viewModel.state.collectAsState().value.lastName
    val biography = viewModel.state.collectAsState().value.bio

    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }
    val bioFocusRequester = remember { FocusRequester() }

    val isContinueEnabled by remember(firstName, lastName, biography) {
        mutableStateOf(!firstName.isNullOrEmpty() && !lastName.isNullOrEmpty() && !biography.isNullOrEmpty())
    }

    LaunchedEffect(Unit) {
        firstNameFocusRequester.requestFocus()
    }

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 7, currentStep = 7)

            Spacer(Modifier.weight(1f))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_trainer_complete_profile_title),
                subtitle = stringResource(Res.string.onboarding_trainer_complete_profile_message)
            )

            Spacer(Modifier.height(24.dp))

            Row(modifier = Modifier.padding(horizontal = 22.dp).fillMaxWidth()) {
                SkyFitSelectToEnterInputComponent(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.first_name_mandatory),
                    hint = stringResource(Res.string.first_name_hint),
                    value = firstName,
                    onValueChange = { viewModel.updateFirstName(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = firstNameFocusRequester,
                    nextFocusRequester = lastNameFocusRequester
                )
                Spacer(Modifier.width(16.dp))
                SkyFitSelectToEnterInputComponent(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.last_name_mandatory),
                    hint = stringResource(Res.string.last_name_hint),
                    value = lastName,
                    onValueChange = { viewModel.updateLastName(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = lastNameFocusRequester,
                    nextFocusRequester = bioFocusRequester
                )
            }

            Spacer(Modifier.height(24.dp))
            SkyFitSelectToEnterMultilineInputComponent(
                modifier = Modifier.padding(horizontal = 22.dp),
                title = stringResource(Res.string.mandatory_biography),
                hint = stringResource(Res.string.biography_hint),
                value = biography,
                onValueChange = { viewModel.updateBiography(it) },
                rightIconRes = Res.drawable.ic_pencil,
                focusRequester = bioFocusRequester
            )

            Spacer(Modifier.weight(1f))

            SkyFitButtonComponent(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                text = stringResource(Res.string.action_continue),
                onClick = {
                    if (isContinueEnabled) {
                        navigator.jumpAndTakeover(NavigationRoute.OnboardingCompleted)
                    }
                },
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                state = if (isContinueEnabled) ButtonState.Rest else ButtonState.Disabled
            )
        }
    }
}
