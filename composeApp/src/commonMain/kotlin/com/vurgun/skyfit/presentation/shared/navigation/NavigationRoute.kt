package com.vurgun.skyfit.presentation.shared.navigation

// Enum for user roles
enum class Role {
    VISITOR, USER, TRAINER, FACILITY_MANAGER
}

sealed class NavigationRoute(
    private val baseRoute: String,
    val roles: List<Role> = listOf(Role.USER, Role.TRAINER, Role.FACILITY_MANAGER),
    vararg params: Param
) {
    val route: String = if (params.isNotEmpty()) {
        val pathParams = params.joinToString("/") { "{${it.key}}" }
        "$baseRoute/$pathParams"
    } else baseRoute

    // Enum for route parameters
    enum class Param(val key: String) {
        USER_ID("userId"),
        FACILITY_ID("facilityId"),
        TRAINER_ID("trainerId"),
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
    data object Splash : NavigationRoute("splash", roles = listOf(Role.VISITOR))
    data object Login : NavigationRoute("login", roles = listOf(Role.VISITOR))
    data object Register : NavigationRoute("register", roles = listOf(Role.VISITOR))
    data object OTPVerification : NavigationRoute("otp_verification", roles = listOf(Role.VISITOR))
    data object ForgotPassword : NavigationRoute("forgot_password", roles = listOf(Role.VISITOR))
    data object ForgotPasswordCode : NavigationRoute("forgot_password_code", roles = listOf(Role.VISITOR))
    data object ForgotPasswordReset : NavigationRoute("forgot_password_reset", roles = listOf(Role.VISITOR))

    //Onboarding
    data object Onboarding : NavigationRoute("onboarding")
    data object OnboardingUserTypeSelection : NavigationRoute("onboarding/user_type_selection")
    data object OnboardingCharacterSelection : NavigationRoute("onboarding/character_selection")
    data object OnboardingBirthYearSelection : NavigationRoute("onboarding/birth_year_selection")
    data object OnboardingGenderSelection : NavigationRoute("onboarding/gender_selection")
    data object OnboardingWeightSelection : NavigationRoute("onboarding/weight_selection")
    data object OnboardingHeightSelection : NavigationRoute("onboarding/height_selection")
    data object OnboardingGoalSelection : NavigationRoute("onboarding/goal_selection")
    data object OnboardingCompleted : NavigationRoute("onboarding/completed")

    //Dashboard
    data object Dashboard : NavigationRoute("dashboard")
    data object DashboardHome : NavigationRoute("dashboard/home")
    data object DashboardExplore : NavigationRoute("dashboard/explore")
    data object DashboardSocial : NavigationRoute("dashboard/social")
    data object DashboardNutrition : NavigationRoute("dashboard/nutrition")
    data object DashboardProfile : NavigationRoute("dashboard/profile")

    // Explore
    data object DashboardExploreTrainers : NavigationRoute("dashboard/explore/trainers")
    data object DashboardExploreExercises : NavigationRoute("dashboard/explore/exercises")
    data object DashboardExploreFacilities : NavigationRoute("dashboard/explore/facilities")
    data object DashboardExploreBlogs : NavigationRoute("dashboard/explore/blogs")
    data object DashboardExploreBlogArticleDetail : NavigationRoute("dashboard/explore/blogs/article/{articleId}")
    data object DashboardExploreChallenges : NavigationRoute("dashboard/explore/challenges")
    data object DashboardExploreChallengeDetail : NavigationRoute("dashboard/explore/challenges/detail/{challengeId}")
    data object DashboardExploreCommunities : NavigationRoute("dashboard/explore/communities")
    data object DashboardExploreCommunityDetail : NavigationRoute("dashboard/explore/communities/detail/{communityId}")


    //region User Screens
    data object UserSocialMedia : NavigationRoute("user/social/feeds")
    data object UserSocialMediaPostAdd : NavigationRoute("user/social/new_post")
    data object UserTrophies : NavigationRoute("user/trophies")
    data object UserPaymentProcess : NavigationRoute("user/payment/process")

    // - Exercises
    data object UserExerciseDetail :
        NavigationRoute("user/exercises/detail/{exerciseId}", roles = listOf(Role.USER), Param.EXERCISE_ID)
    data object UserExerciseInAction :
        NavigationRoute("user/exercises/in_action/{exerciseId}", roles = listOf(Role.USER), Param.EXERCISE_ID)
    data object UserExerciseInActionComplete :
        NavigationRoute("user/exercises/in_action/completed/{exerciseId}", roles = listOf(Role.USER), Param.EXERCISE_ID)

    // - Calendar
    data object UserActivityCalendar : NavigationRoute("user_activity_calendar")
    data object UserActivityCalendarAdd : NavigationRoute("user_activity_calendar/add")
    data object UserActivityCalendarSearch : NavigationRoute("user_activity_calendar/search")
    data object UserActivityCalendarPaymentRequired : NavigationRoute("user_activity_calendar/payment/required")
    data object UserActivityCalendarConfirmed : NavigationRoute("user_activity_calendar/confirmed")

    // - Appointments
    data object UserAppointments : NavigationRoute("user_appointments")
    data object UserAppointmentDetail :
        NavigationRoute("user_appointments/detail/{bookingPath}", roles = listOf(Role.USER), Param.BOOKING_PATH)

    // - Nutrition
    data object UserMealDetail : NavigationRoute("user/nutrition/meals/detail", roles = listOf(Role.USER))
    data object UserMealDetailAdd : NavigationRoute("user/nutrition/meals/detail/add", roles = listOf(Role.USER))
    data object UserMealDetailAddPhoto : NavigationRoute("user/nutrition/meals/detail/add_photo", roles = listOf(Role.USER))

    // - Notifications
    data object UserNotifications : NavigationRoute("notifications", roles = listOf(Role.USER))

    // - Messages
    data object UserChatBot : NavigationRoute("user/chat_bot", roles = listOf(Role.USER))
    data object UserToBotChat : NavigationRoute("user/conversations/toBot", roles = listOf(Role.USER))
    data object UserConversations : NavigationRoute("user/conversations", roles = listOf(Role.USER))
    data object UserToUserChat : NavigationRoute("user/conversations/toUser", roles = listOf(Role.USER))
    data object UserToTrainerChat : NavigationRoute("user/conversations/toTrainer", roles = listOf(Role.USER))
    data object UserToGroupChat : NavigationRoute("user/conversations/toGroup", roles = listOf(Role.USER))
    data object UserToFacilityChat : NavigationRoute("user/conversations/toFacility", roles = listOf(Role.USER))

    // - Profile
    data object UserProfile : NavigationRoute("profile/user/owner", roles = listOf(Role.USER))
    data object UserProfileVisited : NavigationRoute("profile/user/visited", roles = listOf(Role.USER))
    data object UserPhotoDiary : NavigationRoute("profile/user/owner/photo_diary", roles = listOf(Role.USER))
    data object UserMeasurements : NavigationRoute("profile/user/owner/measurements", roles = listOf(Role.USER))
    data object UserBodyAnalysis : NavigationRoute("profile/user/owner/body_analysis", roles = listOf(Role.USER))

    // - Settings
    data object UserSettings : NavigationRoute("user/settings", roles = listOf(Role.USER))
    data object UserSettingsAccount : NavigationRoute("user/settings/account", roles = listOf(Role.USER))
    data object UserSettingsChangePassword : NavigationRoute("user/settings/change_password", roles = listOf(Role.USER))
    data object UserSettingsNotifications :
        NavigationRoute("user/settings/notifications", roles = listOf(Role.USER))

    data object UserSettingsPaymentHistory :
        NavigationRoute("user/settings/payment_history", roles = listOf(Role.USER))

    data object UserSettingsHelp : NavigationRoute("user/settings/help", roles = listOf(Role.USER))

    //endregion User Screens

    //region Trainer Screens
    // - Appointments
    data object TrainerAppointments : NavigationRoute("trainer/appointments")
    data object TrainerAppointmentDetail : NavigationRoute(
        "trainer/appointments/detail/{bookingPath}",
        roles = listOf(Role.TRAINER),
        Param.BOOKING_PATH
    )

    // - Profile
    data object TrainerProfile : NavigationRoute("profile/trainer/owner", roles = listOf(Role.TRAINER))
    data object TrainerPhotoDiary :
        NavigationRoute("profile/trainer/owner/photo_diary", roles = listOf(Role.TRAINER))

    data object TrainerProfileVisited :
        NavigationRoute("profile/trainer/visited/{trainerId}", roles = listOf(Role.TRAINER), Param.TRAINER_ID)

    data object TrainerCalendarVisited : NavigationRoute(
        "profile/trainer/calendar/visited/{trainerId}",
        roles = listOf(Role.USER),
        Param.TRAINER_ID
    )

    // - Settings
    data object TrainerSettings : NavigationRoute("trainer/settings", roles = listOf(Role.TRAINER))
    data object TrainerSettingsAccount : NavigationRoute("trainer/settings/account", roles = listOf(Role.TRAINER))
    data object TrainerSettingsNotifications :
        NavigationRoute("trainer/settings/notifications", roles = listOf(Role.TRAINER))

    data object TrainerSettingsPaymentHistory :
        NavigationRoute("trainer/settings/payment_history", roles = listOf(Role.TRAINER))

    data object TrainerSettingsHelp : NavigationRoute("trainer/settings/help", roles = listOf(Role.TRAINER))
    //endregion Trainer Screens

    //region Facility Screens
    // - Profile
    data object FacilityProfile : NavigationRoute("profile/facility/owner", roles = listOf(Role.FACILITY_MANAGER))
    data object FacilityPhotoDiary :
        NavigationRoute("profile/facility/owner/photo_diary", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilityProfileVisited : NavigationRoute(
        "profile/facility/owner/visited/{facilityId}",
        roles = listOf(Role.USER),
        Param.FACILITY_ID
    )

    // - Calendar
    data object FacilityCalendar :
        NavigationRoute("facility/activity_calendar/owner", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilityCalendarVisited : NavigationRoute(
        "facility/activity_calendar/visitor/{facilityId}",
        roles = listOf(Role.USER),
        Param.FACILITY_ID
    )

    // - Appointments
    data object FacilityAppointmentDetail : NavigationRoute(
        "facility_appointments/detail/{bookingPath}",
        roles = listOf(Role.FACILITY_MANAGER),
        Param.BOOKING_PATH
    )

    // - Classes
    data object FacilityClasses : NavigationRoute("facility_classes/owner", roles = listOf(Role.FACILITY_MANAGER))
    data object FacilityClassesVisited : NavigationRoute("facility_classes/visitor", roles = listOf(Role.USER))

    data object FacilityClassEdit :
        NavigationRoute("facility_class/edit/{classId}", roles = listOf(Role.FACILITY_MANAGER), Param.CLASS_ID)

    data object FacilityClassEditCompleted :
        NavigationRoute("facility_class/edit/completed/{classId}", roles = listOf(Role.FACILITY_MANAGER), Param.CLASS_ID)

    data object FacilityClassDetail : NavigationRoute(
        "facility_class/detail/owner/{classId}",
        roles = listOf(Role.FACILITY_MANAGER),
        Param.CLASS_ID
    )

    data object FacilityClassDetailVisited :
        NavigationRoute("facility_class/detail/visited/{classId}", roles = listOf(Role.USER), Param.CLASS_ID)

    // - Settings
    data object FacilitySettings : NavigationRoute("facility/settings", roles = listOf(Role.FACILITY_MANAGER))
    data object FacilitySettingsAccount : NavigationRoute("facility/settings/account", roles = listOf(Role.FACILITY_MANAGER))
    data object FacilitySettingsSearchMembers :
        NavigationRoute("facility/settings/members/search", roles = listOf(Role.FACILITY_MANAGER))
    data object FacilitySettingsAddMembers :
        NavigationRoute("facility/settings/members/add", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilitySettingsTrainers :
        NavigationRoute("facility/settings/trainers", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilitySettingsPaymentHistory :
        NavigationRoute("facility/settings/payment_history", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilitySettingsNotifications :
        NavigationRoute("facility/settings/notifications", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilitySettingsHelp :
        NavigationRoute("facility/settings/help", roles = listOf(Role.FACILITY_MANAGER))

    //endregion Facility Screens
}