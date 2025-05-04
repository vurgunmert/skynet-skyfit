package com.vurgun.skyfit.feature.calendar

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.calendar.screen.appointments.TrainerAppointmentDetailScreen
import com.vurgun.skyfit.feature.calendar.screen.appointments.TrainerAppointmentListingScreen
import com.vurgun.skyfit.feature.calendar.screen.activitycalendar.UserActivityCalendarScreen
import com.vurgun.skyfit.feature.calendar.screen.activitycalendar.UserActivityCalendarSearchScreen
import com.vurgun.skyfit.feature.calendar.screen.appointments.UserAppointmentDetailScreen
import com.vurgun.skyfit.feature.calendar.screen.appointments.UserAppointmentListingScreen

val calendarScreenModule = screenModule {

    register<SharedScreen.UserActivityCalendar> { UserActivityCalendarScreen() }
    register<SharedScreen.UserActivityCalendarSearch> { UserActivityCalendarSearchScreen() }
    register<SharedScreen.UserAppointmentListing> { UserAppointmentListingScreen() }
    register<SharedScreen.UserAppointmentDetail> { UserAppointmentDetailScreen(it.id) }

    register<SharedScreen.TrainerAppointmentListing> { TrainerAppointmentListingScreen() }
    register<SharedScreen.TrainerAppointmentDetail> { TrainerAppointmentDetailScreen(it.id) }
}