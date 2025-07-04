package com.vurgun.explore.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

@Composable
fun MobileExploreBlogScreen(
    goToBack: () -> Unit
) {

    SkyFitScaffold(
        topBar = {
            Column {
                CompactTopBar("Blog", onClickBack = goToBack)
                SkyFitSearchTextInputComponent(hint = "Antrenman ara")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileExploreBlogArticlesComponent()
            MobileExploreBlogArticlesComponent()
            MobileExploreBlogArticlesComponent()
            MobileExploreBlogArticlesComponent()
            MobileExploreBlogArticlesComponent()
        }
    }
}

@Composable
fun MobileExploreBlogArticlesComponent() {

    Column() {
        NetworkImage(
            imageUrl = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
            modifier = Modifier
                .width(382.dp)
                .height(178.dp)
                .background(MaterialTheme.colors.onSurface.copy(alpha = 0.2f))
        )
        Spacer(Modifier.height(16.dp))
        Text("5 Simple Habits to Boost Your Workout Results", style = SkyFitTypography.bodyLargeSemibold)
        Spacer(Modifier.height(16.dp))
        Text(
            "Achieving your fitness goals doesn’t always require drastic changes. Sometimes, small, consistent habits can lead to the most significant improvements.",
            style = SkyFitTypography.bodyMediumRegular,
            color = SkyFitColor.text.secondary
        )
        Spacer(Modifier.height(16.dp))
        Row {
            Text(
                "Sarah L.", style = SkyFitTypography.bodySmallMedium,
                color = SkyFitColor.text.secondary
            )
            Spacer(Modifier.weight(1f))
            Text(
                "1 saat önce", style = SkyFitTypography.bodySmall,
                color = SkyFitColor.text.secondary
            )
        }
    }
}