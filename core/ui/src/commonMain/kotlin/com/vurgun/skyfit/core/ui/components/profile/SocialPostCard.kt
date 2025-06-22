package com.vurgun.skyfit.core.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.profile.SkyFitPostCardItemComponent.InteractionRow
import com.vurgun.skyfit.core.ui.components.profile.SkyFitPostCardItemComponent.ProfileImage
import com.vurgun.skyfit.core.ui.components.profile.SkyFitPostCardItemComponent.SkyFitPostItemUserInfoRow
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.*

@Composable
fun SocialPostCard(
    post: SocialPostItemViewData,
    onClickShare: () -> Unit,
    onClickComment: () -> Unit,
    onClickLike: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(SkyFitColor.background.default)
        .fillMaxWidth(),
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        ProfileImage(post.creatorImageUrl)

        Spacer(Modifier.width(8.dp))

        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SkyFitPostItemUserInfoRow(
                username = post.creatorName,
                socialLink = post.creatorUsername,
                timeAgo = post.timeAgo
            )

            post.imageUrl?.let {
                NetworkImage(
                    imageUrl = it,
                    modifier = Modifier
                        .height(273.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }

            Text(
                text = post.content,
                color = SkyFitColor.text.default,
                style = SkyFitTypography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )

            InteractionRow(
                likeCount = post.likeCount,
                commentCount = post.commentCount,
                shareCount = post.shareCount,
                onClickComment = onClickComment,
                onClickLike = onClickLike,
                onClickShare = onClickShare
            )
        }
    }
}


private object SkyFitPostCardItemComponent {
    @Composable
    fun ProfileImage(profileImageUrl: String?) {
        NetworkImage(
            imageUrl = profileImageUrl,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(SkyFitColor.border.default)
        )
    }

    @Composable
    fun SkyFitPostItemUserInfoRow(username: String, socialLink: String?, timeAgo: String?) {
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
                    style = SkyFitTypography.bodySmall,
                    maxLines = 1
                )
            }

            timeAgo?.let {
                Spacer(Modifier.width(2.dp))
                Text(
                    text = ". $it",
                    color = SkyFitColor.text.secondary,
                    style = SkyFitTypography.bodySmall,
                    maxLines = 1
                )
            }
        }
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InteractionItem(iconRes = Res.drawable.ic_chat, count = commentCount, onClick = onClickComment)
            InteractionItem(iconRes = Res.drawable.ic_arrow_replay, count = shareCount, onClick = onClickShare)
            InteractionItem(iconRes = Res.drawable.ic_heart, count = likeCount, onClick = onClickLike)
            Icon(
                painterResource(Res.drawable.ic_share),
                contentDescription = null,
                tint = SkyFitColor.text.secondary,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    @Composable
    fun InteractionItem(iconRes: DrawableResource, count: Int, onClick: () -> Unit) {
        Row(
            Modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painterResource(iconRes),
                contentDescription = null,
                tint = SkyFitColor.text.secondary,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "$count",
                color = SkyFitColor.text.secondary,
                style = SkyFitTypography.bodyXSmall
            )
        }
    }
}

