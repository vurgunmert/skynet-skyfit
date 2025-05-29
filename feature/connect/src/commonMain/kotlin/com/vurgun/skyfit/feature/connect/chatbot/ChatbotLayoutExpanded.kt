package com.vurgun.skyfit.feature.connect.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.background_chatbot

internal object ChatbotLayoutExpanded {

    @Composable
    fun Screen() {

        Image(
            painter = painterResource(Res.drawable.background_chatbot),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
    }


    @Preview
    @Composable
    fun ScreenPreview() {

    }
}