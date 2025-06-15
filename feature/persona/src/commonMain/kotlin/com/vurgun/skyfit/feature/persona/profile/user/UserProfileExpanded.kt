package com.vurgun.skyfit.feature.persona.profile.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.components.box.TodoBox
import com.vurgun.skyfit.feature.persona.profile.user.owner.UserProfileViewModel

@Composable
fun UserProfileExpanded(viewModel: UserProfileViewModel) {

    UserProfileExpandedComponent.Content()
}

private object UserProfileExpandedComponent {

    @Composable
    fun Content(
        modifier: Modifier = Modifier
    ) {
        TodoBox("UserProfileContent")
    }
}