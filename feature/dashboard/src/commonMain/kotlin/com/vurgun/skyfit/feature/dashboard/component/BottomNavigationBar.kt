package com.vurgun.skyfit.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.special.ChatBotButtonComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.feature.dashboard.explore.ExploreScreen
import com.vurgun.skyfit.feature.dashboard.home.HomeScreen
import com.vurgun.skyfit.feature.persona.profile.ProfileScreen
import com.vurgun.skyfit.feature.persona.social.SocialMediaScreen
import org.jetbrains.compose.resources.DrawableResource
import skyfit.core.ui.generated.resources.*

@Composable
internal fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentScreen: Screen,
    onClickHome: () -> Unit,
    onClickExplore: () -> Unit,
    onClickSocial: () -> Unit,
    onClickProfile: () -> Unit,
    onClickAIBot: () -> Unit
) {
    Box(
        modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 24.dp)
                    .wrapContentWidth()
                    .shadow(8.dp, shape = RoundedCornerShape(50), ambientColor = Color.Black, spotColor = Color.Black)
                    .background(SkyFitColor.specialty.buttonBgRest, RoundedCornerShape(50)),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BlockTouchSpacer(width = 16.dp)

                BottomBarItem(
                    selected = currentScreen is HomeScreen,
                    selectedIcon = Res.drawable.ic_home_fill,
                    unselectedIcon = Res.drawable.ic_home,
                    onClick = onClickHome
                )

                BottomBarItem(
                    selected = currentScreen is ExploreScreen,
                    selectedIcon = Res.drawable.ic_barbell_fill,
                    unselectedIcon = Res.drawable.ic_barbell,
                    onClick = onClickExplore
                )

                BottomBarItem(
                    selected = currentScreen is SocialMediaScreen,
                    selectedIcon = Res.drawable.ic_plus,
                    unselectedIcon = Res.drawable.ic_search,
                    onClick = onClickSocial
                )

                BottomBarItem(
                    selected = currentScreen is ProfileScreen,
                    selectedIcon = Res.drawable.ic_profile_fill,
                    unselectedIcon = Res.drawable.ic_profile,
                    onClick = onClickProfile
                )

                BlockTouchSpacer(width = 16.dp)
            }
        }

        ChatBotButtonComponent(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 112.dp, end = 20.dp),
            onClick = onClickAIBot
        )
    }
}

@Composable
private fun BottomBarItem(
    selected: Boolean,
    selectedIcon: DrawableResource,
    unselectedIcon: DrawableResource,
    onClick: () -> Unit
) {
    val iconRes = if (selected) selectedIcon else unselectedIcon

    Box(
        modifier = Modifier
            .size(width = 52.dp, height = 68.dp) // 20 icon + 16 side spacing on both
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        SkyIcon(
            res = iconRes,
            size = SkyIconSize.Normal,
            tint = SkyIconTint.Inverse
        )
    }
}

@Composable
private fun BlockTouchSpacer(width: Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .height(68.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                // no-op, blocks click-through
            }
    )
}
