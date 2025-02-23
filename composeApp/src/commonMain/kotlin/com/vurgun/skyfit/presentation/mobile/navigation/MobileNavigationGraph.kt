package com.vurgun.skyfit.presentation.mobile.navigation

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileForgotPasswordCodeScreen
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileForgotPasswordResetScreen
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileForgotPasswordScreen
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileLoginScreen
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileOTPVerificationScreen
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileRegisterScreen
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileSplashScreen
import com.vurgun.skyfit.presentation.mobile.features.dashboard.MobileDashboardScreen
import com.vurgun.skyfit.presentation.mobile.features.explore.MobileExploreBlogArticleDetailScreen
import com.vurgun.skyfit.presentation.mobile.features.explore.MobileExploreBlogScreen
import com.vurgun.skyfit.presentation.mobile.features.explore.MobileExploreChallengeDetailScreen
import com.vurgun.skyfit.presentation.mobile.features.explore.MobileExploreChallengesScreen
import com.vurgun.skyfit.presentation.mobile.features.explore.MobileExploreCommunitiesScreen
import com.vurgun.skyfit.presentation.mobile.features.explore.MobileExploreCommunityDetailScreen
import com.vurgun.skyfit.presentation.mobile.features.explore.MobileExploreExercisesScreen
import com.vurgun.skyfit.presentation.mobile.features.explore.MobileExploreFacilitiesScreen
import com.vurgun.skyfit.presentation.mobile.features.explore.MobileExploreTrainersScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.calendar.MobileFacilityCalendarScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.calendar.MobileFacilityCalendarVisitedScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.classes.MobileFacilityClassDetailVisitedScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.classes.MobileFacilityClassEditCompletedScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.classes.MobileFacilityClassEditScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.classes.MobileFacilityClassesScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityPhotoDiaryScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsAccountScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsAddMembersScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsHelpScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsNotificationsScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsPaymentHistoryScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsSearchMembersScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsTrainersScreen
import com.vurgun.skyfit.presentation.mobile.features.onboarding.MobileOnboardingScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.appointments.MobileTrainerAppointmentDetailScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.appointments.MobileTrainerAppointmentsScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.calendar.MobileTrainerCalendarVisitedScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.profile.MobileTrainerProfileScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.profile.MobileTrainerProfileVisitedScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.settings.MobileTrainerSettingsAccountScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.settings.MobileTrainerSettingsHelpScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.settings.MobileTrainerSettingsNotificationsScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.settings.MobileTrainerSettingsPaymentHistoryScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.settings.MobileTrainerSettingsScreen
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.MobileUserAppointmentDetailScreen
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.MobileUserAppointmentsScreen
import com.vurgun.skyfit.presentation.mobile.features.user.calendar.MobileUserActivityCalendarAddActivityScreen
import com.vurgun.skyfit.presentation.mobile.features.user.calendar.MobileUserActivityCalendarAddedScreen
import com.vurgun.skyfit.presentation.mobile.features.user.calendar.MobileUserActivityCalendarPaymentRequiredScreen
import com.vurgun.skyfit.presentation.mobile.features.user.calendar.MobileUserActivityCalendarScreen
import com.vurgun.skyfit.presentation.mobile.features.user.calendar.MobileUserActivityCalendarSearchScreen
import com.vurgun.skyfit.presentation.mobile.features.user.exercises.MobileUserExerciseDetailScreen
import com.vurgun.skyfit.presentation.mobile.features.user.exercises.MobileUserExerciseInActionCompletedScreen
import com.vurgun.skyfit.presentation.mobile.features.user.exercises.MobileUserExerciseInActionScreen
import com.vurgun.skyfit.presentation.mobile.features.user.messages.MobileUserChatBotScreen
import com.vurgun.skyfit.presentation.mobile.features.user.messages.MobileUserConversationsScreen
import com.vurgun.skyfit.presentation.mobile.features.user.messages.MobileUserToBotChatScreen
import com.vurgun.skyfit.presentation.mobile.features.user.messages.MobileUserToFacilityChatScreen
import com.vurgun.skyfit.presentation.mobile.features.user.messages.MobileUserToGroupChatScreen
import com.vurgun.skyfit.presentation.mobile.features.user.messages.MobileUserToTrainerChatScreen
import com.vurgun.skyfit.presentation.mobile.features.user.messages.MobileUserToUserChatScreen
import com.vurgun.skyfit.presentation.mobile.features.user.notifications.MobileUserNotificationsScreen
import com.vurgun.skyfit.presentation.mobile.features.user.nutrition.MobileUserMealDetailAddPhotoScreen
import com.vurgun.skyfit.presentation.mobile.features.user.nutrition.MobileUserMealDetailAddScreen
import com.vurgun.skyfit.presentation.mobile.features.user.nutrition.MobileUserMealDetailScreen
import com.vurgun.skyfit.presentation.mobile.features.user.payment.MobileUserPaymentProcessScreen
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserBodyAnalysisScreen
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserMeasurementsScreen
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserPhotoDiaryScreen
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserProfileScreen
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserProfileVisitedScreen
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserTrophiesScreen
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsAccountScreen
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsChangePasswordScreen
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsHelpScreen
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsNotificationsScreen
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsPaymentHistoryScreen
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsScreen
import com.vurgun.skyfit.presentation.mobile.features.user.social.MobileUserSocialMediaNewPostScreen
import com.vurgun.skyfit.presentation.shared.navigation.NavigationRoute
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun MobileNavigationGraph() {
    val rootNavigator: Navigator = rememberNavigator()

    NavHost(
        navigator = rootNavigator,
        initialRoute = NavigationRoute.Splash.route
    ) {

        // Auth
        scene(NavigationRoute.Splash.route) { MobileSplashScreen(rootNavigator) }
        scene(NavigationRoute.Login.route) { MobileLoginScreen(rootNavigator) }
        scene(NavigationRoute.Register.route) { MobileRegisterScreen(rootNavigator) }
        scene(NavigationRoute.ForgotPassword.route) { MobileForgotPasswordScreen(rootNavigator) }
        scene(NavigationRoute.ForgotPasswordCode.route) { MobileForgotPasswordCodeScreen(rootNavigator) }
        scene(NavigationRoute.OTPVerification.route) { MobileOTPVerificationScreen(rootNavigator) }
        scene(NavigationRoute.ForgotPasswordReset.route) { MobileForgotPasswordResetScreen(rootNavigator) }

        //General
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