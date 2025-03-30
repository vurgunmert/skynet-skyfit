package com.vurgun.skyfit.designsystem.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.resources.SkyFitColor

@Composable
fun PreviewMobileScaffoldColumn(content: @Composable ColumnScope.() -> Unit) {

    SkyFitMobileScaffold {
        Column(
            Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
    }
}

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