package com.vurgun.skyfit.feature.persona.social

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyFitIconButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitCircularImageComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.special.UserCircleAvatarItem
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.persona.components.SocialPostCard
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_plus
import skyfit.core.ui.generated.resources.logo_skyfit

class SocialMediaScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SocialMediaViewModel>()
        val windowSize = LocalWindowSize.current

        if (windowSize == WindowSize.EXPANDED) {
            MobileSocialMediaScreenExpanded(
                onClickNewPost = { navigator.push(SharedScreen.CreatePost) },
                viewModel = viewModel
            )
        } else {
            MobileSocialMediaScreenCompact(
                onClickNewPost = { navigator.push(SharedScreen.CreatePost) },
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun MobileSocialMediaScreenExpanded(
    onClickNewPost: () -> Unit,
    viewModel: SocialMediaViewModel
) {

    val posts = viewModel.posts.collectAsState().value

    Box(
        modifier = Modifier
            .padding(end = 16.dp, bottom = 24.dp)
            .clip(RoundedCornerShape(20.dp))
            .fillMaxSize()
            .background(SkyFitColor.background.surfaceTertiary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.widthIn(max = 680.dp).wrapContentHeight(),
            horizontalAlignment = Alignment.End,
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SkyButton(
                label = "Gonderi Olustur",
                leftIcon = painterResource(Res.drawable.ic_plus),
                onClick = onClickNewPost
            )

            MobileUserSocialMediaScreenUserProfilesComponent(
                onNewPost = onClickNewPost,
                modifier = Modifier.fillMaxWidth().height(92.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(posts) {
                    SocialPostCard(
                        it,
                        onClick = {},
                        onClickComment = {},
                        onClickLike = {},
                        onClickShare = {})
                }
            }
        }
    }
}

@Composable
private fun MobileSocialMediaScreenCompact(
    onClickNewPost: () -> Unit,
    viewModel: SocialMediaViewModel
) {

    val posts = viewModel.posts.collectAsState().value

    SkyFitMobileScaffold(
        topBar = {
            MobileUserSocialMediaScreenUserProfilesComponent(onNewPost = onClickNewPost)
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
                SocialPostCard(
                    it,
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
private fun MobileUserSocialMediaScreenUserProfilesComponent(
    onNewPost: () -> Unit,
    modifier: Modifier = Modifier,
) {
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