package com.vurgun.skyfit.feature_profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.feature_profile.ui.user.TopBarGroupViewData
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_dna
import skyfit.composeapp.generated.resources.ic_height
import skyfit.composeapp.generated.resources.ic_overweight

@Composable
fun MobileProfileHeader(
    viewData: TopBarGroupViewData
) {
    BoxWithConstraints {
        val width = maxWidth
        val imageHeight = width * 9 / 16
        val contentTopPadding = imageHeight * 3 / 10

        if (!viewData.showInfoMini) {
            MobileUserProfileBackgroundImageComponent(imageHeight)
        }

        Column(
            Modifier
                .padding(top = if (viewData.showInfoMini) 16.dp else contentTopPadding)
                .fillMaxWidth()
        ) {
            if (viewData.showInfoMini) {
                MobileProfileHeaderMini(viewData)
            } else {
                MobileProfileHeaderMini(viewData)
            }
        }
    }
}

@Composable
fun MobileUserProfileBackgroundImageComponent(height: Dp) {
    AsyncImage(
        model = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    )
}


@Composable
fun UserProfileCardPreferenceRow(modifier: Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = Res.drawable.ic_height,
            title = "175",
            subtitle = "Boy (cm)"
        )
        VerticalDivider(Modifier.padding(horizontal = 12.dp))
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = Res.drawable.ic_dna,
            title = "63",
            subtitle = "Kilo (kg)"
        )
        VerticalDivider(Modifier.padding(horizontal = 12.dp))
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = Res.drawable.ic_overweight,
            title = "Ecto",
            subtitle = "Vücut Tipi"
        )
    }
}

@Composable
fun UserProfileCardPreferenceItem(
    modifier: Modifier = Modifier,
    iconRes: DrawableResource,
    title: String,
    subtitle: String
) {
    Column(modifier = modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = SkyFitColor.icon.default
            )
            Spacer(Modifier.width(2.dp))
            Text(text = title, style = SkyFitTypography.bodyMediumSemibold, color = SkyFitColor.text.default)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = subtitle, style = SkyFitTypography.bodySmall, color = SkyFitColor.text.secondary)
    }
}