package com.vurgun.skyfit.navigation

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileForgotPasswordCodeScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileForgotPasswordResetScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileForgotPasswordScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileLoginScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileOTPVerificationScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileRegisterScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileSplashScreen
import com.vurgun.skyfit.feature_dashboard.ui.MobileDashboardScreen
import com.vurgun.skyfit.feature_explore.ui.MobileExploreBlogArticleDetailScreen
import com.vurgun.skyfit.feature_explore.ui.MobileExploreBlogScreen
import com.vurgun.skyfit.feature_explore.ui.MobileExploreChallengeDetailScreen
import com.vurgun.skyfit.feature_explore.ui.MobileExploreChallengesScreen
import com.vurgun.skyfit.feature_explore.ui.MobileExploreCommunitiesScreen
import com.vurgun.skyfit.feature_explore.ui.MobileExploreCommunityDetailScreen
import com.vurgun.skyfit.feature_explore.ui.MobileExploreExercisesScreen
import com.vurgun.skyfit.feature_explore.ui.MobileExploreFacilitiesScreen
import com.vurgun.skyfit.feature_explore.ui.MobileExploreTrainersScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityCalendarScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityCalendarVisitedScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityClassDetailVisitedScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityClassEditCompletedScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityClassEditScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityClassesScreen
import com.vurgun.skyfit.feature_profile.ui.facility.MobileFacilityPhotoDiaryScreen
import com.vurgun.skyfit.feature_profile.ui.facility.MobileFacilityProfileScreen
import com.vurgun.skyfit.feature_profile.ui.facility.MobileFacilityProfileVisitedScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsAccountScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsAddMembersScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsHelpScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsNotificationsScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsSearchMembersScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsTrainersScreen
import com.vurgun.skyfit.feature_onboarding.ui.MobileOnboardingScreen
import com.vurgun.skyfit.feature_appointments.ui.trainer.MobileTrainerAppointmentDetailScreen
import com.vurgun.skyfit.feature_appointments.ui.trainer.MobileTrainerAppointmentsScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileTrainerCalendarVisitedScreen
import com.vurgun.skyfit.feature_profile.ui.trainer.MobileTrainerProfileScreen
import com.vurgun.skyfit.feature_profile.ui.trainer.MobileTrainerProfileVisitedScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsAccountScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsHelpScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsNotificationsScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsScreen
import com.vurgun.skyfit.feature_appointments.ui.MobileUserAppointmentDetailScreen
import com.vurgun.skyfit.feature_appointments.ui.MobileUserAppointmentsScreen
import com.vurgun.skyfit.feature_calendar.ui.MobileUserActivityCalendarAddActivityScreen
import com.vurgun.skyfit.feature_calendar.ui.MobileUserActivityCalendarAddedScreen
import com.vurgun.skyfit.feature_calendar.ui.MobileUserActivityCalendarPaymentRequiredScreen
import com.vurgun.skyfit.feature_calendar.ui.MobileUserActivityCalendarScreen
import com.vurgun.skyfit.feature_calendar.ui.MobileUserActivityCalendarSearchScreen
import com.vurgun.skyfit.feature_exercises.ui.MobileUserExerciseDetailScreen
import com.vurgun.skyfit.feature_exercises.ui.MobileUserExerciseInActionCompletedScreen
import com.vurgun.skyfit.feature_exercises.ui.MobileUserExerciseInActionScreen
import com.vurgun.skyfit.feature_chatbot.ui.MobileUserChatBotScreen
import com.vurgun.skyfit.feature_messaging.ui.MobileUserConversationsScreen
import com.vurgun.skyfit.feature_chatbot.ui.MobileUserToBotChatScreen
import com.vurgun.skyfit.feature_messaging.ui.MobileUserToFacilityChatScreen
import com.vurgun.skyfit.feature_messaging.ui.MobileUserToGroupChatScreen
import com.vurgun.skyfit.feature_messaging.ui.MobileUserToTrainerChatScreen
import com.vurgun.skyfit.feature_messaging.ui.MobileUserToUserChatScreen
import com.vurgun.skyfit.feature_notifications.ui.MobileUserNotificationsScreen
import com.vurgun.skyfit.feature_nutrition.ui.MobileUserMealDetailAddPhotoScreen
import com.vurgun.skyfit.feature_nutrition.ui.MobileUserMealDetailAddScreen
import com.vurgun.skyfit.feature_nutrition.ui.MobileUserMealDetailScreen
import com.vurgun.skyfit.feature_payment.ui.MobileUserPaymentProcessScreen
import com.vurgun.skyfit.feature_body_analysis.ui.MobileUserBodyAnalysisScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserMeasurementsScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserPhotoDiaryScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserProfileScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserProfileVisitedScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserTrophiesScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsAccountScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsChangePasswordScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsHelpScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsNotificationsScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsScreen
import com.vurgun.skyfit.feature_social.ui.MobileUserSocialMediaNewPostScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun MobileNavigationGraph() {
    val rootNavigator: Navigator = rememberNavigator()

    NavHost(
        navigator = rootNavigator,
        initialRoute = NavigationRoute.Splash.route
    ) {

        splashNavGraph(rootNavigator)
        authNavGraph(rootNavigator)

        scene(NavigationRoute.Onboarding.route) { MobileOnboardingScreen(rootNavigator) }
        scene(NavigationRoute.Dashboard.route) { MobileDashboardScreen(rootNavigator) }
        scene(NavigationRoute.DashboardProfile.route) { MobileDashboardScreen(rootNavigator, NavigationRoute.DashboardProfile) }
        scene(NavigationRoute.DashboardExplore.route) { MobileDashboardScreen(rootNavigator, NavigationRoute.DashboardExplore) }
        scene(NavigationRoute.DashboardNutrition.route) {
            MobileDashboardScreen(rootNavigator, NavigationRoute.DashboardNutrition)
        }

        //Explore
        scene(NavigationRoute.DashboardExploreTrainers.route) { MobileExploreTrainersScreen(rootNavigator) }
        scene(NavigationRoute.DashboardExploreExercises.route) { MobileExploreExercisesScreen(rootNavigator) }
        scene(NavigationRoute.DashboardExploreFacilities.route) { MobileExploreFacilitiesScreen(rootNavigator) }
        scene(NavigationRoute.DashboardExploreBlogs.route) { MobileExploreBlogScreen(rootNavigator) }
        scene(NavigationRoute.DashboardExploreBlogArticleDetail.route) { MobileExploreBlogArticleDetailScreen(rootNavigator) }
        scene(NavigationRoute.DashboardExploreChallenges.route) { MobileExploreChallengesScreen(rootNavigator) }
        scene(NavigationRoute.DashboardExploreChallengeDetail.route) { MobileExploreChallengeDetailScreen(rootNavigator) }
        scene(NavigationRoute.DashboardExploreCommunities.route) { MobileExploreCommunitiesScreen(rootNavigator) }
        scene(NavigationRoute.DashboardExploreCommunityDetail.route) { MobileExploreCommunityDetailScreen(rootNavigator) }


        //region User
        //User - Exercises
        scene(NavigationRoute.UserExerciseDetail.route) { MobileUserExerciseDetailScreen(rootNavigator) }
        scene(NavigationRoute.UserExerciseInAction.route) { MobileUserExerciseInActionScreen(rootNavigator) }
        scene(NavigationRoute.UserExerciseInActionComplete.route) { MobileUserExerciseInActionCompletedScreen(rootNavigator) }

        //User - Nutrition
        scene(NavigationRoute.UserMealDetail.route) { MobileUserMealDetailScreen(rootNavigator) }
        scene(NavigationRoute.UserMealDetailAdd.route) { MobileUserMealDetailAddScreen(rootNavigator) }
        scene(NavigationRoute.UserMealDetailAddPhoto.route) { MobileUserMealDetailAddPhotoScreen(rootNavigator) }

        //User - Calendar
        scene(NavigationRoute.UserActivityCalendar.route) { MobileUserActivityCalendarScreen(rootNavigator) }
        scene(NavigationRoute.UserActivityCalendarAdd.route) { MobileUserActivityCalendarAddActivityScreen(rootNavigator) }
        scene(NavigationRoute.UserActivityCalendarSearch.route) { MobileUserActivityCalendarSearchScreen(rootNavigator) }
        scene(NavigationRoute.UserPaymentProcess.route) { MobileUserPaymentProcessScreen(rootNavigator) }
        scene(NavigationRoute.UserActivityCalendarPaymentRequired.route) {
            MobileUserActivityCalendarPaymentRequiredScreen(
                rootNavigator
            )
        }
        scene(NavigationRoute.UserActivityCalendarConfirmed.route) { MobileUserActivityCalendarAddedScreen(rootNavigator) }
        //User - Appointments
        scene(NavigationRoute.UserAppointments.route) { MobileUserAppointmentsScreen(rootNavigator) }
        scene(NavigationRoute.UserAppointmentDetail.route) { MobileUserAppointmentDetailScreen(rootNavigator) }
        //User - Social
        scene(NavigationRoute.UserSocialMedia.route) { MobileDashboardScreen(rootNavigator, NavigationRoute.DashboardSocial) }
        scene(NavigationRoute.UserSocialMediaPostAdd.route) { MobileUserSocialMediaNewPostScreen(rootNavigator) }
        //User - Notifications
        scene(NavigationRoute.UserNotifications.route) { MobileUserNotificationsScreen(rootNavigator) }
        //User - Messages
        scene(NavigationRoute.UserChatBot.route) { MobileUserChatBotScreen(rootNavigator) }
        scene(NavigationRoute.UserToBotChat.route) { MobileUserToBotChatScreen(rootNavigator) }
        scene(NavigationRoute.UserConversations.route) { MobileUserConversationsScreen(rootNavigator) }
        scene(NavigationRoute.UserToUserChat.route) { MobileUserToUserChatScreen(rootNavigator) }
        scene(NavigationRoute.UserToTrainerChat.route) { MobileUserToTrainerChatScreen(rootNavigator) }
        scene(NavigationRoute.UserToGroupChat.route) { MobileUserToGroupChatScreen(rootNavigator) }
        scene(NavigationRoute.UserToFacilityChat.route) { MobileUserToFacilityChatScreen(rootNavigator) }
        //User - Profile
        scene(NavigationRoute.UserProfile.route) { MobileUserProfileScreen(rootNavigator) }
        scene(NavigationRoute.UserProfileVisited.route) { MobileUserProfileVisitedScreen(rootNavigator) }
        scene(NavigationRoute.UserPhotoDiary.route) { MobileUserPhotoDiaryScreen(rootNavigator) }
        scene(NavigationRoute.UserMeasurements.route) { MobileUserMeasurementsScreen(rootNavigator) }
        scene(NavigationRoute.UserBodyAnalysis.route) { MobileUserBodyAnalysisScreen(rootNavigator) }
        scene(NavigationRoute.UserTrophies.route) { MobileUserTrophiesScreen(rootNavigator) }
        //User - Settings
        scene(NavigationRoute.UserSettings.route) { MobileUserSettingsScreen(rootNavigator) }
        scene(NavigationRoute.UserSettingsAccount.route) { MobileUserSettingsAccountScreen(rootNavigator) }
        scene(NavigationRoute.UserSettingsNotifications.route) { MobileUserSettingsNotificationsScreen(rootNavigator) }
        scene(NavigationRoute.UserSettingsPaymentHistory.route) { MobileUserSettingsPaymentHistoryScreen(rootNavigator) }
        scene(NavigationRoute.UserSettingsChangePassword.route) { MobileUserSettingsChangePasswordScreen(rootNavigator) }
        scene(NavigationRoute.UserSettingsHelp.route) { MobileUserSettingsHelpScreen(rootNavigator) }
        //endregion User

        //region Trainer
        //Trainer - Appointments
        scene(NavigationRoute.TrainerAppointments.route) { MobileTrainerAppointmentsScreen(rootNavigator) }
        scene(NavigationRoute.TrainerAppointmentDetail.route) { MobileTrainerAppointmentDetailScreen(rootNavigator) }
        //Trainer - Profile
        scene(NavigationRoute.TrainerProfile.route) { MobileTrainerProfileScreen(rootNavigator) }
        scene(NavigationRoute.TrainerProfileVisited.route) { MobileTrainerProfileVisitedScreen(rootNavigator) }
        //Trainer - Calendar
        scene(NavigationRoute.TrainerCalendarVisited.route) { MobileTrainerCalendarVisitedScreen(rootNavigator) }
        //Trainer - Settings
        scene(NavigationRoute.TrainerSettings.route) { MobileTrainerSettingsScreen(rootNavigator) }
        scene(NavigationRoute.TrainerSettingsAccount.route) { MobileTrainerSettingsAccountScreen(rootNavigator) }
        scene(NavigationRoute.TrainerSettingsNotifications.route) { MobileTrainerSettingsNotificationsScreen(rootNavigator) }
        scene(NavigationRoute.TrainerSettingsPaymentHistory.route) { MobileTrainerSettingsPaymentHistoryScreen(rootNavigator) }
        scene(NavigationRoute.TrainerSettingsHelp.route) { MobileTrainerSettingsHelpScreen(rootNavigator) }
        //endregion Trainer

        //region Facility
        //Facility - Profile
        scene(NavigationRoute.FacilityProfile.route) { MobileFacilityProfileScreen(rootNavigator) }
        scene(NavigationRoute.FacilityProfileVisited.route) { MobileFacilityProfileVisitedScreen(rootNavigator) }
        scene(NavigationRoute.FacilityPhotoDiary.route) { MobileFacilityPhotoDiaryScreen(rootNavigator) }
        //Facility - Calendar
        scene(NavigationRoute.FacilityCalendar.route) { MobileFacilityCalendarScreen(rootNavigator) }
        scene(NavigationRoute.FacilityCalendarVisited.route) { MobileFacilityCalendarVisitedScreen(rootNavigator) }
        //Facility - Classes
        scene(NavigationRoute.FacilityClasses.route) { MobileFacilityClassesScreen(rootNavigator) }
        scene(NavigationRoute.FacilityClassEdit.route) { MobileFacilityClassEditScreen(rootNavigator) }
        scene(NavigationRoute.FacilityClassEditCompleted.route) { MobileFacilityClassEditCompletedScreen(rootNavigator) }
        scene(NavigationRoute.FacilityClassDetailVisited.route) { MobileFacilityClassDetailVisitedScreen(rootNavigator) }
        //Facility - Settings
        scene(NavigationRoute.FacilitySettings.route) { MobileFacilitySettingsScreen(rootNavigator) }
        scene(NavigationRoute.FacilitySettingsAccount.route) { MobileFacilitySettingsAccountScreen(rootNavigator) }
        scene(NavigationRoute.FacilitySettingsSearchMembers.route) { MobileFacilitySettingsSearchMembersScreen(rootNavigator) }
        scene(NavigationRoute.FacilitySettingsAddMembers.route) { MobileFacilitySettingsAddMembersScreen(rootNavigator) }
        scene(NavigationRoute.FacilitySettingsTrainers.route) { MobileFacilitySettingsTrainersScreen(rootNavigator) }
        scene(NavigationRoute.FacilitySettingsPaymentHistory.route) { MobileFacilitySettingsPaymentHistoryScreen(rootNavigator) }
        scene(NavigationRoute.FacilitySettingsNotifications.route) { MobileFacilitySettingsNotificationsScreen(rootNavigator) }
        scene(NavigationRoute.FacilitySettingsHelp.route) { MobileFacilitySettingsHelpScreen(rootNavigator) }
        //endregion Facility
    }
}


fun RouteBuilder.splashNavGraph(navigator: Navigator) {
    scene(NavigationRoute.Splash.route) {
        MobileSplashScreen(navigator)
    }
}

fun RouteBuilder.authNavGraph(navigator: Navigator) {
    scene(NavigationRoute.Login.route) { MobileLoginScreen(navigator) }
    scene(NavigationRoute.Register.route) { MobileRegisterScreen(navigator) }
    scene(NavigationRoute.OTPVerification.route) { MobileOTPVerificationScreen(navigator) }
    scene(NavigationRoute.ForgotPassword.route) { MobileForgotPasswordScreen(navigator) }
    scene(NavigationRoute.ForgotPasswordCode.route) { MobileForgotPasswordCodeScreen(navigator) }
    scene(NavigationRoute.ForgotPasswordReset.route) { MobileForgotPasswordResetScreen(navigator) }
}