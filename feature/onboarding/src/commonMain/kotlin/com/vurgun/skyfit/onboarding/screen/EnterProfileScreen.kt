package com.vurgun.skyfit.onboarding.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.dialog.ErrorDialog
import com.vurgun.skyfit.core.ui.components.form.SkyFormTextField
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.components.text.MultiLineInputText
import com.vurgun.skyfit.core.ui.utils.KeyboardState
import com.vurgun.skyfit.core.ui.utils.keyboardAsState
import com.vurgun.skyfit.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.onboarding.model.OnboardingViewEvent
import com.vurgun.skyfit.onboarding.model.OnboardingViewModel
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

internal class EnterProfileScreen(private val viewModel: OnboardingViewModel) : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow
        val navigator = LocalNavigator.currentOrThrow

        MobileOnboardingEnterProfileScreen(
            viewModel = viewModel,
            goToCompleted = { navigator.push(OnboardingCompletedScreen(viewModel)) },
            goToLogin = { appNavigator.replaceAll(SharedScreen.Authorization) }
        )
    }
}

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

    val userType = viewModel.uiState.collectAsState().value.accountRole

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
                        SkyFormTextField(
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
                        SkyFormTextField(
                            modifier = Modifier.weight(1f),
                            title = stringResource(Res.string.user_last_name_mandatory_label),
                            hint = stringResource(Res.string.user_last_name_hint),
                            value = lastName,
                            onValueChange = { viewModel.updateLastName(it) },
                            rightIconRes = Res.drawable.ic_pencil,
                            focusRequester = lastNameFocusRequester,
                            nextFocusRequester = if (userType == AccountRole.Trainer) bioFocusRequester else null
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    if (userType == AccountRole.Trainer) {
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
                ErrorDialog(message = event.message, onDismiss = viewModel::clearError)
            }

            OnboardingViewEvent.InProgress -> {
                FullScreenLoaderContent()
            }

            OnboardingViewEvent.NavigateToLogin -> {
                goToLogin()
            }
        }
    }
}
