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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.account.model.TrainerAccount
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.global.model.CharacterType
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.ui.components.icon.BackIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.special.CharacterImage
import com.vurgun.skyfit.core.ui.components.special.ChatBotButtonComponent
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
    fun TopBarWithEditorialAndNavigations(
        name: String,
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

            TopbarNavigationGroup()
        }
    }

    // Default: Profile + Actions
    @Composable
    fun TopBarWithAccountAndNavigations(
        account: UserAccount,
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

            TopbarNavigationGroup()
        }
    }

    @Composable
    fun TopBarWithAccountAndNavigations(
        account: TrainerAccount,
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

            TopbarNavigationGroup()
        }
    }

    @Composable
    fun TopBarWithAccountAndNavigations(
        account: FacilityAccount,
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
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.weight(1f))

            TopbarNavigationGroup()
        }
    }

    @Composable
    fun TopBarWithProfileAndNavigations(
        profile: TrainerProfile,
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
                name = profile.firstName,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.weight(1f))

            TopbarNavigationGroup()
        }
    }

    @Composable
    fun TopBarWithProfileAndNavigations(
        profile: FacilityProfile,
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
                name = profile.facilityName,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.weight(1f))

            TopbarNavigationGroup()
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
        Row(modifier = modifier) {
            CharacterImage(characterType, Modifier.size(48.dp))
            Box(Modifier.size(48.dp).background(Color.Black))
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
        onClickNotifications: () -> Unit = {},
        onClickConversations: () -> Unit = {},
        onClickChatBot: () -> Unit = {},
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier,
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

