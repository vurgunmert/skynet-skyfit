package com.vurgun.skyfit.feature.schedule

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.schedule.screen.lessons.FacilityLessonListingScreen
import com.vurgun.skyfit.feature.schedule.screen.activitycalendar.UserActivityCalendarScreen
import com.vurgun.skyfit.feature.schedule.screen.activitycalendar.UserActivityCalendarSearchScreen
import com.vurgun.skyfit.feature.schedule.screen.appointments.TrainerAppointmentDetailScreen
import com.vurgun.skyfit.feature.schedule.screen.appointments.TrainerAppointmentListingScreen
import com.vurgun.skyfit.feature.schedule.screen.appointments.UserAppointmentDetailScreen
import com.vurgun.skyfit.feature.schedule.screen.appointments.UserAppointmentListingScreen

val scheduleScreenModule = screenModule {
    register<SharedScreen.UserActivityCalendar> { UserActivityCalendarScreen(it.selectedDate) }
    register<SharedScreen.UserActivityCalendarSearch> { UserActivityCalendarSearchScreen() }
    register<SharedScreen.UserAppointmentListing> { UserAppointmentListingScreen() }
    register<SharedScreen.UserAppointmentDetail> { UserAppointmentDetailScreen(it.id) }

    register<SharedScreen.TrainerAppointmentListing> { TrainerAppointmentListingScreen() }
    register<SharedScreen.TrainerAppointmentDetail> { TrainerAppointmentDetailScreen(it.id) }

    register<SharedScreen.FacilityManageLessons> { FacilityLessonListingScreen() }
}