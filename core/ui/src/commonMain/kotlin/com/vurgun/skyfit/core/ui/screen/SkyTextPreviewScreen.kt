package com.vurgun.skyfit.core.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun SkyTextPreviewScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(SkyFitColor.background.default)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SkyText("Heading1", styleType = TextStyleType.Heading1)
        SkyText("Heading2", styleType = TextStyleType.Heading2)
        SkyText("Heading3", styleType = TextStyleType.Heading3)
        SkyText("Heading4", styleType = TextStyleType.Heading4)
        SkyText("Heading5", styleType = TextStyleType.Heading5)
        SkyText("Heading6", styleType = TextStyleType.Heading6)

        Spacer(modifier = Modifier.height(12.dp))

        SkyText("BodySmall", styleType = TextStyleType.BodySmall)
        SkyText("BodySmallMedium", styleType = TextStyleType.BodySmallMedium)
        SkyText("BodySmallSemibold", styleType = TextStyleType.BodySmallSemibold)
        SkyText("BodySmallUnderlined", styleType = TextStyleType.BodySmallUnderlined)

        Spacer(modifier = Modifier.height(12.dp))

        SkyText("BodyXSmall", styleType = TextStyleType.BodyXSmall)
        SkyText("BodyXSmallSemibold", styleType = TextStyleType.BodyXSmallSemibold)

        Spacer(modifier = Modifier.height(12.dp))

        SkyText("BodyMediumRegular", styleType = TextStyleType.BodyMediumRegular)
        SkyText("BodyMediumMedium", styleType = TextStyleType.BodyMediumMedium)
        SkyText("BodyMediumMediumBold", styleType = TextStyleType.BodyMediumMediumBold)
        SkyText("BodyMediumSemibold", styleType = TextStyleType.BodyMediumSemibold)
        SkyText("BodyMediumUnderlined", styleType = TextStyleType.BodyMediumUnderlined)

        Spacer(modifier = Modifier.height(12.dp))

        SkyText("BodyLarge", styleType = TextStyleType.BodyLarge)
        SkyText("BodyLargeMedium", styleType = TextStyleType.BodyLargeMedium)
        SkyText("BodyLargeSemibold", styleType = TextStyleType.BodyLargeSemibold)
        SkyText("BodyLargeUnderlined", styleType = TextStyleType.BodyLargeUnderlined)

        Spacer(modifier = Modifier.height(12.dp))

        SkyText("Subtitle1", styleType = TextStyleType.Subtitle1)
        SkyText("Subtitle2", styleType = TextStyleType.Subtitle2)
        SkyText("Caption", styleType = TextStyleType.Caption)
        SkyText("Overline", styleType = TextStyleType.Overline)
    }
}
