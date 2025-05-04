package com.vurgun.skyfit.feature.calendar.screen.activitycalendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.domain.model.WorkoutType
import com.vurgun.skyfit.core.ui.components.button.PrimaryMicroButton
import com.vurgun.skyfit.core.ui.components.chip.SecondaryChip
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_left
import skyfit.core.ui.generated.resources.ic_plus

class UserActivityCalendarSearchScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<UserActivityCalendarSearchViewModel>()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                UserActivityCalendarSearchEffect.NavigateToBack ->
                    navigator.pop()

                UserActivityCalendarSearchEffect.NavigateToNew ->
                    navigator.push(UserActivityCalendarAddActivityScreen())
            }
        }

        MobileUserActivityCalendarSearchScreen(
            goToBack = { navigator.pop() },
            goToAddActivity = { },
            viewModel = viewModel
        )
    }

}

@Composable
private fun MobileUserActivityCalendarSearchScreen(
    goToBack: () -> Unit,
    goToAddActivity: () -> Unit,
    viewModel: UserActivityCalendarSearchViewModel
) {
    val groupedWorkouts by viewModel.filteredWorkouts.collectAsState()

    SkyFitMobileScaffold(
        topBar = {
            Column {
                MobileUserActivityCalendarSearchScreenToolbarComponent(viewModel)
                MobileUserActivityCalendarSearchScreenSearchComponent(viewModel)
                ActivityCalendarSearchScreenFilterChips(viewModel)
            }
        }
    ) {
        if (groupedWorkouts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                BodyMediumSemiboldText(
                    text = "Uygun etkinlik bulunamadı",
                    color = SkyFitColor.text.secondary
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                groupedWorkouts.forEach { (title, items) ->
                    item {
                        CalendarActivityWorkoutsGroupTitle(title)
                    }
                    items(items) { workout ->
                        CalendarActivityWorkoutItem(workout)
                    }
                }
            }
        }
    }
}


@Composable
private fun MobileUserActivityCalendarSearchScreenToolbarComponent(viewModel: UserActivityCalendarSearchViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_chevron_left),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
                .clickable { viewModel.onAction(UserActivityCalendarSearchAction.NavigateToBack) }
        )

        Spacer(Modifier.weight(1f))

        PrimaryMicroButton(
            text = "Yeni",
            rightIconRes = Res.drawable.ic_plus,
            onClick = { viewModel.onAction(UserActivityCalendarSearchAction.NavigateToNew) }
        )
    }
}

@Composable
private fun MobileUserActivityCalendarSearchScreenSearchComponent(
    viewModel: UserActivityCalendarSearchViewModel
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        SkyFitSearchTextInputComponent(
            modifier = Modifier.fillMaxWidth(),
            hint = "Antrenman, antrenör ya da spor salonu ara",
            value = state.searchQuery,
            onValueChange = { query ->
                viewModel.onAction(UserActivityCalendarSearchAction.Search(query))
            },
            onSearch = {
                viewModel.onAction(UserActivityCalendarSearchAction.Search(state.searchQuery))
            }
        )
    }
}

@Composable
private fun ActivityCalendarSearchScreenFilterChips(
    viewModel: UserActivityCalendarSearchViewModel
) {
    val state by viewModel.state.collectAsState()
    val categoryChips = remember { viewModel.getCategoryChips(locale = "tr") }

    LazyRow(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categoryChips) { (id, label) ->
            val selected = state.searchQuery.isBlank() && state.selectedCategoryId == id

            SecondaryChip(
                text = label,
                selected = selected,
                onClick = { viewModel.onAction(UserActivityCalendarSearchAction.SelectCategory(id)) }
            )
        }
    }
}


@Composable
private fun CalendarActivityWorkoutsGroupTitle(text: String) {
    BodyMediumSemiboldText(
        text = text,
        color = SkyFitColor.text.secondary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    )
}

@Composable
private fun CalendarActivityWorkoutItem(workoutType: WorkoutType) {
    Box {
        Row(
            modifier = Modifier
                .clickable { }
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = workoutType.emojiId,
                style = SkyFitTypography.heading3
            )
            Spacer(Modifier.width(8.dp))
            BodyMediumSemiboldText(
                text = workoutType.name["tr"].orEmpty(),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                painter = painterResource(Res.drawable.ic_plus),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            color = SkyFitColor.border.default
        )
    }
}