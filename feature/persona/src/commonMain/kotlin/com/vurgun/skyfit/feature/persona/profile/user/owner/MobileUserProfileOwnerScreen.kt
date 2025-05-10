package com.vurgun.skyfit.feature.persona.profile.user.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.domain.model.UserProfile
import com.vurgun.skyfit.core.data.schedule.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.persona.components.MobileProfileActionsRow
import com.vurgun.skyfit.feature.persona.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature.persona.components.UserProfileCardPreferenceRow
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.appointments_title
import skyfit.core.ui.generated.resources.show_all_action

class UserProfileOwnerScreen : Screen {

    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val viewModel = koinScreenModel<UserProfileOwnerViewModel>()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                UserProfileOwnerEffect.NavigateBack -> {
                    localNavigator.pop()
                }

                UserProfileOwnerEffect.NavigateToAppointments -> {
                    appNavigator.push(SharedScreen.UserAppointmentListing)
                }

                UserProfileOwnerEffect.NavigateToCreatePost -> {
                    appNavigator.push(SharedScreen.CreatePost)
                }

                UserProfileOwnerEffect.NavigateToSettings -> {
                    appNavigator.push(SharedScreen.Settings)
                }

                is UserProfileOwnerEffect.NavigateToVisitFacility -> {
                    appNavigator.push(SharedScreen.FacilityProfileVisitor(effect.gymId))
                }
            }
        }

        MobileUserProfileOwnerScreen(viewModel = viewModel)
    }
}


@Composable
fun MobileUserProfileOwnerScreen(
    viewModel: UserProfileOwnerViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    when (uiState) {
        is UserProfileOwnerUiState.Loading -> FullScreenLoaderContent()
        is UserProfileOwnerUiState.Error -> {
            val message = (uiState as UserProfileOwnerUiState.Error).message
            ErrorScreen(message = message, onConfirm = { viewModel.onAction(UserProfileOwnerAction.NavigateBack) })
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
                        onClickPosts = { }, //onAction(UserProfileOwnerAction.TogglePostVisibility(true))
                        onClickSettings = { onAction(UserProfileOwnerAction.NavigateToSettings) },
                        onClickNewPost = { onAction(UserProfileOwnerAction.NavigateToCreatePost) }
                    )

                    if (content.postsVisible) {
                        // TODO: Posts
                    } else {
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
}