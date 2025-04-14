package com.vurgun.skyfit.feature_navigation

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.AppNavigationGraph
import com.vurgun.skyfit.appModule
import com.vurgun.skyfit.feature.auth.forgotpassword.MobileForgotPasswordResetScreen
import com.vurgun.skyfit.feature.auth.forgotpassword.MobileForgotPasswordScreen
import com.vurgun.skyfit.feature.auth.forgotpassword.MobileForgotPasswordVerifyOTPScreen
import com.vurgun.skyfit.feature.auth.legal.MobilePrivacyPolicyScreen
import com.vurgun.skyfit.feature.auth.legal.MobileTermsAndConditionsScreen
import com.vurgun.skyfit.feature.auth.login.MobileLoginScreen
import com.vurgun.skyfit.feature.auth.login.MobileLoginVerifyOTPScreen
import com.vurgun.skyfit.feature.auth.register.MobileCreatePasswordScreen
import com.vurgun.skyfit.feature.auth.splash.SplashScreen
import com.vurgun.skyfit.feature.calendar.components.screen.MobileUserActivityCalendarAddActivityScreen
import com.vurgun.skyfit.feature.calendar.components.screen.MobileUserActivityCalendarAddedScreen
import com.vurgun.skyfit.feature.calendar.components.screen.MobileUserActivityCalendarPaymentRequiredScreen
import com.vurgun.skyfit.feature.calendar.components.screen.MobileUserActivityCalendarScreen
import com.vurgun.skyfit.feature.calendar.components.screen.MobileUserActivityCalendarSearchScreen
import com.vurgun.skyfit.feature.calendar.components.screen.MobileUserAppointmentDetailScreen
import com.vurgun.skyfit.feature.calendar.components.screen.MobileUserAppointmentsScreen
import com.vurgun.skyfit.feature.settings.changepassword.SettingsChangePasswordScreen
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsHomeScreen
import com.vurgun.skyfit.feature.settings.facility.notification.MobileFacilitySettingsNotificationsScreen
import com.vurgun.skyfit.feature.settings.facility.payment.MobileFacilitySettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature.settings.facility.profile.MobileFacilityManageProfileScreen
import com.vurgun.skyfit.feature.settings.facility.profile.MobileFacilitySettingsEditProfileScreen
import com.vurgun.skyfit.feature.settings.helpsupport.MobileSettingsSupportHelpScreen
import com.vurgun.skyfit.feature.settings.payment.MobileUserPaymentProcessScreen
import com.vurgun.skyfit.feature.settings.trainer.MobileTrainerSettingsAccountScreen
import com.vurgun.skyfit.feature.settings.trainer.MobileTrainerSettingsEditProfileScreen
import com.vurgun.skyfit.feature.settings.trainer.MobileTrainerSettingsHomeScreen
import com.vurgun.skyfit.feature.settings.trainer.MobileTrainerSettingsNotificationsScreen
import com.vurgun.skyfit.feature.settings.trainer.MobileTrainerSettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature.settings.user.MobileUserSettingsAccountScreen
import com.vurgun.skyfit.feature.settings.user.MobileUserSettingsEditProfileScreen
import com.vurgun.skyfit.feature.settings.user.MobileUserSettingsHomeScreen
import com.vurgun.skyfit.feature.settings.user.MobileUserSettingsNotificationsScreen
import com.vurgun.skyfit.feature.settings.user.MobileUserSettingsPaymentHistoryScreen
import com.vurgun.skyfit.ui.core.styling.SkyFitTheme
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.KoinApplication
import org.koin.core.module.Module

@Composable
fun SkyFitHostScreen(content: @Composable () -> Unit) {
    SkyFitTheme {
        PreComposeApp(content = content)
    }
}

//TODO: NEW WAY OF DEALING
@Composable
fun SkyFitApp(
    platformModule: Module = Module(),
) {
    KoinApplication(
        application = {
            modules(platformModule, appModule)
        },
        content = {
            SkyFitTheme {
                AppNavigationGraph()
            }
        }
    )
}


