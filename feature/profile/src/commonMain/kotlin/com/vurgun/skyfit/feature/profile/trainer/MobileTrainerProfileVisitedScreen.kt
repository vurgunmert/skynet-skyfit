package com.vurgun.skyfit.feature.profile.trainer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.feature.profile.components.LifestyleActionRow
import com.vurgun.skyfit.feature.profile.components.MobileProfileActionTabsRow
import com.vurgun.skyfit.feature.profile.components.MobileVisitedProfileActionsComponent
import com.vurgun.skyfit.feature.profile.components.UserProfileCardPreferenceRow
import com.vurgun.skyfit.feature.profile.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature.profile.user.UserProfileHeaderViewData
import com.vurgun.skyfit.feature.social.components.LazySocialPostsColumn
import com.vurgun.skyfit.ui.core.components.button.SkyFitPrimaryCircularBackButton
import com.vurgun.skyfit.ui.core.components.event.LessonSessionColumn
import com.vurgun.skyfit.ui.core.components.image.NetworkImage
import com.vurgun.skyfit.ui.core.components.special.ButtonSize
import com.vurgun.skyfit.ui.core.components.special.ButtonVariant
import com.vurgun.skyfit.ui.core.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_calendar_dots
import skyfit.ui.core.generated.resources.ic_send

@Composable
fun MobileTrainerProfileVisitedScreen(
    goToBack: () -> Unit,
    goToChat: () -> Unit,
    goToVisitCalendar: () -> Unit,
    viewModel: TrainerProfileOwnerViewModel = koinViewModel()
) {

    val scrollState = rememberScrollState()
    var showPosts by remember { mutableStateOf(false) }

    val profileData by viewModel.profileData.collectAsState()
    val specialtiesRowViewData by viewModel.specialtiesRowViewData.collectAsState()
    val lessonsColumViewData by viewModel.lessonsColumViewData.collectAsState()
    val posts = viewModel.posts.collectAsState().value
    var isFollowing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .background(SkyFitColor.background.default)
    ) {
        val width = maxWidth
        val imageHeight = width * 9 / 16
        val contentTopPadding = imageHeight * 3 / 10

//        MobileTrainerProfileBackgroundImageComponent(imageHeight)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(contentTopPadding))

            MobileTrainerProfileVisitedScreenInfoCardComponent(
                viewData = profileData,
                isFollowing = isFollowing,
                onClickFollow = { isFollowing = true },
                onClickUnFollow = { isFollowing = false },
                onClickCalendar = goToVisitCalendar
            )

            MobileTrainerProfileVisitedScreenActionsComponent(
                postsSelected = showPosts,
                showMessage = isFollowing,
                onClickAbout = { showPosts = false },
                onClickPosts = { showPosts = true },
                onClickMessage = goToChat
            )

            if (showPosts) {
                LazySocialPostsColumn(posts)
            } else {
                MobileTrainerProfileVisitedAboutGroup(
                    specialtiesRowViewData, lessonsColumViewData,
                    goToVisitCalendar = goToVisitCalendar,
                )
            }
        }

        MobileTrainerProfileVisitedScreenToolbarComponent(onClickBack = goToBack)
    }
}

@Composable
private fun ColumnScope.MobileTrainerProfileVisitedAboutGroup(
    specialtiesRowViewData: LifestyleActionRowViewData? = null,
    lessonsColumViewData: LessonSessionColumnViewData? = null,
    goToVisitCalendar: () -> Unit
) {
    Column(
        modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (specialtiesRowViewData != null) {
            LifestyleActionRow(viewData = specialtiesRowViewData)
        }

        if (lessonsColumViewData != null) {
            LessonSessionColumn(
                lessons = lessonsColumViewData.items,
                onClickItem = { goToVisitCalendar() }
            )
        }
    }
}


@Composable
private fun MobileTrainerProfileVisitedScreenToolbarComponent(onClickBack: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 24.dp)) {
        SkyFitPrimaryCircularBackButton(onClick = onClickBack)
    }
}

@Composable
private fun MobileTrainerProfileVisitedScreenInfoCardComponent(
    viewData: UserProfileHeaderViewData?,
    isFollowing: Boolean,
    onClickFollow: () -> Unit,
    onClickUnFollow: () -> Unit,
    onClickCalendar: () -> Unit
) {
    viewData ?: return

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(top = 70.dp)
                .padding(horizontal = 16.dp)
                .width(398.dp)
                .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                    .padding(start = 16.dp, top = 36.dp, end = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
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

                Spacer(modifier = Modifier.height(16.dp))

                SkyFitButtonComponent(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (isFollowing) "Takipten Çık" else "Takip Et",
                    onClick = if (isFollowing) onClickUnFollow else onClickFollow,
                    variant = ButtonVariant.Primary,
                    size = ButtonSize.Large
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    SkyFitButtonComponent(
                        modifier = Modifier.weight(1f),
                        text = "Randevu Al",
                        onClick = onClickCalendar,
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Large,
                        leftIconPainter = painterResource(Res.drawable.ic_calendar_dots)
                    )
                }
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
private fun MobileTrainerProfileVisitedScreenActionsComponent(
    postsSelected: Boolean,
    showMessage: Boolean,
    onClickAbout: () -> Unit,
    onClickPosts: () -> Unit,
    onClickMessage: () -> Unit
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
        MobileProfileActionTabsRow(
            modifier = Modifier.weight(1f),
            onClickAbout = onClickAbout,
            onClickPosts = onClickPosts,
            postsSelected = postsSelected
        )

        if (showMessage) {
            Box(
                Modifier
                    .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                    .clickable(onClick = onClickMessage)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_send),
                    contentDescription = "Send",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

