package com.vurgun.skyfit.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun PreviewBox(content: @Composable BoxScope.() -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SkyFitColor.background.default)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
        content = content
    )
}