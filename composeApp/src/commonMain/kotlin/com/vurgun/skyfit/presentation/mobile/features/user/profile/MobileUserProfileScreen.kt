package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItem
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItemComponent
import com.vurgun.skyfit.presentation.shared.features.user.SkyFitUserProfileViewModel
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserProfileScreen(navigator: Navigator) {

    val scrollState = rememberScrollState()
    val postListState = rememberLazyListState()
    var showPosts by remember { mutableStateOf(false) }
    val showInfoMini by remember {
        derivedStateOf { scrollState.value > 30 || postListState.firstVisibleItemIndex > 1 }
    }

    val viewModel = SkyFitUserProfileViewModel()
    val posts = viewModel.posts

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            BoxWithConstraints {
                val width = maxWidth
                val imageHeight = width * 9 / 16
                val contentTopPadding = imageHeight * 3 / 10

                MobileUserProfileBackgroundImageComponent(imageHeight)

                Column(
                    Modifier
                        .padding(top = contentTopPadding)
                        .fillMaxWidth()
                ) {
                    if (showInfoMini) {
                        MobileUserProfileInfoCardMiniComponent(
                            name = "Dexter Moore",
                            social = "@dexteretymo",
                            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
                            preferences = listOf(
                                ProfilePreferenceItem("Boy", "175"),
                                ProfilePreferenceItem("Kilo", "175"),
                                ProfilePreferenceItem("Vucut Tipi", "Ecto"),
                            ))
                    } else {
                        MobileUserProfileInfoCardComponent(
                            name = "Dexter Moore",
                            social = "@dexteretymo",
                            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
                            preferences = listOf(
                                ProfilePreferenceItem("Boy", "175"),
                                ProfilePreferenceItem("Kilo", "175"),
                                ProfilePreferenceItem("Vucut Tipi", "Ecto"),
                            )
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    MobileUserProfileActionsComponent(
                        onClickAbout = { showPosts = false },
                        onClickPosts = { showPosts = true },
                        onClickSettings = {}
                    )
                }
            }
        }
    ) {
        if (showPosts) {
            MobileUserProfilePostsComponent(posts = posts, listState = postListState)
        } else {
            MobileUserProfileAboutGroupComponent(scrollState, navigator)
        }
    }
}

@Composable
private fun MobileUserProfileBackgroundImageComponent(height: Dp) {
    AsyncImage(
        model = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    )
}

@Composable
private fun MobileUserProfileAboutGroupComponent(scrollState: ScrollState, navigator: Navigator) {
    var appointments: List<Any> = emptyList()
    var dietGoals: List<Any> = emptyList()
    var showMeasurements: Boolean = true
    var exerciseHistory: List<Any> = emptyList()
    var photos: List<Any> = emptyList()
    var statistics: List<Any> = emptyList()
    var habits: List<Any> = emptyList()
    var posts: List<Any> = emptyList()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (appointments.isNotEmpty()) {
            MobileUserProfileAppointmentsComponent()
        }
        if (dietGoals.isNotEmpty()) {
            MobileUserProfileDietGoalsEmptyComponent()
        } else {
            MobileUserProfileDietGoalsComponent()
        }

        if (showMeasurements) {
            Spacer(Modifier.height(16.dp))
            MobileUserProfileMeasurementsComponent(onClick = {
                navigator.jumpAndStay(SkyFitNavigationRoute.UserMeasurements)
            })
        }

        if (statistics.isNotEmpty()) {
            MobileUserProfileStatisticsBarsComponent()
        }

        if (exerciseHistory.isEmpty()) {
            MobileUserProfileScreenExploreExercisesComponent {
                navigator.jumpAndStay(SkyFitNavigationRoute.DashboardExploreExercises)
            }
        } else {
            MobileUserProfileExerciseHistoryComponent()
        }

        if (photos.isEmpty()) {
            MobileUserProfilePhotoDiaryEmptyComponent()
        } else {
            MobileUserProfilePhotoDiaryComponent()
        }

        if (habits.isNotEmpty()) {
            MobileUserProfileHabitsComponent()
        }
    }
}

