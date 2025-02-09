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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardViewData
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardItemComponent
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitIconButton
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
                            )
                        )
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
            MobileUserProfileAboutGroupComponent(scrollState, navigator, viewModel)
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
fun MobileUserProfileAboutGroupComponent(
    scrollState: ScrollState,
    navigator: Navigator,
    viewModel: SkyFitUserProfileViewModel
) {
    var dietGoals: List<Any> = listOf(1, 2, 3)
    var showMeasurements: Boolean = true
    var exerciseHistory: List<Any> = listOf(1, 2, 3)
    var photos: List<Any> = emptyList()
    var statistics: List<Any> = emptyList()
    var habits: List<Any> = emptyList()
    var posts: List<Any> = emptyList()
    val appointments = viewModel.appointments.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (appointments.value.isEmpty()) {
            MobileUserProfileAppointmentsComponent(appointments.value)
        }
        if (dietGoals.isEmpty()) {
            MobileUserProfileDietGoalsEmptyComponent(onClickAdd = { })
        } else {
            MobileUserProfileDietGoalsComponent(dietGoals)
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
            MobileUserProfileExerciseHistoryComponent(exerciseHistory)
        }

        if (photos.isEmpty()) {
            MobileUserProfilePhotoDiaryEmptyComponent(onClickAdd = {})
        } else {
            MobileUserProfilePhotoDiaryComponent()
        }

        if (habits.isNotEmpty()) {
            MobileUserProfileHabitsComponent(habits)
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
fun MobileUserProfileAppointmentsComponent(appointments: List<AppointmentCardViewData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        appointments.take(3).forEach {
            AppointmentCardItemComponent(it, Modifier.fillMaxWidth())
        }
    }
}


@Composable
fun MobileUserProfileDietGoalsComponent(records: List<Any>) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
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
            model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
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
            .background(SkyFitColor.background.surfaceSemiTransparent)
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
fun MobileUserProfileStatisticsBarsComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatisticCard(
            title = "Kalori",
            value = "452",
            unit = "kcal",
            color = Color(0xFFD0886D), // Adjusted color from screenshot
            icon = Icons.Default.ThumbUp
        )
        StatisticCard(
            title = "Zaman",
            value = "02:30",
            unit = "saat",
            color = Color(0xFF63C5B6), // Adjusted color
            icon = Icons.Default.Settings
        )
        StatisticCard(
            title = "Mesafe",
            value = "3.2",
            unit = "km",
            color = Color(0xFF9B5DE5), // Adjusted color
            icon = Icons.Default.Star
        )
    }
}

@Composable
fun StatisticCard(title: String, value: String, unit: String, color: Color, icon: ImageVector) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .background(color)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(imageVector = icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(24.dp))
        Text(text = title, color = Color.White, fontSize = 14.sp)
        Text(text = value, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Text(text = unit, color = Color.Black, fontSize = 14.sp)
    }
}


@Composable
fun MobileUserProfileExerciseHistoryComponent(exercises: List<Any>) {
    Column(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.surfaceSemiTransparent)
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                modifier = Modifier.size(24.dp),
                contentDescription = ""
            )
            Text(
                text = "Egzersiz Geçmişi",
                style = SkyFitTypography.bodyMediumSemibold
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(exercises) {
                MobileUserProfileExerciseHistoryItemComponent()
            }
        }
    }
}

@Composable
private fun MobileUserProfileExerciseHistoryItemComponent() {
    Column(Modifier.width(52.dp)) {
        Box(
            Modifier.size(52.dp)
                .background(SkyFitColor.background.fillTransparentSecondary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                modifier = Modifier.size(32.dp),
                contentDescription = ""
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Şınav",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
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

@Composable
fun MobileUserProfilePhotoDiaryComponent() {
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
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
                    text = "Çarşamba, 28 Ağustos", style = SkyFitTypography.bodyMediumRegular,
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
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
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
fun MobileUserProfileHabitsComponent(habits: List<Any>) {
    Column(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.surfaceSemiTransparent)
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                modifier = Modifier.size(24.dp),
                contentDescription = ""
            )
            Text(
                text = "Alışkanlıklar",
                style = SkyFitTypography.bodyMediumSemibold
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(habits) {
                MobileUserProfileHabitItemComponent()
            }
        }
    }
}

@Composable
private fun MobileUserProfileHabitItemComponent() {
    Column(Modifier.width(52.dp)) {
        Box(
            Modifier.size(52.dp)
                .background(SkyFitColor.background.fillTransparentSecondary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                modifier = Modifier.size(32.dp),
                contentDescription = ""
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Düzensiz Uyku",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MobileUserProfilePostsInputComponent() {
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
fun MobileUserProfilePostsComponent(
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
fun MobileUserProfileInfoCardComponent(
    name: String,
    social: String,
    imageUrl: String,
    preferences: List<ProfilePreferenceItem>
) {

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
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun MobileUserProfileInfoCardMiniComponent(
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
fun ProfileCardPreferenceItem(title: String, subtitle: String) {
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
fun HeaderVerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(48.dp)
            .background(SkyFitColor.border.default)
    )
}