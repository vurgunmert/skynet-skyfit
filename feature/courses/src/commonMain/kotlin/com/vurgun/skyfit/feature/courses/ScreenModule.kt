package com.vurgun.skyfit.feature.courses

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.courses.screen.FacilityLessonListingScreen

val coursesScreenModule = screenModule {
    register<SharedScreen.FacilityManageLessons> { FacilityLessonListingScreen() }
}