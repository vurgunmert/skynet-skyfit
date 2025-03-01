package com.vurgun.skyfit.feature_profile.ui.user

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.core.ui.resources.MobileStyleGuide
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.feature_profile.ui.components.LifestyleActionRow
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature_profile.ui.trainer.SkyFitProfileClassItemComponent
import com.vurgun.skyfit.feature_social.ui.PostViewData
import com.vurgun.skyfit.feature_social.ui.SkyFitPostCardItemComponent
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_arrow_right
import skyfit.composeapp.generated.resources.ic_calories
import skyfit.composeapp.generated.resources.ic_chart_pie
import skyfit.composeapp.generated.resources.ic_clock
import skyfit.composeapp.generated.resources.ic_dna
import skyfit.composeapp.generated.resources.ic_exercises
import skyfit.composeapp.generated.resources.ic_height
import skyfit.composeapp.generated.resources.ic_meal
import skyfit.composeapp.generated.resources.ic_overweight
import skyfit.composeapp.generated.resources.ic_path_distance
import skyfit.composeapp.generated.resources.ic_plus
import skyfit.composeapp.generated.resources.ic_settings
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserProfileScreen(navigator: Navigator) {
    val viewModel = remember { SkyFitUserProfileViewModel() }

    // Observing state from ViewModel
    val profileData by viewModel.profileData.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val appointments by viewModel.appointments.collectAsState()
    val showPosts by viewModel.showPosts.collectAsState()
    val showInfoMini by viewModel.showInfoMini.collectAsState()
    val exercisesRowViewData by viewModel.exercisesRowViewData.collectAsState()
    val habitsRowData by viewModel.habitsRowViewData.collectAsState()
    val statistics by viewModel.statistics.collectAsState()
    val photoDiary by viewModel.photoDiary.collectAsState()

    val scrollState = rememberScrollState()
    val postListState = rememberLazyListState()

    // Sync scroll values with ViewModel
    LaunchedEffect(scrollState.value, postListState.firstVisibleItemIndex) {
        viewModel.updateScroll(scrollState.value, postListState.firstVisibleItemIndex)
    }

    SkyFitScaffold(
        topBar = {
            MobileUserProfileTopBarGroupComponent(
                viewData = profileData.copy(showInfoMini = showInfoMini),
                viewModel = viewModel,
                navigator = navigator,
                showPosts = showPosts
            )
        }
    ) {
        if (showPosts) {
            MobileUserProfilePostsComponent(posts = posts, listState = postListState)
        } else {
            MobileUserProfileAboutGroupComponent(
                scrollState,
                navigator,
                appointments,
                exercisesRowViewData,
                habitsRowData,
                statistics,
                photoDiary
            )
        }
    }
}

//region Profile Header Group
@Composable
fun MobileUserProfileTopBarGroupComponent(
    viewData: TopBarGroupViewData,
    viewModel: SkyFitUserProfileViewModel,
    navigator: Navigator,
    showPosts: Boolean
) {
    BoxWithConstraints {
        val width = maxWidth
        val imageHeight = width * 9 / 16
        val contentTopPadding = imageHeight * 3 / 10

        // 🔹 Background Image
        if (!viewData.showInfoMini) {
            MobileUserProfileBackgroundImageComponent(imageHeight)
        }

        Column(
            Modifier
                .padding(top = if (viewData.showInfoMini) 16.dp else contentTopPadding)
                .fillMaxWidth()
        ) {
            if (viewData.showInfoMini) {
                MobileUserProfileInfoCardMiniComponent(viewData)
            } else {
                MobileUserProfileInfoCardComponent(viewData)
            }

            Spacer(Modifier.height(16.dp))

            MobileUserProfileActionsComponent(
                showPosts = showPosts,
                onClickAbout = { viewModel.toggleShowPosts(false) },
                onClickPosts = { viewModel.toggleShowPosts(true) },
                onClickSettings = { navigator.jumpAndStay(NavigationRoute.UserSettings) },
                onClickNewPost = { navigator.jumpAndStay(NavigationRoute.UserSocialMediaPostAdd) }
            )
        }
    }
}

