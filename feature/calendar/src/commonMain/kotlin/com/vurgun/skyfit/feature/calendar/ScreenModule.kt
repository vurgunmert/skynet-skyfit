package com.vurgun.skyfit.feature.calendar

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.calendar.screen.TrainerAppointmentDetailScreen
import com.vurgun.skyfit.feature.calendar.screen.TrainerAppointmentListingScreen
import com.vurgun.skyfit.feature.calendar.screen.UserAppointmentDetailScreen
import com.vurgun.skyfit.feature.calendar.screen.UserAppointmentListingScreen

val calendarScreenModule = screenModule {

    register<SharedScreen.UserAppointmentListing> { UserAppointmentListingScreen() }
    register<SharedScreen.UserAppointmentDetail> { UserAppointmentDetailScreen(it.id) }

    register<SharedScreen.TrainerAppointmentListing> { TrainerAppointmentListingScreen() }
    register<SharedScreen.TrainerAppointmentDetail> { TrainerAppointmentDetailScreen(it.id) }
}