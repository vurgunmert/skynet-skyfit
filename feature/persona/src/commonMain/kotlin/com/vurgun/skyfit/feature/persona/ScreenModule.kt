package com.vurgun.skyfit.feature.persona

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.screen.UnauthorizedAccessScreen
import com.vurgun.skyfit.feature.persona.profile.facility.schedule.FacilityProfileScheduleScreen
import com.vurgun.skyfit.feature.persona.profile.facility.visitor.FacilityProfileVisitorScreen
import com.vurgun.skyfit.feature.persona.profile.trainer.schedule.TrainerProfileScheduleScreen
import com.vurgun.skyfit.feature.persona.profile.trainer.visitor.TrainerProfileVisitorScreen
import com.vurgun.skyfit.feature.persona.profile.user.visitor.UserProfileVisitorScreen
import com.vurgun.skyfit.feature.persona.settings.SettingsScreen
import com.vurgun.skyfit.feature.persona.settings.facility.FacilitySettingsMainScreen
import com.vurgun.skyfit.feature.persona.settings.trainer.TrainerSettingsMainScreen
import com.vurgun.skyfit.feature.persona.settings.user.UserSettingsMainScreen
import com.vurgun.skyfit.feature.persona.social.CreatePostScreen
import kotlinx.serialization.Serializable

val personaScreenModule = screenModule {
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
    register<SettingsHomeEntryPoint.Facility> { FacilitySettingsMainScreen() }
    register<SettingsHomeEntryPoint.Trainer> { TrainerSettingsMainScreen() }
    register<SettingsHomeEntryPoint.User> { UserSettingsMainScreen() }
    register<SettingsHomeEntryPoint.Unauthorized> { UnauthorizedAccessScreen() }

    register<SharedScreen.CreatePost> { CreatePostScreen() }
}

@Serializable
internal sealed class SettingsHomeEntryPoint : ScreenProvider {
    @Serializable data object Facility : SettingsHomeEntryPoint()
    @Serializable data object Trainer : SettingsHomeEntryPoint()
    @Serializable data object User : SettingsHomeEntryPoint()
    @Serializable data object Unauthorized : SettingsHomeEntryPoint()
}
