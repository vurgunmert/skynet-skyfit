package com.vurgun.skyfit.health.posture

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.utility.PlatformType
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.components.button.PrimaryIconButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryIconButton
import com.vurgun.skyfit.core.ui.components.dialog.BasicDialog
import com.vurgun.skyfit.core.ui.components.dialog.rememberBasicDialogState
import com.vurgun.skyfit.core.ui.components.icon.BackIcon
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileFillScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyLargeSemiboldText
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import com.vurgun.skyfit.core.ui.utils.LocalExpandedOverlayController
import com.vurgun.skyfit.health.posture.internal.*
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.*

class PostureAnalysisScreen : Screen {

    override val key: ScreenKey = SharedScreen.PostureAnalysis.key

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<PostureAnalysisViewModel>()

        val isLoading by viewModel.isLoading.collectAsState()
        val headerState by viewModel.headerState.collectAsState()
        val contentState by viewModel.contentState.collectAsState()

        val dialogState = rememberBasicDialogState()
        val overlayController = LocalCompactOverlayController.current
        val expandedOverlayController = LocalExpandedOverlayController.current

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                PostureAnalysisEffect.Exit -> {
                    overlayController?.invoke(null)
                    expandedOverlayController?.invoke(null)
                    navigator.pop()
                }

                PostureAnalysisEffect.ShowExitDialog -> {
                    dialogState.show(
                        title = null,
                        message = "Vücut analizi sayfasından ayrılmak istediğine emin misin?",
                        confirmText = "Evet",
                        dismissText = "Hayır, şimdi değil",
                        onConfirm = { navigator.pop() }
                    )
                }
            }
        }

        if (isLoading) {
            FullScreenLoaderContent()
        } else {
            SkyFitMobileFillScaffold(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(Modifier.fillMaxSize()) {

                    when (contentState) {
                        PostureAnalysisContentState.Instructions ->
                            PostureInstructionContent(
                                onAction = viewModel::onAction,
                                modifier = Modifier
                                    .padding(top = 150.dp)
                                    .align(Alignment.TopCenter)
                            )

                        is PostureAnalysisContentState.CameraPreview -> {
                            val content = (contentState as PostureAnalysisContentState.CameraPreview)

                            if (PlatformType.isDesktop) {
                                DesktopCameraPreviewContent(content, viewModel)
                            } else {
                                CameraPreviewContent(content, viewModel)
                            }
                        }

                        is PostureAnalysisContentState.ImageScanner -> {
                            val image = (contentState as PostureAnalysisContentState.ImageScanner).image
                            PostureScannerContent(image)
                        }

                        is PostureAnalysisContentState.PostureOptions -> {
                            PostureOptionsContent(
                                viewModel = viewModel,
                                onAction = viewModel::onAction,
                                modifier = Modifier
                                    .padding(top = 150.dp)
                                    .align(Alignment.TopCenter)
                            )
                        }

                        is PostureAnalysisContentState.Report -> {
                            val content = (contentState as PostureAnalysisContentState.Report)
                            PostureReportContent(
                                content = content,
                                onAction = viewModel::onAction
                            )
                        }
                    }

                    PostureAnalysisComponent.CompactContentHeader(
                        state = headerState,
                        onAction = viewModel::onAction,
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.systemBars)
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }

        BasicDialog(state = dialogState)
    }
}

internal object PostureAnalysisComponent {

    @Composable
    fun CompactContentHeader(
        state: PostureAnalysisHeaderState,
        onAction: (PostureAnalysisAction) -> Unit,
        modifier: Modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            PrimaryIconButton(
                painter = painterResource(Res.drawable.ic_chevron_left),
                modifier = Modifier.align(Alignment.CenterStart).size(48.dp),
                onClick = { onAction(PostureAnalysisAction.NavigateToBack) }
            )

            TopBarActionRow(
                bodyGuideEnabled = state.bodyGuideEnabled,
                onClickBodyGuide = { onAction(PostureAnalysisAction.ToggleBodyGuide) },
                gridGuideEnabled = state.gridGuideEnabled,
                onClickGridGuide = { onAction(PostureAnalysisAction.ToggleGrid) },
                infoEnabled = state.infoEnabled,
                onClickInfo = { onAction(PostureAnalysisAction.ToggleInfo) },
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }

    @Composable
    fun ExtendedContentHeader(
        state: PostureAnalysisHeaderState,
        onAction: (PostureAnalysisAction) -> Unit,
        modifier: Modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {

            BackIcon(
                onClick = { onAction(PostureAnalysisAction.NavigateToBack) },
                modifier = Modifier.align(Alignment.CenterStart)
            )

            BodyLargeSemiboldText(
                text = "Postür Analizi",
                modifier = Modifier.align(Alignment.Center)
            )

            TopBarActionRow(
                bodyGuideEnabled = state.bodyGuideEnabled,
                onClickBodyGuide = { onAction(PostureAnalysisAction.NavigateToBack) },
                gridGuideEnabled = state.gridGuideEnabled,
                onClickGridGuide = { onAction(PostureAnalysisAction.NavigateToBack) },
                infoEnabled = state.infoEnabled,
                onClickInfo = { onAction(PostureAnalysisAction.NavigateToBack) },
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }

    @Composable
    private fun TopBarActionRow(
        bodyGuideEnabled: Boolean,
        onClickBodyGuide: () -> Unit,
        gridGuideEnabled: Boolean,
        onClickGridGuide: () -> Unit,
        infoEnabled: Boolean,
        onClickInfo: () -> Unit,
        modifier: Modifier
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (bodyGuideEnabled) {
                PrimaryIconButton(
                    painter = painterResource(Res.drawable.ic_posture_fill),
                    modifier = Modifier.size(48.dp),
                    onClick = onClickBodyGuide
                )
            } else {
                SecondaryIconButton(
                    painter = painterResource(Res.drawable.ic_posture_fill),
                    modifier = Modifier.size(48.dp),
                    onClick = onClickBodyGuide
                )
            }

            if (gridGuideEnabled) {
                PrimaryIconButton(
                    painter = painterResource(Res.drawable.ic_visibility_show),
                    modifier = Modifier.size(48.dp),
                    onClick = onClickGridGuide
                )
            } else {
                SecondaryIconButton(
                    painter = painterResource(Res.drawable.ic_visibility_hide),
                    modifier = Modifier.size(48.dp),
                    onClick = onClickGridGuide
                )
            }

            if (infoEnabled) {
                PrimaryIconButton(
                    painter = painterResource(Res.drawable.ic_info_circle),
                    modifier = Modifier.size(48.dp),
                    onClick = onClickInfo
                )
            } else {
                SecondaryIconButton(
                    painter = painterResource(Res.drawable.ic_info_circle),
                    modifier = Modifier.size(48.dp),
                    onClick = onClickInfo
                )
            }
        }
    }
}
