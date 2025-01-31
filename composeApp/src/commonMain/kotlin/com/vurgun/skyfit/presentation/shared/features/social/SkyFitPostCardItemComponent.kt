package com.vurgun.skyfit.presentation.shared.features.social

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItemComponent.InteractionRow
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItemComponent.PostContent
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItemComponent.PostImage
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItemComponent.ProfileImage
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItemComponent.UserInfoSection
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

data class SkyFitPostCardItem(
    val postId: String,
    val username: String,
    val socialLink: String?,
    val timeAgo: String?,
    val profileImageUrl: String?,
    val content: String,
    val imageUrl: String?,
    val favoriteCount: Int,
    val commentCount: Int,
    val shareCount: Int,
)

@Composable
fun SkyFitPostCardItemComponent(
    data: SkyFitPostCardItem,
    onClickShare: () -> Unit,
    onClickComment: () -> Unit,
    onClickLike: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.Top) {

            ProfileImage(data.profileImageUrl)
            Spacer(Modifier.width(8.dp))
            Column {
                UserInfoSection(
                    username = data.username,
                    socialLink = data.socialLink,
                    timeAgo = data.timeAgo
                )

                data.imageUrl?.let { imageUrl ->
                    Spacer(modifier = Modifier.height(8.dp))
                    PostImage(imageUrl = imageUrl)
                }

                Spacer(modifier = Modifier.height(8.dp))
                PostContent(content = data.content)

                Spacer(modifier = Modifier.height(8.dp))
                InteractionRow(
                    likeCount = data.favoriteCount,
                    commentCount = data.commentCount,
                    shareCount = data.shareCount,
                    onClickComment = onClickComment,
                    onClickLike = onClickLike,
                    onClickShare = onClickShare
                )
            }
        }
    }
}


private object SkyFitPostCardItemComponent {
    @Composable
    fun ProfileImage(profileImageUrl: String?) {
        AsyncImage(
            model = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.onSurface.copy(alpha = 0.2f))
        )
    }

    @Composable
    fun UserInfoSection(username: String, socialLink: String?, timeAgo: String?) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = username,
                color = SkyFitColor.text.default,
                style = SkyFitTypography.bodyLargeMedium
            )

            socialLink?.let {
                Spacer(Modifier.width(8.dp))
                Text(
                    text = it,
                    color = SkyFitColor.text.secondary,
                    style = SkyFitTypography.bodySmall
                )
            }

            timeAgo?.let {
                Spacer(Modifier.width(2.dp))
                Text(
                    text = ". $it",
                    color = SkyFitColor.text.secondary,
                    style = SkyFitTypography.bodySmall
                )
            }
        }
    }

    @Composable
    fun PostImage(imageUrl: String) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp)),
        )
    }

    @Composable
    fun PostContent(content: String) {
        Text(
            text = content,
            color = SkyFitColor.text.default,
            style = SkyFitTypography.bodySmall
        )
    }

    @Composable
    fun InteractionRow(
        likeCount: Int,
        commentCount: Int,
        shareCount: Int,
        onClickShare: () -> Unit,
        onClickComment: () -> Unit,
        onClickLike: () -> Unit
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            InteractionItem(count = commentCount, onClick = onClickComment)
            InteractionItem(count = shareCount, onClick = onClickShare)
            InteractionItem(count = likeCount, onClick = onClickLike)
        }
    }

    @Composable
    fun InteractionItem(count: Int, onClick: () -> Unit) {
        Row(
            Modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(Res.drawable.logo_skyfit),
                contentDescription = null,
                tint = SkyFitColor.text.secondary
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "$count",
                color = SkyFitColor.text.secondary,
                style = SkyFitTypography.bodyXSmall
            )
        }
    }
}

