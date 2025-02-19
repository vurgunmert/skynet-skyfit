package com.vurgun.skyfit.presentation.mobile.features.trainer.profile

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserProfileActionsComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserProfilePostsComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserProfilePostsInputComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileVisitedProfileActionsComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.UserProfileCardPreferenceRow
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItemRowComponent
import com.vurgun.skyfit.presentation.shared.features.social.PostViewData
import com.vurgun.skyfit.presentation.shared.features.trainer.SkyFitTrainerProfileViewModel
import com.vurgun.skyfit.presentation.shared.features.user.TopBarGroupViewData
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitIcon
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_clock
import skyfit.composeapp.generated.resources.ic_dashboard
import skyfit.composeapp.generated.resources.ic_exercises
import skyfit.composeapp.generated.resources.ic_note
import skyfit.composeapp.generated.resources.ic_profile_fill
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileTrainerProfileScreen(navigator: Navigator) {

    val viewModel = remember { SkyFitTrainerProfileViewModel() }
    val scrollState = rememberScrollState()
    var showPosts by remember { mutableStateOf(false) }

    val profileData by viewModel.profileData.collectAsState()
    val specialities by viewModel.specialities.collectAsState()
    val privateClasses = viewModel.privateClasses.collectAsState().value
    val posts = viewModel.posts.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

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

                    profileData?.let {
                        MobileTrainerProfileInfoCardComponent(it)
                    }

                    Spacer(Modifier.height(16.dp))
                    MobileUserProfileActionsComponent(
                        showPosts = showPosts,
                        onClickAbout = { showPosts = false },
                        onClickPosts = { showPosts = true },
                        onClickSettings = { navigator.jumpAndStay(SkyFitNavigationRoute.TrainerSettings) },
                        onClickNewPost = { navigator.jumpAndStay(SkyFitNavigationRoute.UserSocialMediaPostAdd) }
                    )
                }
            }
        }
    ) {
        if (showPosts) {
            MobileTrainerProfilePostsComponent(posts)
        } else {
            MobileTrainerProfileAboutGroupComponent(specialities, privateClasses, scrollState)
        }
    }
}

@Composable
fun MobileTrainerProfileAboutGroupComponent(
    specialities: List<SpecialityItemComponentViewData>,
    privateClasses: List<SkyFitClassCalendarCardItem>,
    scrollState: ScrollState
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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

@Composable
fun MobileTrainerProfileBackgroundImageComponent(height: Dp) {
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
fun MobileTrainerProfileInfoCardComponent(viewData: TopBarGroupViewData?) {
    viewData ?: return

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

                Spacer(modifier = Modifier.height(16.dp))

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
private fun MobileTrainerProfilePostInputComponent() {
    MobileUserProfilePostsInputComponent(onClickSend = {})
}

@Composable
fun MobileTrainerProfilePostsComponent(
    posts: List<PostViewData>,
    listState: LazyListState = rememberLazyListState()
) {
    MobileUserProfilePostsComponent(posts, listState)
}

@Composable
fun MobileTrainerProfileSpecialitiesComponent(specialities: List<SpecialityItemComponentViewData>) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                modifier = Modifier.size(24.dp),
                contentDescription = "",
                tint = SkyFitColor.icon.default
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Uzmanlık Alanları",
                style = SkyFitTypography.bodyLargeSemibold
            )
        }

        Spacer(Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(specialities) {
                MobileTrainerProfileSpecialityItemComponent(it)
            }
        }
    }
}

data class SpecialityItemComponentViewData(
    val name: String = "Atletik Performans Geliştirme",
    val iconId: String = "push_up"
)

@Composable
private fun MobileTrainerProfileSpecialityItemComponent(data: SpecialityItemComponentViewData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Ensures everything stays centered
        modifier = Modifier.wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .background(SkyFitColor.background.fillTransparentSecondary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = SkyFitIcon.getIconResourcePainter(data.iconId) ?: painterResource(Res.drawable.logo_skyfit),
                modifier = Modifier.size(32.dp),
                contentDescription = "",
                tint = SkyFitColor.icon.default
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = data.name,
            style = SkyFitTypography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 78.dp), // Set a fixed or dynamic width
            softWrap = true,
            overflow = TextOverflow.Visible
        )
    }
}


