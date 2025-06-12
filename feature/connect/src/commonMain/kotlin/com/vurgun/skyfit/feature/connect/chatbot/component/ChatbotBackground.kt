package com.vurgun.skyfit.feature.connect.chatbot.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.min
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.background_chatbot
import skyfit.core.ui.generated.resources.logo_skyfit

@Composable
internal fun ChatbotBackground() {
    Image(
        painter = painterResource(Res.drawable.background_chatbot),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
internal fun ChatbotAppLogo(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val size = min(maxWidth, maxHeight) * 0.7f
        Image(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Logo",
            modifier = Modifier.size(size)
        )
    }
}