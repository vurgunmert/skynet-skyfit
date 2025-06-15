package com.vurgun.skyfit.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.profile.model.ProfileNavigationRoute
import com.vurgun.skyfit.profile.user.owner.UserProfileAction
import dev.chrisbanes.haze.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_plus
import skyfit.core.ui.generated.resources.ic_settings

internal object ProfileCompactComponent {

    @Composable
    fun Layout(
        header: @Composable () -> Unit,
        content: @Composable () -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            header()
            Spacer(Modifier.height(20.dp))
            content()
        }
    }

    @Composable
    fun Header(
        backgroundImageUrl: String?,
        profileImageUrl: String?,
        cardContents: @Composable ColumnScope.() -> Unit,
    ) {

        val hazeState = rememberHazeState()
        val hazeStyle = HazeStyle(
            backgroundColor = SkyFitColor.background.surfaceSecondary,
            tints = listOf(
                HazeTint(SkyFitColor.background.surfaceSecondary.copy(alpha = 0.5f))
            ),
            blurRadius = 20.dp,
            noiseFactor = 0f
        )

        Box(modifier = Modifier.fillMaxWidth()) {

            NetworkImage(
                imageUrl = backgroundImageUrl,
                modifier = Modifier
                    .sizeIn(maxWidth = 430.dp, maxHeight = 180.dp)
                    .hazeSource(state = hazeState),
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .hazeEffect(hazeState, hazeStyle)
                    .padding(24.dp),
                content = cardContents
            )

            NetworkImage(
                imageUrl = profileImageUrl,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 50.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .sizeIn(100.dp)
            )
        }
    }

    @Composable
    fun UserOwnerNavigationMenu(
        onTabSelected: (ProfileNavigationRoute) -> Unit,
        selectedTab: ProfileNavigationRoute,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier.fillMaxWidth()
    ) {
        NavigationMenuWithAction(
            onTabSelected = onTabSelected,
            selectedTab = selectedTab,
            action = {
                val postsSelected = selectedTab == ProfileNavigationRoute.Posts
                val action = when (postsSelected) {
                    true -> UserProfileAction.ClickCreatePost
                    false -> UserProfileAction.ClickSettings
                }

                Box(
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(SkyFitColor.background.surfaceSecondary)
                        .clickable(onClick = { onAction(action) })
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (postsSelected) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_plus),
                            contentDescription = "New Post",
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(Res.drawable.ic_settings),
                            contentDescription = "Settings",
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            modifier = modifier
        )
    }

    @Composable
    fun UserVisitorNavigationMenu(
        onTabSelected: (ProfileNavigationRoute) -> Unit,
        selectedTab: ProfileNavigationRoute,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier.fillMaxWidth()
    ) {
        NavigationMenuWithAction(
            onTabSelected = onTabSelected,
            selectedTab = selectedTab,
            action = {
                val postsSelected = selectedTab == ProfileNavigationRoute.Posts
                val action = when (postsSelected) {
                    true -> UserProfileAction.ClickCreatePost
                    false -> UserProfileAction.ClickSettings
                }

                Box(
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(SkyFitColor.background.surfaceSecondary)
                        .clickable(onClick = { onAction(action) })
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (postsSelected) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_plus),
                            contentDescription = "New Post",
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(Res.drawable.ic_settings),
                            contentDescription = "Settings",
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            modifier = modifier
        )
    }

    @Composable
    fun TrainerOwnerNavigationMenu() {

    }

    @Composable
    fun TrainerVisitorNavigationMenu() {

    }

    @Composable
    fun FacilityOwnerNavigationMenu() {

    }

    @Composable
    fun FacilityVisitorNavigationMenu() {

    }

    @Composable
    fun NavigationMenuWithAction(
        onTabSelected: (ProfileNavigationRoute) -> Unit,
        selectedTab: ProfileNavigationRoute,
        actionRes: DrawableResource? = null,
        onActionClick: (() -> Unit)? = null,
        modifier: Modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationMenuTabGroup(
                modifier = Modifier.weight(1f),
                onTabSelected = onTabSelected,
                selectedTab = selectedTab,
            )

            if (actionRes != null && onActionClick != null) {
                Spacer(Modifier.width(16.dp))

                NavigationAction(actionRes, onActionClick)
            }
        }
    }

    @Composable
    private fun NavigationMenuTabGroup(
        modifier: Modifier = Modifier.fillMaxWidth(),
        onTabSelected: (ProfileNavigationRoute) -> Unit,
        selectedTab: ProfileNavigationRoute
    ) {
        val aboutSelected = selectedTab == ProfileNavigationRoute.Activities

        Row(
            modifier
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(if (aboutSelected) 1f else 3f)
                    .background(
                        if (aboutSelected) SkyFitColor.specialty.buttonBgRest else SkyFitColor.background.surfaceSecondary,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { onTabSelected(ProfileNavigationRoute.Activities) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Hakkımda",
                    style = SkyFitTypography.bodyLargeMedium,
                    color = if (aboutSelected) SkyFitColor.text.inverse else SkyFitColor.text.default
                )
            }

            Box(
                modifier = Modifier
                    .weight(if (!aboutSelected) 3f else 1f)
                    .background(
                        if (!aboutSelected) SkyFitColor.specialty.buttonBgRest else SkyFitColor.background.surfaceSecondary,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable(onClick = { onTabSelected(ProfileNavigationRoute.Posts) })
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Paylaşımlar",
                    style = SkyFitTypography.bodyLargeMedium,
                    color = if (!aboutSelected) SkyFitColor.text.inverse else SkyFitColor.text.default
                )
            }
        }
    }


    @Composable
    private fun NavigationAction(
        res: DrawableResource,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        SkyIcon(
            res = res,
            modifier = modifier
                .background(
                    color = SkyFitColor.background.surfaceSecondary,
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .padding(16.dp),
            size = SkyIconSize.Medium,
            onClick = onClick
        )
    }
}