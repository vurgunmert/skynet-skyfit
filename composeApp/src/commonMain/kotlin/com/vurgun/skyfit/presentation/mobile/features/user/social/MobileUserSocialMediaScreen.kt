package com.vurgun.skyfit.presentation.mobile.features.user.social

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitAvatarCircle
import com.vurgun.skyfit.presentation.shared.components.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.components.SkyFitPostCardItem
import com.vurgun.skyfit.presentation.shared.components.SkyFitPostCardItemComponent
import com.vurgun.skyfit.presentation.shared.components.UserCircleAvatarItem
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserSocialMediaScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserSocialMediaScreenUserProfilesComponent(onNewPost = {
                navigator.jumpAndStay(SkyFitNavigationRoute.UserSocialMediaPostAdd)
            })
        }
    ) {
        val posts = List(6) { index ->
            SkyFitPostCardItem(
                postId = "post_${index + 1}",
                username = listOf("JohnDoe", "FitnessQueen", "MikeTrainer", "EmmaRunner", "DavidGym", "SophiaYoga").random(),
                socialLink = listOf("https://instagram.com/user", "https://twitter.com/user", "https://linkedin.com/user", null).random(),
                timeAgo = listOf("5 min ago", "2 hours ago", "1 day ago", "3 days ago", "1 week ago").random(),
                profileImageUrl = listOf(
                    "https://example.com/profile1.png",
                    "https://example.com/profile2.png",
                    "https://example.com/profile3.png",
                    "https://example.com/profile4.png",
                    null
                ).random(),
                content = listOf(
                    "Just finished an amazing workout! 💪",
                    "Morning yoga session done! 🧘‍♀️",
                    "Any tips for increasing stamina? 🏃‍♂️",
                    "Trying out a new HIIT routine. 🔥",
                    "Recovery day with some light stretching.",
                    "Nutrition is key! What’s your go-to meal?"
                ).random(),
                imageUrl = listOf(
                    "https://example.com/post1.jpg",
                    "https://example.com/post2.jpg",
                    "https://example.com/post3.jpg",
                    null
                ).random(),
                favoriteCount = (0..500).random(),
                commentCount = (0..200).random(),
                shareCount = (0..100).random(),
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(posts) {
                SkyFitPostCardItemComponent(it,
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
            SkyFitAvatarCircle(
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
        SkyFitAvatarCircle(
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
        Modifier.fillMaxWidth().height(96.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            MobileUserSocialAvatarItemComponent(userAvatar, onClick = {})
        }
        items(avatars) {
            MobileSocialAvatarItemComponent(it, onClick = {})
        }
    }
}