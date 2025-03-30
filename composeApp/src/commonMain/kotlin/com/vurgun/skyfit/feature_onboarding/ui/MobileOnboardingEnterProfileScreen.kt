package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.vurgun.skyfit.core.domain.models.UserType
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.error.ErrorDialog
import com.vurgun.skyfit.core.ui.components.loader.CircularLoader
import com.vurgun.skyfit.core.utils.KeyboardState
import com.vurgun.skyfit.core.utils.keyboardAsState
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewEvent
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewModel
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterInputComponent
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterMultilineInputComponent
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.continue_action
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.onboarding_trainer_profile_message
import skyfit.composeapp.generated.resources.onboarding_trainer_profile_title
import skyfit.composeapp.generated.resources.user_biography_hint
import skyfit.composeapp.generated.resources.user_biography_mandatory_label
import skyfit.composeapp.generated.resources.user_first_name_hint
import skyfit.composeapp.generated.resources.user_first_name_mandatory_label
import skyfit.composeapp.generated.resources.user_last_name_hint
import skyfit.composeapp.generated.resources.user_last_name_mandatory_label

@Composable
fun MobileOnboardingEnterProfileScreen(
    viewModel: OnboardingViewModel,
    rootNavigator: Navigator,
    onboardingNavigator: Navigator
) {
    val firstName: String? = viewModel.uiState.collectAsState().value.firstName
    val lastName: String? = viewModel.uiState.collectAsState().value.lastName
    val biography: String? = viewModel.uiState.collectAsState().value.bio
    val eventState: OnboardingViewEvent by viewModel.eventState.collectAsState()

    val userType = viewModel.uiState.collectAsState().value.userType

    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }
    val bioFocusRequester = remember { FocusRequester() }

    val isContinueEnabled by remember(firstName, lastName) {
        mutableStateOf(!firstName.isNullOrEmpty() && !lastName.isNullOrEmpty())
    }

    LaunchedEffect(Unit) {
        firstNameFocusRequester.requestFocus()
    }


    val keyboardState by keyboardAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(keyboardState) {
        if (keyboardState is KeyboardState.Opened) {
            scrollState.animateScrollTo(keyboardState.heightPx)
        }
    }

    SkyFitMobileScaffold {
        when (val event = eventState) {
            OnboardingViewEvent.Idle -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .imePadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OnboardingStepProgressComponent(totalSteps = 8, currentStep = 8)

                    Spacer(Modifier.weight(1f))


                    OnboardingTitleGroupComponent(
                        title = stringResource(Res.string.onboarding_trainer_profile_title),
                        subtitle = stringResource(Res.string.onboarding_trainer_profile_message)
                    )

                    Spacer(Modifier.height(24.dp))

                    Row(modifier = Modifier.padding(horizontal = 22.dp).fillMaxWidth()) {
                        SkyFitSelectToEnterInputComponent(
                            modifier = Modifier.weight(1f),
                            title = stringResource(Res.string.user_first_name_mandatory_label),
                            hint = stringResource(Res.string.user_first_name_hint),
                            value = firstName,
                            onValueChange = { viewModel.updateFirstName(it) },
                            rightIconRes = Res.drawable.ic_pencil,
                            focusRequester = firstNameFocusRequester,
                            nextFocusRequester = lastNameFocusRequester
                        )
                        Spacer(Modifier.width(16.dp))
                        SkyFitSelectToEnterInputComponent(
                            modifier = Modifier.weight(1f),
                            title = stringResource(Res.string.user_last_name_mandatory_label),
                            hint = stringResource(Res.string.user_last_name_hint),
                            value = lastName,
                            onValueChange = { viewModel.updateLastName(it) },
                            rightIconRes = Res.drawable.ic_pencil,
                            focusRequester = lastNameFocusRequester,
                            nextFocusRequester = bioFocusRequester
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    if (userType == UserType.Trainer) {
                        SkyFitSelectToEnterMultilineInputComponent(
                            modifier = Modifier.padding(horizontal = 22.dp),
                            title = stringResource(Res.string.user_biography_mandatory_label),
                            hint = stringResource(Res.string.user_biography_hint),
                            value = biography,
                            onValueChange = { viewModel.updateBiography(it) },
                            rightIconRes = Res.drawable.ic_pencil,
                            focusRequester = bioFocusRequester
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    SkyFitButtonComponent(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        text = stringResource(Res.string.continue_action),
                        onClick = {
                            if (isContinueEnabled) {
                                viewModel.submitRequest()
                            }
                        },
                        variant = ButtonVariant.Primary,
                        size = ButtonSize.Large,
                        state = if (isContinueEnabled) ButtonState.Rest else ButtonState.Disabled
                    )

                    Spacer(Modifier.height(20.dp))
                }
            }

            OnboardingViewEvent.Completed -> {
                onboardingNavigator.jumpAndTakeover(NavigationRoute.OnboardingCompleted)
            }

            is OnboardingViewEvent.Error -> {
                ErrorDialog(event.message, onDismiss = viewModel::clearError)
            }

            OnboardingViewEvent.InProgress -> {
                CircularLoader()
            }

            OnboardingViewEvent.NavigateToLogin -> {
                rootNavigator.jumpAndTakeover(NavigationRoute.Login)
            }
        }
    }
}
