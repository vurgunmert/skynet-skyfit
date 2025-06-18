package com.vurgun.skyfit.profile.facility

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
import com.vurgun.skyfit.profile.facility.owner.FacilityProfileAction
import com.vurgun.skyfit.profile.facility.owner.FacilityProfileUiState
import com.vurgun.skyfit.profile.facility.owner.FacilityProfileViewModel

@Composable
fun FacilityProfileExpanded(
    viewModel: FacilityProfileViewModel,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        FacilityProfileUiState.Loading ->
            FullScreenLoaderContent()

        is FacilityProfileUiState.Error -> {
            val message = (uiState as FacilityProfileUiState.Error).message
            ErrorScreen(message = message) { }
        }

        is FacilityProfileUiState.Content -> {
            val content = uiState as FacilityProfileUiState.Content

            ProfileExpandedComponent.Layout(
                header = {
                    FacilityProfileExpandedComponent.Header(content, viewModel::onAction)
                },
                content = {
                    Row(Modifier.weight(1f)) {
                        FacilityProfileExpandedComponent.AboutContent(
                            content,
                            viewModel::onAction,
                            modifier = Modifier.width(444.dp).fillMaxHeight()
                        )
                        Spacer(Modifier.width(16.dp))
                        FacilityProfileExpandedComponent.PostsContent(
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

private object FacilityProfileExpandedComponent {

    @Composable
    fun Header(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TodoBox("Header")
    }

    @Composable
    fun AboutContent(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TodoBox("AboutContent", modifier)
    }

    @Composable
    fun PostsContent(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TodoBox("PostsContent", modifier)
    }
}