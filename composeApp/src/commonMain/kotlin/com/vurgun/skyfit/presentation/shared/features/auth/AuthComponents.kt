package com.vurgun.skyfit.presentation.shared.features.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun AuthLogoComponent() {
    Image(
        painterResource(Res.drawable.logo_skyfit),
        null,
        modifier = Modifier.size(108.dp, 118.dp)
    )
}