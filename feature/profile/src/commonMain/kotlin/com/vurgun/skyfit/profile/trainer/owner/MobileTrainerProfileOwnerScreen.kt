package com.vurgun.skyfit.profile.trainer.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.event.LessonSessionColumn
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.components.profile.LifestyleActionRow
import com.vurgun.skyfit.core.ui.components.profile.MobileProfileBackgroundImage
import com.vurgun.skyfit.profile.model.ProfileDestination
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_plus
import skyfit.core.ui.generated.resources.ic_app_logo

class TrainerProfileOwnerScreen : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val viewModel = koinScreenModel<TrainerProfileViewModel>()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                TrainerProfileUiEffect.NavigateToCreatePost -> {
                    appNavigator.push(SharedScreen.CreatePost)
                }

                TrainerProfileUiEffect.NavigateToSettings -> {
                    appNavigator.push(SharedScreen.Settings)
                }

                TrainerProfileUiEffect.NavigateToAppointments -> {
                    appNavigator.push(SharedScreen.TrainerAppointmentListing)
                }
            }
        }

        MobileTrainerProfileOwnerScreen(viewModel = viewModel)
    }

}

@Composable
private fun MobileTrainerProfileOwnerScreen(
    viewModel: TrainerProfileViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is TrainerProfileUiState.Loading -> FullScreenLoaderContent()
        is TrainerProfileUiState.Error -> {
            val message = (uiState as TrainerProfileUiState.Error).message
            ErrorScreen(message = message, onConfirm = {  /* TODO: Where to go? */ })
        }

        is TrainerProfileUiState.Content -> {
            val content = uiState as TrainerProfileUiState.Content
            MobileTrainerProfileOwnerContent(content, viewModel::onAction)
        }
    }
}

@Composable
private fun MobileTrainerProfileOwnerContent(
    content: TrainerProfileUiState.Content,
    onAction: (TrainerProfileUiAction) -> Unit
) {
    val scrollState = rememberScrollState()
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
                imageUrl = content.profile.backgroundImageUrl,
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

                TrainerProfileComponent.MobileTrainerProfileOwner_HeaderCard(content.profile)

//                MobileProfileActionsRow(
//                    postsSelected = content.postsVisible,
//                    onClickAbout = { onAction(TrainerProfileOwnerAction.TogglePostVisibility(false)) },
//                    onClickPosts = {  }, //onAction(TrainerProfileOwnerAction.TogglePostVisibility(true))
//                    onClickSettings = { onAction(TrainerProfileOwnerAction.NavigateToSettings) },
//                    onClickNewPost = { onAction(TrainerProfileOwnerAction.NavigateToCreatePost) }
//                )

                if (content.destination == ProfileDestination.Posts) {

                } else {
                    if (content.specialties == null) {
//                        TrainerProfileComponent.MobileTrainerProfileOwner_NoSpeciality(onClickAdd = {})
                    } else {
                        LifestyleActionRow(viewData = content.specialties)
                    }

                    if (content.lessons.isEmpty()) {
//                        TrainerProfileComponent.MobileTrainerProfileOwner_NoLesson(onClickAdd = {})
                    } else {
                        LessonSessionColumn(
                            lessons = content.lessons,
                            onClickShowAll = { onAction(TrainerProfileUiAction.OnClickToAppointments) }
                        )
                    }
                }

                Spacer(Modifier.height(124.dp))
            }
        }
    }
}

private object TrainerProfileComponent {

    @Composable
    fun MobileTrainerProfileOwner_HeaderCard(trainerProfile: TrainerProfile?) {
        trainerProfile ?: return

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
                            text = trainerProfile.firstName,
                            style = SkyFitTypography.bodyLargeSemibold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "username",
                            style = SkyFitTypography.bodySmallMedium,
                            color = SkyFitColor.text.secondary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

//                    TrainerProfileCardPreferenceRow(
//                        followerCount = trainerProfile.followerCount.toString(),
//                        lessonCount = trainerProfile.lessonCount.toString(),
//                        postCount = trainerProfile.postCount.toString(),
//                        modifier = Modifier.fillMaxWidth()
//                    )
                }
            }

            NetworkImage(
                imageUrl = trainerProfile.profileImageUrl,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .align(Alignment.TopCenter)
            )
        }
    }

    @Composable
    fun MobileTrainerProfileOwner_NoSpeciality(onClickAdd: () -> Unit) {
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
                rightIconPainter = painterResource(Res.drawable.ic_app_logo)
            )
        }
    }

    @Composable
    fun MobileTrainerProfileOwner_NoLesson(onClickAdd: () -> Unit) {
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
                rightIconPainter = painterResource(Res.drawable.ic_app_logo)
            )
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
                                    painter = painterResource(Res.drawable.ic_plus),
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
                                    painter = painterResource(Res.drawable.ic_plus),
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