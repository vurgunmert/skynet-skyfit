package com.vurgun.skyfit.feature.onboarding.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vurgun.skyfit.core.data.domain.model.UserRole
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.core.ui.components.button.SkyFitTextButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.onboarding_welcome_message
import skyfit.core.ui.generated.resources.onboarding_welcome_title
import skyfit.core.ui.generated.resources.user_type_facility
import skyfit.core.ui.generated.resources.user_type_trainer
import skyfit.core.ui.generated.resources.user_type_user

@Composable
internal fun OnboardingUserTypeSelectionScreen(
    viewModel: OnboardingViewModel,
    goToCharacterSelection: () -> Unit,
    goToFacilityDetails: () -> Unit,
) {
    val availableUserRoles by viewModel.availableUserRoles.collectAsStateWithLifecycle()

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
            MobileOnboardingUserTypeSelectionComponent(
                userRoles = availableUserRoles,
                onSelected = { userType ->
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
    userRoles: List<SelectableUserRole>,
    onSelected: (UserRole) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        userRoles.forEach { type ->

            when (type.userRole) {
                UserRole.Facility -> {
                    SkyFitTextButton(
                        text = stringResource(Res.string.user_type_facility),
                        enabled = type.enabled,
                        onClick = { onSelected(UserRole.Facility) }
                    )
                }

                UserRole.Trainer -> {
                    SkyFitTextButton(
                        text = stringResource(Res.string.user_type_trainer),
                        enabled = type.enabled,
                        onClick = { onSelected(UserRole.Trainer) }
                    )
                }

                UserRole.User -> {
                    SkyFitTextButton(
                        text = stringResource(Res.string.user_type_user),
                        enabled = type.enabled,
                        onClick = { onSelected(UserRole.User) }
                    )
                }

                UserRole.Guest -> Unit
            }
        }
    }
}