package com.vurgun.skyfit.presentation.mobile.features.dashboard

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.mobile.features.explore.FacilityProfileCardItemViewData
import com.vurgun.skyfit.presentation.mobile.features.explore.TrainerProfileCardItemViewData
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchFilterBarComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.presentation.shared.features.profile.FacilityProfileCardItemBox
import com.vurgun.skyfit.presentation.shared.features.profile.TrainerProfileCardItemBox
import com.vurgun.skyfit.presentation.shared.navigation.NavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.presentation.shared.viewmodel.DashboardExploreScreenViewModel
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileDashboardExploreScreen(rootNavigator: Navigator) {

    val viewModel = DashboardExploreScreenViewModel()
    val trainers = viewModel.trainers
    val facilities = viewModel.facilities

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Spacer(Modifier.height(16.dp))
                SkyFitSearchTextInputComponent()
                Spacer(Modifier.height(16.dp))
                SkyFitSearchFilterBarComponent(onEnableSearch = { })
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            MobileDashboardExploreScreenFeaturedExercisesComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.UserExerciseDetail) })

            MobileDashboardExploreScreenFeaturedTrainersComponent(
                trainers,
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.TrainerProfileVisited) })

            MobileDashboardExploreScreenFeaturedFacilitiesComponent(
                facilities,
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.FacilityProfileVisited) })

            MobileDashboardExploreScreenFeaturedCommunitiesComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.DashboardExploreCommunities) })

            MobileDashboardExploreScreenFeaturedChallengesComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.DashboardExploreChallenges) })

            Spacer(Modifier.height(124.dp))
        }
    }
}

@Composable
private fun MobileDashboardExploreScreenFeaturedExercisesComponent(onClick: () -> Unit) {

    var exerciseItems = listOf(1, 2, 3, 4, 5)
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MobileDashboardExploreScreenTitleActionComponent("Popüler Antrenmanlar")

        LazyRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(exerciseItems) {
                MobileDashboardExploreScreenFeaturedExerciseItemComponent(onClick)
            }
        }
    }
}

@Composable
private fun MobileDashboardExploreScreenFeaturedExerciseItemComponent(onClick: () -> Unit) {
    Box(Modifier.size(186.dp, 278.dp).clickable(onClick = onClick)) {
        AsyncImage(
            model = "https://ik.imagekit.io/skynet2skyfit/image%2028.png?updatedAt=1740295545580",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )

        Text(
            "Dumbell Exercise",
            style = SkyFitTypography.bodyLargeSemibold,
            modifier = Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 24.dp)
        )
    }
}

@Composable
private fun MobileDashboardExploreScreenFeaturedTrainersComponent(
    trainers: List<TrainerProfileCardItemViewData>,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MobileDashboardExploreScreenTitleActionComponent("Pro Antrenörler")

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(trainers) { trainer ->
                TrainerProfileCardItemBox(
                    imageUrl = trainer.imageUrl,
                    name = trainer.name,
                    followerCount = trainer.followerCount,
                    classCount = trainer.classCount,
                    videoCount = trainer.videoCount,
                    rating = trainer.rating,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun MobileDashboardExploreScreenFeaturedFacilitiesComponent(
    facilities: List<FacilityProfileCardItemViewData>,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MobileDashboardExploreScreenTitleActionComponent("Popüler Tesisler")

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(facilities) { facility ->
                FacilityProfileCardItemBox(
                    imageUrl = facility.imageUrl,
                    name = facility.name,
                    memberCount = facility.memberCount,
                    trainerCount = facility.trainerCount,
                    rating = facility.rating ?: 0.0,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun MobileDashboardExploreScreenFeaturedCommunitiesComponent(onClick: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MobileDashboardExploreScreenTitleActionComponent("Topluluklar")

        Row {
            Box(
                modifier = Modifier.weight(1f).clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "https://ik.imagekit.io/skynet2skyfit/image%2026.png?updatedAt=1740294973693",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Text(
                    "FormdaKal",
                    style = SkyFitTypography.heading4
                )
            }

            Spacer(Modifier.width(6.dp))

            Box(
                modifier = Modifier.weight(1f).clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "https://ik.imagekit.io/skynet2skyfit/image%2027.png?updatedAt=1740294973688",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Text(
                    "FitZirve",
                    style = SkyFitTypography.heading4
                )
            }
        }
    }
}

@Composable
private fun MobileDashboardExploreScreenFeaturedChallengesComponent(onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MobileDashboardExploreScreenTitleActionComponent("Meydan Okumalar")

        Column(
            Modifier.fillMaxWidth()
                .background(SkyFitColor.specialty.buttonBgHover, RoundedCornerShape(16.dp))
                .clickable(onClick = onClick)
                .padding(16.dp)
        ) {
            Text(
                "10,000 Steps a Day Challange",
                style = SkyFitTypography.heading3
            )
            Spacer(Modifier.height(12.dp))
            Row {
                AsyncImage(
                    model = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(32.dp)
                        .clip(CircleShape)
                        .offset(x = (-6).dp)
                )
                AsyncImage(
                    model = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(32.dp)
                        .clip(CircleShape)
                        .offset(x = (-6).dp)
                )
                AsyncImage(
                    model = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(32.dp)
                        .clip(CircleShape)
                        .offset(x = (-6).dp)
                )

                Spacer(Modifier.weight(1f))

                SkyFitButtonComponent(
                    text = "Katil",
                    modifier = Modifier.wrapContentWidth(),
                    onClick = { },
                    variant = ButtonVariant.Primary,
                    size = ButtonSize.Medium,
                    state = ButtonState.Disabled
                )
            }
        }
    }
}

@Composable
private fun MobileDashboardExploreScreenTitleActionComponent(title: String) {
    Row(Modifier.fillMaxWidth()) {
        Text(
            title, style = SkyFitTypography.heading4,
            modifier = Modifier.weight(1f)
        )
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = title, tint = SkyFitColor.icon.default)
    }
}