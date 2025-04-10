package com.vurgun.skyfit.feature.onboarding.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.ui.core.components.button.SkyFitTextButton
import com.vurgun.skyfit.ui.core.components.special.SkyFitLogoComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.onboarding_welcome_message
import skyfit.ui.core.generated.resources.onboarding_welcome_title
import skyfit.ui.core.generated.resources.user_type_facility
import skyfit.ui.core.generated.resources.user_type_trainer
import skyfit.ui.core.generated.resources.user_type_user

@Composable
internal fun OnboardingUserTypeSelectionScreen(
    viewModel: OnboardingViewModel,
    goToCharacterSelection: () -> Unit,
    goToFacilityDetails: () -> Unit,
) {
    SkyFitMobileScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(48.dp))
            SkyFitLogoComponent()
            Spacer(Modifier.height(56.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_welcome_title),
                subtitle = stringResource(Res.string.onboarding_welcome_message)
            )
            Spacer(Modifier.height(64.dp))
            MobileOnboardingUserTypeSelectionComponent(onSelected = { userType ->
                viewModel.updateUserType(userType)

                when (userType) {
                    UserRole.User, UserRole.Trainer -> goToCharacterSelection()
                    UserRole.Facility -> goToFacilityDetails()
                    else -> Unit
                }
            })
        }
    }
}


@Composable
private fun MobileOnboardingUserTypeSelectionComponent(
    onSelected: (UserRole) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SkyFitTextButton(
            text = stringResource(Res.string.user_type_user),
            onClick = { onSelected(UserRole.User) }
        )
        Spacer(Modifier.height(24.dp))
        SkyFitTextButton(
            text = stringResource(Res.string.user_type_trainer),
            onClick = { onSelected(UserRole.Trainer) }
        )
        Spacer(Modifier.height(24.dp))
        SkyFitTextButton(
            text = stringResource(Res.string.user_type_facility),
            onClick = { onSelected(UserRole.Facility) }
        )
    }
}