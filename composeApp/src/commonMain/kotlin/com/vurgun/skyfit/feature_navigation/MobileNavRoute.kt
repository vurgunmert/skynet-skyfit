package com.vurgun.skyfit.feature_navigation

sealed class MobileNavRoute(
    private val baseRoute: String,
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
    data object Maintenance : MobileNavRoute("maintenance")
    data object Splash : MobileNavRoute("splash" )
    data object Login : MobileNavRoute("login" )
    data object LoginVerifyOTP : MobileNavRoute("login_otp_verification" )
    data object CreatePassword : MobileNavRoute("create_password" )
    data object ForgotPassword : MobileNavRoute("forgot_password" )
    data object ForgotPasswordVerifyOTP : MobileNavRoute("forgot_password_code" )
    data object ForgotPasswordReset : MobileNavRoute("forgot_password_reset" )
    data object PrivacyPolicy : MobileNavRoute("privacy_policy" )
    data object TermsAndConditions : MobileNavRoute("terms_and_conditions" )

    //Onboarding
    data object Onboarding : MobileNavRoute("onboarding")

    //Dashboard
    data object Dashboard : MobileNavRoute("dashboard")
    data object DashboardHome : MobileNavRoute("dashboard/home")
    data object DashboardExplore : MobileNavRoute("dashboard/explore")
    data object DashboardSocial : MobileNavRoute("dashboard/social")
    data object DashboardNutrition : MobileNavRoute("dashboard/nutrition")
    data object DashboardProfile : MobileNavRoute("dashboard/profile")

    // Explore
    data object ExploreTrainers : MobileNavRoute("dashboard/explore/trainers")
    data object ExploreExercises : MobileNavRoute("dashboard/explore/exercises")
    data object ExploreFacilities : MobileNavRoute("dashboard/explore/facilities")
    data object ExploreBlogs : MobileNavRoute("dashboard/explore/blogs")
    data object ExploreBlogArticleDetail : MobileNavRoute("dashboard/explore/blogs/article/{articleId}")
    data object ExploreChallenges : MobileNavRoute("dashboard/explore/challenges")
    data object ExploreChallengeDetail : MobileNavRoute("dashboard/explore/challenges/detail/{challengeId}")
    data object ExploreCommunities : MobileNavRoute("dashboard/explore/communities")
    data object ExploreCommunityDetail : MobileNavRoute("dashboard/explore/communities/detail/{communityId}")


    //region User Screens
    data object UserSocialMedia : MobileNavRoute("user/social/feeds")
    data object UserSocialMediaPostAdd : MobileNavRoute("user/social/new_post")
    data object UserTrophies : MobileNavRoute("user/trophies")
    data object UserPaymentProcess : MobileNavRoute("user/payment/process")

    // - Exercises
    data object ExerciseDetail :
        MobileNavRoute("user/exercises/detail/{exerciseId}" , Param.EXERCISE_ID)
    data object ExerciseInProgress :
        MobileNavRoute("user/exercises/in_action/{exerciseId}" , Param.EXERCISE_ID)
    data object ExerciseCompleted :
        MobileNavRoute("user/exercises/in_action/completed/{exerciseId}" , Param.EXERCISE_ID)

    // - Calendar
    data object UserActivityCalendar : MobileNavRoute("user_activity_calendar")
    data object UserActivityCalendarAdd : MobileNavRoute("user_activity_calendar/add")
    data object UserActivityCalendarSearch : MobileNavRoute("user_activity_calendar/search")
    data object UserActivityCalendarPaymentRequired : MobileNavRoute("user_activity_calendar/payment/required")
    data object UserActivityCalendarConfirmed : MobileNavRoute("user_activity_calendar/confirmed")

    // - Appointments
    data object UserAppointments : MobileNavRoute("user_appointments")
    data object UserAppointmentDetail :
        MobileNavRoute("user_appointments/detail/{bookingPath}" , Param.BOOKING_PATH)

    // - Nutrition
    data object UserMealDetail : MobileNavRoute("user/nutrition/meals/detail" )
    data object UserMealDetailAdd : MobileNavRoute("user/nutrition/meals/detail/add" )
    data object UserMealDetailAddPhoto : MobileNavRoute("user/nutrition/meals/detail/add_photo" )

    // - Notifications
    data object UserNotifications : MobileNavRoute("notifications" )

    // - Messages
    data object UserChatBot : MobileNavRoute("user/chat_bot" )
    data object UserToBotChat : MobileNavRoute("user/conversations/toBot" )
    data object UserConversations : MobileNavRoute("user/conversations" )
    data object UserToUserChat : MobileNavRoute("user/conversations/toUser" )
    data object UserToTrainerChat : MobileNavRoute("user/conversations/toTrainer" )
    data object UserToGroupChat : MobileNavRoute("user/conversations/toGroup" )
    data object UserToFacilityChat : MobileNavRoute("user/conversations/toFacility" )

    // - Profile
    data object UserProfile : MobileNavRoute("profile/user/owner" )
    data object UserProfileVisited : MobileNavRoute("profile/user/visited" )
    data object UserPhotoDiary : MobileNavRoute("profile/user/owner/photo_diary" )
    data object UserMeasurements : MobileNavRoute("profile/user/owner/measurements" )
    data object UserBodyAnalysis : MobileNavRoute("profile/user/owner/body_analysis" )

    // - Settings
    object Settings {
        object User {
            data object Home : MobileNavRoute("user/settings/home")
            data object Account : MobileNavRoute("user/settings/account")
            data object EditProfile : MobileNavRoute("user/settings/account/edit_profile")
            data object ChangePassword : MobileNavRoute("user/settings/account/change_password")
            data object PaymentHistory : MobileNavRoute("user/settings/payments/history")
            data object Notifications : MobileNavRoute("user/settings/notifications")
            data object Help : MobileNavRoute("user/settings/help")
        }

        object Trainer {
            data object Home : MobileNavRoute("trainer/settings/home")
            data object Account : MobileNavRoute("trainer/settings/account")
            data object EditProfile : MobileNavRoute("trainer/settings/account/edit_profile")
            data object ChangePassword : MobileNavRoute("trainer/settings/account/change_password")
            data object PaymentHistory : MobileNavRoute("trainer/settings/payments/history")
            data object Notifications : MobileNavRoute("trainer/settings/notifications")
            data object Help : MobileNavRoute("trainer/settings/help")

            // Management
            data object ManageAccounts : MobileNavRoute("trainer/settings/manage/accounts")
            data object ManageMembers : MobileNavRoute("trainer/settings/manage/members")
        }

        object Facility {
            data object Home : MobileNavRoute("facility/settings/home")
            data object Account : MobileNavRoute("facility/settings/account")
            data object EditProfile : MobileNavRoute("facility/settings/account/edit_profile")
            data object ChangePassword : MobileNavRoute("facility/settings/account/change_password")
            data object PaymentHistory : MobileNavRoute("facility/settings/payments/history")
            data object Notifications : MobileNavRoute("facility/settings/notifications")
            data object Help : MobileNavRoute("facility/settings/help")

            // Management
            data object ManageAccounts : MobileNavRoute("facility/settings/manage/accounts")
            data object ManageMembers : MobileNavRoute("facility/settings/manage/members")
            data object AddMembers : MobileNavRoute("facility/settings/manage/members/add")
            data object ManageTrainers : MobileNavRoute("facility/settings/manage/trainers")
            data object ManageBranches : MobileNavRoute("facility/settings/manage/branches")
        }
    }

    //region Trainer Screens
    // - Appointments
    data object TrainerAppointments : MobileNavRoute("trainer/appointments")
    data object TrainerAppointmentDetail : MobileNavRoute("trainer/appointments/detail/{bookingPath}",        Param.BOOKING_PATH)

    // - Profile
    data object TrainerProfile : MobileNavRoute("profile/trainer/owner")
    data object TrainerPhotoDiary :
        MobileNavRoute("profile/trainer/owner/photo_diary")

    data object TrainerProfileVisited :
        MobileNavRoute("profile/trainer/visited/{trainerId}", Param.TRAINER_ID)

    data object TrainerCalendarVisited : MobileNavRoute("profile/trainer/calendar/visited/{trainerId}", Param.TRAINER_ID)
    //endregion Trainer Screens

    //region Facility Screens
    // - Profile
    data object FacilityProfile : MobileNavRoute("profile/facility/owner")
    data object FacilityPhotoGallery :
        MobileNavRoute("profile/facility/owner/photo_diary")

    data object FacilityProfileVisited : MobileNavRoute("profile/facility/owner/visited/{facilityId}", Param.FACILITY_ID)

    // - Calendar
    data object FacilityCalendar :MobileNavRoute("facility/activity_calendar/owner")

    data object FacilityCalendarVisited : MobileNavRoute("facility/activity_calendar/Guest/{facilityId}",        Param.FACILITY_ID)

    // - Appointments
    data object FacilityAppointmentDetail : MobileNavRoute("facility_appointments/detail/{bookingPath}",        Param.BOOKING_PATH)

    // - Classes
    data object FacilityLessons : MobileNavRoute("facility_classes/owner")
    data object FacilityClassesVisited : MobileNavRoute("facility_classes/Guest")

    data object FacilityLessonEdit : MobileNavRoute("facility_class/edit/{classId}", Param.CLASS_ID)

    data object FacilityLessonCreated : MobileNavRoute("facility_class/edit/completed/{classId}", Param.CLASS_ID)

    data object FacilityClassDetail : MobileNavRoute("facility_class/detail/owner/{classId}", Param.CLASS_ID)
    // - Settings
    data object FacilitySettings : MobileNavRoute("facility/settings")
    data object FacilitySettingsAccount : MobileNavRoute("facility/settings/account")
    data object FacilitySettingsSearchMembers :
        MobileNavRoute("facility/settings/members/search")
    data object FacilitySettingsAddMembers :
        MobileNavRoute("facility/settings/members/add")

    data object FacilitySettingsTrainers :
        MobileNavRoute("facility/settings/trainers")

    data object FacilitySettingsPaymentHistory :
        MobileNavRoute("facility/settings/payment_history")

    data object FacilitySettingsNotifications :
        MobileNavRoute("facility/settings/notifications")

    data object FacilitySettingsHelp :
        MobileNavRoute("facility/settings/help")

    //endregion Facility Screens
}