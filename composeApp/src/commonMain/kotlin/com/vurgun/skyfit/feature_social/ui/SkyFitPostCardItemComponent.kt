package com.vurgun.skyfit.feature_social.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.feature_social.ui.SkyFitPostCardItemComponent.InteractionRow
import com.vurgun.skyfit.feature_social.ui.SkyFitPostCardItemComponent.ProfileImage
import com.vurgun.skyfit.feature_social.ui.SkyFitPostCardItemComponent.SkyFitPostItemUserInfoRow
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

data class PostViewData(
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
    data: PostViewData,
    onClickShare: () -> Unit,
    onClickComment: () -> Unit,
    onClickLike: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        ProfileImage(data.profileImageUrl)

        Spacer(Modifier.width(8.dp))

        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SkyFitPostItemUserInfoRow(
                username = data.username,
                socialLink = data.socialLink,
                timeAgo = data.timeAgo
            )

            data.imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(273.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }

            Text(
                text = data.content,
                color = SkyFitColor.text.default,
                style = SkyFitTypography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )

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


private object SkyFitPostCardItemComponent {
    @Composable
    fun ProfileImage(profileImageUrl: String?) {
        AsyncImage(
            model = profileImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
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
            InteractionItem(count = commentCount, onClick = onClickComment)
            InteractionItem(count = shareCount, onClick = onClickShare)
            InteractionItem(count = likeCount, onClick = onClickLike)
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = SkyFitColor.text.secondary,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    @Composable
    fun InteractionItem(count: Int, onClick: () -> Unit) {
        Row(
            Modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painterResource(Res.drawable.logo_skyfit),
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

