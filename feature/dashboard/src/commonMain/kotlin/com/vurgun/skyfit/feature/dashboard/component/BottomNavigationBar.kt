package com.vurgun.skyfit.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.vurgun.skyfit.core.ui.components.special.ChatBotButtonComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.feature.home.screen.HomeScreen
import com.vurgun.skyfit.feature.profile.ProfileScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_home
import skyfit.core.ui.generated.resources.ic_home_fill
import skyfit.core.ui.generated.resources.ic_profile
import skyfit.core.ui.generated.resources.ic_profile_fill

@Composable
internal fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentScreen: Screen,
    onClickHome: () -> Unit,
    onClickProfile: () -> Unit,
    onClickAppAction: () -> Unit
) {
    Box(
        modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
//        Box(
//            modifier = Modifier
//                .size(332.dp, 68.dp)
//                .offset(y = (-10).dp)
//                .shadow(4.dp, shape = RoundedCornerShape(50), ambientColor = Color.Black, spotColor = Color.Black)
//        )

        Box(
            modifier = Modifier
                .shadow(8.dp, shape = RoundedCornerShape(50), ambientColor = Color.Black, spotColor = Color.Black)
                .background(SkyFitColor.specialty.buttonBgRest, RoundedCornerShape(50))
                .padding(horizontal = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BottomBarItem(
                    selected = currentScreen is HomeScreen,
                    selectedIcon = Res.drawable.ic_home_fill,
                    unselectedIcon = Res.drawable.ic_home,
                    onClick = onClickHome
                )

                BottomBarItem(
                    selected = currentScreen is ProfileScreen,
                    selectedIcon = Res.drawable.ic_profile_fill,
                    unselectedIcon = Res.drawable.ic_profile,
                    onClick = onClickProfile
                )
            }
        }

        ChatBotButtonComponent(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 112.dp, end = 20.dp),
            onClick = onClickAppAction
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
        Modifier
            .width(32.dp)
            .height(68.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = SkyFitColor.text.inverse,
            modifier = Modifier.size(24.dp)
        )
    }
}