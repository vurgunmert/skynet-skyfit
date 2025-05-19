package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.confirm_action
import skyfit.core.ui.generated.resources.decline_action
import skyfit.core.ui.generated.resources.ic_info_circle
import skyfit.core.ui.generated.resources.membership_request_approval_message
import skyfit.core.ui.generated.resources.new_membership_request_label

@Composable
fun MembershipRequestCard(
    facilityId: Int,
    facilityName: String,
    facilityImageUrl: String? = null,
    onConfirm: () -> Unit,
    onDecline: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SkyFitColor.background.surfaceInfoActive)
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Row {
            NetworkImage(
                imageUrl = facilityImageUrl,
                modifier = Modifier.clip(CircleShape).size(36.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Row {
                    SkyIcon(
                        res = Res.drawable.ic_info_circle,
                        size = SkyIconSize.Small,
                        tint = SkyIconTint.Info
                    )
                    Spacer(Modifier.width(8.dp))
                    SkyText(
                        text = stringResource(Res.string.new_membership_request_label),
                        styleType = TextStyleType.BodySmallSemibold
                    )
                    Spacer(Modifier.weight(1f))
                    SkyText(
                        text = "1 saat önce",
                        styleType = TextStyleType.BodyXSmallSemibold,
                        color = SkyFitColor.text.infoOnBgFill
                    )
                }
                Spacer(Modifier.height(8.dp))
                SkyText(
                    text = stringResource(Res.string.membership_request_approval_message, facilityName),
                    styleType = TextStyleType.BodySmall
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            SkyButton(
                label = stringResource(Res.string.decline_action),
                variant = SkyButtonVariant.Destructive,
                size = SkyButtonSize.Micro,
                onClick = {}
            )
            Spacer(Modifier.width(16.dp))
            SkyButton(
                label = stringResource(Res.string.confirm_action),
                size = SkyButtonSize.Micro,
                onClick = {}
            )
        }
    }
}