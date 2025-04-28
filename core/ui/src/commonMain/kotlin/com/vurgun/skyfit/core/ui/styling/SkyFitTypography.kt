package com.vurgun.skyfit.core.ui.styling

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import skyfit.core.ui.generated.resources.Inter_Bold
import skyfit.core.ui.generated.resources.Inter_Medium
import skyfit.core.ui.generated.resources.Inter_Regular
import skyfit.core.ui.generated.resources.Inter_SemiBold
import skyfit.core.ui.generated.resources.Res

object SkyFitTypography {

    @Composable
    fun InterFontFamily(): FontFamily {
        return FontFamily(
            Font(Res.font.Inter_Regular, weight = FontWeight.Normal),
            Font(Res.font.Inter_Medium, weight = FontWeight.Medium),
            Font(Res.font.Inter_SemiBold, weight = FontWeight.SemiBold),
            Font(Res.font.Inter_Bold, weight = FontWeight.Bold)
        )
    }

    val typography: Typography
        @Composable
        get() = Typography(
            defaultFontFamily = InterFontFamily(),
            h1 = heading1,
            h2 = heading2,
            h3 = heading3,
            h4 = heading4,
            h5 = heading5,
            h6 = heading6,
            body1 = bodySmall,
            body2 = bodySmallSemibold,
            subtitle1 = subtitle1,
            subtitle2 = subtitle2,
            caption = caption,
            overline = overline
        )

    val heading1: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            lineHeight = 54.sp,
            color = SkyFitColor.text.default
        )

    val heading2: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 48.sp,
            color = SkyFitColor.text.default
        )

    val heading3: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 39.sp,
            color = SkyFitColor.text.default
        )

    val heading4: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            color = SkyFitColor.text.default
        )

    val heading5: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            color = SkyFitColor.text.default
        )

    val heading6: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = SkyFitColor.text.default
        )

    val bodySmall: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            color = SkyFitColor.text.default
        )

    val bodySmallMedium: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            color = SkyFitColor.text.default
        )

    val bodySmallSemibold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            color = SkyFitColor.text.default
        )

    val bodySmallMediumUnderlined: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            textDecoration = TextDecoration.Underline,
            color = SkyFitColor.text.default
        )

    val bodyXSmall: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 12.sp,
            color = SkyFitColor.text.default
        )

    val bodyXSmallSemibold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
            lineHeight = 12.sp,
            color = SkyFitColor.text.default
        )

    val subtitle1: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = SkyFitColor.text.default
        )

    val subtitle2: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = SkyFitColor.text.default
        )

    val caption: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            color = SkyFitColor.text.default
        )

    val overline: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 12.sp,
            color = SkyFitColor.text.default
        )

    val bodyLarge: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = SkyFitColor.text.default
        )

    val bodyLargeMedium: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = SkyFitColor.text.default
        )

    val bodyLargeSemibold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = SkyFitColor.text.default
        )

    val bodyLargeUnderlined: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            textDecoration = TextDecoration.Underline,
            color = SkyFitColor.text.default
        )

    val bodyMediumMedium: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = SkyFitColor.text.default
        )

    val bodyMediumMediumBold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = SkyFitColor.text.default
        )

    val bodyMediumRegular: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = SkyFitColor.text.default
        )

    val bodyMediumSemibold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = SkyFitColor.text.default
        )

    val bodyMediumUnderlined: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = InterFontFamily(),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            textDecoration = TextDecoration.Underline,
            color = SkyFitColor.text.default
        )
}