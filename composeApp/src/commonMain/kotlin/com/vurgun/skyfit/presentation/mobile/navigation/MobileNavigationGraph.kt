package com.vurgun.skyfit.presentation.mobile.navigation

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileForgotPasswordCodeScreen
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileForgotPasswordResetScreen
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileForgotPasswordScreen
import com.vurgun.skyfit.presentation.mobile.features.auth.MobileLoginScreen
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
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsHelpScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsMembersScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsNotificationsScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsPaymentHistoryScreen
import com.vurgun.skyfit.presentation.mobile.features.facility.settings.MobileFacilitySettingsScreen
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
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsHelpScreen
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsNotificationsScreen
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsPaymentHistoryScreen
import com.vurgun.skyfit.presentation.mobile.features.user.settings.MobileUserSettingsScreen
import com.vurgun.skyfit.presentation.mobile.features.user.social.MobileUserSocialMediaNewPostScreen
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun MobileNavigationGraph() {
    val rootNavigator: Navigator = rememberNavigator()

    NavHost(
        navigator = rootNavigator,
        initialRoute = SkyFitNavigationRoute.Register.route
    ) {

        // Auth
        scene(SkyFitNavigationRoute.Splash.route) { MobileSplashScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.Login.route) { MobileLoginScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.Register.route) { MobileRegisterScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.ForgotPassword.route) { MobileForgotPasswordScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.ForgotPasswordCode.route) { MobileForgotPasswordCodeScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.ForgotPasswordReset.route) { MobileForgotPasswordResetScreen(rootNavigator) }

        //General
        scene(SkyFitNavigationRoute.Onboarding.route) { MobileOnboardingScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.Dashboard.route) { MobileDashboardScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardProfile.route) { MobileDashboardScreen(rootNavigator, SkyFitNavigationRoute.DashboardProfile) }
        scene(SkyFitNavigationRoute.DashboardExplore.route) { MobileDashboardScreen(rootNavigator, SkyFitNavigationRoute.DashboardExplore) }
        scene(SkyFitNavigationRoute.DashboardNutrition.route) {
            MobileDashboardScreen(
                rootNavigator,
                SkyFitNavigationRoute.DashboardNutrition
            )
        }

        //Explore
        scene(SkyFitNavigationRoute.DashboardExploreTrainers.route) { MobileExploreTrainersScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardExploreExercises.route) { MobileExploreExercisesScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardExploreFacilities.route) { MobileExploreFacilitiesScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardExploreBlogs.route) { MobileExploreBlogScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardExploreBlogArticleDetail.route) { MobileExploreBlogArticleDetailScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardExploreChallenges.route) { MobileExploreChallengesScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardExploreChallengeDetail.route) { MobileExploreChallengeDetailScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardExploreCommunities.route) { MobileExploreCommunitiesScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.DashboardExploreCommunityDetail.route) { MobileExploreCommunityDetailScreen(rootNavigator) }


        //region User
        //User - Exercises
        scene(SkyFitNavigationRoute.UserExerciseDetail.route) { MobileUserExerciseDetailScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserExerciseInAction.route) { MobileUserExerciseInActionScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserExerciseInActionComplete.route) { MobileUserExerciseInActionCompletedScreen(rootNavigator) }

        //User - Nutrition
        scene(SkyFitNavigationRoute.UserMealDetail.route) { MobileUserMealDetailScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserMealDetailAdd.route) { MobileUserMealDetailAddScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserMealDetailAddPhoto.route) { MobileUserMealDetailAddPhotoScreen(rootNavigator) }

        //User - Calendar
        scene(SkyFitNavigationRoute.UserActivityCalendar.route) { MobileUserActivityCalendarScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserActivityCalendarAdd.route) { MobileUserActivityCalendarAddActivityScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserActivityCalendarSearch.route) { MobileUserActivityCalendarSearchScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserPaymentProcess.route) { MobileUserPaymentProcessScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserActivityCalendarPaymentRequired.route) {
            MobileUserActivityCalendarPaymentRequiredScreen(
                rootNavigator
            )
        }
        scene(SkyFitNavigationRoute.UserActivityCalendarConfirmed.route) { MobileUserActivityCalendarAddedScreen(rootNavigator) }
        //User - Appointments
        scene(SkyFitNavigationRoute.UserAppointments.route) { MobileUserAppointmentsScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserAppointmentDetail.route) { MobileUserAppointmentDetailScreen(rootNavigator) }
        //User - Social
        scene(SkyFitNavigationRoute.UserSocialMedia.route) { MobileDashboardScreen(rootNavigator, SkyFitNavigationRoute.DashboardSocial) }
        scene(SkyFitNavigationRoute.UserSocialMediaPostAdd.route) { MobileUserSocialMediaNewPostScreen(rootNavigator) }
        //User - Notifications
        scene(SkyFitNavigationRoute.UserNotifications.route) { MobileUserNotificationsScreen(rootNavigator) }
        //User - Messages
        scene(SkyFitNavigationRoute.UserChatBot.route) { MobileUserChatBotScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserToBotChat.route) { MobileUserToBotChatScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserConversations.route) { MobileUserConversationsScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserToUserChat.route) { MobileUserToUserChatScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserToTrainerChat.route) { MobileUserToTrainerChatScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserToGroupChat.route) { MobileUserToGroupChatScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserToFacilityChat.route) { MobileUserToFacilityChatScreen(rootNavigator) }
        //User - Profile
        scene(SkyFitNavigationRoute.UserProfile.route) { MobileUserProfileScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserProfileVisited.route) { MobileUserProfileVisitedScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserPhotoDiary.route) { MobileUserPhotoDiaryScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserMeasurements.route) { MobileUserMeasurementsScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserBodyAnalysis.route) { MobileUserBodyAnalysisScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserTrophies.route) { MobileUserTrophiesScreen(rootNavigator) }
        //User - Settings
        scene(SkyFitNavigationRoute.UserSettings.route) { MobileUserSettingsScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserSettingsAccount.route) { MobileUserSettingsAccountScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserSettingsNotifications.route) { MobileUserSettingsNotificationsScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserSettingsPaymentHistory.route) { MobileUserSettingsPaymentHistoryScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.UserSettingsHelp.route) { MobileUserSettingsHelpScreen(rootNavigator) }
        //endregion User

        //region Trainer
        //Trainer - Appointments
        scene(SkyFitNavigationRoute.TrainerAppointments.route) { MobileTrainerAppointmentsScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.TrainerAppointmentDetail.route) { MobileTrainerAppointmentDetailScreen(rootNavigator) }
        //Trainer - Profile
        scene(SkyFitNavigationRoute.TrainerProfile.route) { MobileTrainerProfileScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.TrainerProfileVisited.route) { MobileTrainerProfileVisitedScreen(rootNavigator) }
        //Trainer - Calendar
        scene(SkyFitNavigationRoute.TrainerCalendarVisited.route) { MobileTrainerCalendarVisitedScreen(rootNavigator) }
        //Trainer - Settings
        scene(SkyFitNavigationRoute.TrainerSettings.route) { MobileTrainerSettingsScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.TrainerSettingsAccount.route) { MobileTrainerSettingsAccountScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.TrainerSettingsNotifications.route) { MobileTrainerSettingsNotificationsScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.TrainerSettingsPaymentHistory.route) { MobileTrainerSettingsPaymentHistoryScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.TrainerSettingsHelp.route) { MobileTrainerSettingsHelpScreen(rootNavigator) }
        //endregion Trainer

        //region Facility
        //Facility - Profile
        scene(SkyFitNavigationRoute.FacilityProfile.route) { MobileFacilityProfileScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilityProfileVisited.route) { MobileFacilityProfileVisitedScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilityPhotoDiary.route) { MobileFacilityPhotoDiaryScreen(rootNavigator) }
        //Facility - Calendar
        scene(SkyFitNavigationRoute.FacilityCalendar.route) { MobileFacilityCalendarScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilityCalendarVisited.route) { MobileFacilityCalendarVisitedScreen(rootNavigator) }
        //Facility - Classes
        scene(SkyFitNavigationRoute.FacilityClasses.route) { MobileFacilityClassesScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilityClassEdit.route) { MobileFacilityClassEditScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilityClassEditCompleted.route) { MobileFacilityClassEditCompletedScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilityClassDetailVisited.route) { MobileFacilityClassDetailVisitedScreen(rootNavigator) }
        //Facility - Settings
        scene(SkyFitNavigationRoute.FacilitySettings.route) { MobileFacilitySettingsScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilitySettingsAccount.route) { MobileFacilitySettingsAccountScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilitySettingsMembers.route) { MobileFacilitySettingsMembersScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilitySettingsTrainers.route) { MobileFacilitySettingsTrainersScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilitySettingsPaymentHistory.route) { MobileFacilitySettingsPaymentHistoryScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilitySettingsNotifications.route) { MobileFacilitySettingsNotificationsScreen(rootNavigator) }
        scene(SkyFitNavigationRoute.FacilitySettingsHelp.route) { MobileFacilitySettingsHelpScreen(rootNavigator) }
        //endregion Facility
    }
}