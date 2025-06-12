package com.vurgun.skyfit.feature.home.component

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.feature.home.model.FacilityHomeAction
import com.vurgun.skyfit.feature.home.model.FacilityHomeUiState

@Composable
internal fun FacilityDashboardAppointments(
    content: FacilityHomeUiState.Content,
    onAction: (FacilityHomeAction) -> Unit
) {
    if (content.activeLessons.isEmpty()) {
        HomeNoUpcomingAppointmentCard(
            assignedFacilityId = content.facility.gymId,
            onClickAdd = { onAction(FacilityHomeAction.OnClickLessons) }
        )
    } else {
        MobileDashboardHomeUpcomingAppointmentsComponent(
            appointments = content.activeLessons,
            onClickShowAll = { onAction(FacilityHomeAction.OnClickLessons) }
        )
    }
}
