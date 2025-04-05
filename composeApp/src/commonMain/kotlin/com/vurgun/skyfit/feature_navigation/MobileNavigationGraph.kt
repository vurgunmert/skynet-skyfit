package com.vurgun.skyfit.feature_navigation

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.ui.SkyFitHostScreen
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
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityEditLessonScreen
import com.vurgun.skyfit.feature_lessons.ui.MobileFacilityLessonListScreen
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
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserTrophiesScreen
import com.vurgun.skyfit.feature_settings.ui.changepassword.SettingsChangePasswordScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsAccountScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsAddMembersScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsHelpScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsNotificationsScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsSearchMembersScreen
import com.vurgun.skyfit.feature_settings.ui.facility.MobileFacilitySettingsTrainersScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsAccountScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsEditProfileScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsHelpScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsNotificationsScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature_settings.ui.trainer.MobileTrainerSettingsScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsAccountScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsEditProfileScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsHelpScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsNotificationsScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature_settings.ui.user.MobileUserSettingsHomeScreen
import com.vurgun.skyfit.feature_social.ui.MobileUserSocialMediaNewPostScreen
import com.vurgun.skyfit.feature_splash.presentation.view.SplashScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.KoinContext

//TODO: MOVE OUT
@Composable
fun MobileApp() {
    KoinContext {
        SkyFitHostScreen { MobileNavigationGraph() }
    }
}

@Composable
fun MobileNavigationGraph() {
    val navigator: Navigator = rememberNavigator()

    NavHost(
        navigator = navigator,
        initialRoute = MobileNavRoute.Settings.Trainer.Account.route
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
        settingsNavGraph(navigator)
    }
}

private fun RouteBuilder.mobileAuthNavGraph(navigator: Navigator) {
    scene(MobileNavRoute.Splash.route) { SplashScreen(navigator) }
    scene(MobileNavRoute.Login.route) { MobileLoginScreen(navigator) }
    scene(MobileNavRoute.LoginVerifyOTP.route) { MobileLoginVerifyOTPScreen(navigator) }
    scene(MobileNavRoute.CreatePassword.route) { MobileCreatePasswordScreen(navigator) }
    scene(MobileNavRoute.ForgotPassword.route) { MobileForgotPasswordScreen(navigator) }
    scene(MobileNavRoute.ForgotPasswordVerifyOTP.route) { MobileForgotPasswordVerifyOTPScreen(navigator) }
    scene(MobileNavRoute.ForgotPasswordReset.route) { MobileForgotPasswordResetScreen(navigator) }
    scene(MobileNavRoute.PrivacyPolicy.route) { MobilePrivacyPolicyScreen(navigator) }
    scene(MobileNavRoute.TermsAndConditions.route) { MobileTermsAndConditionsScreen(navigator) }
}

private fun RouteBuilder.mobileOnboardingNavGraph(navigator: Navigator) {
    scene(MobileNavRoute.Onboarding.route) { MobileOnboardingFlowNavGraph(navigator) }
}

private fun RouteBuilder.dashboardNavGraph(navigator: Navigator) {
    scene(MobileNavRoute.Dashboard.route) { MobileDashboardScreen(navigator) }
    scene(MobileNavRoute.DashboardHome.route) { MobileDashboardScreen(navigator, MobileNavRoute.DashboardHome) }
    scene(MobileNavRoute.DashboardExplore.route) { MobileDashboardScreen(navigator, MobileNavRoute.DashboardExplore) }
    scene(MobileNavRoute.DashboardSocial.route) { MobileDashboardScreen(navigator, MobileNavRoute.DashboardSocial) }
    scene(MobileNavRoute.DashboardNutrition.route) { MobileDashboardScreen(navigator, MobileNavRoute.DashboardNutrition) }
    scene(MobileNavRoute.DashboardProfile.route) { MobileDashboardScreen(navigator, MobileNavRoute.DashboardProfile) }
}

private fun RouteBuilder.exploreNavGraph(navigator: Navigator) {
    scene(MobileNavRoute.ExploreTrainers.route) { MobileExploreTrainersScreen(navigator) }
    scene(MobileNavRoute.ExploreExercises.route) { MobileExploreExercisesScreen(navigator) }
    scene(MobileNavRoute.ExploreFacilities.route) { MobileExploreFacilitiesScreen(navigator) }
    scene(MobileNavRoute.ExploreBlogs.route) { MobileExploreBlogScreen(navigator) }
    scene(MobileNavRoute.ExploreBlogArticleDetail.route) { MobileExploreBlogArticleDetailScreen(navigator) }
    scene(MobileNavRoute.ExploreChallenges.route) { MobileExploreChallengesScreen(navigator) }
    scene(MobileNavRoute.ExploreChallengeDetail.route) { MobileExploreChallengeDetailScreen(navigator) }
    scene(MobileNavRoute.ExploreCommunities.route) { MobileExploreCommunitiesScreen(navigator) }
    scene(MobileNavRoute.ExploreCommunityDetail.route) { MobileExploreCommunityDetailScreen(navigator) }
}

