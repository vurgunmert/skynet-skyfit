package com.vurgun.skyfit.profile.user.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.ui.components.box.TodoBox
import com.vurgun.skyfit.core.ui.components.button.SkyFitPrimaryCircularBackButton
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.profile.SocialPostCard
import com.vurgun.skyfit.core.ui.components.profile.SocialQuickPostInputCard
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import com.vurgun.skyfit.profile.component.ProfileCompactComponent
import com.vurgun.skyfit.profile.component.ProfileExpandedComponent
import com.vurgun.skyfit.profile.model.ProfileDestination
import com.vurgun.skyfit.profile.user.model.UserProfileAction
import com.vurgun.skyfit.profile.user.model.UserProfileEffect
import com.vurgun.skyfit.profile.user.model.UserProfileUiState
import com.vurgun.skyfit.profile.user.model.UserProfileViewModel
import com.vurgun.skyfit.profile.user.screen.UserProfileCompactComponent.Appointments
import com.vurgun.skyfit.profile.user.screen.UserProfileCompactComponent.MyDiet
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
fun UserProfileExpanded(
    viewModel: UserProfileViewModel,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val localNavigator = LocalNavigator.currentOrThrow
    val appNavigator = localNavigator.findRootNavigator()
    val overlayController = LocalCompactOverlayController.current
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            UserProfileEffect.NavigateBack -> {
                localNavigator.pop()
            }

            UserProfileEffect.NavigateToAppointments -> {
                overlayController?.invoke(SharedScreen.UserAppointmentListing)
            }

            UserProfileEffect.NavigateToCreatePost -> {
                overlayController?.invoke(SharedScreen.NewPost)
            }

            UserProfileEffect.NavigateToSettings -> {
                localNavigator.replace(SharedScreen.Settings)
            }

            is UserProfileEffect.NavigateToVisitFacility -> {
                localNavigator.replace(SharedScreen.FacilityProfile(effect.gymId))
            }
        }
    }

    when (uiState) {
        UserProfileUiState.Loading ->
            FullScreenLoaderContent()

        is UserProfileUiState.Error -> {
            val message = (uiState as UserProfileUiState.Error).message
            ErrorScreen(message = message) { viewModel.refreshData() }
        }

        is UserProfileUiState.Content -> {
            val content = uiState as UserProfileUiState.Content

            ProfileExpandedComponent.Layout(
                header = {
                    UserProfileExpandedComponent.Header(
                        content = content,
                        onAction = viewModel::onAction,
                        canNavigateBack = content.isVisiting,
                        onClickBack = { viewModel.onAction(UserProfileAction.ClickBack) },
                        modifier = Modifier.fillMaxWidth().height(284.dp)
                    )
                },
                content = {
                    when (content.destination) {
                        ProfileDestination.About -> {
                            UserProfileExpandedComponent.AboutContent(
                                content,
                                viewModel::onAction,
                                modifier = Modifier.fillMaxWidth().weight(1f)
                            )
                        }

                        ProfileDestination.Measurements -> {
                            UserProfileMeasurementContent(
                                content,
                                viewModel::onAction,
                                modifier = Modifier.fillMaxWidth().weight(1f)
                            )
                        }

                        ProfileDestination.Posts -> {
                            UserProfileExpandedComponent.PostsContent(content, viewModel::onAction)
                        }
                    }
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun UserProfileMeasurementContent(
    content: UserProfileUiState.Content,
    onAction: (UserProfileAction) -> Unit,
    modifier: Modifier
) {
    TodoBox(
        "OLCUMLER",
        modifier = modifier.widthIn(max = 680.dp)
    )
}

private object UserProfileExpandedComponent {


    @Composable
    fun Header(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        canNavigateBack: Boolean = false,
        onClickBack: (() -> Unit)? = null,
        modifier: Modifier = Modifier
    ) {
        val userProfile = content.profile

        Box(modifier.fillMaxWidth()) {
            ProfileExpandedComponent.Header(
                backgroundImageUrl = userProfile.backgroundImageUrl,
                profileImageUrl = userProfile.profileImageUrl,
                leftContent = {
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
                centerContent = {
                    ProfileCompactComponent.HeaderNameGroup(
                        firstName = userProfile.firstName,
                        userName = userProfile.username
                    )
                },
                rightContent = {
                    ProfileExpandedComponent.HeaderNavigationGroup(
                        destination = content.destination,
                        onClickAbout = { onAction(UserProfileAction.OnDestinationChanged(ProfileDestination.About)) },
                        onClickPosts = { onAction(UserProfileAction.OnDestinationChanged(ProfileDestination.Posts)) },
                        onClickMeasurements = { onAction(UserProfileAction.OnDestinationChanged(ProfileDestination.Measurements)) },
                        onClickShare = null
                    )
                }
            )

            if (canNavigateBack) {
                SkyFitPrimaryCircularBackButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 48.dp, start = 24.dp)
                        .size(48.dp),
                    onClick = { onClickBack?.invoke() }
                )
            }
        }
    }

    @Composable
    fun AboutContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(modifier.fillMaxSize()) {

            Column(modifier = Modifier.weight(1f)) {
                UserProfileCompactComponent.MyLessonPackages(content, onAction, modifier = Modifier.fillMaxWidth())
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {

                Appointments(content, onAction)

                Spacer(Modifier.height(16.dp))

                MyDiet(content, onAction, modifier = Modifier.fillMaxWidth())
            }
        }
    }

    @Composable
    fun PostsContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
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
}