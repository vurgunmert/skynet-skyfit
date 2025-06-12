package com.vurgun.skyfit.core.ui.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.BackIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.special.ChatBotButtonComponent
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.DrawableResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_bell
import skyfit.core.ui.generated.resources.ic_chat

object ExpandedTopBar {

    @Composable
    fun DefaultTopBar(
        modifier: Modifier = Modifier,
        editorialTitle: String = "",
        editorialSubtitle: String = "",
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickChatBot: () -> Unit,
        extraEndContent: @Composable (() -> Unit)? = null,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DefaultEditorialGroup(
                title = editorialTitle,
                subtitle = editorialSubtitle,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.weight(1f))

            if (extraEndContent != null) {
                extraEndContent()
                Spacer(Modifier.width(8.dp))
            }

            DefaultNavigationRow(onClickNotifications, onClickConversations, onClickChatBot)
        }
    }

    @Composable
    fun PageTopBar(
        title: String,
        onClickBack: () -> Unit,
        modifier: Modifier = Modifier,
        endContent: @Composable () -> Unit = {}
    ) {
        Box {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BackIcon(onClick = onClickBack)
                Spacer(Modifier.weight(1f))
                endContent()
            }

            SkyText(
                text = title,
                styleType = TextStyleType.BodyLargeSemibold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun DefaultEditorialGroup(
        title: String,
        subtitle: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit = {}
    ) {
        Column(modifier = modifier) {
            SkyText(
                text = title,
                styleType = TextStyleType.BodyMediumSemibold
            )
            SkyText(
                text = subtitle,
                styleType = TextStyleType.BodySmall,
                color = SkyFitColor.text.secondary
            )
        }
    }

    @Composable
    fun DefaultNavigationRow(
        onClickNotifications: () -> Unit = {},
        onClickConversations: () -> Unit = {},
        onClickChatBot: () -> Unit = {}
    ) {
        Row(
            Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            TopBarNavigationItem(
                selected = false,
                highlighted = false,
                icon = Res.drawable.ic_bell,
                onClick = onClickNotifications
            )

            TopBarNavigationItem(
                selected = false,
                highlighted = false,
                icon = Res.drawable.ic_chat,
                onClick = onClickConversations
            )

            ChatBotButtonComponent(
                modifier = Modifier,
                onClick = onClickChatBot
            )
        }
    }

    @Composable
    private fun TopBarNavigationItem(
        selected: Boolean,
        highlighted: Boolean,
        icon: DrawableResource,
        size: Dp = 56.dp,
        onClick: () -> Unit
    ) {
        val background = when {
            selected -> Modifier.background(SkyFitColor.border.secondaryButton, CircleShape)
            else -> Modifier.background(SkyFitColor.background.fillTransparentSecondary, CircleShape)
        }

        Box(
            modifier = Modifier
                .then(background)
                .size(size)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            SkyIcon(
                res = icon,
                size = SkyIconSize.Normal,
                tint = if (selected) SkyIconTint.Inverse else SkyIconTint.Default
            )
            if (highlighted) {
                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(19.dp)
                        .clip(CircleShape)
                        .size(6.dp)
                        .background(SkyFitColor.icon.critical)
                )
            }
        }
    }

}