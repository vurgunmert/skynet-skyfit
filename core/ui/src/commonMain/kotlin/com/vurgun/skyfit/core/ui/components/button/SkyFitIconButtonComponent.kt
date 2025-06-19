package com.vurgun.skyfit.core.ui.components.button

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_app_logo
import skyfit.core.ui.generated.resources.ic_chevron_left
import skyfit.core.ui.generated.resources.ic_fiwe_logo_dark

@Composable
fun SkyFitIconButton(
    painter: Painter = painterResource(Res.drawable.ic_app_logo),
    modifier: Modifier = Modifier,
    color: Color = SkyFitColor.background.surfaceSecondary,
    onClick: () -> Unit = {}
) {
    Box(
        modifier
            .background(color, shape = CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = "Button",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun SkyFitPrimaryCircularBackButton(
    modifier: Modifier = Modifier.size(48.dp),
    onClick: () -> Unit = {}
) {
    Box(
        modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(SkyFitColor.specialty.buttonBgRest),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_chevron_left),
            contentDescription = "Back",
            tint = SkyFitColor.icon.inverseSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun PrimaryIconButton(
    painter: Painter = painterResource(Res.drawable.ic_app_logo),
    modifier: Modifier = Modifier.size(44.dp),
    onClick: () -> Unit = {}
) {
    Box(
        modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(SkyFitColor.specialty.buttonBgRest),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = "Button",
            tint = SkyFitColor.icon.inverseSecondary,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun PrimaryIconButton(
    res: DrawableResource = Res.drawable.ic_fiwe_logo_dark,
    modifier: Modifier = Modifier.size(44.dp),
    iconModifier: Modifier = Modifier.size(16.dp),
    onClick: () -> Unit = {}
) {
    Box(
        modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(SkyFitColor.specialty.buttonBgRest),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(res),
            contentDescription = "Button",
            tint = SkyFitColor.icon.inverseSecondary,
            modifier =iconModifier
        )
    }
}

@Composable
fun SecondaryIconButton(
    painter: Painter = painterResource(Res.drawable.ic_app_logo),
    modifier: Modifier = Modifier.size(44.dp),
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier
            .background(SkyFitColor.specialty.secondaryButtonRest, shape = CircleShape)
            .border(1.dp, SkyFitColor.specialty.buttonBgRest, CircleShape)
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = "Button",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun SecondaryIconButton(
    res: DrawableResource = Res.drawable.ic_fiwe_logo_dark,
    modifier: Modifier = Modifier.size(44.dp),
    iconModifier: Modifier = Modifier.size(16.dp),
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier
            .background(SkyFitColor.specialty.secondaryButtonRest, shape = CircleShape)
            .border(1.dp, SkyFitColor.specialty.buttonBgRest, CircleShape)
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(res),
            contentDescription = "Button",
            tint = SkyFitColor.icon.default,
            modifier = iconModifier
        )
    }
}


@Composable
fun SecondaryFlatIconButton(
    painter: Painter = painterResource(Res.drawable.ic_app_logo),
    modifier: Modifier = Modifier.size(44.dp)
) {
    Box(
        modifier
            .background(SkyFitColor.specialty.secondaryButtonRest, shape = CircleShape)
            .border(1.dp, SkyFitColor.specialty.buttonBgRest, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = "Button",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun SkyFitSecondaryIconButton(
    painter: Painter = painterResource(Res.drawable.ic_app_logo),
    modifier: Modifier = Modifier.size(44.dp),
    onClick: () -> Unit = {}
) {
    Box(
        modifier
            .background(SkyFitColor.specialty.secondaryButtonRest, shape = CircleShape)
            .border(1.dp, SkyFitColor.specialty.buttonBgRest, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = "Button",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun SkyFitCircularProgressIconButton(
    painter: Painter = painterResource(Res.drawable.ic_app_logo),
    progress: Float,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.size(40.dp).clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(40.dp)) {
            drawArc(
                color = SkyFitColor.border.default,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 3.dp.toPx())
            )
            drawArc(
                color = SkyFitColor.border.secondaryButton,
                startAngle = -90f,
                sweepAngle = progress * 360f,
                useCenter = false,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Icon(
            painter = painter,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}