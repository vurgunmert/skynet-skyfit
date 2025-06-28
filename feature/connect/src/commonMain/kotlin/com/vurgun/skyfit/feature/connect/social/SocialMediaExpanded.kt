package com.vurgun.skyfit.feature.connect.social

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.components.profile.SocialPostCard
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_plus

@Composable
fun SocialMediaExpanded(
    onClickNewPost: () -> Unit,
    viewModel: UserSocialMediaFeedViewModel
) {
    SocialMediaExpandedComponents.Content(onClickNewPost = onClickNewPost, viewModel = viewModel)
}


private object SocialMediaExpandedComponents {
    @Composable
    fun Content(
        onClickNewPost: () -> Unit,
        viewModel: UserSocialMediaFeedViewModel
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

                UserProfileCards(
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
}