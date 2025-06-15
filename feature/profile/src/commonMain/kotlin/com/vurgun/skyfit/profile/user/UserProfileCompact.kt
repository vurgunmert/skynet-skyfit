package com.vurgun.skyfit.profile.user

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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.global.model.BodyType
import com.vurgun.skyfit.core.data.v1.domain.global.model.HeightUnitType
import com.vurgun.skyfit.core.data.v1.domain.global.model.WeightUnitType
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.box.TodoBox
import com.vurgun.skyfit.core.ui.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.persona.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature.persona.components.UserProfileCardPreferenceRow
import com.vurgun.skyfit.feature.persona.profile.user.owner.*
import com.vurgun.skyfit.profile.component.ProfileCompactComponent
import com.vurgun.skyfit.profile.user.owner.UserProfileAction
import com.vurgun.skyfit.profile.user.owner.UserProfileEffect
import com.vurgun.skyfit.profile.user.owner.UserProfileUiState
import com.vurgun.skyfit.profile.user.owner.UserProfileViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.appointments_title
import skyfit.core.ui.generated.resources.ic_settings
import skyfit.core.ui.generated.resources.show_all_action

@Composable
fun UserProfileCompact(
    content: UserProfileUiState.Content,
    onAction: (UserProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    ProfileCompactComponent.Layout(
        header = {
            UserProfileCompactComponents.Header(content, onAction)
        },
        content = {
            UserProfileCompactComponents.NavigationGroup(content, onAction)
            UserProfileCompactComponents.Content(content, onAction)
        }
    )
}

@Composable
fun UserProfileCompact(viewModel: UserProfileViewModel) {
    val localNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            UserProfileEffect.NavigateBack -> {
                localNavigator.pop()
            }

            UserProfileEffect.NavigateToAppointments -> {
                appNavigator.push(SharedScreen.Appointments)
            }

            UserProfileEffect.NavigateToCreatePost -> {
                appNavigator.push(SharedScreen.CreatePost)
            }

            UserProfileEffect.NavigateToSettings -> {
                appNavigator.push(SharedScreen.Settings)
            }

            is UserProfileEffect.NavigateToVisitFacility -> {
                appNavigator.push(SharedScreen.FacilityProfileVisitor(effect.gymId))
            }
        }
    }


//        MobileUserProfileOwnerScreen(viewModel = viewModel)
    MobileUserProfileOwnerScreen_Expanded(viewModel)
}

internal object UserProfileCompactComponents {

    @Composable
    fun Header(
        content: UserProfileUiState.Content,
        action: (UserProfileAction) -> Unit
    ) {

        val userProfile = content.profile

        ProfileCompactComponent.Header(
            backgroundImageUrl = userProfile.backgroundImageUrl,
            profileImageUrl = userProfile.profileImageUrl,
            cardContents = {
                Spacer(Modifier.height(32.dp))
                HeaderNameGroup()
                Spacer(Modifier.height(12.dp))
                HeaderBodyGroup()
            }
        )

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
    fun HeaderNameGroup(
        firstName: String,
        userName: String
    ) {
        TodoBox("HeaderNameGroup")
    }

    @Composable
    fun HeaderBodyGroup(
        height: Int,
        heightUnitType: HeightUnitType = HeightUnitType.CM,
        weight: Int,
        weightUnitType: WeightUnitType = WeightUnitType.KG,
        bodyType: BodyType
    ) {
        TodoBox("HeaderBodyGroup")
    }

    @Composable
    fun NavigationGroup(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
    ) {
        if (content.isVisiting) {
            ProfileCompactComponent.NavigationMenuWithAction(
                onTabSelected = { onAction(UserProfileAction.ChangeRoute(it)) },
                selectedTab = content.route,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            ProfileCompactComponent.NavigationMenuWithAction(
                onTabSelected = { onAction(UserProfileAction.ChangeRoute(it)) },
                selectedTab = content.route,
                actionRes = Res.drawable.ic_settings,
                onActionClick = { onAction(UserProfileAction.ClickSettings) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun Content(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
    ) {
        //isVisiting
    }

    @Composable
    fun AboutContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
    ) {
        //isVisiting
    }

    @Composable
    fun PostsContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
    ) {
        //isVisiting
    }

    @Composable
    fun OwnerContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
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

                    Header(content.profile)

//                    ProfileCompactComponent.NavigationMenu(
//                        postsSelected = false,
//                        onClickAbout = { onAction(UserProfileAction.TogglePostVisibility(false)) },
//                        onClickPosts = { }, //onAction(UserProfileOwnerAction.TogglePostVisibility(true))
//                        onClickSettings = { onAction(UserProfileAction.ClickSettings) },
//                        onClickNewPost = { onAction(UserProfileAction.ClickCreatePost) }
//                    )

//                    if (content.postsVisible) {
//                        // TODO: Posts
//                    } else {
//                        MyAppointments(
//                            appointments = content.appointments,
//                            onClickItem = { onAction(UserProfileAction.ClickAppointments) },
//                            onClickShowAll = { onAction(UserProfileAction.ClickAppointments) },
//                            modifier = Modifier
//                        )
//                    }

                    Spacer(Modifier.height(124.dp))
                }
            }
        }
    }

    @Composable
    fun VisitorContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit
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

//                    HeaderCard(content.profile)
//
//                    MobileProfileActionsRow(
//                        onClickAbout = { onAction(UserProfileAction.TogglePostVisibility(false)) },
//                        onClickPosts = { }, //onAction(UserProfileOwnerAction.TogglePostVisibility(true))
//                        onClickSettings = { onAction(UserProfileAction.ClickSettings) },
//                        onClickNewPost = { onAction(UserProfileAction.ClickCreatePost) }
//                    )
//
//                    if (content.postsVisible) {
//                        // TODO: Posts
//                    } else {
//                        MyAppointments(
//                            appointments = content.appointments,
//                            onClickItem = { onAction(UserProfileAction.ClickAppointments) },
//                            onClickShowAll = { onAction(UserProfileAction.ClickAppointments) },
//                            modifier = Modifier
//                        )
//                    }

                    Spacer(Modifier.height(124.dp))
                }
            }
        }
    }

    @Composable
    fun MyMembershipPackages() {

    }

    @Composable
    fun MyAppointments(
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
    fun MyDiet() {

    }

    @Composable
    fun MyMeasurements() {

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