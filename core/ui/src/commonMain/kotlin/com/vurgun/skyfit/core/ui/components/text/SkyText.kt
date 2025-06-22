package com.vurgun.skyfit.core.ui.components.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

sealed class TextStyleType {
    object Heading1 : TextStyleType()
    object Heading2 : TextStyleType()
    object Heading3 : TextStyleType()
    object Heading4 : TextStyleType()
    object Heading5 : TextStyleType()
    object Heading6 : TextStyleType()
    object BodySmall : TextStyleType()
    object BodySmallMedium : TextStyleType()
    object BodySmallSemibold : TextStyleType()
    object BodySmallUnderlined : TextStyleType()
    object BodyXSmall : TextStyleType()
    object BodyXSmallSemibold : TextStyleType()
    object BodyMediumMedium : TextStyleType()
    object BodyMediumMediumBold : TextStyleType()
    object BodyMediumRegular : TextStyleType()
    object BodyMediumSemibold : TextStyleType()
    object BodyMediumUnderlined : TextStyleType()
    object BodyLarge : TextStyleType()
    object BodyLargeMedium : TextStyleType()
    object BodyLargeSemibold : TextStyleType()
    object BodyLargeUnderlined : TextStyleType()
    object Subtitle1 : TextStyleType()
    object Subtitle2 : TextStyleType()
    object Caption : TextStyleType()
    object Overline : TextStyleType()
}

@Composable
fun TextStyleType.toTextStyle(): TextStyle = when (this) {
    TextStyleType.Heading1 -> SkyFitTypography.heading1
    TextStyleType.Heading2 -> SkyFitTypography.heading2
    TextStyleType.Heading3 -> SkyFitTypography.heading3
    TextStyleType.Heading4 -> SkyFitTypography.heading4
    TextStyleType.Heading5 -> SkyFitTypography.heading5
    TextStyleType.Heading6 -> SkyFitTypography.heading6
    TextStyleType.BodySmall -> SkyFitTypography.bodySmall
    TextStyleType.BodySmallMedium -> SkyFitTypography.bodySmallMedium
    TextStyleType.BodySmallSemibold -> SkyFitTypography.bodySmallSemibold
    TextStyleType.BodySmallUnderlined -> SkyFitTypography.bodySmallMediumUnderlined
    TextStyleType.BodyXSmall -> SkyFitTypography.bodyXSmall
    TextStyleType.BodyXSmallSemibold -> SkyFitTypography.bodyXSmallSemibold
    TextStyleType.BodyMediumMedium -> SkyFitTypography.bodyMediumMedium
    TextStyleType.BodyMediumMediumBold -> SkyFitTypography.bodyMediumMediumBold
    TextStyleType.BodyMediumRegular -> SkyFitTypography.bodyMediumRegular
    TextStyleType.BodyMediumSemibold -> SkyFitTypography.bodyMediumSemibold
    TextStyleType.BodyMediumUnderlined -> SkyFitTypography.bodyMediumUnderlined
    TextStyleType.BodyLarge -> SkyFitTypography.bodyLarge
    TextStyleType.BodyLargeMedium -> SkyFitTypography.bodyLargeMedium
    TextStyleType.BodyLargeSemibold -> SkyFitTypography.bodyLargeSemibold
    TextStyleType.BodyLargeUnderlined -> SkyFitTypography.bodyLargeUnderlined
    TextStyleType.Subtitle1 -> SkyFitTypography.subtitle1
    TextStyleType.Subtitle2 -> SkyFitTypography.subtitle2
    TextStyleType.Caption -> SkyFitTypography.caption
    TextStyleType.Overline -> SkyFitTypography.overline
}

@Composable
fun SkyText(
    text: String,
    styleType: TextStyleType,
    alignment: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier,
    color: Color? = null
) {
    val resolvedStyle = styleType.toTextStyle()
    val finalColor = color ?: resolvedStyle.color

    Text(
        text = text,
        style = resolvedStyle.copy(color = finalColor),
        textAlign = alignment,
        maxLines = maxLines,
        modifier = modifier
    )
}
