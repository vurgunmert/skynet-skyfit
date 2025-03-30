package com.vurgun.skyfit.core.ui.components.icon

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.delete_action
import skyfit.composeapp.generated.resources.ic_delete

@Composable
fun DeleteIcon() {
    Icon(
        painter = painterResource(Res.drawable.ic_delete),
        contentDescription = stringResource(Res.string.delete_action),
        tint = SkyFitColor.icon.critical,
        modifier = Modifier.size(16.dp)
    )
}

@Composable
fun CircularDeleteIcon(onClick: () -> Unit) {
    Box(
        Modifier
            .clip(CircleShape)
            .border(1.dp, SkyFitColor.border.critical)
            .size(32.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        DeleteIcon()
    }
}