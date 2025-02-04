package com.vurgun.skyfit.presentation.mobile.features.trainer.profile

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenPrivateClassesComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.HeaderVerticalDivider
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserProfilePostsComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserProfilePostsInputComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileVisitedProfileActionsComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.ProfileCardPreferenceItem
import com.vurgun.skyfit.presentation.mobile.features.user.profile.ProfilePreferenceItem
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItem
import com.vurgun.skyfit.presentation.shared.features.trainer.SkyFitTrainerProfileViewModel
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileTrainerProfileScreen(navigator: Navigator) {

    val viewModel = SkyFitTrainerProfileViewModel()
    val scrollState = rememberScrollState()
    var showPosts: Boolean = false
    val specialities: List<Any> = emptyList()
    val privateClasses = viewModel.privateClasses
    val posts = viewModel.posts

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            BoxWithConstraints {
                val width = maxWidth
                val imageHeight = width * 9 / 16
                val contentTopPadding = imageHeight * 3 / 10

                MobileTrainerProfileBackgroundImageComponent(imageHeight)

                Column(
                    Modifier
                        .padding(top = contentTopPadding)
                        .fillMaxWidth()
                ) {
                    MobileTrainerProfileInfoCardComponent(
                        name = "Trainer Solice",
                        social = "@dexteretymo",
                        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
                        preferences = listOf(
                            ProfilePreferenceItem("Boy", "175"),
                            ProfilePreferenceItem("Kilo", "175"),
                            ProfilePreferenceItem("Vucut Tipi", "Ecto"),
                        )
                    )
                    Spacer(Modifier.height(16.dp))
                    MobileTrainerProfileActionsComponent(
                        onClickAbout = { showPosts = false },
                        onClickPosts = { showPosts = true },
                        onClickSettings = {}
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (showPosts) {
                MobileTrainerProfilePostsComponent(posts)
            } else {

                if (specialities.isEmpty()) {
                    MobileTrainerProfileSpecialitiesEmptyComponent(onClickAdd = {})
                } else {
                    MobileTrainerProfileSpecialitiesComponent(specialities)
                }

                if (privateClasses.isEmpty()) {
                    MobileTrainerProfilePrivateClassesEmptyComponent(onClickAdd = {})
                } else {
                    MobileTrainerProfilePrivateClassesComponent(privateClasses)
                }
            }
        }
    }
}

@Composable
private fun MobileTrainerProfileBackgroundImageComponent(height: Dp) {
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
private fun MobileTrainerProfileInfoCardComponent(
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
private fun MobileTrainerProfilePostInputComponent() {
    MobileUserProfilePostsInputComponent()
}

@Composable
private fun MobileTrainerProfilePostsComponent(
    posts: List<SkyFitPostCardItem>,
    listState: LazyListState = rememberLazyListState()
) {
    MobileUserProfilePostsComponent(posts, listState)
}

@Composable
private fun MobileTrainerProfileActionsComponent(
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
private fun MobileTrainerProfileSpecialitiesComponent(specialities: List<Any>) {
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
                text = "Uzmanlık Alanları",
                style = SkyFitTypography.bodyMediumSemibold
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(specialities) {
                MobileTrainerProfileSpecialityItemComponent()
            }
        }
    }
}

@Composable
private fun MobileTrainerProfileSpecialityItemComponent() {
    Column(Modifier.width(68.dp)) {
        Box(
            Modifier.size(68.dp)
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
            text = "Fonksiyonel Antrenman",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MobileTrainerProfileSpecialitiesEmptyComponent(onClickAdd: () -> Unit) {
    Column(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.surfaceSemiTransparent)
            .padding(16.dp)
    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 76.dp),
            contentAlignment = Alignment.Center
        ) {

            SkyFitButtonComponent(
                Modifier.wrapContentWidth(), text = "Profili Düzenle",
                onClick = onClickAdd,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Medium,
                initialState = ButtonState.Rest
            )
        }
    }
}


@Composable
private fun MobileTrainerProfilePrivateClassesComponent(privateClasses: List<SkyFitClassCalendarCardItem>) {
    MobileFacilityProfileVisitedScreenPrivateClassesComponent(privateClasses)
}

@Composable
private fun MobileTrainerProfilePrivateClassesEmptyComponent(onClickAdd: () -> Unit) {
    Column(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.surfaceSemiTransparent)
            .padding(16.dp)
    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 76.dp),
            contentAlignment = Alignment.Center
        ) {

            SkyFitButtonComponent(
                Modifier.wrapContentWidth(), text = "Ozel Ders Ekle",
                onClick = onClickAdd,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Medium,
                initialState = ButtonState.Rest
            )
        }
    }
}

