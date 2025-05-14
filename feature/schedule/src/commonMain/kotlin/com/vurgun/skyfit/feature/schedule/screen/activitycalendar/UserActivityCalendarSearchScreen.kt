package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutType
import com.vurgun.skyfit.core.ui.components.button.PrimaryMicroButton
import com.vurgun.skyfit.core.ui.components.chip.SecondaryPillChip
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_left
import skyfit.core.ui.generated.resources.ic_plus

class UserActivityCalendarSearchScreen(private val initialDate: LocalDate? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<UserActivityCalendarSearchViewModel>()
        val uiState = viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                UserActivityCalendarSearchEffect.NavigateToBack ->
                    navigator.pop()

                is UserActivityCalendarSearchEffect.NavigateToNew ->
                    navigator.push(EditWorkoutScreen(
                        effect.date,
                        effect.workoutType,
                        effect.category
                    ))
            }
        }

        LaunchedEffect(initialDate) {
            viewModel.loadData(initialDate)
        }

        when (uiState.value) {
            is UserActivityCalendarSearchUiState.Content -> {
                val content = (uiState.value as UserActivityCalendarSearchUiState.Content)
                UserActivityCalendarSearchScreen_Compact(viewModel, content, viewModel::onAction)
            }

            is UserActivityCalendarSearchUiState.Error -> {
                val message = (uiState.value as UserActivityCalendarSearchUiState.Error).message
                ErrorScreen(message = message, onConfirm = { navigator.pop() })
            }

            UserActivityCalendarSearchUiState.Loading -> FullScreenLoaderContent()
        }


    }

}

@Composable
private fun UserActivityCalendarSearchScreen_Compact(
    viewModel: UserActivityCalendarSearchViewModel,
    content: UserActivityCalendarSearchUiState.Content,
    onAction: (UserActivityCalendarSearchAction) -> Unit,
) {

    SkyFitMobileScaffold(
        topBar = {
            Column {
                MobileUserActivityCalendarSearchScreenToolbarComponent(onAction)
                MobileUserActivityCalendarSearchScreenSearchComponent(viewModel, onAction)
                ActivityCalendarSearchScreenFilterChips(content, onAction)
            }
        }
    ) {
        if (content.filteredWorkoutTypes.isEmpty()) {
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
                items(content.filteredWorkoutTypes) { workout ->
                    CalendarActivityWorkoutItem(workout, onClick = {
                        onAction(UserActivityCalendarSearchAction.OnSelectWorkout(workout))
                    })
                }
            }
        }
    }
}


@Composable
private fun MobileUserActivityCalendarSearchScreenToolbarComponent(onAction: (UserActivityCalendarSearchAction) -> Unit) {
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
                .clickable { onAction(UserActivityCalendarSearchAction.NavigateToBack) }
        )

        Spacer(Modifier.weight(1f))

        PrimaryMicroButton(
            text = "Yeni",
            rightIconRes = Res.drawable.ic_plus,
            onClick = { onAction(UserActivityCalendarSearchAction.OnSelectWorkout()) }
        )
    }
}

@Composable
private fun MobileUserActivityCalendarSearchScreenSearchComponent(
    viewModel: UserActivityCalendarSearchViewModel,
    onAction: (UserActivityCalendarSearchAction) -> Unit
) {
    val currentQuery by viewModel.searchQuery.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        SkyFitSearchTextInputComponent(
            modifier = Modifier.fillMaxWidth(),
            hint = "Antrenman, antrenör ya da spor salonu ara",
            value = currentQuery,
            onValueChange = { query ->
                onAction(UserActivityCalendarSearchAction.Search(query))
            },
            onSearch = {
                onAction(UserActivityCalendarSearchAction.Search(currentQuery))
            }
        )
    }
}

@Composable
private fun ActivityCalendarSearchScreenFilterChips(
    content: UserActivityCalendarSearchUiState.Content,
    onAction: (UserActivityCalendarSearchAction) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(content.categories) { category ->
            SecondaryPillChip(
                text = category.displayName.getValue("tr"),
                selected = category.id == content.selectedCategoryId,
                onClick = { onAction(UserActivityCalendarSearchAction.SelectCategory(category.id)) }
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
private fun CalendarActivityWorkoutItem(workoutType: WorkoutType, onClick: (WorkoutType) -> Unit) {
    Box {
        Row(
            modifier = Modifier
                .clickable { onClick(workoutType) }
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
                text = workoutType.name,
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