@Composable
private fun MobileUserProfileActionsComponent(
    onClickAbout: () -> Unit,
    onClickPosts: () -> Unit,
    onClickSettings: () -> Unit
) {

    Row(Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
        MobileVisitedProfileActionsComponent(
            Modifier.weight(1f),
            onClickAbout,
            onClickPosts
        )
        Spacer(Modifier.width(16.dp))
        Box(
            Modifier.size(56.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                .clickable(onClick = onClickSettings), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Settings",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun MobileUserProfileAppointmentsComponent() {
    TodoBox("MobileUserProfileAppointmentsComponent", Modifier.size(398.dp, 352.dp))
}

@Composable
private fun MobileUserProfileDietGoalsComponent() {
    TodoBox("MobileUserProfileDietGoalsComponent", Modifier.size(382.dp, 392.dp))
}

@Composable
private fun MobileUserProfileDietGoalsEmptyComponent() {
    TodoBox("MobileUserProfileDietGoalsEmptyComponent", Modifier.size(382.dp, 390.dp))
}

@Composable
private fun MobileUserProfileMeasurementsComponent(onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent, shape = RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = SkyFitColor.icon.default
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Ölçümlerim",
            style = SkyFitTypography.bodyMediumSemibold,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = SkyFitColor.icon.default
        )
    }
}

@Composable
private fun MobileUserProfileStatisticsBarsComponent() {
    TodoBox("MobileUserProfileStatisticsBarsComponent", Modifier.size(382.dp, 250.dp))
}

@Composable
private fun MobileUserProfileExerciseHistoryComponent() {
    TodoBox("MobileUserProfileExerciseHistoryComponent", Modifier.size(382.dp, 162.dp))
}

@Composable
private fun MobileUserProfileScreenExploreExercisesComponent(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 34.dp),
        contentAlignment = Alignment.Center
    ) {

        SkyFitButtonComponent(
            Modifier.wrapContentWidth(), text = "Antrenman Keşfet",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Medium,
            initialState = ButtonState.Rest
        )
    }
}

@Composable
private fun MobileUserProfilePhotoDiaryComponent() {
    TodoBox("MobileUserProfilePhotoDiaryComponent", Modifier.size(382.dp, 410.dp))
}

@Composable
private fun MobileUserProfilePhotoDiaryEmptyComponent() {
    TodoBox("MobileUserProfilePhotoDiaryEmptyComponent", Modifier.size(374.dp, 374.dp))
}

@Composable
private fun MobileUserProfileHabitsComponent() {
    TodoBox("MobileUserProfileHabitsComponent", Modifier.size(382.dp, 162.dp))
}

@Composable
private fun MobileUserProfilePostsInputComponent() {
    Row(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        TextField(
            value = "Bugunku motivasyonu paylas",
            onValueChange = {},
            Modifier.padding(horizontal = 8.dp).weight(1f)
        )
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Image Picker",
            tint = SkyFitColor.icon.inverseSecondary,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun MobileUserProfilePostsComponent(
    posts: List<SkyFitPostCardItem>,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(Modifier.height(16.dp))
            MobileUserProfilePostsInputComponent()
        }

        items(posts) {
            SkyFitPostCardItemComponent(it,
                onClick = {},
                onClickComment = {},
                onClickLike = {},
                onClickShare = {})
        }
    }
}

data class ProfilePreferenceItem(val title: String, val subtitle: String)


@Composable
private fun MobileUserProfileInfoCardComponent(
    name: String,
    social: String,
    imageUrl: String,
    preferences: List<ProfilePreferenceItem>
) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(top = 120.dp)
                .padding(horizontal = 16.dp)
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
                        text = name,
                        style = SkyFitTypography.bodyLargeSemibold
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Space between name and social link
                    Text(
                        text = social,
                        style = SkyFitTypography.bodySmallMedium,
                        color = SkyFitColor.text.secondary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (preferences.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        preferences.forEachIndexed { index, item ->
                            ProfileCardPreferenceItem(item.title, item.subtitle)
                            if (index < preferences.lastIndex) {
                                HeaderVerticalDivider()
                            }
                        }
                    }
                }
            }
        }

        AsyncImage(
            model = imageUrl,
            contentDescription = "Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 50.dp)
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun MobileUserProfileInfoCardMiniComponent(
    name: String,
    social: String,
    imageUrl: String,
    habits: List<Any> = listOf(1, 2, 3),
    preferences: List<ProfilePreferenceItem>
) {

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(Modifier.width(8.dp))
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = name,
                        style = SkyFitTypography.bodyLargeSemibold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = social,
                        style = SkyFitTypography.bodySmallMedium,
                        color = SkyFitColor.text.secondary
                    )
                }

                Spacer(Modifier.width(8.dp))
                SkyFitIconButton(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(8.dp))
                SkyFitIconButton(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(8.dp))
                SkyFitIconButton(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    modifier = Modifier.size(24.dp)
                )
            }

            if (preferences.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    preferences.forEachIndexed { index, item ->
                        ProfileCardPreferenceItem(item.title, item.subtitle)
                        if (index < preferences.lastIndex) {
                            HeaderVerticalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileCardPreferenceItem(title: String, subtitle: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = SkyFitColor.icon.default
            )
            Text(text = title, style = SkyFitTypography.bodyMediumSemibold, color = Color.White)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = subtitle, style = SkyFitTypography.bodySmall, color = Color.Gray)
    }
}

@Composable
private fun HeaderVerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(48.dp)
            .background(SkyFitColor.border.default)
    )
}