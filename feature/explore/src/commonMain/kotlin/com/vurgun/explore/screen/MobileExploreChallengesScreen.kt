package com.vurgun.explore.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.image.CircularImage
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchFilterBarComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_app_logo

@Composable
fun MobileExploreChallengesScreen(
    goToBack: () -> Unit,
    goToChallengeDetail: () -> Unit,
) {

    var isSearchVisible by remember { mutableStateOf(false) }

    SkyFitScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                CompactTopBar("Meydan Okumalar", onClickBack = goToBack)

                Spacer(Modifier.height(16.dp))
                if (isSearchVisible) {
                    SkyFitSearchTextInputComponent()
                    Spacer(Modifier.height(16.dp))
                }
                SkyFitSearchFilterBarComponent(onEnableSearch = { isSearchVisible = it })
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MobileExploreUserChallengesComponent(onClick = goToChallengeDetail)
            Spacer(Modifier.height(12.dp))
            MobileExploreActiveChallengesComponent(onClick = goToChallengeDetail)
            Spacer(Modifier.height(24.dp))
        }
    }
}


@Composable
private fun MobileExploreUserChallengesComponent(onClick: () -> Unit) {
    var userChallenges = listOf(1, 2, 3, 4)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Meydan Okumalarım",
            modifier = Modifier.padding(start = 24.dp),
            style = SkyFitTypography.bodyLargeSemibold
        )

        LazyRow(
            Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(userChallenges) {
                MobileExploreUserChallengeItemComponent(onClick)
            }
        }
    }
}

@Composable
private fun MobileExploreUserChallengeItemComponent(onClick: () -> Unit) {
    Box(Modifier.size(200.dp, 166.dp).clickable(onClick = onClick)) {
        NetworkImage(
            imageUrl = "https://ik.imagekit.io/skynet2skyfit/image%2029.png",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )

        Row(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillSemiTransparent, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text("10,000 Steps a Day Challenge")
            Spacer(Modifier.width(4.dp))
            Icon(
                painter = painterResource(Res.drawable.ic_app_logo),
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
private fun MobileExploreActiveChallengesComponent(onClick: () -> Unit) {
    var activeChallenges = listOf(1, 2, 3, 4)
    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(activeChallenges) {
            MobileExploreActiveChallengeItemComponent(onClick)
        }
    }
}

@Composable
private fun MobileExploreActiveChallengeItemComponent(onClick: () -> Unit) {
    Box(Modifier.size(382.dp, 282.dp).clickable(onClick = onClick)) {
        NetworkImage(
            imageUrl = "https://ik.imagekit.io/skynet2skyfit/challi3.png",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )

        Column(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillSemiTransparent, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text("7 Günlük Esneklik Geliştirme", style = SkyFitTypography.bodyLargeSemibold)
            Spacer(Modifier.height(4.dp))
            Box(Modifier.fillMaxWidth()) {
                Row(Modifier.align(Alignment.CenterStart)) {
                    CircularImage(
                        url = "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680",
                        modifier = Modifier.size(24.dp)
                    )
                    CircularImage(
                        url = "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680",
                        modifier = Modifier.size(24.dp).offset(x = (-16).dp)
                    )
                    CircularImage(
                        url = "https://ik.imagekit.io/skynet2skyfit/Profile%20Photo.png?updatedAt=1739703080462",
                        modifier = Modifier.size(24.dp).offset(x = (-32).dp)
                    )
                    CircularImage(
                        url = "https://ik.imagekit.io/skynet2skyfit/download-5.jpg?updatedAt=1740259432295",
                        modifier = Modifier.size(24.dp).offset(x = (-48).dp)
                    )
                }

                Row(
                    Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("212 Members", style = SkyFitTypography.bodyXSmall, color = SkyFitColor.text.secondary)
                    SkyFitButtonComponent(
                        modifier = Modifier.wrapContentWidth(), text = "Katıl",
                        onClick = { },
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Micro,
                        state = ButtonState.Rest
                    )
                }
            }
        }
    }
}