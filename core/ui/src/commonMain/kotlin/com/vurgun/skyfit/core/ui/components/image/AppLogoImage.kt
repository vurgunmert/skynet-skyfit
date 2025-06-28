package com.vurgun.skyfit.core.ui.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_app_logo

@Composable
fun AppLogoImage(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(Res.drawable.ic_app_logo),
        contentDescription = "Logo",
        modifier = modifier.fillMaxWidth().aspectRatio(1f),
        contentScale = ContentScale.Fit
    )
}

@Preview
@Composable
private fun AppLogoImagePreview_Default() {
    AppLogoImage(modifier = Modifier.size(100.dp))
}

@Preview
@Composable
private fun AppLogoImagePreview_Large() {
    AppLogoImage(modifier = Modifier.size(200.dp))
}