package com.vurgun.skyfit.feature.connect.social

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.profile.SocialPostCard
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold

@Composable
fun SocialMediaCompact(
    onClickNewPost: () -> Unit,
    viewModel: SocialMediaViewModel
) {
    SocialMediaCompactComponents.Content(onClickNewPost, viewModel)
}

private object SocialMediaCompactComponents {

    @Composable
    fun Content(
        onClickNewPost: () -> Unit,
        viewModel: SocialMediaViewModel
    ) {

        val posts = viewModel.posts.collectAsState().value

        SkyFitMobileScaffold(
            topBar = {
                UserProfileCards(onNewPost = onClickNewPost)
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

}
