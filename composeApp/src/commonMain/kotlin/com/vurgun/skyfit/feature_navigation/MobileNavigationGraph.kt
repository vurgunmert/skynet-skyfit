package com.vurgun.skyfit.feature_navigation

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.feature_appointments.ui.MobileUserAppointmentDetailScreen
import com.vurgun.skyfit.feature_appointments.ui.MobileUserAppointmentsScreen
import com.vurgun.skyfit.feature_appointments.ui.trainer.MobileTrainerAppointmentDetailScreen
import com.vurgun.skyfit.feature_appointments.ui.trainer.MobileTrainerAppointmentsScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileCreatePasswordScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileForgotPasswordResetScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileForgotPasswordScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileForgotPasswordVerifyOTPScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileLoginScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileLoginVerifyOTPScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobilePrivacyPolicyScreen
import com.vurgun.skyfit.feature_auth.ui.mobile.MobileTermsAndConditionsScreen
import com.vurgun.skyfit.feature_body_analysis.ui.MobileUserBodyAnalysisScreen
import com.vurgun.skyfit.feature_calendar.ui.MobileUserActivityCalendarAddActivityScreen
import com.vurgun.skyfit.feature_calendar.ui.MobileUserActivityCalendarAddedScreen
import com.vurgun.skyfit.feature_calendar.ui.MobileUserActivityCalendarPaymentRequiredScreen
import com.vurgun.skyfit.feature_calendar.ui.MobileUserActivityCalendarScreen
import com.vurgun.skyfit.feature_calendar.ui.MobileUserActivityCalendarSearchScreen
import com.vurgun.skyfit.feature_chatbot.ui.MobileUserChatBotScreen
import com.vurgun.skyfit.feature_chatbot.ui.MobileUserToBotChatScreen
import com.vurgun.skyfit.feature_dashboard.ui.MobileDashboardScreen
import com.vurgun.skyfit.feature_exercises.ui.MobileUserExerciseDetailScreen
import com.vurgun.skyfit.feature_exercises.ui.MobileUserExerciseInActionCompletedScreen
import com.vurgun.skyfit.feature_exercises.ui.MobileUserExerciseInActionScreen
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
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityEditLessonFeedbackScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityClassEditScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityLessonsScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileTrainerCalendarVisitedScreen
import com.vurgun.skyfit.feature_messaging.ui.MobileUserConversationsScreen
import com.vurgun.skyfit.feature_messaging.ui.MobileUserToFacilityChatScreen
import com.vurgun.skyfit.feature_messaging.ui.MobileUserToGroupChatScreen
import com.vurgun.skyfit.feature_messaging.ui.MobileUserToTrainerChatScreen
import com.vurgun.skyfit.feature_messaging.ui.MobileUserToUserChatScreen
import com.vurgun.skyfit.feature_notifications.ui.MobileUserNotificationsScreen
import com.vurgun.skyfit.feature_nutrition.ui.MobileUserMealDetailAddPhotoScreen
import com.vurgun.skyfit.feature_nutrition.ui.MobileUserMealDetailAddScreen
import com.vurgun.skyfit.feature_nutrition.ui.MobileUserMealDetailScreen
import com.vurgun.skyfit.feature_onboarding.ui.MobileOnboardingFlowNavGraph
import com.vurgun.skyfit.feature_payment.ui.MobileUserPaymentProcessScreen
import com.vurgun.skyfit.feature_profile.domain.model.ProfileViewMode
import com.vurgun.skyfit.feature_profile.ui.facility.MobileFacilityPhotoDiaryScreen
import com.vurgun.skyfit.feature_profile.ui.facility.MobileFacilityProfileScreen
import com.vurgun.skyfit.feature_profile.ui.trainer.MobileTrainerProfileScreen
import com.vurgun.skyfit.feature_profile.ui.trainer.MobileTrainerProfileVisitedScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserMeasurementsScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserPhotoDiaryScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserProfileScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserProfileVisitedScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserTrophiesScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsAccountScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsAddMembersScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsHelpScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsNotificationsScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsSearchMembersScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsTrainersScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsAccountScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsHelpScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsNotificationsScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsAccountScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsChangePasswordScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsHelpScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsNotificationsScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsScreen
import com.vurgun.skyfit.feature_social.ui.MobileUserSocialMediaNewPostScreen
import com.vurgun.skyfit.feature_splash.presentation.view.SplashScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun MobileNavigationGraph() {
    val navigator: Navigator = rememberNavigator()

    NavHost(
        navigator = navigator,
        initialRoute = NavigationRoute.UserAppointments.route
    ) {
        mobileAuthNavGraph(navigator)
        mobileOnboardingNavGraph(navigator)
        dashboardNavGraph(navigator)
        exploreNavGraph(navigator)
        exerciseNavGraph(navigator)
        nutritionNavGraph(navigator)
        userNavGraph(navigator)
        trainerNavGraph(navigator)
        facilityNavGraph(navigator)
    }
}

