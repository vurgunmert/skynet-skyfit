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
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.profile.model.ProfileDestination
import com.vurgun.skyfit.profile.user.model.UserProfileAction
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
        content: @Composable () -> Unit,
        modifier: Modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            header()
            Spacer(Modifier.height(20.dp))
            content()
        }
    }

    @Composable
    fun Header(
        backgroundImageUrl: String?,
        backgroundImageModifier: Modifier = Modifier.fillMaxWidth(),
        profileImageUrl: String?,
        cardContents: @Composable ColumnScope.() -> Unit,
        cardContentsModifier: Modifier = Modifier.fillMaxWidth().padding(top = 150.dp),
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
                modifier = backgroundImageModifier
                    .hazeSource(state = hazeState),
            )

            Column(
                modifier = cardContentsModifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(24.dp))
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
    fun HeaderNameGroup(
        firstName: String,
        userName: String
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkyText(
                text = firstName,
                styleType = TextStyleType.BodyLargeSemibold
            )
            Spacer(modifier = Modifier.width(8.dp))
            SkyText(
                text = userName,
                styleType = TextStyleType.BodySmallMedium,
                color = SkyFitColor.text.secondary
            )
        }
    }

    @Composable
    fun HeaderBodyGroup(
        leftItem: @Composable () -> Unit = { },
        centerItem: @Composable () -> Unit = { },
        rightItem: @Composable () -> Unit = { },
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            leftItem()
            centerItem()
            rightItem()
        }
    }

    @Composable
    fun HeaderEditorialDataItem(
        modifier: Modifier = Modifier,
        iconRes: DrawableResource? = null,
        title: String,
        subtitle: String
    ) {
        Box(
            modifier = modifier.wrapContentSize()
                .padding(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    iconRes?.let {
                        SkyIcon(res = iconRes, size = SkyIconSize.Medium)
                        Spacer(Modifier.width(2.dp))
                    }

                    SkyText(text = title, styleType = TextStyleType.BodyMediumSemibold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                SkyText(
                    text = subtitle,
                    styleType = TextStyleType.BodySmall,
                    color = SkyFitColor.text.secondary
                )
            }
        }

    }

    @Composable
    fun UserOwnerNavigationMenu(
        onTabSelected: (ProfileDestination) -> Unit,
        selectedTab: ProfileDestination,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier.fillMaxWidth()
    ) {
        NavigationMenuWithAction(
            onDestinationChanged = onTabSelected,
            destination = selectedTab,
            action = {
                val postsSelected = selectedTab == ProfileDestination.Posts
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
        onTabSelected: (ProfileDestination) -> Unit,
        selectedTab: ProfileDestination,
        onAction: (UserProfileAction) -> Unit,
        modifier: Modifier = Modifier.fillMaxWidth()
    ) {
        NavigationMenuWithAction(
            onDestinationChanged = onTabSelected,
            destination = selectedTab,
            action = {
                val postsSelected = selectedTab == ProfileDestination.Posts
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
    fun NavigationMenuAction(
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
                .padding(14.dp),
            size = SkyIconSize.Medium,
            onClick = onClick
        )
    }

    @Composable
    fun NavigationMenuWithAction(
        onDestinationChanged: (ProfileDestination) -> Unit,
        destination: ProfileDestination,
        action: @Composable (() -> Unit)? = null,
        modifier: Modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationMenuTabGroup(
                modifier = Modifier.weight(1f),
                onTabSelected = onDestinationChanged,
                selectedTab = destination,
            )

            if (action != null) {
                Spacer(Modifier.width(16.dp))

                action()
            }
        }
    }

    @Composable
    private fun NavigationMenuTabGroup(
        modifier: Modifier = Modifier.fillMaxWidth(),
        onTabSelected: (ProfileDestination) -> Unit,
        selectedTab: ProfileDestination
    ) {
        val aboutSelected = selectedTab == ProfileDestination.About

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
                    .clickable { onTabSelected(ProfileDestination.About) }
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
                    .clickable(onClick = { onTabSelected(ProfileDestination.Posts) })
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
}