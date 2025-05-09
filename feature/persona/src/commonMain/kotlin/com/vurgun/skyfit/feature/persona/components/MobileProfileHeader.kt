package com.vurgun.skyfit.feature.persona.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_dna
import skyfit.core.ui.generated.resources.ic_height
import skyfit.core.ui.generated.resources.ic_overweight

//@Composable
//fun MobileProfileHeader(
//    viewData: UserProfileHeaderViewData
//) {
//    BoxWithConstraints {
//        val width = maxWidth
//        val imageHeight = width * 9 / 16
//        val contentTopPadding = imageHeight * 3 / 10
//
//        if (!viewData.showInfoMini) {
//            MobileProfileBackgroundImage(viewData.profileImageUrl, imageHeight)
//        }
//
//        Column(
//            Modifier
//                .padding(top = if (viewData.showInfoMini) 16.dp else contentTopPadding)
//                .fillMaxWidth()
//        ) {
//            if (viewData.showInfoMini) {
//                MobileProfileHeaderMini(viewData)
//            } else {
//                MobileProfileHeaderMini(viewData)
//            }
//        }
//    }
//}

@Composable
fun MobileProfileBackgroundImage(imageUrl: String?, height: Dp) {
    NetworkImage(
        imageUrl = imageUrl,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    )
}

@Composable
fun MobileProfileBackgroundImage(
    imageUrl: String?,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    NetworkImage(
        imageUrl = imageUrl,
        modifier = modifier
    )
}


@Composable
fun UserProfileCardPreferenceRow(
    height: String,
    weight: String,
    bodyType: String,
    modifier: Modifier
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = Res.drawable.ic_height,
            title = height,
            subtitle = "Boy (cm)"
        )
        VerticalDivider(Modifier.padding(horizontal = 12.dp))
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = Res.drawable.ic_dna,
            title = weight,
            subtitle = "Kilo (kg)"
        )
        VerticalDivider(Modifier.padding(horizontal = 12.dp))
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = Res.drawable.ic_overweight,
            title = bodyType,
            subtitle = "Vücut Tipi"
        )
    }
}

@Composable
fun TrainerProfileCardPreferenceRow(
    followerCount: String,
    lessonCount: String,
    postCount: String,
    modifier: Modifier
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = null,
            title = followerCount,
            subtitle = "Takipçi"
        )
        VerticalDivider(Modifier.padding(horizontal = 12.dp))
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = null,
            title = lessonCount,
            subtitle = "Özel Dersler"
        )
        VerticalDivider(Modifier.padding(horizontal = 12.dp))
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = null,
            title = postCount,
            subtitle = "Paylaşımlar"
        )
    }
}

@Composable
fun UserProfileCardPreferenceItem(
    modifier: Modifier = Modifier,
    iconRes: DrawableResource?,
    title: String,
    subtitle: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            iconRes?.let {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = SkyFitColor.icon.default
                )
                Spacer(Modifier.width(2.dp))
            }

            Text(text = title, style = SkyFitTypography.bodyMediumSemibold, color = SkyFitColor.text.default)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = SkyFitTypography.bodySmall,
            color = SkyFitColor.text.secondary
        )
    }
}