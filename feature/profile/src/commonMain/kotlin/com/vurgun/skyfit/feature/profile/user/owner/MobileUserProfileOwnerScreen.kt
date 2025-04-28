package com.vurgun.skyfit.feature.profile.user.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
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
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.domain.model.UserProfile
import com.vurgun.skyfit.feature.profile.components.MobileProfileActionsRow
import com.vurgun.skyfit.feature.profile.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature.profile.components.UserProfileCardPreferenceRow
import com.vurgun.skyfit.core.ui.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoader
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyLargeMediumText
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.components.text.CardFieldIconText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.appointments_title
import skyfit.core.ui.generated.resources.ic_clock
import skyfit.core.ui.generated.resources.member_since_day_zero
import skyfit.core.ui.generated.resources.member_since_days_few
import skyfit.core.ui.generated.resources.member_since_days_many
import skyfit.core.ui.generated.resources.show_all_action

@Composable
fun MobileUserProfileOwnerScreen(
    goToBack: () -> Unit,
    goToSettings: () -> Unit,
    goToAppointments: () -> Unit,
    goToCreatePost: () -> Unit,
    onVisitFacility: (gymId: Int) -> Unit,
    viewModel: UserProfileOwnerViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                UserProfileOwnerEffect.NavigateBack -> goToBack()
                UserProfileOwnerEffect.NavigateToAppointments -> goToAppointments()
                UserProfileOwnerEffect.NavigateToCreatePost -> goToCreatePost()
                UserProfileOwnerEffect.NavigateToSettings -> goToSettings()
                is UserProfileOwnerEffect.NavigateToVisitFacility -> onVisitFacility(effect.gymId)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    when (uiState) {
        is UserProfileOwnerUiState.Loading -> FullScreenLoader()
        is UserProfileOwnerUiState.Error -> {
            val message = (uiState as UserProfileOwnerUiState.Error).message
            ErrorScreen(message = message, onBack = goToBack)
        }

        is UserProfileOwnerUiState.Content -> {
            val content = uiState as UserProfileOwnerUiState.Content
            MobileUserProfileOwnerComponent.MobileUserProfileOwner_Content(content, viewModel::onAction)
        }
    }
}


private object MobileUserProfileOwnerComponent {

    @Composable
    fun MobileUserProfileOwner_Content(
        content: UserProfileOwnerUiState.Content,
        onAction: (UserProfileOwnerAction) -> Unit
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

                    MobileUserProfileOwner_HeaderCard(content.profile)

                    MobileProfileActionsRow(
                        postsSelected = content.postsVisible,
                        onClickAbout = { onAction(UserProfileOwnerAction.TogglePostVisibility(false)) },
                        onClickPosts = { onAction(UserProfileOwnerAction.TogglePostVisibility(true)) },
                        onClickSettings = { onAction(UserProfileOwnerAction.NavigateToSettings) },
                        onClickNewPost = { onAction(UserProfileOwnerAction.NavigateToCreatePost) }
                    )

                    if (content.postsVisible) {
                        // TODO: Posts
                    } else {
                        content.memberFacilityProfile?.let { facility ->
                            content.profile.memberGymJoinedAt?.let { joinedDate ->
                                MemberFacilityCard(
                                    profile = facility,
                                    memberDays = joinedDate.daysUntil(LocalDate.now()),
                                    onClick = { onAction(UserProfileOwnerAction.NavigateToVisitFacility(facility.gymId)) }
                                )
                            }
                        }

                        MobileUserProfileOwner_UpcomingAppointments(
                            appointments = content.appointments,
                            onClickItem = { onAction(UserProfileOwnerAction.NavigateToAppointments) },
                            onClickShowAll = { onAction(UserProfileOwnerAction.NavigateToAppointments) },
                            modifier = Modifier
                        )
                    }

                    Spacer(Modifier.height(124.dp))
                }
            }
        }
    }

    @Composable
    fun MobileUserProfileOwner_HeaderCard(userProfile: UserProfile) {

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
    fun MobileUserProfileOwner_UpcomingAppointments(
        appointments: List<LessonSessionItemViewData>,
        onClickItem: ((LessonSessionItemViewData) -> Unit)? = null,
        onClickShowAll: (() -> Unit)? = null,
        modifier: Modifier = Modifier,
    ) {
        if (appointments.isEmpty()) return

        Column(
            modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.fillTransparent)
                .clickable(onClick = { onClickShowAll?.invoke() })
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

                if (onClickShowAll != null) {
                    Text(
                        text = stringResource(Res.string.show_all_action),
                        style = SkyFitTypography.bodyXSmall,
                        color = SkyFitColor.border.secondaryButton,
                        modifier = Modifier.clickable(onClick = onClickShowAll)
                    )
                }
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


    @Composable
    fun MemberFacilityCard(
        profile: FacilityProfile,
        memberDays: Int,
        onClick: () -> Unit
    ) {
        Row(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClick)
                .background(SkyFitColor.background.surfaceSecondary)
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NetworkImage(
                imageUrl = profile.backgroundImageUrl,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyLargeMediumText(
                        text = profile.facilityName
                    )
                    BodyMediumRegularText(
                        text = " @${profile.username}",
                        color = SkyFitColor.text.secondary
                    )
                }
                Spacer(Modifier.height(8.dp))

                val memberText = when {
                    memberDays == 0 -> stringResource(Res.string.member_since_day_zero)
                    memberDays in 1..10 -> stringResource(Res.string.member_since_days_few, memberDays)
                    else -> stringResource(Res.string.member_since_days_many, memberDays)
                }

                CardFieldIconText(
                    text = memberText,
                    iconRes = Res.drawable.ic_clock,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}