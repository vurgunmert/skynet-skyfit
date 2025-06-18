package com.vurgun.skyfit.profile.user.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.profile.component.ProfileCompactComponent
import com.vurgun.skyfit.profile.model.ProfileDestination
import com.vurgun.skyfit.profile.user.model.UserProfileAction
import com.vurgun.skyfit.profile.user.model.UserProfileEffect
import com.vurgun.skyfit.profile.user.model.UserProfileUiState
import com.vurgun.skyfit.profile.user.model.UserProfileViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
fun UserProfileCompact(
    viewModel: UserProfileViewModel,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val parentNavigator = LocalNavigator.currentOrThrow.parent ?: return
    val localNavigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            UserProfileEffect.NavigateBack -> {
                parentNavigator.pop()
            }

            UserProfileEffect.NavigateToAppointments -> {
                localNavigator.push(SharedScreen.Appointments)
            }

            UserProfileEffect.NavigateToCreatePost -> {
                localNavigator.push(SharedScreen.CreatePost)
            }

            UserProfileEffect.NavigateToSettings -> {
                localNavigator.push(SharedScreen.Settings)
            }

            is UserProfileEffect.NavigateToVisitFacility -> {
                localNavigator.push(SharedScreen.FacilityProfileVisitor(effect.gymId))
            }
        }
    }

    when (uiState) {
        UserProfileUiState.Loading ->
            FullScreenLoaderContent()

        is UserProfileUiState.Error -> {
            val message = (uiState as UserProfileUiState.Error).message
            ErrorScreen(message = message) { }
        }

        is UserProfileUiState.Content -> {
            val content = uiState as UserProfileUiState.Content

            ProfileCompactComponent.Layout(
                header = {
                    UserProfileCompactComponent.Header(content, viewModel::onAction)
                },
                content = {
                    UserProfileCompactComponent.NavigationGroup(content, viewModel::onAction)
                    UserProfileCompactComponent.Content(content, viewModel::onAction)
                }
            )
        }
    }
}

private object UserProfileCompactComponent {

    @Composable
    fun Header(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
    ) {
        val userProfile = content.profile

        Column {
            ProfileCompactComponent.Header(
                backgroundImageUrl = userProfile.backgroundImageUrl,
                profileImageUrl = userProfile.profileImageUrl,
                cardContents = {
                    Spacer(Modifier.height(32.dp))
                    ProfileCompactComponent.HeaderNameGroup(
                        firstName = userProfile.firstName,
                        userName = userProfile.username
                    )
                    Spacer(Modifier.height(12.dp))
                    ProfileCompactComponent.HeaderBodyGroup(
                        leftItem = {
                            ProfileCompactComponent.HeaderEditorialDataItem(
                                iconRes = Res.drawable.ic_height,
                                title = userProfile.height.toString(),
                                subtitle = "Boy (${userProfile.heightUnit.shortLabel})"
                            )
                        },
                        centerItem = {
                            ProfileCompactComponent.HeaderEditorialDataItem(
                                iconRes = Res.drawable.ic_dna,
                                title = userProfile.weight.toString(),
                                subtitle = "Kilo (${userProfile.weightUnit.shortLabel})"
                            )
                        },
                        rightItem = {
                            ProfileCompactComponent.HeaderEditorialDataItem(
                                iconRes = Res.drawable.ic_overweight,
                                title = userProfile.bodyType.turkishShort,
                                subtitle = stringResource(Res.string.body_type_label)
                            )
                        }
                    )
                }
            )
            Spacer(Modifier.height(21.dp))
            if (content.isVisiting) {
                if (content.isFollowing) {
                    SkyButton(
                        label = stringResource(Res.string.follow_action),
                        modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(),
                        onClick = { onAction(UserProfileAction.OnClickFollow) }
                    )
                } else {
                    SkyButton(
                        label = stringResource(Res.string.unfollow_action),
                        modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(),
                        onClick = { onAction(UserProfileAction.OnClickUnfollow) }
                    )
                }
            }
        }

    }

    @Composable
    fun NavigationGroup(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
    ) {
        if (content.isVisiting) {
            ProfileCompactComponent.NavigationMenuWithAction(
                onDestinationChanged = { onAction(UserProfileAction.OnDestinationChanged(it)) },
                destination = content.destination,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            ProfileCompactComponent.NavigationMenuWithAction(
                onDestinationChanged = { onAction(UserProfileAction.OnDestinationChanged(it)) },
                destination = content.destination,
                action = {
                    SkyIcon(Res.drawable.ic_settings)
//                    actionRes = Res.drawable.ic_settings,
//                    onActionClick = { onAction(UserProfileAction.ClickSettings) },
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun Content(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
    ) {
        if (content.destination == ProfileDestination.Posts) {
            PostsContent(content, onAction)
        } else {
            AboutContent(content, onAction)
        }
    }

    @Composable
    fun AboutContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        //isVisiting
    }

    @Composable
    fun PostsContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        //isVisiting
    }

    @Composable
    fun LessonPackage(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier,
    ) {

    }

    @Composable
    fun Appointments(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val appointments = content.appointments
        if (appointments.isEmpty()) return

        Column(
            modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.fillTransparent)
                .clickable(onClick = { onAction(UserProfileAction.ClickAppointments) })
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

                if (!content.isVisiting) {
                    Text(
                        text = stringResource(Res.string.show_all_action),
                        style = SkyFitTypography.bodyXSmall,
                        color = SkyFitColor.border.secondaryButton,
                        modifier = Modifier.clickable(onClick = { onAction(UserProfileAction.ClickAppointments) })
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
    fun DietCard(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier,) {

    }

    @Composable
    fun MeasurementsCard() {

    }

    @Composable
    fun MyStatistics() {

    }

    @Composable
    fun MyExerciseHistory() {

    }

    @Composable
    fun MyPhotoDiary() {

    }
}