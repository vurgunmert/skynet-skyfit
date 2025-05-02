package com.vurgun.skyfit.core.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import kotlinx.serialization.Serializable

@Serializable
sealed class SharedScreen : ScreenProvider {

    // Splash
    data object Splash : SharedScreen()
    data object Maintenance : SharedScreen()

    // Authorization
    data object Authorization : SharedScreen()
    data object Onboarding : SharedScreen()

    // Dashboard-Home
    data object Dashboard : SharedScreen()

    // Explore
    data object ExploreTrainers: SharedScreen()
    data object ExploreFacilities: SharedScreen()
    data object ExploreExercises: SharedScreen()
    data object ExploreBlogs: SharedScreen()
    data object ExploreChallenges: SharedScreen()
    data object ExploreCommunities: SharedScreen()
    data object BlogDetail: SharedScreen()
    data object ChallengeDetail: SharedScreen()
    data object CommunityDetail: SharedScreen()

    // Calendar

    // Appointments
    data object UserAppointmentListing : SharedScreen()
    data object TrainerAppointmentListing : SharedScreen()
    data class UserAppointmentDetail(val id: Int) : SharedScreen()
    data class TrainerAppointmentDetail(val id: Int) : SharedScreen()

    // Notifications
    data object Notifications : SharedScreen()

    // Messages
    data object Conversations : SharedScreen()
    data class UserChat(val participantId: Int) : SharedScreen()

    // Posture Analysis
    data object PostureAnalysis : SharedScreen()

    // Chatbot
    data object ChatBot : SharedScreen()

    // Profile
    data class UserProfileVisitor(val id: Int) : SharedScreen()
    data class FacilityProfileVisitor(val id: Int) : SharedScreen()
    data class TrainerProfileVisitor(val id: Int) : SharedScreen()
    // Profile - Schedule
    data class FacilitySchedule(val id: Int) : SharedScreen()
    data class TrainerSchedule(val id: Int) : SharedScreen()

    // Courses
    data object FacilityManageLessons : SharedScreen()

    // Social
    data object CreatePost : SharedScreen()

    // Settings
    data object Settings : SharedScreen()
}
