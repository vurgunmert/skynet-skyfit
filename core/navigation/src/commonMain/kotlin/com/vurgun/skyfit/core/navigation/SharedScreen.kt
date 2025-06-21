package com.vurgun.skyfit.core.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
sealed class SharedScreen(open val key: String) : ScreenProvider {

    // Splash & Maintenance
    data object UnderDevelopment : SharedScreen("under_development")
    data object Splash : SharedScreen("splash")
    data object Maintenance : SharedScreen("maintenance")

    // Authorization
    data object Authorization : SharedScreen("authorization")
    data object Onboarding : SharedScreen("onboarding")

    // Dashboard / Main Screens
    data object Main : SharedScreen("main")
    data object Home : SharedScreen("home")
    data object Explore : SharedScreen("explore")
    data object Social : SharedScreen("social")
    data object Profile : SharedScreen("profile")
    data object Nutrition : SharedScreen("nutrition")

    // Explore Subsections
    data object ExploreTrainers : SharedScreen("explore_trainers")
    data object ExploreFacilities : SharedScreen("explore_facilities")
    data object ExploreExercises : SharedScreen("explore_exercises")
    data object ExploreBlogs : SharedScreen("explore_blogs")
    data object ExploreChallenges : SharedScreen("explore_challenges")
    data object ExploreCommunities : SharedScreen("explore_communities")

    data object BlogDetail : SharedScreen("blog_detail")
    data object ChallengeDetail : SharedScreen("challenge_detail")
    data object CommunityDetail : SharedScreen("community_detail")

    // Calendar
    data class UserActivityCalendar(val selectedDate: LocalDate? = null) :
        SharedScreen("calendar:user_activity:${selectedDate.toString()}")

    // Appointments
    data object UserAppointmentListing : SharedScreen("user:appointment:listings")
    data object TrainerAppointmentListing : SharedScreen("trainer:appointment:listings")

    data class UserAppointmentDetail(val id: Int) :
        SharedScreen("appointments:user_detail:$id")

    data class TrainerAppointmentDetail(val id: Int) :
        SharedScreen("appointments:trainer_detail:$id")

    // Notifications
    data object Notifications : SharedScreen("notifications")
    data class NotificationsExpanded(val onDismiss: () -> Unit) :
        SharedScreen("notifications:expanded")

    // Messages
    data object Conversations : SharedScreen("conversations")
    data class ConversationsExpanded(val onDismiss: () -> Unit) :
        SharedScreen("conversations:expanded")

    data class UserChat(val participantId: Int) :
        SharedScreen("conversations:chat:$participantId")

    data class ChatWithTrainer(val trainerId: Int) :
        SharedScreen("conversations:chat:with-trainer:$trainerId")

    data class ChatWithFacility(val facilityId: Int) :
        SharedScreen("conversations:chat:with-facility:$facilityId")

    // Posture Analysis
    data object PostureAnalysis : SharedScreen("posture_analysis")

    // AI ChatBot
    data object ChatBot : SharedScreen("chat_bot")

    // Profiles
    data class UserProfile(val userId: Int) : SharedScreen("profile:user:$userId")
    data class FacilityProfile(val facilityId: Int) : SharedScreen("profile:facility:$facilityId")
    data class TrainerProfile(val trainerId: Int) : SharedScreen("profile:trainer:$trainerId")

    // Schedules
    data class FacilitySchedule(val facilityId: Int) : SharedScreen("schedule:facility:$facilityId")
    data class TrainerSchedule(val trainerId: Int) : SharedScreen("schedule:trainer:$trainerId")

    // Courses / Lessons
    data object FacilityManageLessons : SharedScreen("facility:schedule:lessons")
    data object TrainerManageLessons : SharedScreen("trainer:schedule:lessons")
    data class LessonDetail(val id: Int) : SharedScreen("schedule:lesson:$id")
    data class LessonFilter(
        val lessons: List<Any>,
        val onApply: (Any) -> Unit
    ) : SharedScreen("schedule:lesson:filter")

    // Social / Posts
    data object CreatePost : SharedScreen("social:create_post")

    // Settings
    data object Settings : SharedScreen("settings")
}
