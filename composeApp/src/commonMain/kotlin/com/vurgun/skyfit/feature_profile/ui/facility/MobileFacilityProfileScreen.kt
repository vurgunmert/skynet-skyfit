package com.vurgun.skyfit.feature_profile.ui.facility

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.feature_lessons.ui.components.LessonSessionColumn
import com.vurgun.skyfit.feature_profile.ui.ProfileCardVerticalDetailItemComponent
import com.vurgun.skyfit.feature_profile.ui.RatingStarComponent
import com.vurgun.skyfit.feature_profile.ui.VerticalDetailDivider
import com.vurgun.skyfit.feature_profile.ui.components.MobileProfileActionsRow
import com.vurgun.skyfit.feature_profile.ui.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature_profile.ui.components.PhotoGalleryEmptyStackCard
import com.vurgun.skyfit.feature_profile.ui.components.PhotoGalleryStackCard
import com.vurgun.skyfit.feature_profile.ui.facility.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenTrainersComponent
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileFacilityProfileScreen(navigator: Navigator) {

    val viewModel = FacilityProfileVisitedViewModel()
    val scrollState = rememberScrollState()
    val galleryStackViewData = viewModel.galleryStackViewData.collectAsState().value
    val trainers = viewModel.trainers
    val lessonsColumViewData by viewModel.lessonsColumViewData.collectAsState()
    var showPosts by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .background(SkyFitColor.background.default)
    ) {
        val width = maxWidth
        val imageHeight = width * 9 / 16

        MobileProfileBackgroundImage(
            imageUrl = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
            height = imageHeight
        )

        val contentTopPadding = imageHeight * 8 / 10

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(contentTopPadding))

            MobileFacilityProfileScreenInfoCardComponent()

            MobileProfileActionsRow(
                postsSelected = showPosts,
                onClickAbout = { showPosts = false },
                onClickPosts = { showPosts = true },
                onClickSettings = { navigator.jumpAndStay(NavigationRoute.FacilitySettings) },
                onClickNewPost = { navigator.jumpAndStay(NavigationRoute.UserSocialMediaPostAdd) }
            )

            if (galleryStackViewData == null) {
                PhotoGalleryEmptyStackCard { }
            } else {
                PhotoGalleryStackCard(
                    viewData = galleryStackViewData,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            if (trainers.isEmpty()) {
                MobileFacilityTrainersCard(onClickAdd = {})
            } else {
                MobileFacilityProfileVisitedScreenTrainersComponent(
                    trainers = trainers,
                    onClick = { navigator.jumpAndStay(NavigationRoute.TrainerProfileVisited) }
                )
            }

            lessonsColumViewData?.let {
                LessonSessionColumn(viewData = it, onClickShowAll = {})
            } ?: MobileFacilityLessonsEmptyCard { }

            Spacer(Modifier.height(124.dp))
        }
    }
}

@Composable
fun MobileFacilityProfileScreenInfoCardComponent() {

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ironstudio",
                    style = SkyFitTypography.bodyLargeSemibold,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "@ironstudio",
                    style = SkyFitTypography.bodySmallMedium,
                    color = SkyFitColor.text.secondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileCardVerticalDetailItemComponent(title = "${2.564}", subtitle = "Uye")
                VerticalDetailDivider()
                ProfileCardVerticalDetailItemComponent(title = "${15}", subtitle = "Egitmen")
                VerticalDetailDivider()
                RatingStarComponent(4.3 ?: 0.0)
            }

            Text(
                text = "At IronStudio Fitness, we’re all about building strength, confidence, and a community of like-minded individuals. Our expert trainers offer personalized programs in strength training, functional fitness, and overall wellness. Let's forge your fitness together!",
                style = SkyFitTypography.bodySmall,
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.default
                )
                Text(
                    text = "1425 Maplewood Avenue, Apt 3B, Brookfield, IL 60513, USA",
                    style = SkyFitTypography.bodySmallSemibold,
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
                )
            }
        }
    }
}