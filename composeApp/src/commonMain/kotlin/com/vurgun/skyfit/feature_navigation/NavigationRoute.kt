package com.vurgun.skyfit.feature_navigation

import com.vurgun.skyfit.core.domain.models.UserType

sealed class NavigationRoute(
    private val baseRoute: String,
    val roles: List<UserType> = UserType.getAllUserTypes(),
    vararg params: Param
) {
    val route: String = if (params.isNotEmpty()) {
        val pathParams = params.joinToString("/") { "{${it.key}}" }
        "$baseRoute/$pathParams"
    } else baseRoute

    // Enum for route parameters
    enum class Param(val key: String) {
        User_ID("userId"),
        FACILITY_ID("facilityId"),
        Trainer_ID("trainerId"),
        CLASS_ID("classId"),
        BOOKING_PATH("bookingPath"),
        EXERCISE_ID("exerciseId"),
    }

    // Helper to create dynamic routes
    fun createRoute(vararg args: Pair<Param, String?>): String {
        val pathParams = args.mapNotNull { it.second }.joinToString("/")
        return "$baseRoute/$pathParams"
    }

    // Auth
    data object Maintenance : NavigationRoute("maintenance", roles = listOf(UserType.Guest))
    data object Splash : NavigationRoute("splash", roles = listOf(UserType.Guest))
    data object Login : NavigationRoute("login", roles = listOf(UserType.Guest))
    data object LoginVerifyOTP : NavigationRoute("login_otp_verification", roles = listOf(UserType.Guest))
    data object CreatePassword : NavigationRoute("create_password", roles = listOf(UserType.Guest))
    data object ForgotPassword : NavigationRoute("forgot_password", roles = listOf(UserType.Guest))
    data object ForgotPasswordVerifyOTP : NavigationRoute("forgot_password_code", roles = listOf(UserType.Guest))
    data object ForgotPasswordReset : NavigationRoute("forgot_password_reset", roles = listOf(UserType.Guest))
    data object PrivacyPolicy : NavigationRoute("privacy_policy", roles = listOf(UserType.Guest))
    data object TermsAndConditions : NavigationRoute("terms_and_conditions", roles = listOf(UserType.Guest))

    //Onboarding
    data object Onboarding : NavigationRoute("onboarding")
    data object OnboardingUserTypeSelection : NavigationRoute("onboarding/user_type_selection")
    data object OnboardingCharacterSelection : NavigationRoute("onboarding/character_selection")
    data object OnboardingBirthYearSelection : NavigationRoute("onboarding/birth_year_selection")
    data object OnboardingGenderSelection : NavigationRoute("onboarding/gender_selection")
    data object OnboardingWeightSelection : NavigationRoute("onboarding/weight_selection")
    data object OnboardingHeightSelection : NavigationRoute("onboarding/height_selection")
    data object OnboardingUserGoalSelection : NavigationRoute("onboarding/goal_selection")
    data object OnboardingBodyTypeSelection : NavigationRoute("onboarding/body_type_selection")
    data object OnboardingEnterProfile : NavigationRoute("onboarding/enter_profile")
    data object OnboardingFacilityDetails : NavigationRoute("onboarding/facility_details")
    data object OnboardingFacilityProfileTags : NavigationRoute("onboarding/facility_profile_tags")
    data object OnboardingCompleted : NavigationRoute("onboarding/completed")

    //Dashboard
    data object Dashboard : NavigationRoute("dashboard")
    data object DashboardHome : NavigationRoute("dashboard/home")
    data object DashboardExplore : NavigationRoute("dashboard/explore")
    data object DashboardSocial : NavigationRoute("dashboard/social")
    data object DashboardNutrition : NavigationRoute("dashboard/nutrition")
    data object DashboardProfile : NavigationRoute("dashboard/profile")

    // Explore
    data object ExploreTrainers : NavigationRoute("dashboard/explore/trainers")
    data object ExploreExercises : NavigationRoute("dashboard/explore/exercises")
    data object ExploreFacilities : NavigationRoute("dashboard/explore/facilities")
    data object ExploreBlogs : NavigationRoute("dashboard/explore/blogs")
    data object ExploreBlogArticleDetail : NavigationRoute("dashboard/explore/blogs/article/{articleId}")
    data object ExploreChallenges : NavigationRoute("dashboard/explore/challenges")
    data object ExploreChallengeDetail : NavigationRoute("dashboard/explore/challenges/detail/{challengeId}")
    data object ExploreCommunities : NavigationRoute("dashboard/explore/communities")
    data object ExploreCommunityDetail : NavigationRoute("dashboard/explore/communities/detail/{communityId}")


    //region User Screens
    data object UserSocialMedia : NavigationRoute("user/social/feeds")
    data object UserSocialMediaPostAdd : NavigationRoute("user/social/new_post")
    data object UserTrophies : NavigationRoute("user/trophies")
    data object UserPaymentProcess : NavigationRoute("user/payment/process")

    // - Exercises
    data object ExerciseDetail :
        NavigationRoute("user/exercises/detail/{exerciseId}", roles = listOf(UserType.User), Param.EXERCISE_ID)
    data object ExerciseInProgress :
        NavigationRoute("user/exercises/in_action/{exerciseId}", roles = listOf(UserType.User), Param.EXERCISE_ID)
    data object ExerciseCompleted :
        NavigationRoute("user/exercises/in_action/completed/{exerciseId}", roles = listOf(UserType.User), Param.EXERCISE_ID)

    // - Calendar
    data object UserActivityCalendar : NavigationRoute("user_activity_calendar")
    data object UserActivityCalendarAdd : NavigationRoute("user_activity_calendar/add")
    data object UserActivityCalendarSearch : NavigationRoute("user_activity_calendar/search")
    data object UserActivityCalendarPaymentRequired : NavigationRoute("user_activity_calendar/payment/required")
    data object UserActivityCalendarConfirmed : NavigationRoute("user_activity_calendar/confirmed")

    // - Appointments
    data object UserAppointments : NavigationRoute("user_appointments")
    data object UserAppointmentDetail :
        NavigationRoute("user_appointments/detail/{bookingPath}", roles = listOf(UserType.User), Param.BOOKING_PATH)

    // - Nutrition
    data object UserMealDetail : NavigationRoute("user/nutrition/meals/detail", roles = listOf(UserType.User))
    data object UserMealDetailAdd : NavigationRoute("user/nutrition/meals/detail/add", roles = listOf(UserType.User))
    data object UserMealDetailAddPhoto : NavigationRoute("user/nutrition/meals/detail/add_photo", roles = listOf(UserType.User))

    // - Notifications
    data object UserNotifications : NavigationRoute("notifications", roles = listOf(UserType.User))

    // - Messages
    data object UserChatBot : NavigationRoute("user/chat_bot", roles = listOf(UserType.User))
    data object UserToBotChat : NavigationRoute("user/conversations/toBot", roles = listOf(UserType.User))
    data object UserConversations : NavigationRoute("user/conversations", roles = listOf(UserType.User))
    data object UserToUserChat : NavigationRoute("user/conversations/toUser", roles = listOf(UserType.User))
    data object UserToTrainerChat : NavigationRoute("user/conversations/toTrainer", roles = listOf(UserType.User))
    data object UserToGroupChat : NavigationRoute("user/conversations/toGroup", roles = listOf(UserType.User))
    data object UserToFacilityChat : NavigationRoute("user/conversations/toFacility", roles = listOf(UserType.User))

    // - Profile
    data object UserProfile : NavigationRoute("profile/user/owner", roles = listOf(UserType.User))
    data object UserProfileVisited : NavigationRoute("profile/user/visited", roles = listOf(UserType.User))
    data object UserPhotoDiary : NavigationRoute("profile/user/owner/photo_diary", roles = listOf(UserType.User))
    data object UserMeasurements : NavigationRoute("profile/user/owner/measurements", roles = listOf(UserType.User))
    data object UserBodyAnalysis : NavigationRoute("profile/user/owner/body_analysis", roles = listOf(UserType.User))

    // - Settings
    data object UserSettings : NavigationRoute("user/settings", roles = listOf(UserType.User))
    data object UserSettingsAccount : NavigationRoute("user/settings/account", roles = listOf(UserType.User))
    data object UserSettingsChangePassword : NavigationRoute("user/settings/change_password", roles = listOf(UserType.User))
    data object UserSettingsNotifications :
        NavigationRoute("user/settings/notifications", roles = listOf(UserType.User))

    data object UserSettingsPaymentHistory :
        NavigationRoute("user/settings/payment_history", roles = listOf(UserType.User))

    data object UserSettingsHelp : NavigationRoute("user/settings/help", roles = listOf(UserType.User))

    //endregion User Screens

    //region Trainer Screens
    // - Appointments
    data object TrainerAppointments : NavigationRoute("trainer/appointments")
    data object TrainerAppointmentDetail : NavigationRoute(
        "trainer/appointments/detail/{bookingPath}",
        roles = listOf(UserType.Trainer),
        Param.BOOKING_PATH
    )

    // - Profile
    data object TrainerProfile : NavigationRoute("profile/trainer/owner", roles = listOf(UserType.Trainer))
    data object TrainerPhotoDiary :
        NavigationRoute("profile/trainer/owner/photo_diary", roles = listOf(UserType.Trainer))

    data object TrainerProfileVisited :
        NavigationRoute("profile/trainer/visited/{trainerId}", roles = listOf(UserType.Trainer), Param.Trainer_ID)

    data object TrainerCalendarVisited : NavigationRoute(
        "profile/trainer/calendar/visited/{trainerId}",
        roles = listOf(UserType.User),
        Param.Trainer_ID
    )

    // - Settings
    data object TrainerSettings : NavigationRoute("trainer/settings", roles = listOf(UserType.Trainer))
    data object TrainerSettingsAccount : NavigationRoute("trainer/settings/account", roles = listOf(UserType.Trainer))
    data object TrainerSettingsNotifications :
        NavigationRoute("trainer/settings/notifications", roles = listOf(UserType.Trainer))

    data object TrainerSettingsPaymentHistory :
        NavigationRoute("trainer/settings/payment_history", roles = listOf(UserType.Trainer))

    data object TrainerSettingsHelp : NavigationRoute("trainer/settings/help", roles = listOf(UserType.Trainer))
    //endregion Trainer Screens

    //region Facility Screens
    // - Profile
    data object FacilityProfile : NavigationRoute("profile/facility/owner", roles = listOf(UserType.Facility))
    data object FacilityPhotoGallery :
        NavigationRoute("profile/facility/owner/photo_diary", roles = listOf(UserType.Facility))

    data object FacilityProfileVisited : NavigationRoute(
        "profile/facility/owner/visited/{facilityId}",
        roles = listOf(UserType.User),
        Param.FACILITY_ID
    )

    // - Calendar
    data object FacilityCalendar :
        NavigationRoute("facility/activity_calendar/owner", roles = listOf(UserType.Facility))

    data object FacilityCalendarVisited : NavigationRoute(
        "facility/activity_calendar/Guest/{facilityId}",
        roles = listOf(UserType.User),
        Param.FACILITY_ID
    )

    // - Appointments
    data object FacilityAppointmentDetail : NavigationRoute(
        "facility_appointments/detail/{bookingPath}",
        roles = listOf(UserType.Facility),
        Param.BOOKING_PATH
    )

    // - Classes
    data object FacilityManageLessons : NavigationRoute("facility_classes/owner", roles = listOf(UserType.Facility))
    data object FacilityClassesVisited : NavigationRoute("facility_classes/Guest", roles = listOf(UserType.User))

    data object FacilityClassEdit :
        NavigationRoute("facility_class/edit/{classId}", roles = listOf(UserType.Facility), Param.CLASS_ID)

    data object FacilityClassEditCompleted :
        NavigationRoute("facility_class/edit/completed/{classId}", roles = listOf(UserType.Facility), Param.CLASS_ID)

    data object FacilityClassDetail : NavigationRoute(
        "facility_class/detail/owner/{classId}",
        roles = listOf(UserType.Facility),
        Param.CLASS_ID
    )

    data object FacilityClassDetailVisited :
        NavigationRoute("facility_class/detail/visited/{classId}", roles = listOf(UserType.User), Param.CLASS_ID)

    // - Settings
    data object FacilitySettings : NavigationRoute("facility/settings", roles = listOf(UserType.Facility))
    data object FacilitySettingsAccount : NavigationRoute("facility/settings/account", roles = listOf(UserType.Facility))
    data object FacilitySettingsSearchMembers :
        NavigationRoute("facility/settings/members/search", roles = listOf(UserType.Facility))
    data object FacilitySettingsAddMembers :
        NavigationRoute("facility/settings/members/add", roles = listOf(UserType.Facility))

    data object FacilitySettingsTrainers :
        NavigationRoute("facility/settings/trainers", roles = listOf(UserType.Facility))

    data object FacilitySettingsPaymentHistory :
        NavigationRoute("facility/settings/payment_history", roles = listOf(UserType.Facility))

    data object FacilitySettingsNotifications :
        NavigationRoute("facility/settings/notifications", roles = listOf(UserType.Facility))

    data object FacilitySettingsHelp :
        NavigationRoute("facility/settings/help", roles = listOf(UserType.Facility))

    //endregion Facility Screens
}