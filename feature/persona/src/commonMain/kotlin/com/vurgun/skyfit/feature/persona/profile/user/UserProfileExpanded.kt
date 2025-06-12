package com.vurgun.skyfit.feature.persona.profile.user

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.vurgun.skyfit.core.data.v1.domain.global.model.CharacterType
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.ui.components.special.CharacterImage
import com.vurgun.skyfit.feature.persona.components.core.ProfileExpandedComponent
import com.vurgun.skyfit.feature.persona.profile.user.owner.UserProfileOwnerAction
import com.vurgun.skyfit.feature.persona.profile.user.owner.UserProfileOwnerUiState
import com.vurgun.skyfit.feature.persona.profile.user.owner.UserProfileOwnerViewModel

@Composable
fun UserProfileExpanded(viewModel: UserProfileOwnerViewModel) {
    val userProfile = (viewModel.uiState as UserProfileOwnerUiState.Content).profile

    ProfileExpandedComponent.Layout(
        modifier = Modifier.fillMaxSize(),
        topBar = { UserProfileExpandedComponent.TopBar(
            userProfile,
            onRequestOverlay = viewModel.onAction(UserProfileOwnerAction.ShowOverlay),
            modifier = TODO()
        ) },
        ownerHeader = { UserProfileExpandedComponent.Header() },
        ownerContent = { UserProfileExpandedComponent.Content() }
    )
}

private object UserProfileExpandedComponent {

    @Composable
    fun TopBar(
        userProfile: UserProfile,
        onRequestOverlay: (ScreenProvider) -> Unit,
        modifier: Modifier = Modifier
    ) {
        ProfileExpandedComponent.TopBar(
            modifier = modifier,
            profileName = userProfile.firstName,
            profileContent = {
                //character + trophies

                Row {
                    CharacterImage(CharacterType.Panda, Modifier.size(48.dp))
                    CharacterImage(CharacterType.Panda, Modifier.size(48.dp))
                    CharacterImage(CharacterType.Panda, Modifier.size(48.dp))
                    CharacterImage(CharacterType.Panda, Modifier.size(48.dp))
                }
            },
            onNavigate = onRequestOverlay
        )
    }

    @Composable
    fun Header(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(288.dp)
        ) {

        }
    }

    @Composable
    fun Content(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
        ) {

        }
    }
}