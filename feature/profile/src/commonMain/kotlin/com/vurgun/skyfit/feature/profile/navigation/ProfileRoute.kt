package com.vurgun.skyfit.feature.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.profile.components.viewdata.ProfileViewMode
import com.vurgun.skyfit.feature.profile.facility.MobileFacilityProfileScreen
import com.vurgun.skyfit.feature.profile.trainer.MobileTrainerProfileScreen
import com.vurgun.skyfit.feature.profile.user.MobileUserProfileScreen
import com.vurgun.skyfit.feature.settings.component.rbac.RequireRole
import com.vurgun.skyfit.ui.core.screen.UnauthorizedAccessScreen
import kotlinx.serialization.Serializable

sealed interface ProfileRoute {

    @Serializable
    data object Main : ProfileRoute
}


fun NavGraphBuilder.profileRoutes(
    userRole: UserRole
) {
    composable<ProfileRoute.Main> {

        RequireRole(userRole, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
            when (userRole) {
                UserRole.Facility -> MobileFacilityProfileScreen(
                    goToManageLessons = { },
                    goToSettings = { },
                    goToCreatePost = { },
                    goToVisitCalendar = { },
                    goToVisitTrainerProfile = { },
                    goToPhotoGallery = { },
                    goToChat = { },
                    viewMode = ProfileViewMode.OWNER,
                    goToBack = { }
                )

                UserRole.Trainer -> MobileTrainerProfileScreen(
                    goToSettings = { },
                    goToCreatePost = { }
                )

                UserRole.User -> MobileUserProfileScreen(
                    goToBack = { },
                    goToSettings = { },
                    goToAppointments = { },
                    goToMeasurements = { },
                    goToExercises = { },
                    goToPhotoDiary = { },
                    goToCreatePost = { }
                )

                UserRole.Guest -> UnauthorizedAccessScreen()
            }
        }
    }
}