@Composable
fun MobileUserProfileBackgroundImageComponent(height: Dp) {
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
fun MobileUserProfileInfoCardComponent(viewData: TopBarGroupViewData) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(top = 70.dp)
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
                    .padding(start = 16.dp, top = 36.dp, end = 16.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewData.name,
                        style = SkyFitTypography.bodyLargeSemibold
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Space between name and social link
                    Text(
                        text = viewData.social,
                        style = SkyFitTypography.bodySmallMedium,
                        color = SkyFitColor.text.secondary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (viewData.preferences.isNotEmpty()) {
                    UserProfileCardPreferenceRow(Modifier.fillMaxWidth())
                }
            }
        }

        AsyncImage(
            model = viewData.imageUrl,
            contentDescription = "Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun MobileUserProfileInfoCardMiniComponent(viewData: TopBarGroupViewData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = viewData.imageUrl,
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(46.dp).clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.width(8.dp))
            Text(text = viewData.name, style = SkyFitTypography.bodyLargeSemibold)
            Text(text = viewData.social, style = SkyFitTypography.bodySmallMedium, color = SkyFitColor.text.secondary)
        }

        UserProfileCardPreferenceRow(Modifier.fillMaxWidth())
    }
}


@Composable
fun UserProfileCardPreferenceRow(modifier: Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = Res.drawable.ic_height,
            title = "175",
            subtitle = "Boy (cm)"
        )
        VerticalDivider(Modifier.padding(horizontal = 12.dp))
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = Res.drawable.ic_dna,
            title = "63",
            subtitle = "Kilo (kg)"
        )
        VerticalDivider(Modifier.padding(horizontal = 12.dp))
        UserProfileCardPreferenceItem(
            modifier = Modifier.weight(1f),
            iconRes = Res.drawable.ic_overweight,
            title = "Ecto",
            subtitle = "Vücut Tipi"
        )
    }
}

@Composable
fun UserProfileCardPreferenceItem(
    modifier: Modifier = Modifier,
    iconRes: DrawableResource,
    title: String,
    subtitle: String
) {
    Column(modifier = modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = SkyFitColor.icon.default
            )
            Spacer(Modifier.width(2.dp))
            Text(text = title, style = SkyFitTypography.bodyMediumSemibold, color = SkyFitColor.text.default)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = subtitle, style = SkyFitTypography.bodySmall, color = SkyFitColor.text.secondary)
    }
}
//endregion Profile Header Group

@Composable
fun MobileUserProfileAboutGroupComponent(
    scrollState: ScrollState,
    navigator: Navigator,
    appointments: List<SkyFitClassCalendarCardItem> = emptyList(),
    exercisesRowData: LifestyleActionRowViewData? = null,
    habitsRowData: LifestyleActionRowViewData? = null,
    statistics: UserProfileActivityStatisticsViewData? = null,
    photoDiary: UserProfilePhotoDiaryViewData? = null
) {
    var dietGoals: List<Any> = listOf(1, 2, 3)
    var showMeasurements: Boolean = true

    Column(
        modifier = Modifier
            .widthIn(max = MobileStyleGuide.screenWithMax)
            .fillMaxHeight()
            .padding(MobileStyleGuide.padding16)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (appointments.isNotEmpty()) {
            MobileUserProfileAppointmentsComponent(
                appointments = appointments,
                onClick = { navigator.jumpAndStay(NavigationRoute.UserAppointments) })
        }

        if (dietGoals.isEmpty()) {
            MobileUserProfileDietGoalsEmptyComponent(onClickAdd = { })
        } else {
            MobileUserProfileDietGoalsComponent(dietGoals)
        }

        if (showMeasurements) {
            MobileUserProfileMeasurementsComponent(onClick = {
                navigator.jumpAndStay(NavigationRoute.UserMeasurements)
            })
        }

        if (statistics != null) {
            MobileUserProfileStatisticsBarsComponent(statistics)
        }

        if (exercisesRowData != null) {
            LifestyleActionRow(viewData = exercisesRowData)
        } else {
            MobileUserProfileScreenExploreExercisesComponent {
                navigator.jumpAndStay(NavigationRoute.ExploreExercises)
            }
        }

        if (photoDiary == null) {
            MobileUserProfilePhotoDiaryEmptyComponent(onClickAdd = {
                navigator.jumpAndStay(NavigationRoute.UserPhotoDiary)
            })
        } else {
            MobileUserProfilePhotoDiaryComponent(photoDiary,
                onClickAdd = {
                    navigator.jumpAndStay(NavigationRoute.UserPhotoDiary)
                })
        }

        if (habitsRowData != null) {
            LifestyleActionRow(viewData = habitsRowData)
        }

        Spacer(Modifier.height(124.dp))
    }
}