@Composable
fun MobileNavigationGraph() {
    val navigator: Navigator = rememberNavigator()

    NavHost(
        navigator = navigator,
        initialRoute = MobileNavRoute.Settings.Facility.Account.route
    ) {
        mobileAuthNavGraph(navigator)
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
    scene(MobileNavRoute.Splash.route) {
        SplashScreen(
            goToMaintenance = { navigator.jumpAndTakeover(MobileNavRoute.Maintenance) },
            goToLogin = { navigator.jumpAndTakeover(MobileNavRoute.Login) },
            goToDashboard = { navigator.jumpAndTakeover(MobileNavRoute.Dashboard) }
        )
    }
    scene(MobileNavRoute.Login.route) {
        MobileLoginScreen(
            goToOtp = { navigator.jumpAndTakeover(MobileNavRoute.LoginVerifyOTP) },
            goToOnboarding = { navigator.jumpAndTakeover(MobileNavRoute.Onboarding) },
            goToDashboard = { navigator.jumpAndTakeover(MobileNavRoute.Dashboard) },
            goToForgotPassword = { navigator.jumpAndTakeover(MobileNavRoute.ForgotPassword) },
            goToPrivacyPolicy = { navigator.jumpAndTakeover(MobileNavRoute.PrivacyPolicy) },
            goToTermsAndConditions = { navigator.jumpAndTakeover(MobileNavRoute.TermsAndConditions) },
        )
    }
    scene(MobileNavRoute.LoginVerifyOTP.route) {
        MobileLoginVerifyOTPScreen(
            goToCreatePassword = { navigator.jumpAndTakeover(MobileNavRoute.CreatePassword) },
            goToDashboard = { navigator.jumpAndTakeover(MobileNavRoute.Dashboard) },
            goToOnboarding = { navigator.jumpAndTakeover(MobileNavRoute.Onboarding) },
        )
    }
    scene(MobileNavRoute.CreatePassword.route) {
        MobileCreatePasswordScreen(
            goToOnboarding = { navigator.jumpAndTakeover(MobileNavRoute.Onboarding) },
            goToTermsAndConditions = { navigator.jumpAndTakeover(MobileNavRoute.TermsAndConditions) },
            goToPrivacyPolicy = { navigator.jumpAndTakeover(MobileNavRoute.PrivacyPolicy) },
        )
    }
    scene(MobileNavRoute.ForgotPassword.route) {
        MobileForgotPasswordScreen(
            goToBack = navigator::popBackStack,
            goToVerify = { navigator.jumpAndTakeover(MobileNavRoute.ForgotPasswordVerifyOTP) }
        )
    }
    scene(MobileNavRoute.ForgotPasswordVerifyOTP.route) {
        MobileForgotPasswordVerifyOTPScreen(
            goToReset = { navigator.jumpAndTakeover(MobileNavRoute.ForgotPasswordReset) }
        )
    }
    scene(MobileNavRoute.ForgotPasswordReset.route) {
        MobileForgotPasswordResetScreen(
            goToLogin = { navigator.jumpAndTakeover(MobileNavRoute.Login) },
            goToDashboard = { navigator.jumpAndTakeover(MobileNavRoute.Dashboard) },
        )
    }
    scene(MobileNavRoute.PrivacyPolicy.route) {
        MobilePrivacyPolicyScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.TermsAndConditions.route) {
        MobileTermsAndConditionsScreen(
            goToBack = navigator::popBackStack
        )
    }
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
//    scene(MobileNavRoute.ExploreTrainers.route) { MobileExploreTrainersScreen(
//        goToBack = navigator::popBackStack
//    ) }
//    scene(MobileNavRoute.ExploreExercises.route) { MobileExploreExercisesScreen(
//        goToBack = navigator::popBackStack
//    ) }
//    scene(MobileNavRoute.ExploreFacilities.route) { MobileExploreFacilitiesScreen(
//        goToBack = navigator::popBackStack
//    ) }
//    scene(MobileNavRoute.ExploreBlogs.route) { MobileExploreBlogScreen(
//        goToBack = navigator::popBackStack
//    ) }
//    scene(MobileNavRoute.ExploreBlogArticleDetail.route) { MobileExploreBlogArticleDetailScreen(
//        goToBack = navigator::popBackStack
//    ) }
//    scene(MobileNavRoute.ExploreChallenges.route) { MobileExploreChallengesScreen(
//        goToBack = navigator::popBackStack,
//        goToChallengeDetail = { navigator.jumpAndStay(MobileNavRoute.ExploreChallengeDetail) }
//    ) }
//    scene(MobileNavRoute.ExploreChallengeDetail.route) { MobileExploreChallengeDetailScreen(
//        goToBack = navigator::popBackStack
//    ) }
//    scene(MobileNavRoute.ExploreCommunities.route) { MobileExploreCommunitiesScreen(
//        goToBack = navigator::popBackStack
//    ) }
//    scene(MobileNavRoute.ExploreCommunityDetail.route) { MobileExploreCommunityDetailScreen(
//        goToBack = navigator::popBackStack
//    ) }
}

private fun RouteBuilder.exerciseNavGraph(navigator: Navigator) {
//    scene(MobileNavRoute.ExerciseDetail.route) { MobileUserExerciseDetailScreen(
//        goToBack = TODO(),
//        goToExerciseAction = TODO()
//    ) }
//    scene(MobileNavRoute.ExerciseInProgress.route) { MobileUserExerciseInActionScreen(
//        goToBack = TODO(),
//        goToComplete = TODO(),
//        goToTrophies = TODO()
//    ) }
//    scene(MobileNavRoute.ExerciseCompleted.route) { MobileUserExerciseInActionCompletedScreen(
//        goToDashboard = TODO()
//    ) }
}

private fun RouteBuilder.nutritionNavGraph(navigator: Navigator) {
//    scene(MobileNavRoute.UserMealDetail.route) { MobileUserMealDetailScreen(navigator) }
//    scene(MobileNavRoute.UserMealDetailAdd.route) { MobileUserMealDetailAddScreen(navigator) }
//    scene(MobileNavRoute.UserMealDetailAddPhoto.route) { MobileUserMealDetailAddPhotoScreen(navigator) }
}

private fun RouteBuilder.userNavGraph(navigator: Navigator) {
    //User - Calendar
    scene(MobileNavRoute.UserActivityCalendar.route) {
        MobileUserActivityCalendarScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.UserActivityCalendarAdd.route) {
        MobileUserActivityCalendarAddActivityScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.UserActivityCalendarSearch.route) {
        MobileUserActivityCalendarSearchScreen(
            goToBack = navigator::popBackStack,
            goToAddActivity = { navigator.jumpAndStay(MobileNavRoute.UserActivityCalendarAdd) }
        )
    }
    scene(MobileNavRoute.UserPaymentProcess.route) {
        MobileUserPaymentProcessScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.UserActivityCalendarPaymentRequired.route) {
        MobileUserActivityCalendarPaymentRequiredScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.UserActivityCalendarConfirmed.route) {
        MobileUserActivityCalendarAddedScreen(
            goToBack = navigator::popBackStack
        )
    }
    //User - Appointments
    scene(MobileNavRoute.UserAppointments.route) {
        MobileUserAppointmentsScreen(
            goToBack = navigator::popBackStack,
            goToDetails = { navigator.jumpAndStay(MobileNavRoute.UserAppointmentDetail) }
        )
    }
    scene(MobileNavRoute.UserAppointmentDetail.route) {
        MobileUserAppointmentDetailScreen(
            goToBack = navigator::popBackStack
        )
    }
    //User - Social
    scene(MobileNavRoute.UserSocialMedia.route) { MobileDashboardScreen(navigator, MobileNavRoute.DashboardSocial) }
//    scene(MobileNavRoute.UserSocialMediaPostAdd.route) { MobileUserSocialMediaNewPostScreen(navigator) }
    //User - Notifications
    scene(MobileNavRoute.UserNotifications.route) {
//        MobileUserNotificationsScreen(
//            goBack = navigator::popBackStack,
//            goToSettings = { navigator.jumpAndStay(MobileNavRoute.Settings.User.Notifications) }
//        )
    }
    //User - Messages
//    scene(MobileNavRoute.UserChatBot.route) { MobileUserChatBotScreen(navigator) }
//    scene(MobileNavRoute.UserToBotChat.route) { MobileUserToBotChatScreen(navigator) }
//    scene(MobileNavRoute.UserConversations.route) { MobileUserConversationsScreen(navigator) }
//    scene(MobileNavRoute.UserToUserChat.route) { MobileUserToUserChatScreen(navigator) }
//    scene(MobileNavRoute.UserToTrainerChat.route) { MobileUserToTrainerChatScreen(navigator) }
//    scene(MobileNavRoute.UserToGroupChat.route) { MobileUserToGroupChatScreen(navigator) }
//    scene(MobileNavRoute.UserToFacilityChat.route) { MobileUserToFacilityChatScreen(navigator) }
//    //User - Profile
//    scene(MobileNavRoute.UserProfile.route) { MobileUserProfileScreen(navigator) }
//    scene(MobileNavRoute.UserProfileVisited.route) { MobileUserProfileScreen(navigator) }
//    scene(MobileNavRoute.UserPhotoDiary.route) { MobileUserPhotoDiaryScreen(navigator) }
//    scene(MobileNavRoute.UserMeasurements.route) { MobileUserMeasurementsScreen(navigator) }
//    scene(MobileNavRoute.UserBodyAnalysis.route) { MobileUserBodyAnalysisScreen(navigator) }
//    scene(MobileNavRoute.UserTrophies.route) { MobileUserTrophiesScreen(navigator) }

}

private fun RouteBuilder.settingsNavGraph(navigator: Navigator) {
    //User - Settings
    scene(MobileNavRoute.Settings.User.Home.route) {
        MobileUserSettingsHomeScreen(
            goToBack = navigator::popBackStack,
            goToLogin = { navigator.jumpAndTakeover(MobileNavRoute.Login) },
            goToAccount = { navigator.jumpAndStay(MobileNavRoute.Settings.User.Account) },
            goToPaymentHistory = { navigator.jumpAndStay(MobileNavRoute.Settings.User.PaymentHistory) },
            goToNotifications = { navigator.jumpAndStay(MobileNavRoute.Settings.User.Notifications) },
            goToHelp = { navigator.jumpAndStay(MobileNavRoute.Settings.User.Help) }
        )
    }
    scene(MobileNavRoute.Settings.User.Account.route) {
        MobileUserSettingsAccountScreen(
            goToBack = navigator::popBackStack,
            goToEditProfile = { navigator.jumpAndStay(MobileNavRoute.Settings.User.EditProfile) },
            goToChangePassword = { navigator.jumpAndStay(MobileNavRoute.Settings.User.ChangePassword) },
        )
    }
    scene(MobileNavRoute.Settings.User.EditProfile.route) {
        MobileUserSettingsEditProfileScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.User.Notifications.route) {
        MobileUserSettingsNotificationsScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.User.PaymentHistory.route) {
        MobileUserSettingsPaymentHistoryScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.User.ChangePassword.route) {
        SettingsChangePasswordScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.User.Help.route) {
        MobileSettingsSupportHelpScreen(goToBack = navigator::popBackStack)
    }
    //Trainer - Settings
    scene(MobileNavRoute.Settings.Trainer.Home.route) {
        MobileTrainerSettingsHomeScreen(
            goToBack = navigator::popBackStack,
            goToLogin = { navigator.jumpAndStay(MobileNavRoute.Login) },
            goToAccount = { navigator.jumpAndStay(MobileNavRoute.Settings.Trainer.Account) },
            goToPaymentHistory = { navigator.jumpAndStay(MobileNavRoute.Settings.Trainer.PaymentHistory) },
            goToMembers = { navigator.jumpAndStay(MobileNavRoute.Settings.Trainer.Notifications) },
            goToNotifications = { navigator.jumpAndStay(MobileNavRoute.Settings.Trainer.Notifications) },
            goToHelp = { navigator.jumpAndStay(MobileNavRoute.Settings.Trainer.Help) }
        )
    }
    scene(MobileNavRoute.Settings.Trainer.Account.route) {
        MobileTrainerSettingsAccountScreen(
            goToBack = navigator::popBackStack,
            goToEditProfile = { navigator.jumpAndStay(MobileNavRoute.Settings.Trainer.EditProfile) },
            goToChangePassword = { navigator.jumpAndStay(MobileNavRoute.Settings.Trainer.ChangePassword) },
            goToManageAccounts = { navigator.jumpAndStay(MobileNavRoute.Settings.Trainer.ManageAccounts) }
        )
    }
    scene(MobileNavRoute.Settings.Trainer.EditProfile.route) {
        MobileTrainerSettingsEditProfileScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.Trainer.Notifications.route) {
        MobileTrainerSettingsNotificationsScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.Trainer.PaymentHistory.route) {
        MobileTrainerSettingsPaymentHistoryScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.Trainer.ChangePassword.route) {
        SettingsChangePasswordScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.Trainer.Help.route) {
        MobileSettingsSupportHelpScreen(goToBack = navigator::popBackStack)
    }
    scene(MobileNavRoute.Settings.Trainer.ManageAccounts.route) { }
    scene(MobileNavRoute.Settings.Trainer.ManageMembers.route) { }
    //Facility - Settings
    scene(MobileNavRoute.Settings.Facility.Home.route) {
        MobileFacilitySettingsHomeScreen(
            goToBack = navigator::popBackStack,
            goToLogin = { navigator.jumpAndTakeover(MobileNavRoute.Login) },
            goToAccountSettings = { navigator.jumpAndStay(MobileNavRoute.FacilitySettingsAccount) },
            goToPaymentHistory = { navigator.jumpAndStay(MobileNavRoute.FacilitySettingsPaymentHistory) },
            goToNotifications = { navigator.jumpAndStay(MobileNavRoute.FacilitySettingsNotifications) },
            goToManageTrainers = { navigator.jumpAndStay(MobileNavRoute.FacilitySettingsTrainers) },
            goToManageMembers = { navigator.jumpAndStay(MobileNavRoute.FacilitySettingsSearchMembers) },
            goToManageBranches = { navigator.jumpAndStay(MobileNavRoute.FacilitySettingsSearchMembers) },
            goToSupport = { navigator.jumpAndStay(MobileNavRoute.FacilitySettingsHelp) },
        )
    }

    scene(MobileNavRoute.Settings.Facility.Account.route) {
        MobileFacilityManageProfileScreen(
            goToBack = navigator::popBackStack,
            goToEditProfile = { navigator.jumpAndStay(MobileNavRoute.Settings.Facility.EditProfile) },
            goToChangePassword = { navigator.jumpAndStay(MobileNavRoute.Settings.Trainer.ChangePassword) },
            goToManageAccounts = { navigator.jumpAndStay(MobileNavRoute.Settings.Trainer.ManageAccounts) }
        )
    }

    scene(MobileNavRoute.Settings.Facility.EditProfile.route) {
        MobileFacilitySettingsEditProfileScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.Facility.PaymentHistory.route) {
        MobileFacilitySettingsPaymentHistoryScreen(
            goToBack =
                navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.Facility.ChangePassword.route) {
        SettingsChangePasswordScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.Facility.Notifications.route) {
        MobileFacilitySettingsNotificationsScreen(
            goToBack = navigator::popBackStack
        )
    }
    scene(MobileNavRoute.Settings.Facility.Help.route) {
        MobileSettingsSupportHelpScreen(goToBack = navigator::popBackStack)
    }
    scene(MobileNavRoute.Settings.Facility.ManageAccounts.route) { }

    scene(MobileNavRoute.Settings.Facility.ManageBranches.route) { }
//    scene(MobileNavRoute.Settings.Facility.ManageMembers.route) {
//        MobileFacilitySettingsManageMembersScreen(
//            goToBack = navigator::popBackStack,
//            goToAddMember = { navigator.jumpAndStay(MobileNavRoute.Settings.Facility.AddMembers) }
//        )
//    }
}

private fun RouteBuilder.trainerNavGraph(navigator: Navigator) {
//    //Trainer - Appointments
//    scene(MobileNavRoute.TrainerAppointments.route) {
//        MobileTrainerAppointmentsScreen(
//            goToBack = navigator::popBackStack
//        )
//    }
//    scene(MobileNavRoute.TrainerAppointmentDetail.route) {
//        MobileTrainerAppointmentDetailScreen(
//            goToBack = navigator::popBackStack
//        )
//    }
//    //Trainer - Profile
//    scene(MobileNavRoute.TrainerProfile.route) { MobileTrainerProfileScreen(
//        goToCreatePost = TODO()
//    ) }
//    scene(MobileNavRoute.TrainerProfileVisited.route) { MobileTrainerProfileVisitedScreen(navigator) }
//    //Trainer - Calendar
//    scene(MobileNavRoute.TrainerCalendarVisited.route) { MobileTrainerCalendarVisitedScreen(navigator) }
}

private fun RouteBuilder.facilityNavGraph(navigator: Navigator) {
//    //Facility - Profile
//    scene(MobileNavRoute.FacilityProfile.route) { MobileFacilityProfileScreen(
//        viewMode = ProfileViewMode.OWNER,
//        goToBack = TODO(),
//        goToManageLessons = TODO(),
//        goToSettings = TODO(),
//        goToCreatePost = TODO(),
//        goToVisitCalendar = TODO(),
//        goToVisitTrainerProfile = TODO(),
//        goToPhotoGallery = TODO(),
//        goToChat = TODO(),
//        viewModel = TODO()
//    ) }
//    scene(MobileNavRoute.FacilityProfileVisited.route) { MobileFacilityProfileScreen(
//        viewMode = ProfileViewMode.VISITOR,
//        goToBack = TODO(),
//        goToManageLessons = TODO(),
//        goToSettings = TODO(),
//        goToCreatePost = TODO(),
//        goToVisitCalendar = TODO(),
//        goToVisitTrainerProfile = TODO(),
//        goToPhotoGallery = TODO(),
//        goToChat = TODO(),
//        viewModel = TODO(),
//    ) }
//    scene(MobileNavRoute.FacilityPhotoGallery.route) { MobileFacilityPhotoDiaryScreen(goToBack = navigator::popBackStack) }
//    //Facility - Calendar
//    scene(MobileNavRoute.FacilityCalendar.route) { MobileFacilityCalendarScreen(navigator) }
//    scene(MobileNavRoute.FacilityCalendarVisited.route) { MobileFacilityCalendarVisitedScreen(navigator) }
//    //Facility - Classes
//    scene(MobileNavRoute.FacilityLessons.route) {
//        MobileFacilityLessonListScreen(
//            goToBack = navigator::popBackStack,
//            goToCreateNew = { navigator.jumpAndStay(MobileNavRoute.FacilityLessonEdit) },
//            goToEdit = { navigator.jumpAndStay(MobileNavRoute.FacilityLessonEdit) }
//        )
//    }
//    scene(MobileNavRoute.FacilityLessonEdit.route) {
//        MobileFacilityEditLessonScreen(
//            goToBack = navigator::popBackStack,
//            goToLessonCreated = { navigator.jumpAndTakeover(MobileNavRoute.FacilityLessonCreated) }
//        )
//    }
//    scene(MobileNavRoute.FacilityLessonCreated.route) {
//        MobileFacilityEditLessonFeedbackScreen(
//            goToBack = navigator::popBackStack,
//            goToNewLesson = { navigator.jumpAndTakeover(MobileNavRoute.FacilityLessonEdit) },
//            goToDashboard = { navigator.jumpAndTakeover(MobileNavRoute.Dashboard) },
//        )
//    }
}