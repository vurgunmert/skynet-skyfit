package com.vurgun.skyfit.feature.connect.social

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.SkyFitIconButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitCircularImageComponent
import com.vurgun.skyfit.core.ui.components.special.UserCircleAvatarItem
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_app_logo

@Composable
fun UserProfileCards(
    onNewPost: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val userAvatar =
        UserCircleAvatarItem(
            "https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg",
            name = "Maximillian"
        )
    var avatars = listOf(
        UserCircleAvatarItem(
            "https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg",
            name = "Sude Kale"
        ),
        UserCircleAvatarItem(
            "https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg",
            name = "Kale Kale"
        ),
        UserCircleAvatarItem(
            "https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg",
            name = "Sude Kale"
        ),
        UserCircleAvatarItem(
            "https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg",
            name = "Kale Kale"
        ),
        UserCircleAvatarItem(
            "https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg",
            name = "Sude Kale"
        ),
    )

    LazyRow(
        modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(96.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 16.dp)
    ) {
        item {
            MobileUserSocialAvatarItemComponent(userAvatar, onClick = onNewPost)
        }
        items(avatars) {
            MobileSocialAvatarItemComponent(it, onClick = onNewPost)
        }
    }
}


@Composable
fun MobileUserSocialAvatarItemComponent(item: UserCircleAvatarItem, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            SkyFitCircularImageComponent(
                Modifier.size(72.dp),
                item = item,
                onClick = onClick
            )
            SkyFitIconButton(
                painter = painterResource(Res.drawable.ic_app_logo),
                modifier = Modifier.align(Alignment.BottomEnd).size(24.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(item.name.toString(), style = SkyFitTypography.bodyXSmall)
    }
}

@Composable
fun MobileSocialAvatarItemComponent(item: UserCircleAvatarItem, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SkyFitCircularImageComponent(
            Modifier.size(72.dp),
            item = item,
            onClick = onClick
        )
        Spacer(Modifier.height(8.dp))
        Text(item.name.toString(), style = SkyFitTypography.bodyXSmall)
    }
}
