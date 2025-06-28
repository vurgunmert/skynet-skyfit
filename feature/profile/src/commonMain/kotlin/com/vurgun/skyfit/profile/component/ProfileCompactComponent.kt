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
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyFitPrimaryCircularBackButton
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.event.LessonSessionColumn
import com.vurgun.skyfit.core.ui.components.event.NoLessonOnSelectedDaysEventItem
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.schedule.weekly.CalendarWeekDaySelector
import com.vurgun.skyfit.core.ui.components.schedule.weekly.CalendarWeekDaySelectorController
import com.vurgun.skyfit.core.ui.components.schedule.weekly.CalendarWeekDaySelectorState
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.profile.model.ProfileDestination
import com.vurgun.skyfit.profile.user.model.UserProfileAction
import dev.chrisbanes.haze.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

internal object ProfileCompactComponent {

    @Composable
    fun Layout(
        header: @Composable () -> Unit,
        content: @Composable () -> Unit,
        modifier: Modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .background(SkyFitColor.background.default)
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
        backgroundImageModifier: Modifier = Modifier.fillMaxWidth(),
        profileImageUrl: String?,
        cardContents: @Composable ColumnScope.() -> Unit,
        canNavigateBack: Boolean = false,
        onClickBack: (() -> Unit)? = null,
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
                content = cardContents,
                horizontalAlignment = Alignment.CenterHorizontally,
            )

            profileImageUrl.takeUnless { it.isNullOrEmpty() }?.let { url ->
                NetworkImage(
                    imageUrl = url,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 50.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .size(100.dp)
                )
            }

            if (canNavigateBack) {
                SkyFitPrimaryCircularBackButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 48.dp, start = 24.dp)
                        .size(48.dp),
                    onClick = { onClickBack?.invoke() }
                )
            }
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
            VerticalDivider(Modifier.height(52.dp))
            centerItem()
            VerticalDivider(Modifier.height(52.dp))
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
        Column(
            modifier = modifier.wrapContentSize()
                .padding(8.dp),
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
                    true -> UserProfileAction.OnClickNewPost
                    false -> UserProfileAction.OnClickSettings
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
                    true -> UserProfileAction.OnClickNewPost
                    false -> UserProfileAction.OnClickSettings
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

    //region Lessons
    @Composable
    fun WeeklyLessonScheduleGroup(
        calendarUiState: CalendarWeekDaySelectorState,
        calendarViewModel: CalendarWeekDaySelectorController,
        lessons: List<LessonSessionItemViewData>,
        goToVisitCalendar: () -> Unit,
        modifier: Modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SkyFitColor.background.fillTransparent),
    ) {
        Column(
            modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = SkyFitAsset.getPainter(SkyFitAsset.SkyFitIcon.EXERCISES.id),
                    modifier = Modifier.size(24.dp),
                    contentDescription = null,
                    tint = SkyFitColor.icon.default
                )

                Text(
                    text = stringResource(Res.string.lessons_label),
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = stringResource(Res.string.show_all_action),
                    style = SkyFitTypography.bodyXSmall,
                    color = SkyFitColor.border.secondaryButton,
                    modifier = Modifier.clickable(onClick = goToVisitCalendar)
                )
            }

            CalendarWeekDaySelector(
                daysOfWeek = calendarUiState.weekDays,
                onDaySelected = calendarViewModel::setSelectedDate,
                onPreviousWeek = calendarViewModel::loadPreviousWeek,
                onNextWeek = calendarViewModel::loadNextWeek,
                modifier = Modifier.fillMaxWidth()
            )

            if (lessons.isEmpty()) {
                NoLessonOnSelectedDaysEventItem()
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    lessons.forEach { item ->
                        AvailableActivityCalendarEventItem(
                            title = item.title,
                            iconId = item.iconId,
                            date = item.date.toString(),
                            timePeriod = item.hours.toString(),
                            location = item.location.toString(),
                            trainer = item.trainer.toString(),
                            capacity = item.capacityRatio.toString(),
                            note = item.note
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun LessonSchedule(
        lessons: List<LessonSessionItemViewData>,
        onClickShowAll: () -> Unit,
        onClickLesson: (LessonSessionItemViewData) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        LessonSessionColumn(
            lessons = lessons,
            onClickShowAll = onClickShowAll,
            onClickItem = onClickLesson,
            modifier = modifier
        )
    }

    @Composable
    fun NoScheduledLessonsCard(
        onClickAdd: (() -> Unit)? = null,
        cardModifier: Modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SkyFitColor.background.fillTransparent)
    ) {
        Column(Modifier.fillMaxWidth()) {

            SkyText(
                text = stringResource(Res.string.lessons_label),
                styleType = TextStyleType.BodyLargeSemibold
            )
            Spacer(Modifier.height(16.dp))

            Column(
                cardModifier.padding(vertical = 34.dp, horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SkyText(
                    text = "📅 Henüz yaklaşan bir dersiniz yok.",
                    styleType = TextStyleType.BodyLargeSemibold,
                    color = SkyFitColor.text.default
                )

                onClickAdd?.let { action ->
                    Spacer(Modifier.height(8.dp))
                    SkyText(
                        text = "Yeni bir ders oluşturmak için “Ders Ekle” butonunu kullanabilirsiniz.",
                        styleType = TextStyleType.BodyMediumRegular,
                        color = SkyFitColor.text.secondary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    SkyButton(
                        label = stringResource(Res.string.add_lesson_action),
                        size = SkyButtonSize.Medium,
                        onClick = action,
                    )
                }
            }
        }
    }
    //endregion Lessons
}