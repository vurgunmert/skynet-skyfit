package com.vurgun.skyfit.core.ui.components.topbar

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.account.model.TrainerAccount
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.global.model.CharacterType
import com.vurgun.skyfit.core.ui.components.icon.BackIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.special.CharacterImage
import com.vurgun.skyfit.core.ui.components.special.ChatBotButtonComponent
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.DrawableResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_bell
import skyfit.core.ui.generated.resources.ic_chat

object ExpandedTopBar {

    // Page: Back + Title
    @Composable
    fun TopBarWithBackAndTitle(
        title: String,
        onClickBack: () -> Unit,
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickChatBot: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Box(modifier = modifier) {
            BackIcon(
                onClick = onClickBack,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            SkyText(
                text = title,
                styleType = TextStyleType.BodyLargeSemibold,
                modifier = Modifier.align(Alignment.Center)
            )

            TopbarNavigationGroup(
                onClickNotifications = onClickNotifications,
                onClickConversations = onClickConversations,
                onClickChatBot = onClickChatBot,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }

    // Default: Editorial + Actions
    @Composable
    fun TopBarWithEditorialAndNavigation(
        name: String,
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickChatBot: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TopBarWelcomeEditorialGroup(
                name = name,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.weight(1f))

            TopbarNavigationGroup(
                onClickNotifications = onClickNotifications,
                onClickConversations = onClickConversations,
                onClickChatBot = onClickChatBot
            )
        }
    }

    // Default: Profile + Actions
    @Composable
    fun TopBarWithAccountAndNavigation(
        account: Account,
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickChatBot: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        when (account) {
            is FacilityAccount -> TopBarWithAccountAndNavigation(
                account,
                onClickNotifications,
                onClickConversations,
                onClickChatBot,
                modifier
            )

            is TrainerAccount -> TopBarWithAccountAndNavigation(
                account,
                onClickNotifications,
                onClickConversations,
                onClickChatBot,
                modifier
            )

            is UserAccount -> TopBarWithAccountAndNavigation(
                account,
                onClickNotifications,
                onClickConversations,
                onClickChatBot,
                modifier
            )
        }
    }

    @Composable
    fun TopBarWithAccountAndNavigation(
        account: UserAccount,
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickChatBot: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TopBarWelcomeEditorialGroup(
                name = account.firstName
            )

            Spacer(Modifier.weight(1f))

            TopBarTrophyGroup(
                characterType = account.characterType
            )

            Spacer(Modifier.width(16.dp))

            TopbarNavigationGroup(
                onClickNotifications = onClickNotifications,
                onClickConversations = onClickConversations,
                onClickChatBot = onClickChatBot,
                modifier = Modifier.wrapContentWidth()
            )
        }
    }

    @Composable
    fun TopBarWithAccountAndNavigation(
        account: TrainerAccount,
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickChatBot: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TopBarWelcomeEditorialGroup(
                name = account.firstName,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.weight(1f))

            TopBarTrophyGroup(
                characterType = account.characterType,
                modifier = Modifier.wrapContentWidth()
            )

            Spacer(Modifier.width(16.dp))

            TopbarNavigationGroup(
                onClickNotifications = onClickNotifications,
                onClickConversations = onClickConversations,
                onClickChatBot = onClickChatBot
            )
        }
    }

    @Composable
    fun TopBarWithAccountAndNavigation(
        account: FacilityAccount,
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickChatBot: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(88.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TopBarWelcomeEditorialGroup(
                name = account.gymName,
                modifier = Modifier.wrapContentWidth()
            )
            Spacer(Modifier.weight(1f))
            TopbarNavigationGroup(
                onClickNotifications = onClickNotifications,
                onClickConversations = onClickConversations,
                onClickChatBot = onClickChatBot
            )
        }
    }

    @Composable
    private fun TopBarWelcomeEditorialGroup(
        name: String,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            SkyText(
                text = "Merhaba $name!",
                styleType = TextStyleType.BodyMediumSemibold
            )
            SkyText(
                text = "Bugünün meydan okumalarına hazır mısın?",
                styleType = TextStyleType.BodySmall,
                color = SkyFitColor.text.secondary
            )
        }
    }

    @Composable
    private fun TopBarTrophyGroup(
        characterType: CharacterType,
        modifier: Modifier = Modifier,
    ) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            CharacterImage(characterType, Modifier.size(48.dp))
            Spacer(Modifier.width(16.dp))
            Box(Modifier.size(24.dp, 32.dp).background(Color.Red))
            Spacer(Modifier.width(16.dp))
            Box(Modifier.size(24.dp, 32.dp).background(Color.Red))
            Spacer(Modifier.width(16.dp))
            Box(Modifier.size(24.dp, 32.dp).background(Color.Red))
        }
    }

    @Composable
    private fun TopbarNavigationGroup(
        notificationsEnabled: Boolean = false,
        conversationsEnabled: Boolean = false,
        chatbotEnabled: Boolean = true,
        onClickNotifications: () -> Unit = {},
        onClickConversations: () -> Unit = {},
        onClickChatBot: () -> Unit = {},
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(50))
                .wrapContentWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            FeatureVisible(notificationsEnabled) {
                TopBarNavigationItem(
                    selected = false,
                    highlighted = false,
                    icon = Res.drawable.ic_bell,
                    onClick = onClickNotifications
                )
            }

            FeatureVisible(conversationsEnabled) {
                TopBarNavigationItem(
                    selected = false,
                    highlighted = false,
                    icon = Res.drawable.ic_chat,
                    onClick = onClickConversations
                )
            }

            FeatureVisible(chatbotEnabled) {
                ChatBotButtonComponent(
                    modifier = Modifier,
                    onClick = onClickChatBot
                )
            }
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
        val backgroundColor = if (selected) {
            SkyFitColor.border.secondaryButton
        } else {
            SkyFitColor.background.fillTransparentSecondary
        }
        val interactionSource = remember { MutableInteractionSource() }
        val indication = rememberUpdatedState(LocalIndication.current)

        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(backgroundColor)
                .clickable(
                    interactionSource = interactionSource,
                    indication = indication.value,
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
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-4).dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(SkyFitColor.icon.critical)
                )
            }
        }
    }

}

