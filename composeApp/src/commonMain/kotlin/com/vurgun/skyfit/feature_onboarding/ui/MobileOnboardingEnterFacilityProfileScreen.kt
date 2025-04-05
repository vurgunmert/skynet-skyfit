package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.utils.KeyboardState
import com.vurgun.skyfit.core.utils.keyboardAsState
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewModel
import com.vurgun.skyfit.feature_settings.ui.MobileUserSettingsScreenPhotoEditComponent
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterInputComponent
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterMultilineInputComponent
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.continue_action
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.onboarding_facility_profile_message
import skyfit.composeapp.generated.resources.onboarding_facility_profile_title
import skyfit.composeapp.generated.resources.mandatory_address_label
import skyfit.composeapp.generated.resources.user_biography_hint
import skyfit.composeapp.generated.resources.mandatory_biography_label
import skyfit.composeapp.generated.resources.user_workplace_mandatory_label
import skyfit.composeapp.generated.resources.mandatory_workplace_name_label

@Composable
fun MobileOnboardingFacilityDetailsScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
) {
    val name = viewModel.uiState.collectAsState().value.facilityName
    val address = viewModel.uiState.collectAsState().value.address
    val biography = viewModel.uiState.collectAsState().value.biography

    val isContinueEnabled by remember(name, address, biography) {
        mutableStateOf(!name.isNullOrEmpty() && !address.isNullOrEmpty() && !biography.isNullOrEmpty())
    }

    val keyboardState by keyboardAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(keyboardState) {
        if (keyboardState is KeyboardState.Opened) {
            scrollState.animateScrollTo(keyboardState.heightPx)
        }
    }

    val nameFocusRequester = remember { FocusRequester() }
    val addressFocusRequester = remember { FocusRequester() }
    val bioFocusRequester = remember { FocusRequester() }

    SkyFitScaffold {
        Column(
            modifier = Modifier
                .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth)
                .fillMaxHeight()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 2, currentStep = 1)

            Spacer(Modifier.weight(1f))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_facility_profile_title),
                subtitle = stringResource(Res.string.onboarding_facility_profile_message)
            )

            Column(
                modifier = Modifier.padding(horizontal = 22.dp).fillMaxWidth(),
            ) {

                Spacer(Modifier.height(24.dp))

                MobileUserSettingsScreenPhotoEditComponent(
                    urlString = null,
                    label = "Arkaplanı Düzenle",
                    onImagePicked = viewModel::updateBackgroundImage
                )
                Spacer(Modifier.height(24.dp))

                SkyFitSelectToEnterInputComponent(
                    title = stringResource(Res.string.user_workplace_mandatory_label),
                    hint = stringResource(Res.string.mandatory_workplace_name_label),
                    value = name,
                    onValueChange = { viewModel.updateFacilityName(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = nameFocusRequester,
                    nextFocusRequester = addressFocusRequester
                )

                Spacer(Modifier.height(16.dp))
                SkyFitSelectToEnterMultilineInputComponent(
                    title = stringResource(Res.string.mandatory_address_label),
                    hint = stringResource(Res.string.mandatory_address_label),
                    value = address,
                    onValueChange = { viewModel.updateFacilityAddress(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = addressFocusRequester,
                    nextFocusRequester = bioFocusRequester
                )

                Spacer(Modifier.height(16.dp))
                SkyFitSelectToEnterMultilineInputComponent(
                    title = stringResource(Res.string.mandatory_biography_label),
                    hint = stringResource(Res.string.user_biography_hint),
                    value = biography,
                    onValueChange = { viewModel.updateFacilityBiography(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = bioFocusRequester
                )
            }

            Spacer(Modifier.height(16.dp))
            Spacer(Modifier.weight(1f))

            SkyFitButtonComponent(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                text = stringResource(Res.string.continue_action),
                onClick = {
                    if (isContinueEnabled) {
                        navigator.jumpAndStay(MobileNavRoute.OnboardingFacilityProfileTags)
                    }
                },
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                state = if (isContinueEnabled) ButtonState.Rest else ButtonState.Disabled
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}