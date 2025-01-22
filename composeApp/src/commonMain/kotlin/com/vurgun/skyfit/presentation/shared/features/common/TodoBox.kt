package com.vurgun.skyfit.presentation.shared.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor

@Composable
fun TodoBox(todo: String, modifier: Modifier = Modifier.size(60.dp)) {
    Box(
        modifier.background(SkyFitColor.background.surfaceSecondaryHover).border(1.dp, Color.Gray),
        contentAlignment = Alignment.Center,
    ) {
        Text("TODO: $todo")
    }
}