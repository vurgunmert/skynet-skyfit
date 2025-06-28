package com.vurgun.skyfit.feature.persona.settings.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.vurgun.skyfit.core.data.v1.domain.global.model.ProfileTag
import com.vurgun.skyfit.core.ui.components.button.LargePrimaryIconButton
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.text.CardFieldIconText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.*

@Composable
private fun ProfileCardChip(text: String) {
    Box(
        Modifier
            .wrapContentSize()
            .background(SkyFitColor.specialty.secondaryButtonRest, CircleShape)
            .border(1.dp, SkyFitColor.border.secondaryButton, CircleShape)
            .padding(start = 12.dp, top = 6.dp, end = 12.dp, bottom = 6.dp)
    ) {
        Text(
            text = text,
            style = SkyFitTypography.bodyMediumMedium
        )
    }
}

@Composable
private fun AccountSettingsProfileCardBackgroundImage(url: String?) {
    if (url.isNullOrEmpty()) {
        Icon(
            painter = painterResource(Res.drawable.ic_image),
            contentDescription = "Placeholder",
            modifier = Modifier.size(59.dp)
        )
    } else {
        NetworkImage(
            imageUrl = url,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
        )
    }
}

@Composable
private fun AccountSettingsProfileCardForegroundImage(url: String?) {
    if (url.isNullOrEmpty()) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_image),
                contentDescription = "Placeholder",
                modifier = Modifier.size(40.dp)
            )
        }
    } else {
        NetworkImage(
            imageUrl = url,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.surfaceSecondary)
        )
    }
}

@Composable
private fun AccountSettingsProfileCard(
    imageContent: @Composable BoxScope.() -> Unit,
    editorialContent: @Composable ColumnScope.() -> Unit
) {
    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp, max = 200.dp),
                contentAlignment = Alignment.Center,
                content = imageContent
            )

            editorialContent()
        }
    }
}

@Composable
fun UserAccountSettingsProfileCard(
    backgroundImageUrl: String?,
    foregroundImageUrl: String?,
    name: String,
    username: String,
    height: String,
    weight: String,
    bodyType: String,
    onClick: () -> Unit
) {
    AccountSettingsProfileCard(
        imageContent = {
            AccountSettingsProfileCardBackgroundImage(backgroundImageUrl)
            AccountSettingsProfileCardForegroundImage(foregroundImageUrl)
            LargePrimaryIconButton(
                iconRes = Res.drawable.ic_pencil,
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                onClick = onClick
            )
        },
        editorialContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardFieldIconText(Res.drawable.ic_profile_fill, name, Modifier.weight(1f))
                CardFieldIconText(Res.drawable.ic_at_symbol, username, Modifier.weight(1f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardFieldIconText(Res.drawable.ic_info_circle, height, Modifier.weight(1f))
                CardFieldIconText(Res.drawable.ic_info_circle, weight, Modifier.weight(1f))
                CardFieldIconText(Res.drawable.ic_info_circle, bodyType, Modifier.weight(1f))
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrainerAccountSettingsProfileCard(
    backgroundImageUrl: String?,
    foregroundImageUrl: String?,
    name: String,
    username: String,
    biography: String,
    tags: List<ProfileTag>,
    onClick: () -> Unit
) {

    AccountSettingsProfileCard(
        imageContent = {
            AccountSettingsProfileCardBackgroundImage(backgroundImageUrl)
            AccountSettingsProfileCardForegroundImage(foregroundImageUrl)
            LargePrimaryIconButton(
                iconRes = Res.drawable.ic_pencil,
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                onClick = onClick
            )
        },
        editorialContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardFieldIconText(Res.drawable.ic_profile_fill, name, Modifier.weight(1f))
                CardFieldIconText(Res.drawable.ic_at_symbol, username, Modifier.weight(1f))
            }

            CardFieldIconText(
                iconRes = Res.drawable.ic_note,
                text = biography,
                modifier = Modifier.fillMaxWidth()
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                tags.forEach {
                    ProfileCardChip(text = it.name)
                }
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacilityAccountSettingsProfileCard(
    backgroundImageUrl: String? = null,
    name: String,
    address: String,
    note: String,
    tags: List<ProfileTag>,
    onClick: () -> Unit
) {

    AccountSettingsProfileCard(
        imageContent = {
            AccountSettingsProfileCardBackgroundImage(backgroundImageUrl)
            LargePrimaryIconButton(
                iconRes = Res.drawable.ic_pencil,
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                onClick = onClick
            )
        },
        editorialContent = {
            CardFieldIconText(
                iconRes = Res.drawable.ic_profile_fill,
                text = name,
                modifier = Modifier.fillMaxWidth()
            )

            CardFieldIconText(
                iconRes = Res.drawable.ic_location_pin,
                text = address,
                modifier = Modifier.fillMaxWidth()
            )

            CardFieldIconText(
                iconRes = Res.drawable.ic_note,
                text = note,
                modifier = Modifier.fillMaxWidth()
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                tags.forEach {
                    ProfileCardChip(text = it.name)
                }
            }
        }
    )
}

