package com.vurgun.skyfit.onboarding.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import com.vurgun.skyfit.core.ui.components.button.SkyFitTextButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.onboarding.model.OnboardingViewModel
import com.vurgun.skyfit.onboarding.model.SelectableUserRole
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

internal class UserTypeSelectionScreen(private val viewModel: OnboardingViewModel): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        OnboardingUserTypeSelectionScreen(
            viewModel = viewModel,
            goToCharacterSelection = { navigator.push(SelectCharacterScreen(viewModel)) },
            goToFacilityDetails = { navigator.push(EnterFacilityDetailScreen(viewModel)) }
        )
    }

}

@Composable
internal fun OnboardingUserTypeSelectionScreen(
    viewModel: OnboardingViewModel,
    goToCharacterSelection: () -> Unit,
    goToFacilityDetails: () -> Unit,
) {
    val availableUserRoles by viewModel.availableUserRoles.collectAsState()

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SkyFitLogoComponent()
            Spacer(Modifier.height(48.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_welcome_title),
                subtitle = stringResource(Res.string.onboarding_welcome_message)
            )
            Spacer(Modifier.height(48.dp))
            MobileOnboardingUserTypeSelectionComponent(
                userRoles = availableUserRoles,
                onSelected = { userType ->
                    viewModel.updateUserType(userType)

                    when (userType) {
                        AccountRole.User, AccountRole.Trainer -> goToCharacterSelection()
                        AccountRole.Facility -> goToFacilityDetails()
                        else -> Unit
                    }
                })
            Spacer(Modifier.height(64.dp))
        }
    }
}

@Composable
private fun MobileOnboardingUserTypeSelectionComponent(
    userRoles: List<SelectableUserRole>,
    onSelected: (AccountRole) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        userRoles.forEach { type ->

            when (type.accountRole) {
                AccountRole.Facility -> {
                    SkyFitTextButton(
                        text = stringResource(Res.string.user_type_facility),
                        enabled = type.enabled,
                        onClick = { onSelected(AccountRole.Facility) }
                    )
                }

                AccountRole.Trainer -> {
                    SkyFitTextButton(
                        text = stringResource(Res.string.user_type_trainer),
                        enabled = type.enabled,
                        onClick = { onSelected(AccountRole.Trainer) }
                    )
                }

                AccountRole.User -> {
                    SkyFitTextButton(
                        text = stringResource(Res.string.user_type_user),
                        enabled = type.enabled,
                        onClick = { onSelected(AccountRole.User) }
                    )
                }

                AccountRole.Guest -> Unit
            }
        }
    }
}