package com.vurgun.skyfit.profile.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.components.box.TodoBox
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.profile.component.ProfileExpandedComponent
import com.vurgun.skyfit.profile.model.ProfileDestination
import com.vurgun.skyfit.profile.user.owner.UserProfileAction
import com.vurgun.skyfit.profile.user.owner.UserProfileUiState
import com.vurgun.skyfit.profile.user.owner.UserProfileViewModel

@Composable
fun UserProfileExpanded(
    viewModel: UserProfileViewModel,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        UserProfileUiState.Loading ->
            FullScreenLoaderContent()

        is UserProfileUiState.Error -> {
            val message = (uiState as UserProfileUiState.Error).message
            ErrorScreen(message = message) { }
        }

        is UserProfileUiState.Content -> {
            val content = uiState as UserProfileUiState.Content

            ProfileExpandedComponent.Layout(
                header = {
                    UserProfileExpandedComponent.Header(content, viewModel::onAction)
                },
                content = {
                    if (content.route == ProfileDestination.Posts) {
                        UserProfileExpandedComponent.PostsContent(content, viewModel::onAction)
                    } else {
                        UserProfileExpandedComponent.AboutContent(content, viewModel::onAction)
                    }
                },
                modifier = modifier
            )
        }
    }
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