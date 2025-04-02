package com.vurgun.skyfit.designsystem.components.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_check

@Composable
fun SelectablePopupMenuItem(
    selected: Boolean,
    onSelect: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    DropdownMenuItem(
        onClick = onSelect,
        modifier = Modifier.background(
            color = when {
                selected -> SkyFitColor.background.surfaceSecondaryHover
                else -> SkyFitColor.transparent
            }
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
        ) {
            content()

            if (selected) {
                Icon(
                    painter = painterResource(Res.drawable.ic_check),
                    tint = SkyFitColor.icon.linkInverse,
                    contentDescription = null
                )
            }
        }
    }
}