package com.vurgun.skyfit.presentation.shared.resources

import androidx.compose.ui.graphics.Color

object SkyFitColor {
    val transparent = Color.Transparent

    object background {
        val bg = Color(0xFF00171C)  // bg
        val default = bg
        val inverse = Color(0xFFEBF9FF)  // bg-inverse
        val surface = Color(0xFF013B46)  // bg-surface
        val surfaceOpalTransparent = Color(0xFF012E36).copy(alpha = 0.02f)  // bg-surface-hover
        val surfaceSemiTransparent = Color(0xFF012E36).copy(alpha = 0.05f)  // bg-surface-hover
        val surfaceHover = Color(0xFF012E36)  // bg-surface-hover
        val surfaceActive = Color(0xFF01272F)  // bg-surface-active
        val surfaceSelected = Color(0xFF012127)  // bg-surface-selected
        val surfaceSecondary = Color(0xFF012E36)  // bg-surface-secondary
        val surfaceSecondaryHover = Color(0xFF012127)  // bg-surface-secondary-hover
        val surfaceSecondaryActive = Color(0xFF001A1F)  // bg-surface-secondary-active
        val surfaceSecondarySelected = Color(0xFF001A1F)  // bg-surface-secondary-selected
        val surfaceDisabled = Color(0xFFBCEFFF).copy(alpha = 0.05f)  // bg-surface-disabled
        val surfaceTertiary = Color(0xFF01272F)  // bg-surface-tertiary
        val surfaceTertiaryHover = Color(0xFF001A1F)  // bg-surface-tertiary-hover
        val surfaceTertiaryActive = Color(0xFF001417)  // bg-surface-tertiary-active
        val surfaceBrand = Color(0xFF001417)  // bg-surface-brand
        val surfaceBrandHover = Color(0xFF001A1F)  // bg-surface-brand-hover
        val surfaceBrandActive = Color(0xFF012127)  // bg-surface-brand-active
        val surfaceBrandSelected = Color(0xFF012127)  // bg-surface-brand-selected
        val surfaceInfo = Color(0xFF00527C)  // bg-surface-info
        val surfaceInfoHover = Color(0xFF003A5A)  // bg-surface-info-hover
        val surfaceInfoActive = Color(0xFF002133)  // bg-surface-info-active
        val surfaceSuccess = Color(0xFF0C5132)  // bg-surface-success
        val surfaceSuccessHover = Color(0xFF083D25)  // bg-surface-success-hover
        val surfaceSuccessActive = Color(0xFF092A1B)  // bg-surface-success-active
        val surfaceCaution = Color(0xFF4F4700)  // bg-surface-caution
        val surfaceCautionHover = Color(0xFF332E00)  // bg-surface-caution-hover
        val surfaceCautionActive = Color(0xFF1F1C00)  // bg-surface-caution-active
        val surfaceWarning = Color(0xFF5E4200)  // bg-surface-warning
        val surfaceWarningHover = Color(0xFF412D00)  // bg-surface-warning-hover
        val surfaceWarningActive = Color(0xFF251A00)  // bg-surface-warning-active
        val surfaceCritical = Color(0xFF8E1F0B)  // bg-surface-critical
        val surfaceCriticalHover = Color(0xFF5F1507)  // bg-surface-critical-hover
        val surfaceCriticalActive = Color(0xFF2F0A04)  // bg-surface-critical-active
        val fillTransparent = Color(0xFFBCEFFF).copy(alpha = 0.03f)  // bg-fill-transparent
        val fillTransparentHover = Color(0xFFBCEFFF).copy(alpha = 0.05f)  // bg-fill-transparent-hover
        val fillTransparentActive = Color(0xFFBCEFFF).copy(alpha = 0.07f)  // bg-fill-transparent-active
        val fillTransparentSecondary = Color(0xFFBCEFFF).copy(alpha = 0.06f)  // bg-fill-transparent-secondary
        val fillTransparentSecondaryHover =
            Color(0xFFBCEFFF).copy(alpha = 0.07f)  // bg-fill-transparent-secondary-hover
        val fillTransparentSecondaryActive =
            Color(0xFFBCEFFF).copy(alpha = 0.08f)  // bg-fill-transparent-secondary-active
        val fillTransparentSelected = Color(0xFFBCEFFF).copy(alpha = 0.07f)  // bg-fill-transparent-selected
    }

