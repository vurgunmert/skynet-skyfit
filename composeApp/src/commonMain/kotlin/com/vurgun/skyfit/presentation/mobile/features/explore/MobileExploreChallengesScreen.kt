package com.vurgun.skyfit.presentation.mobile.features.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchFilterBarComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileExploreChallengesScreen(rootNavigator: Navigator) {

    var isSearchVisible by remember { mutableStateOf(false) }

    SkyFitScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                SkyFitScreenHeader("Meydan Okumalaar", onClickBack = { rootNavigator.popBackStack() })
                Spacer(Modifier.height(16.dp))
                if (isSearchVisible) {
                    SkyFitSearchTextInputComponent()
                    Spacer(Modifier.height(16.dp))
                }
                SkyFitSearchFilterBarComponent(onEnableSearch = { isSearchVisible = it })
            }
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            MobileExploreUserChallengesComponent()
            Spacer(Modifier.height(12.dp))
            MobileExploreActiveChallengesComponent()
            Spacer(Modifier.height(24.dp))
        }
    }
}


@Composable
private fun MobileExploreUserChallengesComponent() {
    var userChallenges = listOf(1, 2, 3, 4)

    Text("Meydan Okumalarım")
    Spacer(Modifier.height(16.dp))
    LazyRow {
        items(userChallenges) {
            MobileExploreUserChallengeItemComponent()
        }
    }
}

@Composable
private fun MobileExploreUserChallengeItemComponent() {
    Box(Modifier.size(200.dp, 166.dp)) {
        AsyncImage(
            model = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )

        Row(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSemiTransparent, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text("10,000 Steps a Day Challenge")
            Spacer(Modifier.width(4.dp))
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Medal",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text("4.")
        }
    }
}

@Composable
private fun MobileExploreActiveChallengesComponent() {
    var activeChallenges = listOf(1, 2, 3, 4)
    LazyColumn {
        items(activeChallenges) {
            MobileExploreActiveChallengeItemComponent()
        }
    }
}

@Composable
private fun MobileExploreActiveChallengeItemComponent() {
    Box(Modifier.size(382.dp, 282.dp)) {
        AsyncImage(
            model = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )

        Column(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSemiTransparent, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text("7 Günlük Esneklik Geliştirme", style = SkyFitTypography.bodyLargeSemibold)
            Spacer(Modifier.height(4.dp))
            Row {
                Icon(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    contentDescription = "Medal",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    contentDescription = "Medal",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    contentDescription = "Medal",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(Modifier.weight(1f))
                Text("212 Members")
                SkyFitButtonComponent(
                    modifier = Modifier.fillMaxWidth(), text = "Katil",
                    onClick = { },
                    variant = ButtonVariant.Secondary,
                    size = ButtonSize.Medium,
                    state = ButtonState.Rest
                )
            }
        }
    }
}