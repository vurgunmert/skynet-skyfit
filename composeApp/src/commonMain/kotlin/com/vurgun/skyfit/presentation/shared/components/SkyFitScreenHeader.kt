package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SkyFitScreenHeader(title: String) {
    Box {
        Text(title, modifier = Modifier.align(Alignment.Center))
    }
}