package com.vurgun.skyfit.presentation.shared.navigation

// Enum for user roles
enum class Role {
    VISITOR, USER, TRAINER, FACILITY_MANAGER
}

sealed class SkyFitNavigationRoute(
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
    data object Splash : SkyFitNavigationRoute("splash", roles = listOf(Role.VISITOR))
    data object Login : SkyFitNavigationRoute("login", roles = listOf(Role.VISITOR))
    data object Register : SkyFitNavigationRoute("register", roles = listOf(Role.VISITOR))
    data object ForgotPassword : SkyFitNavigationRoute("forgot_password", roles = listOf(Role.VISITOR))
    data object ForgotPasswordCode : SkyFitNavigationRoute("forgot_password_code", roles = listOf(Role.VISITOR))
    data object ForgotPasswordReset : SkyFitNavigationRoute("forgot_password_reset", roles = listOf(Role.VISITOR))

    //Onboarding
    data object Onboarding : SkyFitNavigationRoute("onboarding")
    data object OnboardingUserTypeSelection : SkyFitNavigationRoute("onboarding/user_type_selection")
    data object OnboardingCharacterSelection : SkyFitNavigationRoute("onboarding/character_selection")
    data object OnboardingBirthYearSelection : SkyFitNavigationRoute("onboarding/birth_year_selection")
    data object OnboardingGenderSelection : SkyFitNavigationRoute("onboarding/gender_selection")
    data object OnboardingWeightSelection : SkyFitNavigationRoute("onboarding/weight_selection")
    data object OnboardingHeightSelection : SkyFitNavigationRoute("onboarding/height_selection")
    data object OnboardingGoalSelection : SkyFitNavigationRoute("onboarding/goal_selection")
    data object OnboardingCompleted : SkyFitNavigationRoute("onboarding/completed")

    //Dashboard
    data object Dashboard : SkyFitNavigationRoute("dashboard")
    data object DashboardHome : SkyFitNavigationRoute("dashboard/home")
    data object DashboardExplore : SkyFitNavigationRoute("dashboard/explore")
    data object DashboardSocial : SkyFitNavigationRoute("dashboard/social")
    data object DashboardNutrition : SkyFitNavigationRoute("dashboard/nutrition")
    data object DashboardProfile : SkyFitNavigationRoute("dashboard/profile")

    // Explore
    data object DashboardExploreTrainers : SkyFitNavigationRoute("dashboard/explore/trainers")
    data object DashboardExploreExercises : SkyFitNavigationRoute("dashboard/explore/exercises")
    data object DashboardExploreFacilities : SkyFitNavigationRoute("dashboard/explore/facilities")
    data object DashboardExploreBlogs : SkyFitNavigationRoute("dashboard/explore/blogs")
    data object DashboardExploreBlogArticleDetail : SkyFitNavigationRoute("dashboard/explore/blogs/article/{articleId}")
    data object DashboardExploreChallenges : SkyFitNavigationRoute("dashboard/explore/challenges")
    data object DashboardExploreChallengeDetail : SkyFitNavigationRoute("dashboard/explore/challenges/detail/{challengeId}")
    data object DashboardExploreCommunities : SkyFitNavigationRoute("dashboard/explore/communities")
    data object DashboardExploreCommunityDetail : SkyFitNavigationRoute("dashboard/explore/communities/detail/{communityId}")


    //region User Screens
    data object UserSocialMedia : SkyFitNavigationRoute("user/social/feeds")
    data object UserSocialMediaPostAdd : SkyFitNavigationRoute("user/social/new_post")
    data object UserTrophies : SkyFitNavigationRoute("user/trophies")

    // - Exercises
    data object UserExerciseDetail :
        SkyFitNavigationRoute("user/exercises/detail/{exerciseId}", roles = listOf(Role.USER), Param.EXERCISE_ID)
    data object UserExerciseInAction :
        SkyFitNavigationRoute("user/exercises/in_action/{exerciseId}", roles = listOf(Role.USER), Param.EXERCISE_ID)
    data object UserExerciseInActionComplete :
        SkyFitNavigationRoute("user/exercises/in_action/completed/{exerciseId}", roles = listOf(Role.USER), Param.EXERCISE_ID)

    // - Calendar
    data object UserActivityCalendar : SkyFitNavigationRoute("user_activity_calendar")
    data object UserActivityCalendarAdd : SkyFitNavigationRoute("user_activity_calendar/add")
    data object UserActivityCalendarSearch : SkyFitNavigationRoute("user_activity_calendar/search")
    data object UserActivityCalendarPayment : SkyFitNavigationRoute("user_activity_calendar/payment")
    data object UserActivityCalendarConfirmed : SkyFitNavigationRoute("user_activity_calendar/confirmed")

    // - Appointments
    data object UserAppointments : SkyFitNavigationRoute("user_appointments")
    data object UserAppointmentDetail :
        SkyFitNavigationRoute("user_appointments/detail/{bookingPath}", roles = listOf(Role.USER), Param.BOOKING_PATH)

    // - Nutrition
    data object UserMealDetail : SkyFitNavigationRoute("user/nutrition/meals/detail", roles = listOf(Role.USER))
    data object UserMealDetailAdd : SkyFitNavigationRoute("user/nutrition/meals/detail/add", roles = listOf(Role.USER))
    data object UserMealDetailAddPhoto : SkyFitNavigationRoute("user/nutrition/meals/detail/add_photo", roles = listOf(Role.USER))

    // - Notifications
    data object UserNotifications : SkyFitNavigationRoute("notifications", roles = listOf(Role.USER))

    // - Messages
    data object UserChatBot : SkyFitNavigationRoute("user/chat_bot", roles = listOf(Role.USER))
    data object UserConversations : SkyFitNavigationRoute("user/conversations", roles = listOf(Role.USER))
    data object UserToUserChat : SkyFitNavigationRoute("user/conversations/toUser", roles = listOf(Role.USER))
    data object UserToGroupChat : SkyFitNavigationRoute("user/conversations/toGroup", roles = listOf(Role.USER))
    data object UserToFacilityChat : SkyFitNavigationRoute("user/conversations/toFacility", roles = listOf(Role.USER))

    // - Profile
    data object UserProfile : SkyFitNavigationRoute("profile/user/owner", roles = listOf(Role.USER))
    data object UserProfileVisited : SkyFitNavigationRoute("profile/user/visited", roles = listOf(Role.USER))
    data object UserPhotoDiary : SkyFitNavigationRoute("profile/user/owner/photo_diary", roles = listOf(Role.USER))
    data object UserMeasurements : SkyFitNavigationRoute("profile/user/owner/measurements", roles = listOf(Role.USER))
    data object UserBodyAnalysis : SkyFitNavigationRoute("profile/user/owner/body_analysis", roles = listOf(Role.USER))

    // - Settings
    data object UserSettings : SkyFitNavigationRoute("user/settings", roles = listOf(Role.USER))
    data object UserSettingsAccount : SkyFitNavigationRoute("user/settings/account", roles = listOf(Role.USER))
    data object UserSettingsNotifications :
        SkyFitNavigationRoute("user/settings/notifications", roles = listOf(Role.USER))

    data object UserSettingsPaymentHistory :
        SkyFitNavigationRoute("user/settings/payment_history", roles = listOf(Role.USER))

    data object UserSettingsHelp : SkyFitNavigationRoute("user/settings/help", roles = listOf(Role.USER))

    //endregion User Screens

    //region Trainer Screens
    // - Appointments
    data object TrainerAppointments : SkyFitNavigationRoute("trainer/appointments")
    data object TrainerAppointmentDetail : SkyFitNavigationRoute(
        "trainer/appointments/detail/{bookingPath}",
        roles = listOf(Role.TRAINER),
        Param.BOOKING_PATH
    )

    // - Profile
    data object TrainerProfile : SkyFitNavigationRoute("profile/trainer/owner", roles = listOf(Role.TRAINER))
    data object TrainerPhotoDiary :
        SkyFitNavigationRoute("profile/trainer/owner/photo_diary", roles = listOf(Role.TRAINER))

    data object TrainerProfileVisited :
        SkyFitNavigationRoute("profile/trainer/visited/{trainerId}", roles = listOf(Role.TRAINER), Param.TRAINER_ID)

    data object TrainerCalendarVisited : SkyFitNavigationRoute(
        "profile/trainer/calendar/visited/{trainerId}",
        roles = listOf(Role.USER),
        Param.TRAINER_ID
    )

    // - Settings
    data object TrainerSettings : SkyFitNavigationRoute("trainer/settings", roles = listOf(Role.TRAINER))
    data object TrainerSettingsAccount : SkyFitNavigationRoute("trainer/settings/account", roles = listOf(Role.TRAINER))
    data object TrainerSettingsNotifications :
        SkyFitNavigationRoute("trainer/settings/notifications", roles = listOf(Role.TRAINER))

    data object TrainerSettingsPaymentHistory :
        SkyFitNavigationRoute("trainer/settings/payment_history", roles = listOf(Role.TRAINER))

    data object TrainerSettingsHelp : SkyFitNavigationRoute("trainer/settings/help", roles = listOf(Role.TRAINER))
    //endregion Trainer Screens

    //region Facility Screens
    // - Profile
    data object FacilityProfile : SkyFitNavigationRoute("profile/facility/owner", roles = listOf(Role.FACILITY_MANAGER))
    data object FacilityPhotoDiary :
        SkyFitNavigationRoute("profile/facility/owner/photo_diary", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilityProfileVisited : SkyFitNavigationRoute(
        "profile/facility/owner/visited/{facilityId}",
        roles = listOf(Role.USER),
        Param.FACILITY_ID
    )

    // - Calendar
    data object FacilityCalendar :
        SkyFitNavigationRoute("facility/activity_calendar/owner", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilityCalendarVisited : SkyFitNavigationRoute(
        "facility/activity_calendar/visitor/{facilityId}",
        roles = listOf(Role.USER),
        Param.FACILITY_ID
    )

    // - Appointments
    data object FacilityAppointmentDetail : SkyFitNavigationRoute(
        "facility_appointments/detail/{bookingPath}",
        roles = listOf(Role.FACILITY_MANAGER),
        Param.BOOKING_PATH
    )

    // - Classes
    data object FacilityClasses : SkyFitNavigationRoute("facility_classes/owner", roles = listOf(Role.FACILITY_MANAGER))
    data object FacilityClassesVisited : SkyFitNavigationRoute("facility_classes/visitor", roles = listOf(Role.USER))
    data object FacilityClassAdd :
        SkyFitNavigationRoute("facility_class/add/{classId}", roles = listOf(Role.FACILITY_MANAGER), Param.CLASS_ID)

    data object FacilityClassEdit :
        SkyFitNavigationRoute("facility_class/edit/{classId}", roles = listOf(Role.FACILITY_MANAGER), Param.CLASS_ID)

    data object FacilityClassEditCompleted :
        SkyFitNavigationRoute("facility_class/edit/completed/{classId}", roles = listOf(Role.FACILITY_MANAGER), Param.CLASS_ID)

    data object FacilityClassDetail : SkyFitNavigationRoute(
        "facility_class/detail/owner/{classId}",
        roles = listOf(Role.FACILITY_MANAGER),
        Param.CLASS_ID
    )

    data object FacilityClassDetailVisited :
        SkyFitNavigationRoute("facility_class/detail/visited/{classId}", roles = listOf(Role.USER), Param.CLASS_ID)

    // - Settings
    data object FacilitySettings : SkyFitNavigationRoute("facility/settings", roles = listOf(Role.FACILITY_MANAGER))
    data object FacilitySettingsMembers :
        SkyFitNavigationRoute("facility/settings/members", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilitySettingsTrainers :
        SkyFitNavigationRoute("facility/settings/trainers", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilitySettingsPaymentHistory :
        SkyFitNavigationRoute("facility/settings/payment_history", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilitySettingsNotifications :
        SkyFitNavigationRoute("facility/settings/notifications", roles = listOf(Role.FACILITY_MANAGER))

    data object FacilitySettingsHelp :
        SkyFitNavigationRoute("facility/settings/help", roles = listOf(Role.FACILITY_MANAGER))

    //endregion Facility Screens
}