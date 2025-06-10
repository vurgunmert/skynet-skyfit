package com.vurgun.skyfit.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.show_all_action
import skyfit.core.ui.generated.resources.upcoming_appointments_label

@Composable
fun UpcomingAppointmentsTitle(onClickShowAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalPadding.current.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SkyText(
            text = stringResource(Res.string.upcoming_appointments_label),
            styleType = TextStyleType.BodyMediumSemibold
        )

        SkyText(
            text = stringResource(Res.string.show_all_action),
            styleType = TextStyleType.BodyXSmall,
            color = SkyFitColor.border.secondaryButton,
            modifier = Modifier.clickable(onClick = onClickShowAll)
        )
    }
}