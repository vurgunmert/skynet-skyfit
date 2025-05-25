package com.vurgun.skyfit.core.ui.components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_down

@Composable
fun SkyFormSelectText(
    title: String,
    hint: String,
    options: List<String>,
    selected: String?,
    onSelectionChanged: (String) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { isMenuExpanded = true }
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SkyText(
                text = title,
                styleType = TextStyleType.BodyMediumSemibold,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SkyFitColor.background.surfaceSecondary,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SkyText(
                    text = selected.takeUnless { it.isNullOrEmpty() } ?: hint,
                    styleType = TextStyleType.BodyMediumRegular,
                    color = SkyFitColor.text.secondary,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(16.dp))

                SkyIcon(
                    res = Res.drawable.ic_chevron_down,
                    size = SkyIconSize.Small
                )
            }
        }

        BoxWithConstraints(Modifier.fillMaxWidth()) {
            BasicPopupMenu(
                modifier = Modifier.width(maxWidth),
                isOpen = isMenuExpanded,
                onDismiss = { isMenuExpanded = false }
            ) {
                options.forEachIndexed { index, value ->
                    SelectablePopupMenuItem(
                        selected = value == selected,
                        onSelect = {
                            onSelectionChanged(value)
                            isMenuExpanded = false
                        },
                        content = {
                            BodyMediumRegularText(value, modifier = Modifier.weight(1f))
                        }
                    )
                    if (index != options.lastIndex) {
                        Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                    }
                }
            }
        }
    }
}