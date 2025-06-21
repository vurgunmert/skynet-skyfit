package com.vurgun.skyfit.core.ui.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun SecondaryPillChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) {
        SkyFitColor.specialty.buttonBgActive
    } else {
        SkyFitColor.specialty.secondaryButtonRest
    }

    val borderColor = if (selected) {
        SkyFitColor.border.secondary
    } else {
        SkyFitColor.border.secondaryButton
    }

    val textColor = if (selected) {
        SkyFitColor.text.inverse
    } else {
        SkyFitColor.text.default
    }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(backgroundColor)
            .border(1.dp, borderColor, CircleShape)
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = SkyFitTypography.bodyMediumMedium.copy(color = textColor)
        )
    }
}


@Composable
fun RectangleChip(
    text: String,
    textColor: Color = SkyFitColor.text.default,
    backgroundColor: Color = SkyFitColor.background.surfaceInfo,
    rightIconRes: DrawableResource? = null,
    rightIconTint: SkyIconTint = SkyIconTint.Default,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .height(28.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SkyText(
            text = text,
            color = textColor,
            styleType = TextStyleType.BodyMediumMedium
        )

        if (rightIconRes != null) {
            Spacer(Modifier.width(10.dp))
            SkyIcon(
                res = rightIconRes,
                size = SkyIconSize.Small,
                tint = rightIconTint
            )
        }
    }
}