package com.vurgun.skyfit.feature.onboarding.screen

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
import com.vurgun.skyfit.designsystem.components.text.MultiLineInputText
import com.vurgun.skyfit.designsystem.components.text.SingleLineInputText
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.ui.core.components.special.ButtonSize
import com.vurgun.skyfit.ui.core.components.special.ButtonState
import com.vurgun.skyfit.ui.core.components.special.ButtonVariant
import com.vurgun.skyfit.ui.core.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.dialog.ErrorDialog
import com.vurgun.skyfit.ui.core.components.loader.CircularLoader
import com.vurgun.skyfit.ui.core.utils.KeyboardState
import com.vurgun.skyfit.ui.core.utils.keyboardAsState
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.continue_action
import skyfit.ui.core.generated.resources.ic_pencil
import skyfit.ui.core.generated.resources.onboarding_trainer_profile_message
import skyfit.ui.core.generated.resources.onboarding_trainer_profile_title
import skyfit.ui.core.generated.resources.user_biography_hint
import skyfit.ui.core.generated.resources.mandatory_biography_label
import skyfit.ui.core.generated.resources.user_first_name_hint
import skyfit.ui.core.generated.resources.user_first_name_mandatory_label
import skyfit.ui.core.generated.resources.user_last_name_hint
import skyfit.ui.core.generated.resources.user_last_name_mandatory_label

@Composable
internal fun MobileOnboardingEnterProfileScreen(
    viewModel: OnboardingViewModel,
    goToCompleted: () -> Unit,
    goToLogin: () -> Unit,
) {
    val firstName: String? = viewModel.uiState.collectAsState().value.firstName
    val lastName: String? = viewModel.uiState.collectAsState().value.lastName
    val biography: String? = viewModel.uiState.collectAsState().value.bio
    val eventState: OnboardingViewEvent by viewModel.eventState.collectAsState()

    val userType = viewModel.uiState.collectAsState().value.userRole

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
                        SingleLineInputText(
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
                        SingleLineInputText(
                            modifier = Modifier.weight(1f),
                            title = stringResource(Res.string.user_last_name_mandatory_label),
                            hint = stringResource(Res.string.user_last_name_hint),
                            value = lastName,
                            onValueChange = { viewModel.updateLastName(it) },
                            rightIconRes = Res.drawable.ic_pencil,
                            focusRequester = lastNameFocusRequester,
                            nextFocusRequester = if(userType == UserRole.Trainer) bioFocusRequester else null
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    if (userType == UserRole.Trainer) {
                        MultiLineInputText(
                            modifier = Modifier.padding(horizontal = 22.dp),
                            title = stringResource(Res.string.mandatory_biography_label),
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
                goToCompleted()
            }

            is OnboardingViewEvent.Error -> {
                ErrorDialog(event.message, onDismiss = viewModel::clearError)
            }

            OnboardingViewEvent.InProgress -> {
                CircularLoader()
            }

            OnboardingViewEvent.NavigateToLogin -> {
               goToLogin()
            }
        }
    }
}