private fun RouteBuilder.exerciseNavGraph(navigator: Navigator) {
    scene(MobileNavRoute.ExerciseDetail.route) { MobileUserExerciseDetailScreen(navigator) }
    scene(MobileNavRoute.ExerciseInProgress.route) { MobileUserExerciseInActionScreen(navigator) }
    scene(MobileNavRoute.ExerciseCompleted.route) { MobileUserExerciseInActionCompletedScreen(navigator) }
}

private fun RouteBuilder.nutritionNavGraph(navigator: Navigator) {
    scene(MobileNavRoute.UserMealDetail.route) { MobileUserMealDetailScreen(navigator) }
    scene(MobileNavRoute.UserMealDetailAdd.route) { MobileUserMealDetailAddScreen(navigator) }
    scene(MobileNavRoute.UserMealDetailAddPhoto.route) { MobileUserMealDetailAddPhotoScreen(navigator) }
}

private fun RouteBuilder.userNavGraph(navigator: Navigator) {
    //User - Calendar
    scene(MobileNavRoute.UserActivityCalendar.route) { MobileUserActivityCalendarScreen(navigator) }
    scene(MobileNavRoute.UserActivityCalendarAdd.route) { MobileUserActivityCalendarAddActivityScreen(navigator) }
    scene(MobileNavRoute.UserActivityCalendarSearch.route) { MobileUserActivityCalendarSearchScreen(navigator) }
    scene(MobileNavRoute.UserPaymentProcess.route) { MobileUserPaymentProcessScreen(navigator) }
    scene(MobileNavRoute.UserActivityCalendarPaymentRequired.route) {
        MobileUserActivityCalendarPaymentRequiredScreen(
            navigator
        )
    }
    scene(MobileNavRoute.UserActivityCalendarConfirmed.route) { MobileUserActivityCalendarAddedScreen(navigator) }
    //User - Appointments
    scene(MobileNavRoute.UserAppointments.route) { MobileUserAppointmentsScreen(navigator) }
    scene(MobileNavRoute.UserAppointmentDetail.route) { MobileUserAppointmentDetailScreen(navigator) }
    //User - Social
    scene(MobileNavRoute.UserSocialMedia.route) { MobileDashboardScreen(navigator, MobileNavRoute.DashboardSocial) }
    scene(MobileNavRoute.UserSocialMediaPostAdd.route) { MobileUserSocialMediaNewPostScreen(navigator) }
    //User - Notifications
    scene(MobileNavRoute.UserNotifications.route) { MobileUserNotificationsScreen(navigator) }
    //User - Messages
    scene(MobileNavRoute.UserChatBot.route) { MobileUserChatBotScreen(navigator) }
    scene(MobileNavRoute.UserToBotChat.route) { MobileUserToBotChatScreen(navigator) }
    scene(MobileNavRoute.UserConversations.route) { MobileUserConversationsScreen(navigator) }
    scene(MobileNavRoute.UserToUserChat.route) { MobileUserToUserChatScreen(navigator) }
    scene(MobileNavRoute.UserToTrainerChat.route) { MobileUserToTrainerChatScreen(navigator) }
    scene(MobileNavRoute.UserToGroupChat.route) { MobileUserToGroupChatScreen(navigator) }
    scene(MobileNavRoute.UserToFacilityChat.route) { MobileUserToFacilityChatScreen(navigator) }
    //User - Profile
    scene(MobileNavRoute.UserProfile.route) { MobileUserProfileScreen(navigator) }
    scene(MobileNavRoute.UserProfileVisited.route) { MobileUserProfileScreen(navigator) }
    scene(MobileNavRoute.UserPhotoDiary.route) { MobileUserPhotoDiaryScreen(navigator) }
    scene(MobileNavRoute.UserMeasurements.route) { MobileUserMeasurementsScreen(navigator) }
    scene(MobileNavRoute.UserBodyAnalysis.route) { MobileUserBodyAnalysisScreen(navigator) }
    scene(MobileNavRoute.UserTrophies.route) { MobileUserTrophiesScreen(navigator) }

}

