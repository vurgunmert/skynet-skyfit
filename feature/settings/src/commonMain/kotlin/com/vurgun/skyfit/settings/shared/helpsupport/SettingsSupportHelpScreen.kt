package com.vurgun.skyfit.settings.shared.helpsupport

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.support.model.SupportType
import com.vurgun.skyfit.core.ui.components.box.DashedBorderBox
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryLargeButton
import com.vurgun.skyfit.core.ui.components.dialog.BasicDialog
import com.vurgun.skyfit.core.ui.components.dialog.rememberBasicDialogState
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.ExpandedTopBar
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyPageScaffold
import com.vurgun.skyfit.core.ui.components.text.*
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class SettingsSupportHelpScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<HelpSupportViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val dialogState = rememberBasicDialogState()

        CollectEffect(viewModel.uiEffect) { effect ->
            when (effect) {
                HelpSupportUiEffect.NavigateBack -> navigator.pop()
                HelpSupportUiEffect.NotifyRequestSuccess -> {
                    dialogState.show(
                        title = "Istek gonderildi",
                        message = "Iletisim bilgilerinizi kontrol ediniz.",
                        dismissText = "Tamam"
                    )
                }
            }
        }

        when (uiState) {
            HelpSupportUiState.Loading -> {
                FullScreenLoaderContent()
            }

            is HelpSupportUiState.Error -> {
                ErrorScreen(message = (uiState as HelpSupportUiState.Error).message) { navigator.pop() }
            }

            is HelpSupportUiState.Content -> {
                val content = uiState as HelpSupportUiState.Content
                MobileSettingsSupportHelpScreen(content, viewModel::onAction)
            }
        }

        BasicDialog(state = dialogState)
    }
}

@Composable
fun MobileSettingsSupportHelpScreen(
    content: HelpSupportUiState.Content,
    onAction: (HelpSupportUiAction) -> Unit
) {
    val windowSize = LocalWindowSize.current
    var isSubjectPopupOpened by remember { mutableStateOf(false) }

    SkyPageScaffold(
        topBar = {
            if (windowSize == WindowSize.EXPANDED) {
                ExpandedTopBar(
                    title = stringResource(Res.string.settings_support_label),
                    onClickBack = { onAction(HelpSupportUiAction.OnClickBack) })
            } else {
                CompactTopBar(
                    title = stringResource(Res.string.settings_support_label),
                    onClickBack = { onAction(HelpSupportUiAction.OnClickBack) })
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = stringResource(Res.string.settings_support_help_subtitle),
                style = SkyFitTypography.bodyMediumMedium,
                color = SkyFitColor.text.secondary,
                textAlign = TextAlign.Center
            )

            Column(Modifier.fillMaxWidth()) {
                TitledMediumRegularText(
                    modifier = Modifier.fillMaxWidth().clickable { isSubjectPopupOpened = true },
                    title = stringResource(Res.string.settings_support_subject_label),
                    value = content.selectedSupportType.name,
                    rightIconRes = Res.drawable.ic_chevron_down
                )

                SelectSupportSubjectPopupMenu(
                    modifier = Modifier.fillMaxWidth(),
                    isOpen = isSubjectPopupOpened,
                    onDismiss = { isSubjectPopupOpened = false },
                    types = content.supportTypes,
                    selectedType = content.selectedSupportType,
                    onSelectionChanged = { onAction(HelpSupportUiAction.OnChangeSupportType(it)) }
                )
            }

            SingleLineInputText(
                title = stringResource(Res.string.settings_support_email_label),
                hint = stringResource(Res.string.settings_support_email_hint),
                value = content.email,
                onValueChange = { onAction(HelpSupportUiAction.OnChangeEmail(it)) }
            )

            MultiLineInputText(
                title = stringResource(Res.string.settings_support_description_label),
                hint = stringResource(Res.string.settings_support_description_hint),
                value = content.description,
                onValueChange = { onAction(HelpSupportUiAction.OnChangeDescription(it)) }
            )

            FeatureVisible(false) { // Attachment
                Column(Modifier.fillMaxWidth()) {
                    BodyMediumSemiboldText(
                        text = stringResource(Res.string.settings_support_upload_file_label),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    DashedBorderBox {
                        Column(
                            Modifier.padding(12.dp).fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_arrow_line_up),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = SkyFitColor.icon.default
                            )
                            Spacer(Modifier.height(16.dp))
                            BodySmallRegularText(
                                text = stringResource(Res.string.settings_support_upload_file_hint),
                                color = SkyFitColor.text.secondary
                            )
                        }
                    }
                }
            }

            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                PrimaryLargeButton(
                    text = stringResource(Res.string.send_action),
                    onClick = {}
                )
            }

            FeatureVisible(false) { // SSS
                SecondaryLargeButton(
                    text = stringResource(Res.string.settings_support_questions_label),
                    modifier = Modifier.fillMaxWidth(),
                    rightIconPainter = painterResource(Res.drawable.ic_open_link),
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun SelectSupportSubjectPopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    types: List<SupportType>,
    selectedType: SupportType,
    onSelectionChanged: (SupportType) -> Unit
) {

    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            types.forEachIndexed { index, type ->
                SelectablePopupMenuItem(
                    selected = selectedType == type,
                    onSelect = {
                        onSelectionChanged(type)
                        onDismiss()
                    },
                    content = {
                        BodyMediumRegularText(type.name, modifier = Modifier.weight(1f))
                    }
                )
                if (index != types.lastIndex) {
                    Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                }
            }
        }
    }
}