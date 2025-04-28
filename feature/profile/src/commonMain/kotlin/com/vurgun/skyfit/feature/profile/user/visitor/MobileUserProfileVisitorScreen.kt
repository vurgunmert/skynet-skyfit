package com.vurgun.skyfit.feature.profile.user.visitor

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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.domain.model.UserProfile
import com.vurgun.skyfit.feature.profile.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature.profile.components.UserProfileCardPreferenceRow
import com.vurgun.skyfit.core.ui.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoader
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.appointments_title

@Composable
fun MobileUserProfileVisitorScreen(
    normalUserId: Int,
    goToBack: () -> Unit,
    viewModel: UserProfileVisitorViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                UserProfileVisitorEffect.NavigateBack -> goToBack()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadProfile(normalUserId)
    }

    when (uiState) {
        is UserProfileVisitorUiState.Loading -> FullScreenLoader()
        is UserProfileVisitorUiState.Error -> {
            val message = (uiState as UserProfileVisitorUiState.Error).message
            ErrorScreen(message = message, onBack = goToBack)
        }

        is UserProfileVisitorUiState.Content -> {
            val content = uiState as UserProfileVisitorUiState.Content
            MobileUserProfileVisitorComponent.MobileUserVisitor_Content(content, viewModel::onAction)
        }
    }
}

private object MobileUserProfileVisitorComponent {

    @Composable
    fun MobileUserVisitor_Content(
        content: UserProfileVisitorUiState.Content,
        onAction: (UserProfileVisitorAction) -> Unit
    ) {
        val scrollState = rememberScrollState()

        var backgroundAlpha by remember { mutableStateOf(1f) }
        val transitionThreshold = 300f

        LaunchedEffect(scrollState.value) {
            val scrollY = scrollState.value.toFloat()
            backgroundAlpha = when {
                scrollY >= transitionThreshold -> 0f
                else -> (1f - (scrollY / transitionThreshold))
            }
        }

        SkyFitMobileScaffold { defaultPadding ->

            BoxWithConstraints(
                modifier = Modifier
                    .padding(defaultPadding)
                    .fillMaxSize()
                    .background(SkyFitColor.background.default)
            ) {
                val width = maxWidth
                val imageHeight = width * 9 / 16
                val contentTopPadding = imageHeight * 5 / 10

                MobileProfileBackgroundImage(
                    imageUrl = content.profile.backgroundImageUrl,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .height(imageHeight)
                        .alpha(backgroundAlpha)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(Modifier.height(contentTopPadding))

                    MobileUserProfileVisitor_HeaderCard(content.profile)

                    //TODO: Visitor Action Row

                    if (content.postsVisible) {
                        // TODO: Posts
                    } else {
                        MobileUserProfileVisitor_UpcomingAppointments(
                            appointments = content.appointments,
                            modifier = Modifier
                        )
                    }

                    Spacer(Modifier.height(124.dp))
                }
            }
        }
    }

    @Composable
    fun MobileUserProfileVisitor_HeaderCard(userProfile: UserProfile) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(top = 70.dp)
                    .width(398.dp)
                    .heightIn(max = 140.dp)
                    .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF012E36).copy(alpha = 0.88f), RoundedCornerShape(16.dp))
                        .blur(40.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 36.dp, end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = userProfile.firstName,
                            style = SkyFitTypography.bodyLargeSemibold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = userProfile.username,
                            style = SkyFitTypography.bodySmallMedium,
                            color = SkyFitColor.text.secondary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    UserProfileCardPreferenceRow(
                        height = userProfile.height.toString(),
                        weight = userProfile.weight.toString(),
                        bodyType = userProfile.bodyType.turkishShort,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            NetworkImage(
                imageUrl = userProfile.profileImageUrl,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .align(Alignment.TopCenter)
            )
        }
    }

    @Composable
    fun MobileUserProfileVisitor_UpcomingAppointments(
        appointments: List<LessonSessionItemViewData>,
        modifier: Modifier = Modifier,
    ) {
        if (appointments.isEmpty()) return

        Column(
            modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.fillTransparent)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = SkyFitAsset.getPainter(SkyFitAsset.SkyFitIcon.EXERCISES.id),
                    modifier = Modifier.size(24.dp),
                    contentDescription = null,
                    tint = SkyFitColor.icon.default
                )

                Text(
                    text = stringResource(Res.string.appointments_title),
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.weight(1f)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                appointments.forEach { item ->
                    AvailableActivityCalendarEventItem(
                        title = item.title,
                        iconId = item.iconId,
                        date = item.date.toString(),
                        timePeriod = item.hours.toString(),
                        location = item.location.toString(),
                        trainer = item.trainer.toString(),
                        capacity = item.capacityRatio.toString(),
                        note = item.note
                    )
                }
            }
        }
    }
}