private fun RouteBuilder.settingsNavGraph(navigator: Navigator) {
    //User - Settings
    scene(MobileNavRoute.Settings.User.Home.route) { MobileUserSettingsHomeScreen(navigator) }
    scene(MobileNavRoute.Settings.User.Account.route) { MobileUserSettingsAccountScreen(navigator) }
    scene(MobileNavRoute.Settings.User.EditProfile.route) { MobileUserSettingsEditProfileScreen(navigator) }
    scene(MobileNavRoute.Settings.User.Notifications.route) { MobileUserSettingsNotificationsScreen(navigator) }
    scene(MobileNavRoute.Settings.User.PaymentHistory.route) { MobileUserSettingsPaymentHistoryScreen(navigator) }
    scene(MobileNavRoute.Settings.User.ChangePassword.route) { SettingsChangePasswordScreen(navigator) }
    scene(MobileNavRoute.Settings.User.Help.route) { MobileUserSettingsHelpScreen(navigator) }
    //Trainer - Settings
    scene(MobileNavRoute.Settings.Trainer.Home.route) { MobileTrainerSettingsScreen(navigator) }
    scene(MobileNavRoute.Settings.Trainer.Account.route) { MobileTrainerSettingsAccountScreen(navigator) }
    scene(MobileNavRoute.Settings.Trainer.EditProfile.route) { MobileTrainerSettingsEditProfileScreen(navigator) }
    scene(MobileNavRoute.Settings.Trainer.Notifications.route) { MobileTrainerSettingsNotificationsScreen(navigator) }
    scene(MobileNavRoute.Settings.Trainer.PaymentHistory.route) { MobileTrainerSettingsPaymentHistoryScreen(navigator) }
    scene(MobileNavRoute.Settings.Trainer.ChangePassword.route) { SettingsChangePasswordScreen(navigator) }
    scene(MobileNavRoute.Settings.Trainer.Help.route) { MobileTrainerSettingsHelpScreen(navigator) }
    scene(MobileNavRoute.Settings.Trainer.ManageAccounts.route) { MobileTrainerSettingsHelpScreen(navigator) }
    scene(MobileNavRoute.Settings.Trainer.ManageMembers.route) { MobileTrainerSettingsHelpScreen(navigator) }
    //Facility - Settings
    scene(MobileNavRoute.Settings.Facility.Home.route) { MobileFacilitySettingsScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.Account.route) { MobileFacilitySettingsAccountScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.EditProfile.route) { MobileFacilitySettingsAccountScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.PaymentHistory.route) { MobileFacilitySettingsPaymentHistoryScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.ChangePassword.route) { SettingsChangePasswordScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.Notifications.route) { MobileFacilitySettingsNotificationsScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.Help.route) { MobileFacilitySettingsHelpScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.ManageAccounts.route) { MobileFacilitySettingsTrainersScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.ManageTrainers.route) { MobileFacilitySettingsTrainersScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.ManageBranches.route) { MobileFacilitySettingsTrainersScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.ManageMembers.route) { MobileFacilitySettingsSearchMembersScreen(navigator) }
    scene(MobileNavRoute.Settings.Facility.AddMembers.route) { MobileFacilitySettingsAddMembersScreen(navigator) }
}

private fun RouteBuilder.trainerNavGraph(navigator: Navigator) {
    //Trainer - Appointments
    scene(MobileNavRoute.TrainerAppointments.route) { MobileTrainerAppointmentsScreen(navigator) }
    scene(MobileNavRoute.TrainerAppointmentDetail.route) { MobileTrainerAppointmentDetailScreen(navigator) }
    //Trainer - Profile
    scene(MobileNavRoute.TrainerProfile.route) { MobileTrainerProfileScreen(navigator) }
    scene(MobileNavRoute.TrainerProfileVisited.route) { MobileTrainerProfileVisitedScreen(navigator) }
    //Trainer - Calendar
    scene(MobileNavRoute.TrainerCalendarVisited.route) { MobileTrainerCalendarVisitedScreen(navigator) }
}

private fun RouteBuilder.facilityNavGraph(navigator: Navigator) {
    //Facility - Profile
    scene(MobileNavRoute.FacilityProfile.route) { MobileFacilityProfileScreen(navigator, viewMode = ProfileViewMode.OWNER) }
    scene(MobileNavRoute.FacilityProfileVisited.route) { MobileFacilityProfileScreen(navigator, viewMode = ProfileViewMode.VISITOR) }
    scene(MobileNavRoute.FacilityPhotoGallery.route) { MobileFacilityPhotoDiaryScreen(navigator) }
    //Facility - Calendar
    scene(MobileNavRoute.FacilityCalendar.route) { MobileFacilityCalendarScreen(navigator) }
    scene(MobileNavRoute.FacilityCalendarVisited.route) { MobileFacilityCalendarVisitedScreen(navigator) }
    //Facility - Classes
    scene(MobileNavRoute.FacilityLessons.route) { MobileFacilityLessonListScreen(navigator) }
    scene(MobileNavRoute.FacilityLessonEdit.route) { MobileFacilityEditLessonScreen(navigator) }
    scene(MobileNavRoute.FacilityLessonCreated.route) { MobileFacilityEditLessonFeedbackScreen(navigator) }
}