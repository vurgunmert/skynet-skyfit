package com.vurgun.skyfit.profile.trainer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.box.TodoBox
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.profile.component.ProfileExpandedComponent
import com.vurgun.skyfit.profile.trainer.owner.TrainerProfileUiAction
import com.vurgun.skyfit.profile.trainer.owner.TrainerProfileUiState
import com.vurgun.skyfit.profile.trainer.owner.TrainerProfileViewModel

@Composable
fun TrainerProfileExpanded(
    viewModel: TrainerProfileViewModel,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        TrainerProfileUiState.Loading ->
            FullScreenLoaderContent()

        is TrainerProfileUiState.Error -> {
            val message = (uiState as TrainerProfileUiState.Error).message
            ErrorScreen(message = message) { }
        }

        is TrainerProfileUiState.Content -> {
            val content = uiState as TrainerProfileUiState.Content

            ProfileExpandedComponent.Layout(
                header = {
                    TrainerProfileExpandedComponent.Header(content, viewModel::onAction)
                },
                content = {
                    Row(Modifier.weight(1f)) {
                        TrainerProfileExpandedComponent.AboutContent(
                            content,
                            viewModel::onAction,
                            modifier = Modifier.width(444.dp).fillMaxHeight()
                        )
                        Spacer(Modifier.width(16.dp))
                        TrainerProfileExpandedComponent.PostsContent(
                            content,
                            viewModel::onAction,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                    }
                },
                modifier = modifier
            )
        }

    }
}

private object TrainerProfileExpandedComponent {

    @Composable
    fun Header(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TodoBox("HEMEN BUNU YAP!", modifier)
    }

    @Composable
    fun AboutContent(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TodoBox("AboutContent", modifier)
    }

    @Composable
    fun PostsContent(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TodoBox("PostsContent", modifier)
    }
}