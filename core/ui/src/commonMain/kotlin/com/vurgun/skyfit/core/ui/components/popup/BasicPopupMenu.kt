package com.vurgun.skyfit.core.ui.components.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun BasicPopupMenu(
    modifier: Modifier = Modifier.width(160.dp),
    isOpen: Boolean,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    if (!isOpen) return

    MaterialTheme(
        colors = MaterialTheme.colors.copy(surface = SkyFitColor.background.surfaceSecondary),
        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))
    ) {
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = { onDismiss() },
            modifier = modifier.background(Color.Transparent)
        ) {
            Surface(elevation = 8.dp) {
                Column(content = content)
            }
        }
    }
}