private fun RouteBuilder.mobileAuthNavGraph(navigator: Navigator) {
    scene(NavigationRoute.Splash.route) { SplashScreen(navigator) }
    scene(NavigationRoute.Login.route) { MobileLoginScreen(navigator) }
    scene(NavigationRoute.LoginVerifyOTP.route) { MobileLoginVerifyOTPScreen(navigator) }
    scene(NavigationRoute.CreatePassword.route) { MobileCreatePasswordScreen(navigator) }
    scene(NavigationRoute.ForgotPassword.route) { MobileForgotPasswordScreen(navigator) }
    scene(NavigationRoute.ForgotPasswordVerifyOTP.route) { MobileForgotPasswordVerifyOTPScreen(navigator) }
    scene(NavigationRoute.ForgotPasswordReset.route) { MobileForgotPasswordResetScreen(navigator) }
    scene(NavigationRoute.PrivacyPolicy.route) { MobilePrivacyPolicyScreen(navigator) }
    scene(NavigationRoute.TermsAndConditions.route) { MobileTermsAndConditionsScreen(navigator) }
}

private fun RouteBuilder.mobileOnboardingNavGraph(navigator: Navigator) {
    scene(NavigationRoute.Onboarding.route) { MobileOnboardingFlowNavGraph(navigator) }
}

private fun RouteBuilder.dashboardNavGraph(navigator: Navigator) {
    scene(NavigationRoute.Dashboard.route) { MobileDashboardScreen(navigator) }
    scene(NavigationRoute.DashboardHome.route) { MobileDashboardScreen(navigator, NavigationRoute.DashboardHome) }
    scene(NavigationRoute.DashboardExplore.route) { MobileDashboardScreen(navigator, NavigationRoute.DashboardExplore) }
    scene(NavigationRoute.DashboardSocial.route) { MobileDashboardScreen(navigator, NavigationRoute.DashboardSocial) }
    scene(NavigationRoute.DashboardNutrition.route) { MobileDashboardScreen(navigator, NavigationRoute.DashboardNutrition) }
    scene(NavigationRoute.DashboardProfile.route) { MobileDashboardScreen(navigator, NavigationRoute.DashboardProfile) }
}

private fun RouteBuilder.exploreNavGraph(navigator: Navigator) {
    scene(NavigationRoute.ExploreTrainers.route) { MobileExploreTrainersScreen(navigator) }
    scene(NavigationRoute.ExploreExercises.route) { MobileExploreExercisesScreen(navigator) }
    scene(NavigationRoute.ExploreFacilities.route) { MobileExploreFacilitiesScreen(navigator) }
    scene(NavigationRoute.ExploreBlogs.route) { MobileExploreBlogScreen(navigator) }
    scene(NavigationRoute.ExploreBlogArticleDetail.route) { MobileExploreBlogArticleDetailScreen(navigator) }
    scene(NavigationRoute.ExploreChallenges.route) { MobileExploreChallengesScreen(navigator) }
    scene(NavigationRoute.ExploreChallengeDetail.route) { MobileExploreChallengeDetailScreen(navigator) }
    scene(NavigationRoute.ExploreCommunities.route) { MobileExploreCommunitiesScreen(navigator) }
    scene(NavigationRoute.ExploreCommunityDetail.route) { MobileExploreCommunityDetailScreen(navigator) }
}

