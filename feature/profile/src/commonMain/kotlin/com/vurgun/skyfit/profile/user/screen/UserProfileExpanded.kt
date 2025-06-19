package com.vurgun.skyfit.profile.user.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.box.TodoBox
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.profile.SocialPostCard
import com.vurgun.skyfit.core.ui.components.profile.SocialQuickPostInputCard
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.profile.component.ProfileCompactComponent
import com.vurgun.skyfit.profile.component.ProfileExpandedComponent
import com.vurgun.skyfit.profile.model.ProfileDestination
import com.vurgun.skyfit.profile.user.model.UserProfileAction
import com.vurgun.skyfit.profile.user.model.UserProfileUiState
import com.vurgun.skyfit.profile.user.model.UserProfileViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
fun UserProfileExpanded(
    viewModel: UserProfileViewModel,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                        content,
                        viewModel::onAction,
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
                            UserProfileExpandedComponent.PostsContent(content,viewModel::onAction)
                        }
                    }
                },
                modifier = modifier
            )
        }
    }
}

private object UserProfileExpandedComponent {

    @Composable
    fun Header(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val userProfile = content.profile

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
    }

    @Composable
    fun AboutContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        FlowColumn(
            modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TodoBox("PostsContent", Modifier.size(372.dp, 392.dp))
            TodoBox("PostsContent", Modifier.size(372.dp, 392.dp))
            TodoBox("PostsContent", Modifier.size(372.dp, 392.dp))
        }
    }

    @Composable
    fun PostsContent(
        content: UserProfileUiState.Content,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.fillMaxSize().widthIn(max = 680.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            SocialQuickPostInputCard(
                modifier = Modifier.widthIn(max = 680.dp)
            ) { onAction(UserProfileAction.OnClickNewPost) }

            content.posts.forEach { post ->
                SocialPostCard(
                    post,
                    onClick = {},
                    onClickComment = {},
                    onClickLike = {},
                    onClickShare = {})
            }
        }
    }
}