package com.vurgun.skyfit.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
internal fun HomeNoUpcomingAppointmentCard(
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

        Column(
            Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                .padding(vertical = 34.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SkyText(
                text = "📅 Henüz yaklaşan bir dersiniz yok.",
                styleType = TextStyleType.BodyLargeSemibold,
                color = SkyFitColor.text.default
            )
            Spacer(Modifier.height(8.dp))
            SkyText(
                text = "Yeni bir ders oluşturmak için “Ders Ekle” butonunu kullanabilirsiniz.",
                styleType = TextStyleType.BodyMediumRegular,
                color = SkyFitColor.text.secondary,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

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