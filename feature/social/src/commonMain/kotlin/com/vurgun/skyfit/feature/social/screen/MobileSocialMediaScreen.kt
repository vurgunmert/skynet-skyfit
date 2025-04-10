package com.vurgun.skyfit.feature.social.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.social.components.SocialPostCard
import com.vurgun.skyfit.ui.core.components.special.SkyFitCircularImageComponent
import com.vurgun.skyfit.ui.core.components.button.SkyFitIconButton
import com.vurgun.skyfit.ui.core.components.special.UserCircleAvatarItem
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.logo_skyfit

@Composable
fun MobileSocialMediaScreen(
    goToCreatePost: () -> Unit,
) {

    val viewModel = UserSocialMediaViewModel()
    val posts = viewModel.posts.collectAsState().value

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserSocialMediaScreenUserProfilesComponent(onNewPost = goToCreatePost)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(posts) {
                SocialPostCard(it,
                    onClick = {},
                    onClickComment = {},
                    onClickLike = {},
                    onClickShare = {})
            }
        }
    }
}

@Composable
private fun MobileUserSocialAvatarItemComponent(item: UserCircleAvatarItem, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            SkyFitCircularImageComponent(
                Modifier.size(72.dp),
                item = item,
                onClick = onClick
            )
            SkyFitIconButton(
                painter = painterResource(Res.drawable.logo_skyfit),
                modifier = Modifier.align(Alignment.BottomEnd).size(24.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(item.name.toString(), style = SkyFitTypography.bodyXSmall)
    }
}

@Composable
private fun MobileSocialAvatarItemComponent(item: UserCircleAvatarItem, onClick: () -> Unit) {
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

@Composable
private fun MobileUserSocialMediaScreenUserProfilesComponent(onNewPost: () -> Unit) {
    val userAvatar =
        UserCircleAvatarItem(
            "https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg",
            name = "Maximillian"
        )
    var avatars = listOf(
        UserCircleAvatarItem("https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg", name = "Sude Kale"),
        UserCircleAvatarItem("https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg", name = "Kale Kale"),
        UserCircleAvatarItem("https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg", name = "Sude Kale"),
        UserCircleAvatarItem("https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg", name = "Kale Kale"),
        UserCircleAvatarItem("https://img.freepik.com/free-photo/young-adult-doing-indoor-sport-gym_23-2149205541.jpg", name = "Sude Kale"),
    )

    LazyRow(
        Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(96.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 16.dp)
    ) {
        item {
            MobileUserSocialAvatarItemComponent(userAvatar, onClick = {})
        }
        items(avatars) {
            MobileSocialAvatarItemComponent(it, onClick = {})
        }
    }
}