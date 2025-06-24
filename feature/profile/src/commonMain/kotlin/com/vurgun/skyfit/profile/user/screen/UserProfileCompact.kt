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
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.box.TodoBox
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.chip.RectangleChip
import com.vurgun.skyfit.core.ui.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.profile.SocialPostCard
import com.vurgun.skyfit.core.ui.components.profile.SocialQuickPostInputCard
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.profile.component.ProfileCompactComponent
import com.vurgun.skyfit.profile.model.ProfileDestination
import com.vurgun.skyfit.profile.user.measurements.UserMeasurementsScreen
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
    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val localNavigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            UserProfileEffect.NavigateBack -> {
                localNavigator.pop()
            }

            UserProfileEffect.NavigateToAppointments -> {
                appNavigator.push(SharedScreen.UserAppointmentListing)
            }

            UserProfileEffect.NavigateToCreatePost -> {
                appNavigator.push(SharedScreen.NewPost)
            }

            UserProfileEffect.NavigateToSettings -> {
                appNavigator.push(SharedScreen.Settings)
            }

            UserProfileEffect.NavigateToMeasurements -> {
                appNavigator.push(UserMeasurementsScreen())
            }

            is UserProfileEffect.NavigateToVisitFacility -> {
                appNavigator.push(SharedScreen.FacilityProfile(effect.gymId))
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
            UserProfileCompactComponent.Content(content, viewModel::onAction)
        }
    }
}

internal object UserProfileCompactComponent {

    @Composable
    fun Content(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
    ) {

        ProfileCompactComponent.Layout(
            header = {
                Header(content, onAction)
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    VisitorActionGroup(content, onAction)

                    NavigationGroup(content, onAction)

                    if (content.destination == ProfileDestination.Posts) {
                        PostsContent(content, onAction)
                    } else {
                        AboutContent(content, onAction)
                    }

                    Spacer(Modifier.height(132.dp))
                }
            }
        )
    }

    @Composable
    fun Header(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
    ) {
        val userProfile = content.profile

        ProfileCompactComponent.Header(
            backgroundImageUrl = userProfile.backgroundImageUrl,
            backgroundImageModifier = Modifier.fillMaxWidth().height(180.dp),
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
                            subtitle = "Boy (${userProfile.heightUnit.shortLabel})",
                            modifier = Modifier.weight(1f)
                        )
                    },
                    centerItem = {
                        ProfileCompactComponent.HeaderEditorialDataItem(
                            iconRes = Res.drawable.ic_dna,
                            title = userProfile.weight.toString(),
                            subtitle = "Kilo (${userProfile.weightUnit.shortLabel})",
                            modifier = Modifier.weight(1f)
                        )
                    },
                    rightItem = {
                        ProfileCompactComponent.HeaderEditorialDataItem(
                            iconRes = Res.drawable.ic_overweight,
                            title = userProfile.bodyType.turkishShort,
                            subtitle = stringResource(Res.string.body_type_label),
                            modifier = Modifier.weight(1f)
                        )
                    }
                )
            },
            cardContentsModifier = Modifier.fillMaxWidth().padding(top = 118.dp),
            canNavigateBack = content.isVisiting,
            onClickBack = { onAction(UserProfileAction.OnClickBack) }
        )
    }

    @Composable
    fun VisitorActionGroup(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
    ) {
        if (content.isVisiting) {
            Spacer(Modifier.height(8.dp))

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
                    if (content.destination == ProfileDestination.Posts) {
                        ProfileCompactComponent.NavigationMenuAction(
                            res = Res.drawable.ic_plus,
                            onClick = { onAction(UserProfileAction.OnClickNewPost) }
                        )
                    } else {
                        ProfileCompactComponent.NavigationMenuAction(
                            res = Res.drawable.ic_settings,
                            onClick = { onAction(UserProfileAction.OnClickSettings) }
                        )
                    }

                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun AboutContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        MyLessonPackages(content, onAction, modifier = Modifier.fillMaxWidth())
        Appointments(content, onAction, modifier = Modifier.fillMaxWidth())
        MyDiet(content, onAction, modifier = Modifier.fillMaxWidth())
        MyMeasurements(content, onAction, modifier = Modifier.fillMaxWidth())
    }

    @Composable
    fun PostsContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!content.isVisiting) {
                SocialQuickPostInputCard(
                    creatorImageUrl = content.profile.backgroundImageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SkyFitColor.background.default, RoundedCornerShape(16.dp))
                ) { onAction(UserProfileAction.OnSendQuickPost(it)) }
            }

            content.posts.forEach { post ->
                SocialPostCard(
                    post = post,
                    onClick = { onAction(UserProfileAction.OnClickPost) },
                    onClickComment = { onAction(UserProfileAction.OnClickCommentPost) },
                    onClickLike = { onAction(UserProfileAction.OnClickLikePost) },
                    onClickShare = { onAction(UserProfileAction.OnClickSharePost) }
                )
            }
        }
    }

    @Composable
    fun MyLessonPackages(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val myPackage = content.profile.membershipPackage ?: return
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = SkyFitColor.background.fillTransparentSecondary,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SkyIcon(
                    res = Res.drawable.ic_package,
                    size = SkyIconSize.Large,
                )
                Spacer(Modifier.width(8.dp))
                SkyText(
                    text = myPackage.packageName,
                    styleType = TextStyleType.BodyMediumSemibold
                )
                Spacer(Modifier.weight(1f))
                RectangleChip("${myPackage.usedLessonCount}/${myPackage.lessonCount}")
            }

            Spacer(Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SkyFitColor.background.default,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(8.dp)
            ) {
                SkyText(
                    text = stringResource(Res.string.course_contents_label),
                    styleType = TextStyleType.BodyMediumSemibold
                )
                Spacer(Modifier.height(8.dp))
                myPackage.categories.map {
                    it.name
                }
                SkyText(
                    text = myPackage.categories.joinToString("\n") { "• ${it.name}" },
                    styleType = TextStyleType.BodyMediumRegular,
                    color = SkyFitColor.text.secondary
                )
            }
        }
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
                .clickable(enabled = !content.isVisiting, onClick = { onAction(UserProfileAction.OnClickAppointments) })
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
                        modifier = Modifier.clickable(onClick = { onAction(UserProfileAction.OnClickAppointments) })
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
    fun MyDiet(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        FeatureVisible(false) {
            TodoBox(
                "Diyet Listesi",
                modifier = Modifier.fillMaxWidth().height(196.dp)
            )
        }
    }

    @Composable
    fun MyMeasurements(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.fillTransparent)
                .fillMaxWidth()
                .clickable { onAction(UserProfileAction.OnClickMeasurements) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SkyIcon(
                res = Res.drawable.ic_chart_pie,
                size = SkyIconSize.Large,
                modifier = Modifier
            )
            Spacer(Modifier.width(16.dp))
            SkyText(
                text = stringResource(Res.string.my_measurements_label),
                styleType = TextStyleType.BodyMediumSemibold
            )
            Spacer(Modifier.weight(1f))
            SkyIcon(
                res = Res.drawable.ic_arrow_right,
                size = SkyIconSize.Large,
                modifier = Modifier
            )
        }
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