    object text {
        val default = Color(0xFFF5F5F5)  // text
        val secondary = Color(0xFFB5B5B5)  // text-secondary
        val disabled = Color(0xFFB5B5B5).copy(alpha = 0.50f)  // text-disabled
        val link = Color(0xFFCD5DFF)  // text-link
        val linkHover = Color(0xFF004299)  // text-link-hover
        val linkActive = Color(0xFF005BD3)  // text-link-active
        val linkInverse = Color(0xFF006FFF)  // text-link-inverse
        val info = Color(0xFFAEF4FF)  // text-info
        val infoHover = Color(0xFFE0F0FF)  // text-info-hover
        val infoActive = Color(0xFFCAEEFF)  // text-info-active
        val infoOnBgFill = Color(0xFF4EAFF4)  // text-info-on-bg-fill
        val success = Color(0xFFCDFFEA)  // text-success
        val successHover = Color(0xFFB4FED2)  // text-success-hover
        val successActive = Color(0xFF92FEC2)  // text-success-active
        val successOnBgFill = Color(0xFF64DF99)  // text-success-on-bg-fill
        val caution = Color(0xFFFFF8DB)  // text-caution
        val cautionHover = Color(0xFFFFF4BF)  // text-caution-hover
        val cautionActive = Color(0xFFFFF9D)  // text-caution-active
        val cautionOnBgFill = Color(0xFFEDDF5F)  // text-caution-on-bg-fill
        val warning = Color(0xFFFFF1E3)  // text-warning
        val warningHover = Color(0xFFFFEBD5)  // text-warning-hover
        val warningActive = Color(0xFFFFE4C6)  // text-warning-active
        val warningOnBgFill = Color(0xFFE7B642)  // text-warning-on-bg-fill
        val critical = Color(0xFFFEE9E8)  // text-critical
        val criticalHover = Color(0xFFFEE2E1)  // text-critical-hover
        val criticalActive = Color(0xFFFEDA09)  // text-critical-active
        val criticalOnBgFill = Color(0xFFED7272)  // text-critical-on-bg-fill
        val inverse = Color(0xFF373737)  // text-inverse
        val inverseSecondary = Color(0xFF787878)  // text-inverse-secondary
    }

    object icon {
        val default = Color(0xFFF5F5F5)  // icon
        val hover = Color(0xFFB5B5B5)  // icon-hover
        val active = Color(0xFFFAFAFA)  // icon-active
        val disabled = Color(0xFFB5B5B5).copy(alpha = 0.50f)  // icon-disabled
        val secondary = Color(0xFF8A8A8A)  // icon-secondary
        val secondaryHover = Color(0xFF616161)  // icon-secondary-hover
        val secondaryActive = Color(0xFFB5B5B5)  // icon-secondary-active
        val info = Color(0xFF0094D5)  // icon-info
        val success = Color(0xFF29845A)  // icon-success
        val caution = Color(0xFF998A00)  // icon-caution
        val warning = Color(0xFFB28400)  // icon-warning
        val critical = Color(0xFFEf4D2F)  // icon-critical
        val inverse = Color(0xFF4A4A4A)  // icon-inverse
        val inverseSecondary = Color(0xFF303030)  // icon-inverse-secondary
    }

    object border {
        val default = Color(0xFF616161)  // border
        val hover = Color(0xFFCCCCCC)  // border-hover
        val disabled = Color(0xFFEBEBEB)  // border-disabled
        val secondary = Color(0xFFEBEBEB)  // border-secondary
        val secondaryButton = Color(0xFF73D4D4)  // border-secondary
        val secondaryButtonHover = Color(0xFF4CA8A9)  // border-secondary
        val secondaryButtonDisabled = Color(0xFF013B46)  // border-secondary
        val tertiary = Color(0xFFCCCCCC)  // border-tertiary
        val focus = Color(0xFF005BD3)  // border-focus
        val info = Color(0xFF0094D5)  // border-info
        val success = Color(0xFF29845A)  // border-success
        val caution = Color(0xFFFEB78)  // border-caution
        val warning = Color(0xFFFFC879)  // border-warning
        val critical = Color(0xFF8E1F0B)  // border-critical
        val criticalSecondary = Color(0xFFFEC3C1)  // border-critical-secondary
        val inverse = Color(0xFFE3E3E3)  // border-inverse
        val inverseActive = Color(0xFFE3E3E3)  // border-inverse-active
        val inverseHover = Color(0xFFCCCCCC)  // border-inverse-hover
    }

