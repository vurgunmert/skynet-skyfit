package com.vurgun.skyfit.feature.dashboard.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.feature.persona.components.FacilityProfileCardItemBox
import com.vurgun.skyfit.feature.persona.components.VerticalTrainerProfileCard
import com.vurgun.skyfit.feature.persona.components.viewdata.FacilityProfileCardItemViewData
import com.vurgun.skyfit.feature.persona.components.viewdata.TrainerProfileCardItemViewData
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_right

class ExploreScreen : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ExploreViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            is ExploreUiState.Content -> {
                val content = (uiState as ExploreUiState.Content)
                MobileExploreScreen(content, viewModel::onAction)
            }

            is ExploreUiState.Error -> {
                TODO()
            }

            ExploreUiState.Loading -> FullScreenLoaderContent()
        }
    }

}

@Composable
private fun MobileExploreScreen(
    content: ExploreUiState.Content,
    onAction: (ExploreAction) -> Unit,
) {

    val trainers = content.trainers
    val facilities = content.facilities

    SkyFitMobileScaffold(
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
                onClick = { onAction(ExploreAction.OnClickExercise) }
            )

            MobileDashboardExploreScreenFeaturedTrainersComponent(
                trainers,
                onClick = { onAction(ExploreAction.OnClickTrainer) }
            )

            MobileDashboardExploreScreenFeaturedFacilitiesComponent(
                facilities,
                onClick = { onAction(ExploreAction.OnClickFacility) }
            )

            MobileDashboardExploreScreenFeaturedCommunitiesComponent(
                onClick = { onAction(ExploreAction.OnClickCommunities) }
            )

            MobileDashboardExploreScreenFeaturedChallengesComponent(
                onClick = { onAction(ExploreAction.OnClickChallanges) }
            )

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
        NetworkImage(
            imageUrl = "https://ik.imagekit.io/skynet2skyfit/image%2028.png?updatedAt=1740295545580",
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
                VerticalTrainerProfileCard(
                    imageUrl = trainer.imageUrl,
                    name = trainer.name,
                    followerCount = trainer.followerCount,
                    lessonCount = trainer.classCount,
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
                    rating = facility.rating ?: 0f,
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
                NetworkImage(
                    imageUrl = "https://ik.imagekit.io/skynet2skyfit/image%2026.png?updatedAt=1740294973693",
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
                NetworkImage(
                    imageUrl = "https://ik.imagekit.io/skynet2skyfit/image%2027.png?updatedAt=1740294973688",
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
                NetworkImage(
                    imageUrl = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
                    modifier = Modifier.size(32.dp)
                        .clip(CircleShape)
                        .offset(x = (-6).dp)
                )
                NetworkImage(
                    imageUrl = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
                    modifier = Modifier.size(32.dp)
                        .clip(CircleShape)
                        .offset(x = (-6).dp)
                )
                NetworkImage(
                    imageUrl = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
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
            text = title,
            style = SkyFitTypography.heading4,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(Res.drawable.ic_chevron_right),
            contentDescription = title,
            tint = SkyFitColor.icon.default
        )
    }
}