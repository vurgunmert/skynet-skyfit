package com.vurgun.skyfit.feature_settings.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.core.domain.models.FitnessTagType
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.designsystem.components.button.LargePrimaryIconButton
import com.vurgun.skyfit.designsystem.components.text.CardFieldIconText
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_at_symbol
import skyfit.composeapp.generated.resources.ic_image
import skyfit.composeapp.generated.resources.ic_info_circle
import skyfit.composeapp.generated.resources.ic_location_pin
import skyfit.composeapp.generated.resources.ic_note
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.ic_profile_fill

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
        AsyncImage(
            model = url,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
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
        AsyncImage(
            model = url,
            contentDescription = "Foreground Image",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.surfaceSecondary),
            contentScale = ContentScale.Crop
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
                modifier = Modifier.fillMaxWidth().height(120.dp),
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
    social: String,
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
                CardFieldIconText(Res.drawable.ic_at_symbol, social, Modifier.weight(1f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardFieldIconText(Res.drawable.ic_info_circle, "127 cm", Modifier.weight(1f))
                CardFieldIconText(Res.drawable.ic_info_circle, "63 kg", Modifier.weight(1f))
                CardFieldIconText(Res.drawable.ic_info_circle, "Ecto", Modifier.weight(1f))
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
    social: String,
    note: String,
    tags: List<FitnessTagType>,
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
                CardFieldIconText(Res.drawable.ic_profile_fill, "Maxine", Modifier.weight(1f))
                CardFieldIconText(Res.drawable.ic_at_symbol, "maxjacobson", Modifier.weight(1f))
            }

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
                    ProfileCardChip(text = it.label)
                }
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacilityAccountSettingsProfileCard(
    backgroundImageUrl: String? = null,
    name: String = "Ironstudio",
    address: String = "1425 Maplewood Avenue, Apt 3B, Brookfield, IL 60513, USA",
    note: String = "Whether you're a beginner or looking to advance, My balanced approach will guide you every step of the way. \uD83C\uDFC3\uD83C\uDFFD\u200D♂\uFE0F\n@ironstudio",
    tags: List<FitnessTagType> = FitnessTagType.getAllTags().take(5).shuffled(),
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
                    ProfileCardChip(text = it.label)
                }
            }
        }
    )
}

