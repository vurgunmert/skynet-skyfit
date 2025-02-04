package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.profile.AvatarImage
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserTrophiesScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Ödüller", onClickBack = { navigator.popBackStack() })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            MobileUserTrophiesUserInfoComponent()

            Spacer(Modifier.height(16.dp))

            MobileUserTrophiesComponent()
        }
    }
}


@Composable
private fun MobileUserTrophiesUserInfoComponent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AvatarImage(
            avatarUrl = "",
            modifier = Modifier.size(64.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text("Maxime", style = SkyFitTypography.bodyMediumSemibold)
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                modifier = Modifier.size(24.dp),
                contentDescription = ""
            )
            Text("4/17", style = SkyFitTypography.bodyMediumMedium)
        }
    }
}

@Composable
private fun MobileUserTrophiesComponent() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // 3 columns to align like the screenshot
        modifier = Modifier
            .size(320.dp, 786.dp)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(15) { // Replace 15 with the actual count of trophies
            MobileUserTrophyItemComponent()
        }
    }
}

@Composable
private fun MobileUserTrophyItemComponent() {
    Box(
        modifier = Modifier
            .size(72.dp, 98.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SkyFitColor.border.secondaryButton),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Star, // Replace with actual trophy icon
            contentDescription = "Trophy",
            tint = Color.White
        )
    }
}