    object specialty {
        val backdropBg = Color(0xFF000000).copy(alpha = 0.14f)  // backdrop-bg
        val inputBgSurface = Color(0xFFEEEEEE)  // input-bg-surface
        val inputBgSurfaceHover = Color(0xFFDDDDDD)  // input-bg-surface-hover
        val inputBgSurfaceActive = Color(0xFFCCCCCC)  // input-bg-surface-active
        val inputBorder = Color(0xFF888888)  // input-border
        val inputBorderHover = Color(0xFFAAAAAA)  // input-border-hover
        val inputBorderActive = Color(0xFF444444)  // input-border-active
        val checkboxIconDisabled = Color(0xFFEEEEEE)  // checkbox-icon-disabled
        val checkboxBgSurfaceDisabled = Color(0xFF000000).copy(alpha = 0.07f)  // checkbox-bg-surface-disabled
        val radioButtonIconDisabled = Color(0xFFEEEEEE)  // radio-button-icon-disabled
        val radioButtonBgSurfaceDisabled = Color(0xFF000000).copy(alpha = 0.07f)  // radio-button-bg-surface-disabled
        val navBg = Color(0xFFBFBFBF)  // nav-bg
        val navBgSurface = Color(0xFF000000).copy(alpha = 0.03f)  // nav-bg-surface
        val navBgSurfaceHover = Color(0xFF666666)  // nav-bg-surface-hover
        val navBgSurfaceActive = Color(0xFF333333)  // nav-bg-surface-active
        val navBgSurfaceSelected = Color(0xFF444444)  // nav-bg-surface-selected
        val videoThumbnailPlayButtonTextOnBgFill = Color(0xFFEEEEEE)  // video-thumbnail-play-button-text-on-bg-fill
        val videoThumbnailPlayButtonBgFill =
            Color(0xFF000000).copy(alpha = 0.14f)  // video-thumbnail-play-button-bg-fill
        val videoThumbnailPlayButtonBgFillHover =
            Color(0xFF000000).copy(alpha = 0.15f)  // video-thumbnail-play-button-bg-fill-hover
        val avatarOneBgFill = Color(0xFFCCCCCC)  // avatar-one-bg-fill
        val avatarTwoBgFill = Color(0xFF00FF00)  // avatar-two-bg-fill
        val avatarThreeBgFill = Color(0xFF00FFFF)  // avatar-three-bg-fill
        val avatarFourBgFill = Color(0xFF007FFF)  // avatar-four-bg-fill
        val avatarFiveBgFill = Color(0xFFFF007F)  // avatar-five-bg-fill
        val avatarTextOnBgFill = Color(0xFFEEEEEE)  // avatar-text-on-bg-fill
        val avatarOneTextOnBgFill = Color(0xFFFF00FF).copy(alpha = 0.14f)  // avatar-one-text-on-bg-fill
        val avatarTwoTextOnBgFill = Color(0xFF00FF00).copy(alpha = 0.14f)  // avatar-two-text-on-bg-fill
        val avatarThreeTextOnBgFill = Color(0xFF00FFFF).copy(alpha = 0.14f)  // avatar-three-text-on-bg-fill
        val avatarFourTextOnBgFill = Color(0xFF007FFF).copy(alpha = 0.14f)  // avatar-four-text-on-bg-fill
        val avatarFiveTextOnBgFill = Color(0xFFFF007F).copy(alpha = 0.14f)  // avatar-five-text-on-bg-fill
        val buttonBgRest = Color(0xFF73D4D4) // button-bg-rest
        val buttonBgHover = Color(0xFF4CA8A9)  // button-bg-hover
        val buttonBgActive = Color(0xFF73D4D4)  // button-bg-active
        val buttonBgDisabled = Color(0xFF01454F)  // button-bg-disabled
        val buttonBgLoading = Color(0xFF01454F)  // button-bg-loading
        val buttonBgPressed = Color(0xFF4CA8A9)  // button-bg-pressed
        val secondaryButtonRest = Color(0xFF73D4D4).copy(alpha = 0.07f)
        val statisticOrange = Color(0xFFD48A73)
        val statisticPink = Color(0xFFC773D4)
        val statisticBlue = border.secondaryButton
    }
}