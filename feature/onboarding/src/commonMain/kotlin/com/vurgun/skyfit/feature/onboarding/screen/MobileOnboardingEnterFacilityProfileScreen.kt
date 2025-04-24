package com.vurgun.skyfit.feature.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.text.MultiLineInputText
import com.vurgun.skyfit.ui.core.components.text.SingleLineInputText
import com.vurgun.skyfit.feature.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.ui.core.components.image.SkyFitPickImageWrapper
import com.vurgun.skyfit.ui.core.components.special.ButtonSize
import com.vurgun.skyfit.ui.core.components.special.ButtonState
import com.vurgun.skyfit.ui.core.components.special.ButtonVariant
import com.vurgun.skyfit.ui.core.components.special.DumbButtonComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.utils.KeyboardState
import com.vurgun.skyfit.ui.core.utils.keyboardAsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.continue_action
import skyfit.ui.core.generated.resources.ic_image
import skyfit.ui.core.generated.resources.ic_pencil
import skyfit.ui.core.generated.resources.mandatory_address_label
import skyfit.ui.core.generated.resources.mandatory_biography_label
import skyfit.ui.core.generated.resources.mandatory_workplace_name_label
import skyfit.ui.core.generated.resources.onboarding_edit_background_action
import skyfit.ui.core.generated.resources.onboarding_facility_profile_message
import skyfit.ui.core.generated.resources.onboarding_facility_profile_title
import skyfit.ui.core.generated.resources.user_biography_hint
import skyfit.ui.core.generated.resources.user_workplace_mandatory_label

@Composable
internal fun MobileOnboardingFacilityDetailsScreen(
    viewModel: OnboardingViewModel,
    goToProfileTags: () -> Unit
) {
    val name = viewModel.uiState.collectAsState().value.facilityName
    val address = viewModel.uiState.collectAsState().value.address
    val biography = viewModel.uiState.collectAsState().value.biography
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }

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

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
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

//                MobileUserSettingsScreenPhotoEditComponent(
//                    urlString = null,
//                    label = "Arkaplanı Düzenle",
//                    onImagePicked = viewModel::updateBackgroundImage
//                )
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val painter = selectedImage?.let { BitmapPainter(image = it) } ?: painterResource(Res.drawable.ic_image)
                    Image(
                        painter = painter,
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .size(64.dp)
                            .background(SkyFitColor.background.surfaceSecondary)
                    )

                    Spacer(Modifier.width(16.dp))

                    SkyFitPickImageWrapper(onImagesSelected = { _, bitmap ->
                        selectedImage = bitmap
                    }) {
                        DumbButtonComponent(
                            text = stringResource(Res.string.onboarding_edit_background_action),
                            variant = ButtonVariant.Secondary,
                            rightIconPainter = painterResource(Res.drawable.ic_pencil)
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))

                SingleLineInputText(
                    title = stringResource(Res.string.user_workplace_mandatory_label),
                    hint = stringResource(Res.string.mandatory_workplace_name_label),
                    value = name,
                    onValueChange = { viewModel.updateFacilityName(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = nameFocusRequester,
                    nextFocusRequester = addressFocusRequester
                )

                Spacer(Modifier.height(16.dp))
                MultiLineInputText(
                    title = stringResource(Res.string.mandatory_address_label),
                    hint = stringResource(Res.string.mandatory_address_label),
                    value = address,
                    onValueChange = { viewModel.updateFacilityAddress(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = addressFocusRequester,
                    nextFocusRequester = bioFocusRequester
                )

                Spacer(Modifier.height(16.dp))
                MultiLineInputText(
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
                        goToProfileTags()
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