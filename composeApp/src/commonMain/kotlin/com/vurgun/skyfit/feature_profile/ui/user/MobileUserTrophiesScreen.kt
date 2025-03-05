package com.vurgun.skyfit.feature_profile.ui.user

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitImageComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.image.CircularImage
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_medal

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
        CircularImage(
            avatarUrl = "https://ik.imagekit.io/skynet2skyfit/Profile%20Photo.png?updatedAt=1739703080462",
            modifier = Modifier.size(64.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text("Maxime", style = SkyFitTypography.bodyMediumSemibold)
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.ic_medal),
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
            MobileUserTrophyItemComponent(modifier = Modifier)
        }
    }
}

@Composable
fun MobileUserTrophyItemComponent(
    url: String = "https://ik.imagekit.io/skynet2skyfit/badge_muscle_master.png?updatedAt=1738863832700",
    modifier: Modifier = Modifier
) {
    SkyFitImageComponent(
        url = url,
        modifier = modifier
    )
}