@Composable
fun MobileUserProfileActionsComponent(
    showPosts: Boolean,
    onClickAbout: () -> Unit,
    onClickPosts: () -> Unit,
    onClickSettings: () -> Unit,
    onClickNewPost: () -> Unit
) {
    Row(
        Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MobileVisitedProfileActionsComponent(
            modifier = Modifier.weight(1f),
            onClickAbout = onClickAbout,
            onClickPosts = onClickPosts
        )

        Box(
            Modifier
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                .clickable(onClick = if (showPosts) onClickNewPost else onClickSettings)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            if (showPosts) {
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
    }
}

@Composable
fun MobileUserProfileAppointmentsComponent(appointments: List<SkyFitClassCalendarCardItem>, onClick: () -> Unit = {}) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                painter = painterResource(Res.drawable.ic_exercises),
                modifier = Modifier.size(24.dp),
                contentDescription = "Exercise",
                tint = SkyFitColor.icon.default
            )
            Text(
                text = "Randevularım",
                style = SkyFitTypography.bodyMediumSemibold
            )
        }

        appointments.forEach {
            SkyFitProfileClassItemComponent(
                item = it,
                onClick = onClick
            )
        }
    }
}


@Composable
fun MobileUserProfileDietGoalsComponent(records: List<Any>) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                painter = painterResource(Res.drawable.ic_meal),
                modifier = Modifier.size(24.dp),
                contentDescription = "",
                tint = SkyFitColor.icon.default
            )
            Text(
                text = "Diyet Listesi",
                style = SkyFitTypography.bodyMediumSemibold
            )
        }
        records.forEach {
            Spacer(Modifier.height(16.dp))
            MobileUserProfileDietGoalItemComponent()
        }
    }
}

@Composable
private fun MobileUserProfileDietGoalItemComponent() {
    Row(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Peynirli Salata",
                style = SkyFitTypography.bodySmall
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "7 gün sadece kahvaltıda",
                style = SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.default)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "234 kcal",
                style = SkyFitTypography.bodyLarge.copy(color = SkyFitColor.text.default)
            )
        }
        Spacer(Modifier.width(12.dp))
        AsyncImage(
            model = "https://ik.imagekit.io/skynet2skyfit/meal_yogurt_granola.png?updatedAt=1738866100967",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(74.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun MobileUserProfileDietGoalsEmptyComponent(onClickAdd: () -> Unit) {
    Column(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent)
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                modifier = Modifier.size(24.dp),
                contentDescription = ""
            )
            Text(
                text = "Diyet Listesi",
                style = SkyFitTypography.bodyMediumSemibold
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 96.dp),
            contentAlignment = Alignment.Center
        ) {

            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(), text = "Diyet Listesi Oluştur",
                onClick = onClickAdd,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Medium,
                state = ButtonState.Rest
            )
        }
    }
}

@Composable
fun MobileUserProfileMeasurementsComponent(onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent, shape = RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_chart_pie),
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
            painter = painterResource(Res.drawable.ic_arrow_right),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = SkyFitColor.icon.default
        )
    }
}

data class UserProfileActivityStatisticsViewData(
    val calories: String = "452",
    val calorieUnit: String = "kcal",
    val time: String = "02:30",
    val timeUnit: String = "saat",
    val distance: String = "3.2",
    val distanceUnit: String = "km",
)

@Composable
fun MobileUserProfileStatisticsBarsComponent(viewData: UserProfileActivityStatisticsViewData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StatisticCard(
            title = "Kalori",
            value = viewData.calories,
            unit = viewData.calorieUnit,
            color = SkyFitColor.specialty.statisticOrange,
            iconRes = Res.drawable.ic_calories
        )
        Spacer(Modifier.width(16.dp))
        StatisticCard(
            title = "Zaman",
            value = viewData.time,
            unit = viewData.timeUnit,
            color = SkyFitColor.specialty.statisticBlue,
            iconRes = Res.drawable.ic_clock
        )
        Spacer(Modifier.width(16.dp))
        StatisticCard(
            title = "Mesafe",
            value = viewData.distance,
            unit = viewData.distanceUnit,
            color = SkyFitColor.specialty.statisticPink,
            iconRes = Res.drawable.ic_path_distance
        )
    }
}

