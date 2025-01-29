package com.vurgun.skyfit.presentation.mobile.features.user.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserActivityCalendarSearchScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserActivityCalendarSearchScreenToolbarComponent(
                onClickBack = { navigator.popBackStack() },
                onClickNew = { navigator.jumpAndStay(SkyFitNavigationRoute.UserActivityCalendarAdd) }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserActivityCalendarSearchScreenSearchComponent()
            MobileUserActivityCalendarSearchScreenTabsComponent()
            MobileUserActivityCalendarSearchFeaturedExercisesComponent() //todo
            MobileUserActivityCalendarSearchPopularExercisesComponent()
            MobileUserActivityCalendarSearchResultsComponent()
        }
    }
}


@Composable
private fun MobileUserActivityCalendarSearchScreenToolbarComponent(
    onClickBack: () -> Unit,
    onClickNew: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onClickBack,
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Back",
                tint = SkyFitColor.text.default
            )
        }

        Spacer(Modifier.weight(1f))

        SkyFitButtonComponent(
            Modifier.wrapContentWidth(), text = "Yeni",
            onClick = onClickNew,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro,
            initialState = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.logo_skyfit)
        )
    }
}

@Composable
private fun MobileUserActivityCalendarSearchScreenSearchComponent() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        SkyFitSearchTextInputComponent("Antrenman, antrenör ya da spor salonu ara")
    }
}

@Composable
private fun MobileUserActivityCalendarSearchScreenTabsComponent() {
    TodoBox("MobileUserActivityCalendarSearchScreenTabsComponent", Modifier.size(430.dp, 64.dp))
}

@Composable
private fun MobileUserActivityCalendarSearchFeaturedExercisesComponent() {
    TodoBox("MobileUserActivityCalendarSearchFeaturedExercisesComponent", Modifier.size(430.dp, 203.dp))

    Column(Modifier.fillMaxWidth()) {
        Text(
            "En yeni",
            style = SkyFitTypography.bodyMediumSemibold
        )
        Spacer(Modifier.height(16.dp))
        MobileSettingsMenuItemComponent("Yuruyus")
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = SkyFitColor.border.default
        )
        MobileSettingsMenuItemComponent("Bisiklet")
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = SkyFitColor.border.default
        )
        MobileSettingsMenuItemComponent("Yoga")
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = SkyFitColor.border.default
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun MobileUserActivityCalendarSearchPopularExercisesComponent() {
    TodoBox("MobileUserActivityCalendarSearchPopularExercisesComponent", Modifier.size(430.dp, 258.dp))
}


@Composable
private fun MobileUserActivityCalendarSearchResultsComponent() {
    TodoBox("MobileUserActivityCalendarSearchResultsComponent", Modifier.size(430.dp, 660.dp))
}

@Composable
private fun MobileUserCalendarActivityMenuItemComponent(text: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = SkyFitColor.icon.default
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = SkyFitTypography.bodyMediumMedium
        )
        Spacer(Modifier.width(16.dp))
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = SkyFitColor.icon.default
        )
    }
}
