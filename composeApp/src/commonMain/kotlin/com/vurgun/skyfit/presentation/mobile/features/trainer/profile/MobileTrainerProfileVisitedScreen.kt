package com.vurgun.skyfit.presentation.mobile.features.trainer.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenPrivateClassesComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileVisitedProfileActionsComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.UserProfileCardPreferenceRow
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.button.SkyFitPrimaryCircularBackButton
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.presentation.shared.features.trainer.SkyFitTrainerProfileViewModel
import com.vurgun.skyfit.presentation.shared.features.user.TopBarGroupViewData
import com.vurgun.skyfit.presentation.shared.navigation.NavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_calendar_dots
import skyfit.composeapp.generated.resources.ic_send

@Composable
fun MobileTrainerProfileVisitedScreen(navigator: Navigator) {

    val viewModel = remember { SkyFitTrainerProfileViewModel() }
    val scrollState = rememberScrollState()
    var showPosts by remember { mutableStateOf(false) }

    val profileData by viewModel.profileData.collectAsState()
    val specialities by viewModel.specialities.collectAsState()
    val privateClasses = viewModel.privateClasses.collectAsState().value
    val posts = viewModel.posts.collectAsState().value
    var isFollowing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .background(SkyFitColor.background.default)
    ) {
        val width = maxWidth
        val imageHeight = width * 9 / 16
        val contentTopPadding = imageHeight * 3 / 10

        MobileTrainerProfileBackgroundImageComponent(imageHeight)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(contentTopPadding))

            MobileTrainerProfileVisitedScreenInfoCardComponent(
                viewData = profileData,
                isFollowing = isFollowing,
                onClickFollow = { isFollowing = true },
                onClickUnFollow = { isFollowing = false },
                onClickCalendar = { navigator.jumpAndStay(NavigationRoute.FacilityCalendarVisited) }
            )

            MobileTrainerProfileVisitedScreenActionsComponent(
                showMessage = isFollowing,
                onClickAbout = { showPosts = false },
                onClickPosts = { showPosts = true },
                onClickMessage = { navigator.jumpAndStay(NavigationRoute.UserToTrainerChat) }
            )

            if (showPosts) {
                MobileTrainerProfilePostsComponent(posts)
            } else {
                MobileTrainerProfileVisitedAboutGroup(specialities, privateClasses, navigator)
            }
        }

        MobileTrainerProfileVisitedScreenToolbarComponent(onClickBack = { navigator.popBackStack() })
    }
}

@Composable
private fun ColumnScope.MobileTrainerProfileVisitedAboutGroup(
    specialities: List<SpecialityItemComponentViewData> = emptyList(),
    privateClasses: List<SkyFitClassCalendarCardItem> = emptyList(),
    navigator: Navigator
) {
    Column(
        modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (specialities.isNotEmpty()) {
            MobileTrainerProfileSpecialitiesComponent(specialities)
        }

        if (privateClasses.isNotEmpty()) {
            MobileFacilityProfileVisitedScreenPrivateClassesComponent(privateClasses, onClick = {
                navigator.jumpAndStay(NavigationRoute.FacilityCalendarVisited)
            })
        }
    }
}


@Composable
private fun MobileTrainerProfileVisitedScreenToolbarComponent(onClickBack: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 24.dp)) {
        SkyFitPrimaryCircularBackButton(onClick = onClickBack)
    }
}

@Composable
private fun MobileTrainerProfileVisitedScreenInfoCardComponent(
    viewData: TopBarGroupViewData?,
    isFollowing: Boolean,
    onClickFollow: () -> Unit,
    onClickUnFollow: () -> Unit,
    onClickCalendar: () -> Unit
) {
    viewData ?: return

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(top = 70.dp)
                .padding(horizontal = 16.dp)
                .width(398.dp)
                .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                    .padding(start = 16.dp, top = 36.dp, end = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewData.name,
                        style = SkyFitTypography.bodyLargeSemibold
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Space between name and social link
                    Text(
                        text = viewData.social,
                        style = SkyFitTypography.bodySmallMedium,
                        color = SkyFitColor.text.secondary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (viewData.preferences.isNotEmpty()) {
                    UserProfileCardPreferenceRow(Modifier.fillMaxWidth())
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
                }
            }
        }

        AsyncImage(
            model = viewData.imageUrl,
            contentDescription = "Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun MobileTrainerProfileVisitedScreenActionsComponent(
    showMessage: Boolean,
    onClickAbout: () -> Unit,
    onClickPosts: () -> Unit,
    onClickMessage: () -> Unit
) {
    Row(
        Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MobileVisitedProfileActionsComponent(
            modifier = Modifier.weight(1f),
            onClickAbout = onClickAbout,
            onClickPosts = onClickPosts
        )

        if (showMessage) {
            Box(
                Modifier
                    .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                    .clickable(onClick = onClickMessage)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_send),
                    contentDescription = "Send",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

