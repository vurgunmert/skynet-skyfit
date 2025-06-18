package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun SideBySideLayout(
    modifier: Modifier = Modifier,
    leftModifier: Modifier = Modifier,
    rightModifier: Modifier = Modifier,
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(SkyFitColor.background.default)
    ) {
        Box(
            modifier = leftModifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            leftContent()
        }
        Box(
            modifier = rightModifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            rightContent()
        }
    }
}