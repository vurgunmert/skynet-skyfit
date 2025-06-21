package com.vurgun.skyfit.profile

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.profile.facility.schedule.FacilityProfileScheduleScreen
import com.vurgun.skyfit.profile.facility.screen.FacilityProfileScreen
import com.vurgun.skyfit.profile.screen.ProfileScreen
import com.vurgun.skyfit.profile.trainer.TrainerProfileScreen
import com.vurgun.skyfit.profile.trainer.schedule.TrainerProfileScheduleScreen
import com.vurgun.skyfit.profile.user.screen.UserProfileScreen

val screenProfileModule = screenModule {
    register<SharedScreen.Profile> { ProfileScreen() }
    register<SharedScreen.UserProfile> { UserProfileScreen(it.userId) }
    register<SharedScreen.FacilityProfile> { FacilityProfileScreen(it.facilityId) }
    register<SharedScreen.TrainerProfile> { TrainerProfileScreen(it.trainerId) }
    register<SharedScreen.FacilitySchedule> { FacilityProfileScheduleScreen(it.facilityId) }
    register<SharedScreen.TrainerSchedule> { TrainerProfileScheduleScreen(it.trainerId) }
}