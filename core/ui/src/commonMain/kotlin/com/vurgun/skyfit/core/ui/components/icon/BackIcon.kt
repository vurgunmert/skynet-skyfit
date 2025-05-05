package com.vurgun.skyfit.core.ui.components.icon

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_left

@Composable
fun BackIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ActionIcon(
        res = Res.drawable.ic_chevron_left,
        modifier = modifier.size(16.dp),
        onClick = onClick
    )
}