private fun RouteBuilder.exerciseNavGraph(navigator: Navigator) {
    scene(NavigationRoute.ExerciseDetail.route) { MobileUserExerciseDetailScreen(navigator) }
    scene(NavigationRoute.ExerciseInProgress.route) { MobileUserExerciseInActionScreen(navigator) }
    scene(NavigationRoute.ExerciseCompleted.route) { MobileUserExerciseInActionCompletedScreen(navigator) }
}

private fun RouteBuilder.nutritionNavGraph(navigator: Navigator) {
    scene(NavigationRoute.UserMealDetail.route) { MobileUserMealDetailScreen(navigator) }
    scene(NavigationRoute.UserMealDetailAdd.route) { MobileUserMealDetailAddScreen(navigator) }
    scene(NavigationRoute.UserMealDetailAddPhoto.route) { MobileUserMealDetailAddPhotoScreen(navigator) }
}

private fun RouteBuilder.userNavGraph(navigator: Navigator) {
    //User - Calendar
    scene(NavigationRoute.UserActivityCalendar.route) { MobileUserActivityCalendarScreen(navigator) }
    scene(NavigationRoute.UserActivityCalendarAdd.route) { MobileUserActivityCalendarAddActivityScreen(navigator) }
    scene(NavigationRoute.UserActivityCalendarSearch.route) { MobileUserActivityCalendarSearchScreen(navigator) }
    scene(NavigationRoute.UserPaymentProcess.route) { MobileUserPaymentProcessScreen(navigator) }
    scene(NavigationRoute.UserActivityCalendarPaymentRequired.route) {
        MobileUserActivityCalendarPaymentRequiredScreen(
            navigator
        )
    }
    scene(NavigationRoute.UserActivityCalendarConfirmed.route) { MobileUserActivityCalendarAddedScreen(navigator) }
    //User - Appointments
    scene(NavigationRoute.UserAppointments.route) { MobileUserAppointmentsScreen(navigator) }
    scene(NavigationRoute.UserAppointmentDetail.route) { MobileUserAppointmentDetailScreen(navigator) }
    //User - Social
    scene(NavigationRoute.UserSocialMedia.route) { MobileDashboardScreen(navigator, NavigationRoute.DashboardSocial) }
    scene(NavigationRoute.UserSocialMediaPostAdd.route) { MobileUserSocialMediaNewPostScreen(navigator) }
    //User - Notifications
    scene(NavigationRoute.UserNotifications.route) { MobileUserNotificationsScreen(navigator) }
    //User - Messages
    scene(NavigationRoute.UserChatBot.route) { MobileUserChatBotScreen(navigator) }
    scene(NavigationRoute.UserToBotChat.route) { MobileUserToBotChatScreen(navigator) }
    scene(NavigationRoute.UserConversations.route) { MobileUserConversationsScreen(navigator) }
    scene(NavigationRoute.UserToUserChat.route) { MobileUserToUserChatScreen(navigator) }
    scene(NavigationRoute.UserToTrainerChat.route) { MobileUserToTrainerChatScreen(navigator) }
    scene(NavigationRoute.UserToGroupChat.route) { MobileUserToGroupChatScreen(navigator) }
    scene(NavigationRoute.UserToFacilityChat.route) { MobileUserToFacilityChatScreen(navigator) }
    //User - Profile
    scene(NavigationRoute.UserProfile.route) { MobileUserProfileScreen(navigator) }
    scene(NavigationRoute.UserProfileVisited.route) { MobileUserProfileScreen(navigator) }
    scene(NavigationRoute.UserPhotoDiary.route) { MobileUserPhotoDiaryScreen(navigator) }
    scene(NavigationRoute.UserMeasurements.route) { MobileUserMeasurementsScreen(navigator) }
    scene(NavigationRoute.UserBodyAnalysis.route) { MobileUserBodyAnalysisScreen(navigator) }
    scene(NavigationRoute.UserTrophies.route) { MobileUserTrophiesScreen(navigator) }
    //User - Settings
    scene(NavigationRoute.UserSettings.route) { MobileUserSettingsScreen(navigator) }
    scene(NavigationRoute.UserSettingsAccount.route) { MobileUserSettingsAccountScreen(navigator) }
    scene(NavigationRoute.UserSettingsNotifications.route) { MobileUserSettingsNotificationsScreen(navigator) }
    scene(NavigationRoute.UserSettingsPaymentHistory.route) { MobileUserSettingsPaymentHistoryScreen(navigator) }
    scene(NavigationRoute.UserSettingsChangePassword.route) { MobileUserSettingsChangePasswordScreen(navigator) }
    scene(NavigationRoute.UserSettingsHelp.route) { MobileUserSettingsHelpScreen(navigator) }
}

