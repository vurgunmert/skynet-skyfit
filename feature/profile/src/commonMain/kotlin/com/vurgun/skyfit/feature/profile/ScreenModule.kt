package com.vurgun.skyfit.feature.profile

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.profile.facility.schedule.FacilityProfileScheduleScreen
import com.vurgun.skyfit.feature.profile.facility.visitor.FacilityProfileVisitorScreen
import com.vurgun.skyfit.feature.profile.trainer.schedule.TrainerProfileScheduleScreen
import com.vurgun.skyfit.feature.profile.trainer.visitor.TrainerProfileVisitorScreen
import com.vurgun.skyfit.feature.profile.user.visitor.UserProfileVisitorScreen

val profileScreenModule = screenModule {
    register<SharedScreen.UserProfileVisitor> {
        UserProfileVisitorScreen(normalUserId = it.id)
    }
    register<SharedScreen.FacilityProfileVisitor> {
        FacilityProfileVisitorScreen(facilityId = it.id)
    }
    register<SharedScreen.FacilitySchedule> {
        FacilityProfileScheduleScreen(facilityId = it.id)
    }
    register<SharedScreen.TrainerProfileVisitor> {
        TrainerProfileVisitorScreen(trainerId = it.id)
    }
    register<SharedScreen.TrainerSchedule> {
        TrainerProfileScheduleScreen(it.id)
    }
}