@Composable
fun StatisticCard(
    title: String,
    value: String,
    unit: String,
    color: Color,
    iconRes: DrawableResource
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .background(color)
            .padding(top = 16.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(40.dp).background(SkyFitColor.background.fillTransparentBlur, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(iconRes),
                    contentDescription = title,
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(text = title, style = SkyFitTypography.bodyMediumRegular, color = SkyFitColor.text.inverse)
        }
        Spacer(Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, style = SkyFitTypography.heading3, color = SkyFitColor.text.inverse)
            Text(text = unit, style = SkyFitTypography.bodyMediumSemibold, color = SkyFitColor.text.inverse)
        }
    }
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
            modifier = Modifier.wrapContentWidth(), text = "Antrenman Keşfet",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Medium,
            state = ButtonState.Rest
        )
    }
}

data class UserProfilePhotoDiaryViewData(
    val imageUrls: List<String> = listOf(
        "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
        "https://gymstudiohome.com/assets/img/slide/1.jpg",
    ),
    val lastUpdatedDate: String = "Çarşamba, 28 Ağustos"
)

@Composable
fun MobileUserProfilePhotoDiaryComponent(
    viewData: UserProfilePhotoDiaryViewData,
    onClickAdd: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClickAdd)
    ) {
        val imageSize = maxWidth
        val image2Size = maxWidth - 16.dp
        val image3Size = maxWidth - 32.dp

        AsyncImage(
            model = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(image3Size)
                .clip(RoundedCornerShape(16.dp))
        )

        AsyncImage(
            model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
                .size(image2Size)
                .clip(RoundedCornerShape(16.dp))
        )

        AsyncImage(
            model = "https://gymstudiohome.com/assets/img/slide/1.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .size(imageSize)
                .clip(RoundedCornerShape(16.dp))
        )

        Box(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Fotoğraf Günlüğüm",
                    style = SkyFitTypography.heading4,
                )
                Text(
                    text = viewData.lastUpdatedDate, style = SkyFitTypography.bodyMediumRegular,
                    color = SkyFitColor.text.secondary
                )
            }
        }
    }
}

@Composable
private fun MobileUserProfilePhotoDiaryEmptyComponent(onClickAdd: () -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(20.dp))
    ) {
        val componentSize = maxWidth

        Box(Modifier.size(componentSize), contentAlignment = Alignment.Center) {
            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(), text = "Fotoğraf Ekle",
                onClick = onClickAdd,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Medium,
                state = ButtonState.Rest,
                leftIconPainter = painterResource(Res.drawable.logo_skyfit)
            )
        }

        Box(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Fotoğraf Günlüğüm",
                style = SkyFitTypography.heading4,
            )
        }
    }
}

@Composable
fun MobileUserProfilePostsInputComponent(
    onClickSend: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current // For closing keyboard

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Profile Image
        AsyncImage(
            model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )

        // TextField - No Background, No Underline, Expands Vertically
        BasicTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
                .wrapContentHeight(), // Expands when needed
            textStyle = SkyFitTypography.bodyLarge.copy(color = SkyFitColor.text.default),

            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (textFieldValue.isNotBlank()) { // Only send if text is not empty
                        onClickSend(textFieldValue.trim())
                        textFieldValue = "" // Clear input
                        keyboardController?.hide() // Close keyboard ✅
                    }
                }
            ),
            singleLine = false, // Allows multi-line input
            decorationBox = { innerTextField ->
                Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (textFieldValue.isEmpty()) {
                        Text(
                            text = "Bugünkü motivasyonunu paylaş",
                            style = SkyFitTypography.bodyLarge,
                            color = SkyFitColor.text.secondary
                        )
                    }
                    innerTextField()
                }
            }
        )

        // Image Picker Icon
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Image Picker",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(20.dp)
        )
    }
}


@Composable
fun MobileUserProfilePostsComponent(
    posts: List<PostViewData>,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(Modifier.height(16.dp))
            MobileUserProfilePostsInputComponent(onClickSend = {})
        }

        items(posts) {
            SkyFitPostCardItemComponent(
                data = it,
                onClick = {},
                onClickComment = {},
                onClickLike = {},
                onClickShare = {}
            )
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}


@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = SkyFitColor.border.default,
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp
) {
    val indentMod = if (startIndent.value != 0f) {
        Modifier.padding(start = startIndent)
    } else {
        Modifier
    }
    val targetThickness = if (thickness == Dp.Hairline) {
        (1f / LocalDensity.current.density).dp
    } else {
        thickness
    }
    Box(
        modifier.then(indentMod)
            .height(52.dp)
            .width(targetThickness)
            .background(color = color)
    )
}