private fun RouteBuilder.trainerNavGraph(navigator: Navigator) {
    //Trainer - Appointments
    scene(NavigationRoute.TrainerAppointments.route) { MobileTrainerAppointmentsScreen(navigator) }
    scene(NavigationRoute.TrainerAppointmentDetail.route) { MobileTrainerAppointmentDetailScreen(navigator) }
    //Trainer - Profile
    scene(NavigationRoute.TrainerProfile.route) { MobileTrainerProfileScreen(navigator) }
    scene(NavigationRoute.TrainerProfileVisited.route) { MobileTrainerProfileVisitedScreen(navigator) }
    //Trainer - Calendar
    scene(NavigationRoute.TrainerCalendarVisited.route) { MobileTrainerCalendarVisitedScreen(navigator) }
    //Trainer - Settings
    scene(NavigationRoute.TrainerSettings.route) { MobileTrainerSettingsScreen(navigator) }
    scene(NavigationRoute.TrainerSettingsAccount.route) { MobileTrainerSettingsAccountScreen(navigator) }
    scene(NavigationRoute.TrainerSettingsNotifications.route) { MobileTrainerSettingsNotificationsScreen(navigator) }
    scene(NavigationRoute.TrainerSettingsPaymentHistory.route) { MobileTrainerSettingsPaymentHistoryScreen(navigator) }
    scene(NavigationRoute.TrainerSettingsHelp.route) { MobileTrainerSettingsHelpScreen(navigator) }
}

private fun RouteBuilder.facilityNavGraph(navigator: Navigator) {
    //Facility - Profile
    scene(NavigationRoute.FacilityProfile.route) { MobileFacilityProfileScreen(navigator, viewMode = ProfileViewMode.OWNER) }
    scene(NavigationRoute.FacilityProfileVisited.route) { MobileFacilityProfileScreen(navigator, viewMode = ProfileViewMode.VISITOR) }
    scene(NavigationRoute.FacilityPhotoGallery.route) { MobileFacilityPhotoDiaryScreen(navigator) }
    //Facility - Calendar
    scene(NavigationRoute.FacilityCalendar.route) { MobileFacilityCalendarScreen(navigator) }
    scene(NavigationRoute.FacilityCalendarVisited.route) { MobileFacilityCalendarVisitedScreen(navigator) }
    //Facility - Classes
    scene(NavigationRoute.FacilityLessons.route) { MobileFacilityLessonsScreen(navigator) }
    scene(NavigationRoute.FacilityLessonEdit.route) { MobileFacilityClassEditScreen(navigator) }
    scene(NavigationRoute.FacilityLessonCreated.route) { MobileFacilityEditLessonFeedbackScreen(navigator) }

    //Facility - Settings
    scene(NavigationRoute.FacilitySettings.route) { MobileFacilitySettingsScreen(navigator) }
    scene(NavigationRoute.FacilitySettingsAccount.route) { MobileFacilitySettingsAccountScreen(navigator) }
    scene(NavigationRoute.FacilitySettingsSearchMembers.route) { MobileFacilitySettingsSearchMembersScreen(navigator) }
    scene(NavigationRoute.FacilitySettingsAddMembers.route) { MobileFacilitySettingsAddMembersScreen(navigator) }
    scene(NavigationRoute.FacilitySettingsTrainers.route) { MobileFacilitySettingsTrainersScreen(navigator) }
    scene(NavigationRoute.FacilitySettingsPaymentHistory.route) { MobileFacilitySettingsPaymentHistoryScreen(navigator) }
    scene(NavigationRoute.FacilitySettingsNotifications.route) { MobileFacilitySettingsNotificationsScreen(navigator) }
    scene(NavigationRoute.FacilitySettingsHelp.route) { MobileFacilitySettingsHelpScreen(navigator) }
}