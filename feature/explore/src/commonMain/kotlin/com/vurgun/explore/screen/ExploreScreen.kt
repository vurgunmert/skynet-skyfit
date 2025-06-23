package com.vurgun.explore.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
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
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.explore.model.ExploreUiAction
import com.vurgun.explore.model.ExploreUiEffect
import com.vurgun.explore.model.ExploreUiState
import com.vurgun.explore.model.ExploreViewModel
import com.vurgun.skyfit.core.data.v1.domain.profile.FacilityProfileCardItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.TrainerProfileCardItemViewData
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.SharedScreen.FacilityProfile
import com.vurgun.skyfit.core.navigation.SharedScreen.TrainerProfile
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.profile.FacilityProfileCardItemBox
import com.vurgun.skyfit.core.ui.components.profile.VerticalTrainerProfileCard
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_arrow_right

class ExploreScreen : Screen {

    override val key: ScreenKey get() = SharedScreen.Explore.key

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ExploreViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                ExploreUiEffect.NavigateToBack -> {
                    navigator.pop()
                }

                is ExploreUiEffect.NavigateToVisitFacility -> {
                    appNavigator.push(FacilityProfile(effect.facilityId))
                }

                is ExploreUiEffect.NavigateToVisitTrainer -> {
                    appNavigator.push(TrainerProfile(effect.trainerId))
                }

                is ExploreUiEffect.NavigateToExerciseDetail -> {
//                    TODO()
                }

                ExploreUiEffect.NavigateToExploreChallenges -> {
//                    TODO()
                }

                ExploreUiEffect.NavigateToExploreCommunities -> {
//                    TODO()
                }

                ExploreUiEffect.NavigateToExploreFacilities -> {
//                    TODO()
                }

                ExploreUiEffect.NavigateToExploreTrainers -> {
//                    TODO()
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (val state = uiState) {
            is ExploreUiState.Content -> {
                MobileExploreScreen(state, viewModel::onAction)
            }

            is ExploreUiState.Error -> {
                ErrorScreen(message = state.message, onConfirm = { viewModel.loadData() })
            }

            ExploreUiState.Loading -> FullScreenLoaderContent()
        }
    }

}

@Composable
private fun MobileExploreScreen(
    content: ExploreUiState.Content,
    onAction: (ExploreUiAction) -> Unit,
) {

    Scaffold(
        topBar = {
            FeatureVisible(false) {
                Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Spacer(Modifier.height(16.dp))
                    SkyFitSearchTextInputComponent()
                    Spacer(Modifier.height(16.dp))
                    SkyFitSearchFilterBarComponent(onEnableSearch = { })
                }
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
            FeatureVisible(content.exercises.isNotEmpty()) {
                ExploreFeaturedExercisesGroup(
                    exercises = content.exercises,
                    onClickTitle = { onAction(ExploreUiAction.OnClickExercises) },
                    onSelectExercise = { onAction(ExploreUiAction.OnSelectExercise(it)) }
                )
            }

            FeatureVisible(content.trainers.isNotEmpty()) {
                ExploreFeaturedTrainersGroup(
                    trainers = content.trainers,
                    onClickTitle = { onAction(ExploreUiAction.OnClickTrainers) },
                    onSelectTrainer = { onAction(ExploreUiAction.OnSelectTrainer(it)) }
                )
            }

            FeatureVisible(content.facilities.isNotEmpty()) {
                MobileDashboardExploreScreenFeaturedFacilitiesComponent(
                    facilities = content.facilities,
                    onClickTitle = { onAction(ExploreUiAction.OnClickFacilities) },
                    onSelectFacility = { onAction(ExploreUiAction.OnSelectFacility(it)) }
                )
            }

            FeatureVisible(content.communities.isNotEmpty()) {
                MobileDashboardExploreScreenFeaturedCommunitiesComponent(
                    communities = content.communities,
                    onClickTitle = { onAction(ExploreUiAction.OnClickCommunities) },
                    onSelectCommunity = { onAction(ExploreUiAction.OnSelectCommunity(it)) }
                )
            }

            FeatureVisible(content.challenges.isNotEmpty()) {
                ExploreFeaturedChallengesGroup(
                    challenges = content.challenges,
                    onClickTitle = { onAction(ExploreUiAction.OnClickChallenges) },
                    onSelectChallenge = { onAction(ExploreUiAction.OnSelectChallenge(it)) }
                )
            }

            Spacer(Modifier.height(124.dp))
        }
    }
}

@Composable
private fun ExploreFeaturedExercisesGroup(
    exercises: List<Int>,
    onClickTitle: () -> Unit,
    onSelectExercise: (exerciseId: Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ExploreModuleSectionTitle(
            title = "Popüler Antrenmanlar",
            onClick = onClickTitle
        )

        LazyRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(exercises) { exercise ->
                MobileDashboardExploreScreenFeaturedExerciseItemComponent({ onSelectExercise(exercise) })
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
private fun ExploreFeaturedTrainersGroup(
    trainers: List<TrainerProfileCardItemViewData>,
    onClickTitle: () -> Unit,
    onSelectTrainer: (trainerId: Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ExploreModuleSectionTitle(title = "Pro Antrenörler", onClick = onClickTitle)

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
                    onClick = { onSelectTrainer(trainer.trainerId) }
                )
            }
        }
    }
}

@Composable
private fun MobileDashboardExploreScreenFeaturedFacilitiesComponent(
    facilities: List<FacilityProfileCardItemViewData>,
    onClickTitle: () -> Unit,
    onSelectFacility: (facilityId: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ExploreModuleSectionTitle("Popüler Tesisler", onClickTitle)

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
                    onClick = { onSelectFacility(facility.facilityId) }
                )
            }
        }
    }
}

@Composable
private fun MobileDashboardExploreScreenFeaturedCommunitiesComponent(
    communities: List<Int>,
    onClickTitle: () -> Unit,
    onSelectCommunity: (communityId: Int) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ExploreModuleSectionTitle(
            title = "Topluluklar",
            onClick = onClickTitle
        )


        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            maxItemsInEachRow = 2
        ) {
            communities.forEach { communityId ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .weight(1f)
                        .clickable(onClick = { onSelectCommunity(communityId) }),
                    contentAlignment = Alignment.Center
                ) {
                    NetworkImage(
                        imageUrl = "https://ik.imagekit.io/skynet2skyfit/image%2026.png?updatedAt=1740294973693",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                    )
                    Text(
                        "FormdaKal",
                        style = SkyFitTypography.heading4
                    )
                }
            }
        }
    }
}

@Composable
private fun ExploreFeaturedChallengesGroup(
    challenges: List<Int>,
    onClickTitle: () -> Unit,
    onSelectChallenge: (challengeId: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ExploreModuleSectionTitle("Meydan Okumalar", onClickTitle)

        Column(
            Modifier.fillMaxWidth()
                .background(SkyFitColor.specialty.buttonBgHover, RoundedCornerShape(16.dp))
                .clickable(onClick = { onSelectChallenge(0) }) //TODO: Challenge Id
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
private fun ExploreModuleSectionTitle(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SkyText(
            text = title,
            styleType = TextStyleType.Heading4,
        )
        SkyIcon(
            res = Res.drawable.ic_arrow_right,
            size = SkyIconSize.Medium
        )
    }
}