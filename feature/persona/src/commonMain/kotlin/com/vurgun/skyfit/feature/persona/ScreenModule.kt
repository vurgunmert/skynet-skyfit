package com.vurgun.skyfit.feature.persona

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.persona.profile.ProfileScreen
import com.vurgun.skyfit.feature.persona.profile.facility.schedule.FacilityProfileScheduleScreen
import com.vurgun.skyfit.feature.persona.profile.facility.visitor.FacilityProfileVisitorScreen
import com.vurgun.skyfit.feature.persona.profile.trainer.schedule.TrainerProfileScheduleScreen
import com.vurgun.skyfit.feature.persona.profile.trainer.visitor.TrainerProfileVisitorScreen
import com.vurgun.skyfit.feature.persona.profile.user.visitor.UserProfileVisitorScreen
import com.vurgun.skyfit.feature.persona.settings.SettingsScreen
import com.vurgun.skyfit.feature.persona.social.CreatePostScreen
import com.vurgun.skyfit.feature.persona.social.SocialMediaScreen

val personaScreenModule = screenModule {

    register<SharedScreen.Social> { SocialMediaScreen() }

    register<SharedScreen.Profile> { ProfileScreen() }

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

    register<SharedScreen.Settings> { SettingsScreen() }

    register<SharedScreen.CreatePost> { CreatePostScreen() }
}