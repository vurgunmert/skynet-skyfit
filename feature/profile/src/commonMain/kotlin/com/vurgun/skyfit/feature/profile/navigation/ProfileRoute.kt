package com.vurgun.skyfit.feature.profile.navigation

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.profile.components.viewdata.ProfileViewMode
import com.vurgun.skyfit.feature.profile.facility.MobileFacilityProfileScreen
import com.vurgun.skyfit.feature.profile.trainer.MobileTrainerProfileScreen
import com.vurgun.skyfit.feature.profile.user.MobileUserProfileScreen
import com.vurgun.skyfit.feature.settings.component.rbac.RequireRole
import com.vurgun.skyfit.ui.core.screen.UnauthorizedAccessScreen

@Composable
fun ProfileRoot(
    userRole: UserRole,
    goToFacilityCourses: () -> Unit,
    goToAppointments: () -> Unit,
    goToSettings: () -> Unit,
) {
    RequireRole(userRole, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
        when (userRole) {
            UserRole.Facility -> MobileFacilityProfileScreen(
                goToCourses = goToFacilityCourses,
                goToSettings = goToSettings,
                goToCreatePost = { },
                goToVisitCalendar = { },
                goToVisitTrainerProfile = { },
                goToPhotoGallery = { },
                goToChat = { },
                viewMode = ProfileViewMode.OWNER,
                goToBack = { }
            )

            UserRole.Trainer -> MobileTrainerProfileScreen(
                goToSettings = goToSettings,
                goToCreatePost = { }
            )

            UserRole.User -> MobileUserProfileScreen(
                goToBack = { },
                goToSettings = goToSettings,
                goToAppointments = goToAppointments,
                goToMeasurements = { },
                goToExercises = { },
                goToPhotoDiary = { },
                goToCreatePost = { }
            )

            UserRole.Guest -> UnauthorizedAccessScreen()
        }
    }
}
