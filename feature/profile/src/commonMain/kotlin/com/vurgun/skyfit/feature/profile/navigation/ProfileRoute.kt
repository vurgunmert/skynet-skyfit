package com.vurgun.skyfit.feature.profile.navigation

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.profile.facility.MobileFacilityProfileOwnerScreen
import com.vurgun.skyfit.feature.profile.trainer.MobileTrainerProfileScreen
import com.vurgun.skyfit.feature.profile.user.MobileUserProfileOwnerScreen
import com.vurgun.skyfit.feature.settings.component.rbac.RequireRole
import com.vurgun.skyfit.ui.core.screen.UnauthorizedAccessScreen
import kotlinx.serialization.Serializable

@Composable
fun ProfileOwnerRoot(
    userRole: UserRole,
    goToFacilityCourses: () -> Unit,
    goToAppointments: () -> Unit,
    goToSettings: () -> Unit,
    goToVisitFacility: (facilityId: Int) -> Unit,
) {
    RequireRole(userRole, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
        when (userRole) {
            UserRole.Facility -> MobileFacilityProfileOwnerScreen(
                goToLessonListing = goToFacilityCourses,
                goToSettings = goToSettings,
                goToCreatePost = { },
                goToTrainers = { },
                goToPhotoGallery = { },
                goToBack = { }
            )

            UserRole.Trainer -> MobileTrainerProfileScreen(
                goToSettings = goToSettings,
                goToCreatePost = { }
            )

            UserRole.User -> MobileUserProfileOwnerScreen(
                goToBack = { },
                goToSettings = goToSettings,
                goToAppointments = goToAppointments,
                goToMeasurements = { },
                goToExercises = { },
                goToPhotoDiary = { },
                goToCreatePost = { },
                onVisitFacility = goToVisitFacility
            )

            UserRole.Guest -> UnauthorizedAccessScreen()
        }
    }
}

@Serializable
data class VisitFacilityProfileRoute(val facilityId: Int)

@Serializable
data class VisitFacilityProfileCalendarRoute(val facilityId: Int)

@Serializable
data class VisitTrainerProfileRoute(val trainerId: Int)

@Serializable
data class VisitUserProfileRoute(val userId: Int)