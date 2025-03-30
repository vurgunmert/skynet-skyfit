package com.vurgun.skyfit.designsystem.components.popup

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
import com.vurgun.skyfit.core.ui.resources.SkyFitColor

@Composable
fun BasicPopupMenu(
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
            modifier = Modifier
                .width(160.dp)
                .background(Color.Transparent) // Prevents overriding the rounded shape
        ) {
            Surface(elevation = 8.dp) {
                Column(content = content)
            }
        }
    }
}