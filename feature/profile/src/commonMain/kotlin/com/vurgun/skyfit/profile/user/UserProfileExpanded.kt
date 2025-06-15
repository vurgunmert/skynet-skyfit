package com.vurgun.skyfit.profile.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.components.box.TodoBox
import com.vurgun.skyfit.profile.component.ProfileExpandedComponent
import com.vurgun.skyfit.profile.user.owner.UserProfileAction
import com.vurgun.skyfit.profile.user.owner.UserProfileUiState

@Composable
fun UserProfileExpanded(
    content: UserProfileUiState.Content,
    onAction: (UserProfileAction) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
) {

    ProfileExpandedComponent.Layout(
        header = {
            UserProfileExpandedComponent.Header(content, onAction)
        },
        content = {
            if (content.postsSelected) {
                UserProfileExpandedComponent.PostsContent(content, onAction)
            } else {
                UserProfileExpandedComponent.AboutContent(content, onAction)
            }
        },
        modifier = modifier
    )
}

private object UserProfileExpandedComponent {

    @Composable
    fun Header(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TodoBox("Header")
    }

    @Composable
    fun AboutContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TodoBox("AboutContent")
    }

    @Composable
    fun PostsContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TodoBox("PostsContent")
    }
}