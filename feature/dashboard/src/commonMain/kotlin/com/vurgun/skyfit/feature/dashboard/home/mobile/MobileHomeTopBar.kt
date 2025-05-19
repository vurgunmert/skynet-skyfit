package com.vurgun.skyfit.feature.dashboard.home.mobile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.persona.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.persona.domain.model.UserProfile
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.image.SkyImageShape
import com.vurgun.skyfit.core.ui.components.image.SkyImageSize
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
internal fun MobileUserHomeTopBar(
    profile: UserProfile,
    memberFacility: FacilityProfile? = null,
    onClickFacility: (id: Int) -> Unit,
    notificationsEnabled: Boolean,
    onClickNotifications: () -> Unit,
    conversationsEnabled: Boolean,
    onClickConversations: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (memberFacility != null) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClickFacility(memberFacility.gymId) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                SkyImage(
                    url = memberFacility.backgroundImageUrl,
                    shape = SkyImageShape.Circle,
                    size = SkyImageSize.Size48
                )

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    SkyText(
                        text = memberFacility.facilityName,
                        styleType = TextStyleType.BodyMediumSemibold
                    )
                    Spacer(Modifier.height(4.dp))

                    val memberText = profile.memberDurationDays?.let { days ->
                        if (days == 0) {
                            stringResource(Res.string.member_since_day_zero)
                        } else {
                            stringResource(Res.string.member_since_days, days)
                        }
                    } ?: memberFacility.gymAddress

                    SkyText(
                        text = memberText,
                        styleType = TextStyleType.BodySmall,
                        color = SkyFitColor.text.secondary
                    )
                }
            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        MobileHomeTopBarActionRow(
            notificationsEnabled,
            onClickNotifications,
            conversationsEnabled,
            onClickConversations
        )
    }
}

@Composable
internal fun MobileHomeTopBar(
    notificationsEnabled: Boolean,
    onClickNotifications: () -> Unit,
    conversationsEnabled: Boolean,
    onClickConversations: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.weight(1f))

        MobileHomeTopBarActionRow(
            notificationsEnabled,
            onClickNotifications,
            conversationsEnabled,
            onClickConversations
        )
    }
}

@Composable
private fun MobileHomeTopBarActionRow(
    notificationsEnabled: Boolean,
    onClickNotifications: () -> Unit,
    conversationsEnabled: Boolean,
    onClickConversations: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FeatureVisible(notificationsEnabled) {
            Spacer(Modifier.width(16.dp))
            SkyIcon(
                res = Res.drawable.ic_bell,
                size = SkyIconSize.Normal,
                onClick = onClickNotifications
            )
        }

        FeatureVisible(conversationsEnabled) {
            Spacer(Modifier.width(16.dp))
            SkyIcon(
                res = Res.drawable.ic_chat,
                size = SkyIconSize.Normal,
                onClick = onClickConversations
            )
        }
    }
}