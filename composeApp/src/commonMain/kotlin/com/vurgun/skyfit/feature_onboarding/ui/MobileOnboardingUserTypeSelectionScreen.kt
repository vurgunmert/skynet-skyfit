package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Box
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
import com.vurgun.skyfit.core.domain.model.UserType
import com.vurgun.skyfit.core.ui.components.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitTextButton
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingCharacterSelection
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingFacilityDetails
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.OnboardingViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.facility
import skyfit.composeapp.generated.resources.onboarding_select_user_type_message
import skyfit.composeapp.generated.resources.onboarding_select_user_type_title
import skyfit.composeapp.generated.resources.trainer
import skyfit.composeapp.generated.resources.user

@Composable
fun OnboardingUserTypeSelectionScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
) {
    SkyFitScaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
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
                MobileOnboardingUserTypeSelectionComponent(onSelected = { userType ->
                    viewModel.updateUserType(userType)

                    when (userType) {
                        UserType.User, UserType.Trainer -> navigator.jumpAndStay(OnboardingCharacterSelection)
                        UserType.Facility -> navigator.jumpAndStay(OnboardingFacilityDetails)
                        else -> Unit
                    }
                })
            }
        }
    }
}


@Composable
private fun MobileOnboardingUserTypeSelectionComponent(
    onSelected: (UserType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SkyFitTextButton(
            text = stringResource(Res.string.user),
            onClick = { onSelected(UserType.User) }
        )
        Spacer(Modifier.height(24.dp))
        SkyFitTextButton(
            text = stringResource(Res.string.trainer),
            onClick = { onSelected(UserType.Trainer) }
        )
        Spacer(Modifier.height(24.dp))
        SkyFitTextButton(
            text = stringResource(Res.string.facility),
            onClick = { onSelected(UserType.Facility) }
        )
    }
}