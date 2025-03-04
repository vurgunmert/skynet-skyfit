package com.vurgun.skyfit.core.ui.resources

import androidx.compose.ui.unit.dp

object SkyFitStyleGuide {

    object Padding {
        val xSmall = 2.dp   // Very small padding (fine-tuning)
        val small = 4.dp    // Small padding (buttons, small gaps)
        val medium = 8.dp   // Medium padding (between components)
        val large = 16.dp   // Default spacing (general use)
        val xLarge = 24.dp  // Used in many designs (e.g., titles, sections)
        val xxLarge = 32.dp // Used for larger sections
        val xxxLarge = 48.dp // Major separations (e.g., screen-level spacing)
        val huge = 64.dp    // For big sections like page margins
        val xHuge = 128.dp  // Extra-large spacing for big layouts
    }

    object Mobile {
        val maxWidth = 430.dp
        val contentWidth = 382.dp
        val contentPadding = Padding.large
    }

    object Tablet {
        val minWidth = 600.dp
        val maxWidth = 1024.dp
        val contentWidth = 600.dp // Increase content width
        val contentPadding = Padding.xLarge
    }

    object Desktop {
        val minWidth = 1024.dp
        val maxWidth = 1280.dp // Designer requested max 1280x832
        val contentWidth = 800.dp // Centered
        val contentPadding = Padding.xxLarge
    }

    object Web {
        val minWidth = 1280.dp
        val contentWidth = 1000.dp // More spacious layout
        val contentPadding = Padding.xxxLarge
    }
}
