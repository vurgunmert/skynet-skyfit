package com.vurgun.skyfit.feature.connect.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.data.notification.NotificationType
import com.vurgun.skyfit.core.data.v1.data.notification.SkyFitNotificationDTO
import com.vurgun.skyfit.core.ui.components.image.CircleNetworkImage
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_check_circle
import skyfit.core.ui.generated.resources.ic_info_circle
import skyfit.core.ui.generated.resources.ic_warning
import skyfit.core.ui.generated.resources.ic_warning_diamond
import skyfit.core.ui.generated.resources.ic_app_logo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationItemSwipeDismissBackground(dismissState: DismissState) {
    val color = when (dismissState.dismissDirection) {
        DismissDirection.StartToEnd -> Color(0xFFFF1744)
        DismissDirection.EndToStart -> Color(0xFF1DE9B6)
        null -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (dismissState.dismissDirection == DismissDirection.EndToStart) {
            Spacer(modifier = Modifier.weight(1f))

            Box(
                Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SkyFitColor.background.surfaceCriticalActive)
                    .border(1.dp, SkyFitColor.border.critical, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    painterResource(Res.drawable.ic_app_logo),
                    contentDescription = "Delete",
                    modifier = Modifier
                        .align(Alignment.Center),
                    tint = Color.White
                )
            }
        }
    }

}

@Composable
fun SkyFitNotificationItem(notification: SkyFitNotificationDTO) {

    Box(
        Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(notification.type.backgroundColor())
            .border(1.dp, notification.type.borderColor(), RoundedCornerShape(12.dp))
    ) {

        Row(
            Modifier
                .wrapContentHeight()
                .padding(16.dp)
        ) {

            notification.NotificationIcon()

            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    // Notification Icon
                    Image(
                        painter = notification.type.toNotificationIcon(),
                        contentDescription = "Notification Icon",
                        modifier = Modifier.size(16.dp),
                        colorFilter = ColorFilter.tint(notification.type.toIconColor())
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    // Title
                    Text(
                        text = notification.title,
                        color = SkyFitColor.text.default,
                        style = SkyFitTypography.bodySmallSemibold,
                        modifier = Modifier.weight(1f).padding(end = 16.dp),
                        maxLines = 2
                    )
                    // Time
                    Text(
                        modifier = Modifier,
                        text = notification.timestamp,
                        color = notification.type.timeColor(),
                        style = SkyFitTypography.bodyXSmallSemibold
                    )
                }
                // Description
                Text(
                    text = notification.message,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    color = SkyFitColor.text.default,
                    style = SkyFitTypography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun SkyFitNotificationDTO.NotificationIcon() {

    if (this.iconId != null) {
        Image(
            painter = painterResource(Res.drawable.ic_app_logo),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    } else if (this.iconUrl != null) {
        CircleNetworkImage(this.iconUrl, size = 36.dp)
    }
}

fun NotificationType.backgroundColor(): Color {
    return when (this) {
        NotificationType.CRITICAL -> SkyFitColor.background.surfaceCriticalActive
        NotificationType.WARNING -> SkyFitColor.background.surfaceWarningActive
        NotificationType.INFO -> SkyFitColor.background.surfaceInfoActive
        NotificationType.SUCCESS -> SkyFitColor.background.surfaceSuccessActive
        NotificationType.ERROR -> SkyFitColor.background.surfaceCriticalActive
        NotificationType.SYSTEM -> SkyFitColor.background.surfaceInfoActive
    }
}

// Extension function to compute border color
fun NotificationType.borderColor(): Color {
    return when (this) {
        NotificationType.CRITICAL -> SkyFitColor.border.critical
        NotificationType.WARNING -> SkyFitColor.border.warning
        NotificationType.INFO -> SkyFitColor.border.info
        NotificationType.SUCCESS -> SkyFitColor.border.success
        NotificationType.ERROR -> SkyFitColor.border.critical
        NotificationType.SYSTEM -> SkyFitColor.border.info
    }
}

// Extension function to compute icon resource
@Composable
private fun NotificationType.toNotificationIcon(): Painter {
    return when (this) {
        NotificationType.CRITICAL -> painterResource(Res.drawable.ic_warning_diamond)
        NotificationType.WARNING -> painterResource(Res.drawable.ic_warning)
        NotificationType.INFO -> painterResource(Res.drawable.ic_info_circle)
        NotificationType.SUCCESS -> painterResource(Res.drawable.ic_check_circle)
        NotificationType.ERROR -> painterResource(Res.drawable.ic_warning_diamond)
        NotificationType.SYSTEM -> painterResource(Res.drawable.ic_info_circle)
    }
}

// Extension function to compute icon color
private fun NotificationType.toIconColor(): Color {
    return when (this) {
        NotificationType.CRITICAL -> SkyFitColor.icon.critical
        NotificationType.WARNING -> SkyFitColor.icon.warning
        NotificationType.INFO -> SkyFitColor.icon.info
        NotificationType.SUCCESS -> SkyFitColor.icon.success
        NotificationType.ERROR -> SkyFitColor.icon.critical
        NotificationType.SYSTEM -> SkyFitColor.icon.default
    }
}

// Extension function to compute time color
fun NotificationType.timeColor(): Color {
    return when (this) {
        NotificationType.CRITICAL -> SkyFitColor.text.criticalOnBgFill
        NotificationType.WARNING -> SkyFitColor.text.warningOnBgFill
        NotificationType.INFO -> SkyFitColor.text.infoOnBgFill
        NotificationType.SUCCESS -> SkyFitColor.text.successOnBgFill
        NotificationType.ERROR -> SkyFitColor.text.criticalOnBgFill
        NotificationType.SYSTEM -> SkyFitColor.text.infoOnBgFill
    }
}