@Composable
fun MobileTrainerProfilePrivateClassesComponent(privateClasses: List<SkyFitClassCalendarCardItem>) {
    var isMenuOpen by remember { mutableStateOf(false) }

    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Info",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Özel Dersler",
                style = SkyFitTypography.bodyLargeSemibold
            )
            Spacer(Modifier.weight(1f)) // Pushes the menu to the right

            Box {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "More Options",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isMenuOpen = true }
                )

                // Attach the TrainerClassMenuPopup here
                TrainerClassMenuPopup(
                    isOpen = isMenuOpen,
                    onDismiss = { isMenuOpen = false },
                    onAddEvent = { /* Handle Add Event */ },
                    onEdit = { /* Handle Edit */ }
                )
            }
        }

        privateClasses.forEach {
            Spacer(Modifier.height(16.dp))
            SkyFitProfileClassItemComponent(
                item = it,
                onClick = { }
            )
        }
    }
}


@Composable
fun SkyFitProfileClassItemComponent(item: SkyFitClassCalendarCardItem, onClick: () -> Unit) {

    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = SkyFitIcon.getIconResourcePainter(item.iconId, defaultRes = Res.drawable.ic_exercises),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = SkyFitColor.icon.default
                )
                Text(
                    text = item.title,
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                )
            }

            item.hours?.let {
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_clock)
            }

            item.trainer?.let {
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_profile_fill)
            }

            item.category?.let {
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_dashboard)
            }

            item.note?.let {
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_note)
            }
        }
    }
}


@Composable
private fun TrainerClassMenuPopup(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onAddEvent: () -> Unit,
    onEdit: () -> Unit
) {
    if (isOpen) {
        MaterialTheme(
            colors = MaterialTheme.colors.copy(surface = SkyFitColor.background.surfaceSecondary),
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))
        ) {
            DropdownMenu(
                expanded = isOpen,
                onDismissRequest = { onDismiss() },
                modifier = Modifier
                    .width(160.dp)
                    .background(Color.Transparent) // Prevents overriding the rounded shape
            ) {
                Surface(elevation = 8.dp) {
                    Column {
                        // "Etkinlik Ekle" Option
                        DropdownMenuItem(
                            onClick = {
                                onAddEvent()
                                onDismiss()
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Etkinlik Ekle",
                                    style = SkyFitTypography.bodyMediumRegular
                                )
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Event",
                                    tint = SkyFitColor.icon.default,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Divider(
                            color = SkyFitColor.border.default,
                            thickness = 1.dp
                        )

                        // "Düzenle" Option
                        DropdownMenuItem(
                            onClick = {
                                onEdit()
                                onDismiss()
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Düzenle",
                                    style = SkyFitTypography.bodyMediumRegular
                                )
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = SkyFitColor.icon.default,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MobileTrainerProfileSpecialitiesEmptyComponent(onClickAdd: () -> Unit) {
    Box(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(24.dp))
            .padding(vertical = 56.dp),
        contentAlignment = Alignment.Center
    ) {
        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(), text = "Profili Düzenle",
            onClick = onClickAdd,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Micro,
            rightIconPainter = painterResource(Res.drawable.logo_skyfit)
        )
    }
}

@Composable
private fun MobileTrainerProfilePrivateClassesEmptyComponent(onClickAdd: () -> Unit) {
    Box(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(24.dp))
            .padding(vertical = 56.dp),
        contentAlignment = Alignment.Center
    ) {
        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(), text = "Ozel Ders Ekle",
            onClick = onClickAdd,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Micro,
            rightIconPainter = painterResource(Res.drawable.logo_skyfit)
        )
    }
}

