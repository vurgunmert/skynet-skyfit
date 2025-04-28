package com.vurgun.skyfit.feature.settings.helpsupport

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.settings_support_issue_account_label
import skyfit.core.ui.generated.resources.settings_support_issue_other_label
import skyfit.core.ui.generated.resources.settings_support_issue_payment_label
import skyfit.core.ui.generated.resources.settings_support_issue_technical_label

@Composable
fun SelectSupportSubjectPopupMenu(
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