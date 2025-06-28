package com.vurgun.skyfit.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.form.SkyFormMultilineTextField
import com.vurgun.skyfit.core.ui.components.form.SkyFormTextField
import com.vurgun.skyfit.core.ui.components.image.SkyFitPickImageWrapper
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.onboarding.model.OnboardingViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

internal class EnterFacilityDetailScreen(private val viewModel: OnboardingViewModel): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileOnboardingFacilityDetailsScreen(
            viewModel = viewModel,
            goToProfileTags = { navigator.push(SelectProfileTagsScreen(viewModel)) }
        )
    }

}

@Composable
internal fun MobileOnboardingFacilityDetailsScreen(
    viewModel: OnboardingViewModel,
    goToProfileTags: () -> Unit
) {
    val name = viewModel.uiState.collectAsState().value.facilityName
    val address = viewModel.uiState.collectAsState().value.address
    val biography = viewModel.uiState.collectAsState().value.bio
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    val isContinueEnabled by remember(name, address, biography) {
        mutableStateOf(!name.isNullOrEmpty() && !address.isNullOrEmpty() && !biography.isNullOrEmpty())
    }

//    val keyboardState by keyboardAsState()
//    val scrollState = rememberScrollState()
//
//    LaunchedEffect(keyboardState) {
//        if (keyboardState is KeyboardState.Opened) {
//            scrollState.animateScrollTo(keyboardState.heightPx)
//        }
//    }

    val nameFocusRequester = remember { FocusRequester() }
    val addressFocusRequester = remember { FocusRequester() }
    val bioFocusRequester = remember { FocusRequester() }

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding(),
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
                    Box(Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .size(64.dp)
                        .background(SkyFitColor.background.surfaceSecondary)
                    ) {
                        selectedImage?.let { image -> BitmapPainter(image = image) }?.let { painter ->
                            Image(
                                painter = painter,
                                contentDescription = "Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .size(64.dp)
                                    .background(SkyFitColor.background.surfaceSecondary)
                            )
                        }
                    }

                    Spacer(Modifier.width(16.dp))

                    SkyFitPickImageWrapper(onImagesSelected = { bytes, bitmap ->
                        selectedImage = bitmap
                        viewModel.updateBackgroundImage(bytes)
                    }) {
                        DumbButtonComponent(
                            text = stringResource(Res.string.onboarding_edit_background_action),
                            variant = ButtonVariant.Secondary,
                            rightIconPainter = painterResource(Res.drawable.ic_plus)
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))

                SkyFormTextField(
                    title = stringResource(Res.string.user_workplace_mandatory_label),
                    hint = stringResource(Res.string.mandatory_workplace_name_label),
                    value = name,
                    onValueChange = { viewModel.updateFacilityName(it) },
                    rightIconRes = Res.drawable.ic_plus,
                    focusRequester = nameFocusRequester,
                    nextFocusRequester = addressFocusRequester
                )

                Spacer(Modifier.height(16.dp))

                SkyFormMultilineTextField(
                    title = stringResource(Res.string.mandatory_address_label),
                    hint = stringResource(Res.string.mandatory_address_label),
                    value = address,
                    onValueChange = { viewModel.updateFacilityAddress(it) },
                    rightIconRes = Res.drawable.ic_pencil,
                    focusRequester = addressFocusRequester,
                    nextFocusRequester = bioFocusRequester
                )

                Spacer(Modifier.height(16.dp))

                SkyFormMultilineTextField(
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