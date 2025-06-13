package com.vurgun.skyfit.feature.persona.profile.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.components.box.TodoBox
import com.vurgun.skyfit.feature.persona.profile.user.owner.UserProfileOwnerViewModel

@Composable
fun UserProfileExpanded(viewModel: UserProfileOwnerViewModel) {

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