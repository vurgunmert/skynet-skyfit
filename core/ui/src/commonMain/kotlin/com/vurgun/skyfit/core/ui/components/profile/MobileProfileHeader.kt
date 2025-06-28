package com.vurgun.skyfit.core.ui.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_dna
import fiwe.core.ui.generated.resources.ic_height
import fiwe.core.ui.generated.resources.ic_overweight

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




//@Composable
//fun TrainerProfileCardPreferenceRow(
//    followerCount: String,
//    lessonCount: String,
//    postCount: String,
//    modifier: Modifier
//) {
//    Row(
//        modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.Center
//    ) {
//        ProfileHeaderPersonalDataItem(
//            modifier = Modifier.weight(1f),
//            iconRes = null,
//            title = followerCount,
//            subtitle = "Takipçi"
//        )
//        VerticalDivider(Modifier.padding(horizontal = 12.dp))
//        ProfileHeaderPersonalDataItem(
//            modifier = Modifier.weight(1f),
//            iconRes = null,
//            title = lessonCount,
//            subtitle = "Özel Dersler"
//        )
//        VerticalDivider(Modifier.padding(horizontal = 12.dp))
//        ProfileHeaderPersonalDataItem(
//            modifier = Modifier.weight(1f),
//            iconRes = null,
//            title = postCount,
//            subtitle = "Paylaşımlar"
//        )
//    }
//}

