package com.vurgun.skyfit.feature.persona.settings.shared.helpsupport

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.box.DashedBorderBox
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryLargeButton
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.components.text.BodySmallRegularText
import com.vurgun.skyfit.core.ui.components.text.MultiLineInputText
import com.vurgun.skyfit.core.ui.components.text.SingleLineInputText
import com.vurgun.skyfit.core.ui.components.text.TitledMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_arrow_line_up
import skyfit.core.ui.generated.resources.ic_chevron_down
import skyfit.core.ui.generated.resources.ic_open_link
import skyfit.core.ui.generated.resources.send_action
import skyfit.core.ui.generated.resources.settings_support_description_hint
import skyfit.core.ui.generated.resources.settings_support_description_label
import skyfit.core.ui.generated.resources.settings_support_email_hint
import skyfit.core.ui.generated.resources.settings_support_email_label
import skyfit.core.ui.generated.resources.settings_support_help_subtitle
import skyfit.core.ui.generated.resources.settings_support_issue_account_label
import skyfit.core.ui.generated.resources.settings_support_issue_other_label
import skyfit.core.ui.generated.resources.settings_support_issue_payment_label
import skyfit.core.ui.generated.resources.settings_support_issue_technical_label
import skyfit.core.ui.generated.resources.settings_support_label
import skyfit.core.ui.generated.resources.settings_support_questions_label
import skyfit.core.ui.generated.resources.settings_support_subject_label
import skyfit.core.ui.generated.resources.settings_support_upload_file_hint
import skyfit.core.ui.generated.resources.settings_support_upload_file_label

class SettingsSupportHelpScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        MobileSettingsSupportHelpScreen(
            goToBack = { navigator.pop() }
        )
    }

}

@Composable
fun MobileSettingsSupportHelpScreen(goToBack: () -> Unit) {

    var email: String? by remember { mutableStateOf(null) }
    var description: String? by remember { mutableStateOf(null) }
    var isSubjectPopupOpened by remember { mutableStateOf(false) }
    var selectedSubjectType by remember { mutableStateOf(HelpSupportSubjectType.ACCOUNT) }
    val supportOptions = listOf(
        HelpSupportSubjectType.ACCOUNT to stringResource(Res.string.settings_support_issue_account_label),
        HelpSupportSubjectType.PAYMENT to stringResource(Res.string.settings_support_issue_payment_label),
        HelpSupportSubjectType.TECHNICAL to stringResource(Res.string.settings_support_issue_technical_label),
        HelpSupportSubjectType.OTHER to stringResource(Res.string.settings_support_issue_other_label),
    )

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.settings_support_label), onClickBack = goToBack)
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
                    value = supportOptions.first { it.first == selectedSubjectType }.second,
                    rightIconRes = Res.drawable.ic_chevron_down
                )

                SelectSupportSubjectPopupMenu(
                    modifier = Modifier.fillMaxWidth(),
                    isOpen = isSubjectPopupOpened,
                    onDismiss = { isSubjectPopupOpened = false },
                    selectedType = selectedSubjectType,
                    onSelectionChanged = { selectedSubjectType = it }
                )
            }

            SingleLineInputText(
                title = stringResource(Res.string.settings_support_email_label),
                hint = stringResource(Res.string.settings_support_email_hint),
                value = email,
                onValueChange = { email = it }
            )

            MultiLineInputText(
                title = stringResource(Res.string.settings_support_description_label),
                hint = stringResource(Res.string.settings_support_description_hint),
                value = description,
                onValueChange = { description = it }
            )

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

            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                PrimaryLargeButton(
                    text = stringResource(Res.string.send_action),
                    onClick = {}
                )
            }

            SecondaryLargeButton(
                text = stringResource(Res.string.settings_support_questions_label),
                modifier = Modifier.fillMaxWidth(),
                rightIconPainter = painterResource(Res.drawable.ic_open_link),
                onClick = {}
            )
        }
    }
}

@Composable
private fun SelectSupportSubjectPopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selectedType: HelpSupportSubjectType,
    onSelectionChanged: (HelpSupportSubjectType) -> Unit
) {
    val supportOptions = listOf(
        HelpSupportSubjectType.ACCOUNT to stringResource(Res.string.settings_support_issue_account_label),
        HelpSupportSubjectType.PAYMENT to stringResource(Res.string.settings_support_issue_payment_label),
        HelpSupportSubjectType.TECHNICAL to stringResource(Res.string.settings_support_issue_technical_label),
        HelpSupportSubjectType.OTHER to stringResource(Res.string.settings_support_issue_other_label),
    )

    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            supportOptions.forEachIndexed { index, (type, label) ->
                SelectablePopupMenuItem(
                    selected = selectedType == type,
                    onSelect = {
                        onSelectionChanged(type)
                        onDismiss()
                    },
                    content = {
                        BodyMediumRegularText(label, modifier = Modifier.weight(1f))
                    }
                )
                if (index != supportOptions.lastIndex) {
                    Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                }
            }
        }
    }
}