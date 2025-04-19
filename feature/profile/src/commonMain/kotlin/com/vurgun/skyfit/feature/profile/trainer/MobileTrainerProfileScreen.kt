package com.vurgun.skyfit.feature.profile.trainer

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.feature.profile.components.LifestyleActionRow
import com.vurgun.skyfit.feature.profile.components.MobileProfileActionsRow
import com.vurgun.skyfit.feature.profile.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature.profile.components.UserProfileCardPreferenceRow
import com.vurgun.skyfit.feature.profile.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.ProfileViewMode
import com.vurgun.skyfit.feature.profile.user.UserProfileHeaderViewData
import com.vurgun.skyfit.ui.core.components.event.LessonSessionColumn
import com.vurgun.skyfit.ui.core.components.image.NetworkImage
import com.vurgun.skyfit.ui.core.components.special.ButtonSize
import com.vurgun.skyfit.ui.core.components.special.ButtonVariant
import com.vurgun.skyfit.ui.core.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.logo_skyfit

@Composable
fun MobileTrainerProfileScreen(
    goToSettings: () -> Unit,
    goToCreatePost: () -> Unit,
    viewMode: ProfileViewMode = ProfileViewMode.OWNER,
    viewModel: TrainerProfileOwnerViewModel = koinViewModel()
) {

    val scrollState = rememberScrollState()
    val postsVisible by viewModel.postsVisible.collectAsStateWithLifecycle()
    val posts by viewModel.posts.collectAsStateWithLifecycle()

    val profileData by viewModel.profileData.collectAsState()
    val specialtiesRowViewData by viewModel.specialtiesRowViewData.collectAsState()

    val lessonsColumViewData by viewModel.lessonsColumViewData.collectAsState()

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
                imageUrl = profileData?.backgroundImageUrl,
                Modifier
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

                MobileTrainerProfileInfoCardComponent(profileData)

                if (viewMode == ProfileViewMode.OWNER) {
                    MobileProfileActionsRow(
                        postsSelected = postsVisible,
                        onClickAbout = { viewModel.togglePostTab(false) },
                        onClickPosts = { viewModel.togglePostTab(true) },
                        onClickSettings = goToSettings,
                        onClickNewPost = goToCreatePost
                    )
                }

                if (postsVisible) {

                } else {
                    MobileTrainerProfileAboutGroupComponent(
                        specialtiesRowViewData = specialtiesRowViewData,
                        lessonSessionColumnViewData = lessonsColumViewData
                    )
                }
            }
        }
    }
}

@Composable
fun MobileTrainerProfileAboutGroupComponent(
    specialtiesRowViewData: LifestyleActionRowViewData?,
    lessonSessionColumnViewData: LessonSessionColumnViewData? = null
) {
//    if (specialtiesRowViewData == null) {
//        MobileTrainerProfileSpecialitiesEmptyComponent(onClickAdd = {})
//    } else {
//        LifestyleActionRow(viewData = specialtiesRowViewData)
//    }

    if (lessonSessionColumnViewData != null) {
        LessonSessionColumn(
            viewData = lessonSessionColumnViewData,
            onClickShowAll = {}
        )
    } else {
        MobileTrainerProfilePrivateClassesEmptyComponent(onClickAdd = {})
    }

    Spacer(Modifier.height(124.dp))
}

@Composable
fun MobileTrainerProfileInfoCardComponent(viewData: UserProfileHeaderViewData?) {
    viewData ?: return

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
                        text = viewData.name,
                        style = SkyFitTypography.bodyLargeSemibold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewData.username,
                        style = SkyFitTypography.bodySmallMedium,
                        color = SkyFitColor.text.secondary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                UserProfileCardPreferenceRow(
                    height = viewData.height,
                    weight = viewData.weight,
                    bodyType = viewData.bodyType,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        NetworkImage(
            imageUrl = viewData.profileImageUrl,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.TopCenter)
        )
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

