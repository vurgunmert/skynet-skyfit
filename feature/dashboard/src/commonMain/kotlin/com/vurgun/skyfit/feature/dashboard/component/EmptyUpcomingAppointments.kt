package com.vurgun.skyfit.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.error_trainer_no_facility
import skyfit.core.ui.generated.resources.lesson_add_action
import skyfit.core.ui.generated.resources.upcoming_appointments_label

@Composable
internal fun EmptyFacilityAppointmentContent(
    assignedFacilityId: Int?,
    onClickAdd: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.default, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 16.dp, horizontal = 20.dp)
    ) {
        SkyText(
            text = stringResource(Res.string.upcoming_appointments_label),
            styleType = TextStyleType.BodyLargeSemibold,
        )
        Spacer(Modifier.height(8.dp))
        Box(
            Modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .height(105.dp)
                .background(SkyFitColor.background.surfaceBrandActive)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            if (assignedFacilityId == null) {
                SkyText(
                    text = stringResource(Res.string.error_trainer_no_facility),
                    styleType = TextStyleType.BodyMediumMedium,
                    color = SkyFitColor.text.warningOnBgFill
                )
            } else {
                SkyButton(
                    label = stringResource(Res.string.lesson_add_action),
                    onClick = { onClickAdd(assignedFacilityId) }
                )
            }
        }
    }
}