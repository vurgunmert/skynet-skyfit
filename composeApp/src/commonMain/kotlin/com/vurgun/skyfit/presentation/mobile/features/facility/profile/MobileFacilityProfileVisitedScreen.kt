package com.vurgun.skyfit.presentation.mobile.features.facility.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.vurgun.skyfit.presentation.mobile.features.explore.TrainerProfileCardItemViewData
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenInfoCardComponent
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenPhotosComponent
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenPrivateClassesComponent
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenToolbarComponent
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenTrainersComponent
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitPrimaryCircularBackButton
import com.vurgun.skyfit.presentation.shared.components.SkyFitSecondaryIconButton
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItemRowComponent
import com.vurgun.skyfit.presentation.shared.features.facility.FacilityProfileVisitedViewModel
import com.vurgun.skyfit.presentation.shared.features.profile.ProfileCardVerticalDetailItemComponent
import com.vurgun.skyfit.presentation.shared.features.profile.RatingStarComponent
import com.vurgun.skyfit.presentation.shared.features.profile.TrainerProfileCardItemBox
import com.vurgun.skyfit.presentation.shared.features.profile.VerticalDetailDivider
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitIcon
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_calendar_dots
import skyfit.composeapp.generated.resources.ic_dashboard
import skyfit.composeapp.generated.resources.ic_exercises
import skyfit.composeapp.generated.resources.ic_profile_fill
import skyfit.composeapp.generated.resources.ic_send
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileFacilityProfileVisitedScreen(navigator: Navigator) {

    val viewModel = FacilityProfileVisitedViewModel()
    val scrollState = rememberScrollState()
    val photos: List<Any> = listOf(1, 2, 3)
    val trainers = viewModel.trainers
    val privateClasses = viewModel.privateClasses

    var isFollowing by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .background(SkyFitColor.background.default)
    ) {
        val width = maxWidth
        val imageHeight = width * 9 / 16

        AsyncImage(
            model = "https://ik.imagekit.io/skynet2skyfit/fake_facility_background.png?updatedAt=1739637015088",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
        )

        val contentTopPadding = imageHeight * 8 / 10

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(contentTopPadding))

            MobileFacilityProfileVisitedScreenInfoCardComponent(
                isFollowing = isFollowing,
                onClickFollow = { isFollowing = true },
                onClickUnFollow = { isFollowing = false },
                onClickCalendar = { navigator.jumpAndStay(SkyFitNavigationRoute.FacilityCalendarVisited) },
                onClickMessage = { navigator.jumpAndStay(SkyFitNavigationRoute.UserToFacilityChat) }
            )

            if (photos.isNotEmpty()) {
                MobileFacilityProfileVisitedScreenPhotosComponent()
            }

            if (trainers.isNotEmpty()) {
                MobileFacilityProfileVisitedScreenTrainersComponent(trainers)
            }

            if (privateClasses.isNotEmpty()) {
                MobileFacilityProfileVisitedScreenPrivateClassesComponent(privateClasses, onClick = {
                    navigator.jumpAndStay(SkyFitNavigationRoute.FacilityCalendarVisited)
                })
            }
        }

        MobileFacilityProfileVisitedScreenToolbarComponent(onClickBack = { navigator.popBackStack() })
    }
}


object MobileFacilityProfileVisitedScreen {

    @Composable
    fun MobileFacilityProfileVisitedScreenToolbarComponent(onClickBack: () -> Unit) {
        Box(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 24.dp)) {
            SkyFitPrimaryCircularBackButton(onClick = onClickBack)
        }
    }

    @Composable
    fun MobileFacilityProfileVisitedScreenPhotosComponent() {
        BoxWithConstraints(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            val imageSize = maxWidth
            val image2Size = maxWidth - 16.dp
            val image3Size = maxWidth - 32.dp

            AsyncImage(
                model = "https://ik.imagekit.io/skynet2skyfit/fake_facility_gym.png?updatedAt=1739637015082",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(image3Size)
                    .clip(RoundedCornerShape(16.dp))
            )

            AsyncImage(
                model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp)
                    .size(image2Size)
                    .clip(RoundedCornerShape(16.dp))
            )

            AsyncImage(
                model = "https://ik.imagekit.io/skynet2skyfit/fake_facility_gym.png?updatedAt=1739637015082",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
                    .size(imageSize)
                    .clip(RoundedCornerShape(16.dp))
            )

            Box(
                Modifier.align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Salonu Kesfet",
                        style = SkyFitTypography.heading4,
                    )
                    Text(
                        text = "8 fotograf, 1 video", style = SkyFitTypography.bodyMediumRegular,
                        color = SkyFitColor.text.secondary
                    )
                }
            }
        }
    }

    @Composable
    fun MobileFacilityProfileVisitedScreenInfoCardComponent(
        isFollowing: Boolean,
        onClickFollow: () -> Unit,
        onClickUnFollow: () -> Unit,
        onClickCalendar: () -> Unit,
        onClickMessage: () -> Unit,
    ) {

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
                Spacer(modifier = Modifier.height(16.dp))

                SkyFitButtonComponent(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (isFollowing) "Takipten Çık" else "Takip Et",
                    onClick = if (isFollowing) onClickUnFollow else onClickFollow,
                    variant = ButtonVariant.Primary,
                    size = ButtonSize.Large
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    SkyFitButtonComponent(
                        modifier = Modifier.weight(1f),
                        text = "Randevu Al",
                        onClick = onClickCalendar,
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Large,
                        leftIconPainter = painterResource(Res.drawable.ic_calendar_dots)
                    )
                    if (isFollowing) {
                        Spacer(modifier = Modifier.width(10.dp))
                        SkyFitSecondaryIconButton(
                            painter = painterResource(Res.drawable.ic_send),
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun MobileFacilityProfileVisitedScreenTrainersComponent(trainers: List<TrainerProfileCardItemViewData>) {
        Text(
            text = "Antrenörlerimiz",
            style = SkyFitTypography.bodyLargeSemibold,
            modifier = Modifier.padding(12.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Horizontal spacing
        ) {
            items(trainers) { trainer ->
                TrainerProfileCardItemBox(
                    imageUrl = trainer.imageUrl,
                    name = trainer.name,
                    followerCount = trainer.followerCount,
                    classCount = trainer.classCount,
                    videoCount = trainer.videoCount,
                    rating = trainer.rating,
                    onClick = { /* Handle click */ }
                )
            }
        }
    }

    @Composable
    fun MobileFacilityProfileVisitedScreenPrivateClassesComponent(privateClasses: List<SkyFitClassCalendarCardItem>,
                                                                  onClick: () -> Unit = {}) {
        Column(
            Modifier
                .padding(16.dp).fillMaxWidth()
                .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(20.dp))
                .clickable(onClick = onClick)
                .padding(16.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    contentDescription = "Info",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Özel Dersler",
                    style = SkyFitTypography.bodyLargeSemibold
                )
            }

            privateClasses.forEach {
                Spacer(Modifier.height(16.dp))
                SkyFitFacilityProfileClassItemComponent(
                    item = it,
                    onClick = { }
                )
            }
        }
    }
}


@Composable
fun SkyFitFacilityProfileClassItemComponent(item: SkyFitClassCalendarCardItem, onClick: () -> Unit) {

    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = SkyFitIcon.getIconResourcePainter(item.iconId, defaultRes = Res.drawable.ic_exercises),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = SkyFitColor.icon.default
                )
                Text(
                    text = item.title,
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                )
            }

            item.trainer?.let {
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_profile_fill)
            }

            item.category?.let {
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_dashboard)
            }
        }
    }
}