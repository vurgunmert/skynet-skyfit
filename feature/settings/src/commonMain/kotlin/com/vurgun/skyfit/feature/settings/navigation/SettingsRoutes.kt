package com.vurgun.skyfit.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.settings.account.MobileSettingsAddAccountScreen
import com.vurgun.skyfit.feature.settings.account.MobileSettingsManageAccountsScreen
import com.vurgun.skyfit.feature.settings.changepassword.SettingsChangePasswordScreen
import com.vurgun.skyfit.feature.settings.component.rbac.RequireRole
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsAccountScreen
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsAddMemberScreen
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsAddTrainerScreen
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsEditProfileScreen
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsHomeScreen
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsManageBranchesScreen
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsManageMembersScreen
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsManageTrainersScreen
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsNotificationsScreen
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature.settings.helpsupport.MobileSettingsSupportHelpScreen
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
import com.vurgun.skyfit.ui.core.screen.UnauthorizedAccessScreen
import kotlinx.serialization.Serializable

sealed interface SettingsRoute {

    @Serializable
    data object Main: SettingsRoute

    @Serializable
    data object Account: SettingsRoute

    @Serializable
    data object EditProfile: SettingsRoute

    @Serializable
    data object ChangePassword: SettingsRoute

    @Serializable
    data object PaymentHistory: SettingsRoute

    @Serializable
    data object Notifications: SettingsRoute

    @Serializable
    data object Help: SettingsRoute

    // Optional routes for trainers or facilities
    @Serializable
    data object ManageAccounts: SettingsRoute

    @Serializable
    data object AddAccount: SettingsRoute

    @Serializable
    data object ManageMembers: SettingsRoute

    @Serializable
    data object AddMembers: SettingsRoute

    @Serializable
    data object ManageTrainers: SettingsRoute

    @Serializable
    data object AddTrainers: SettingsRoute

    @Serializable
    data object ManageBranches: SettingsRoute
}

fun NavGraphBuilder.settingsRoutes(
    navController: NavHostController,
    role: UserRole,
    goToLogin: () -> Unit
) {
    composable<SettingsRoute.Main> {
        RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
            when (role) {
                UserRole.Facility -> MobileFacilitySettingsHomeScreen(
                    goToBack = { navController.popBackStack() },
                    goToLogin = goToLogin,
                    goToAccountSettings = {
                        navController.navigate(SettingsRoute.Account)
                    },
                    goToPaymentHistory = {
                        navController.navigate(SettingsRoute.PaymentHistory)
                    },
                    goToNotifications = {
                        navController.navigate(SettingsRoute.Notifications)
                    },
                    goToManageTrainers = {
                        navController.navigate(SettingsRoute.ManageTrainers)
                    },
                    goToManageMembers = {
                        navController.navigate(SettingsRoute.ManageMembers)
                    },
                    goToManageBranches = {
                        navController.navigate(SettingsRoute.ManageBranches)
                    },
                    goToSupport = {
                        navController.navigate(SettingsRoute.Help)
                    }
                )

                UserRole.Trainer -> MobileTrainerSettingsHomeScreen(
                    goToBack = { navController.popBackStack() },
                    goToLogin = goToLogin,
                    goToAccount = {
                        navController.navigate(SettingsRoute.Account)
                    },
                    goToPaymentHistory = {
                        navController.navigate(SettingsRoute.PaymentHistory)
                    },
                    goToMembers = {
                        navController.navigate(SettingsRoute.ManageMembers)
                    },
                    goToNotifications = {
                        navController.navigate(SettingsRoute.Notifications)
                    },
                    goToHelp = {
                        navController.navigate(SettingsRoute.Help)
                    }
                )

                UserRole.User -> MobileUserSettingsHomeScreen(
                    goToBack = { navController.popBackStack() },
                    goToLogin = goToLogin,
                    goToAccount = {
                        navController.navigate(SettingsRoute.Account)
                    },
                    goToPaymentHistory = {
                        navController.navigate(SettingsRoute.PaymentHistory)
                    },
                    goToNotifications = {
                        navController.navigate(SettingsRoute.Notifications)
                    },
                    goToHelp = {
                        navController.navigate(SettingsRoute.Help)
                    }
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.Account> {
        RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
            when (role) {
                UserRole.Facility -> MobileFacilitySettingsAccountScreen(
                    goToBack = { navController.popBackStack() },
                    goToEditProfile = {
                        navController.navigate(SettingsRoute.EditProfile)
                    },
                    goToChangePassword = {
                        navController.navigate(SettingsRoute.ChangePassword)
                    },
                    goToManageAccounts = {
                        navController.navigate(SettingsRoute.ManageAccounts)
                    }
                )

                UserRole.Trainer -> MobileTrainerSettingsAccountScreen(
                    goToBack = { navController.popBackStack() },
                    goToEditProfile = {
                        navController.navigate(SettingsRoute.EditProfile)
                    },
                    goToChangePassword = {
                        navController.navigate(SettingsRoute.ChangePassword)
                    },
                    goToManageAccounts = {
                        navController.navigate(SettingsRoute.ManageAccounts)
                    }
                )

                UserRole.User -> MobileUserSettingsAccountScreen(
                    goToBack = { navController.popBackStack() },
                    goToEditProfile = {
                        navController.navigate(SettingsRoute.EditProfile)
                    },
                    goToChangePassword = {
                        navController.navigate(SettingsRoute.ChangePassword)
                    },
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.EditProfile> {
        RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
            when (role) {
                UserRole.Facility -> MobileFacilitySettingsEditProfileScreen(
                    goToBack = { navController.popBackStack() },
                )

                UserRole.Trainer -> MobileTrainerSettingsEditProfileScreen(
                    goToBack = { navController.popBackStack() },
                )

                UserRole.User -> MobileUserSettingsEditProfileScreen(
                    goToBack = { navController.popBackStack() },
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.ChangePassword> {
        RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
            when (role) {
                UserRole.Facility,
                UserRole.Trainer,
                UserRole.User -> SettingsChangePasswordScreen(
                    goToBack = { navController.popBackStack() },
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.PaymentHistory> {
        RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
            when (role) {
                UserRole.Facility -> MobileFacilitySettingsPaymentHistoryScreen(
                    goToBack = { navController.popBackStack() },
                )

                UserRole.Trainer -> MobileTrainerSettingsPaymentHistoryScreen(
                    goToBack = { navController.popBackStack() },
                )

                UserRole.User -> MobileUserSettingsPaymentHistoryScreen(
                    goToBack = { navController.popBackStack() },
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.Notifications> {
        RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
            when (role) {
                UserRole.Facility -> MobileFacilitySettingsNotificationsScreen(
                    goToBack = { navController.popBackStack() },
                )

                UserRole.Trainer -> MobileTrainerSettingsNotificationsScreen(
                    goToBack = { navController.popBackStack() },
                )

                UserRole.User -> MobileUserSettingsNotificationsScreen(
                    goToBack = { navController.popBackStack() },
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.Help> {
        RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
            MobileSettingsSupportHelpScreen(
                goToBack = { navController.popBackStack() },
            )
        }
    }

    composable<SettingsRoute.ManageAccounts> {
        RequireRole(role, listOf(UserRole.Trainer, UserRole.Facility)) {
            when (role) {
                UserRole.Facility,
                UserRole.Trainer -> MobileSettingsManageAccountsScreen(
                    goToBack = { navController.popBackStack() },
                    goToAddAccount = {
                        navController.navigate(SettingsRoute.AddAccount)
                    }
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.AddAccount> {
        RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
            when (role) {
                UserRole.User,
                UserRole.Trainer,
                UserRole.Facility -> MobileSettingsAddAccountScreen(
                    goToBack = { navController.popBackStack() },
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.ManageMembers> {
        RequireRole(role, listOf(UserRole.Trainer, UserRole.Facility)) {
            when (role) {
                UserRole.Facility -> MobileFacilitySettingsManageMembersScreen(
                    goToBack = { navController.popBackStack() },
                    goToAddMember = {
                        navController.navigate(SettingsRoute.AddMembers)
                    }
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.AddMembers> {
        RequireRole(role, listOf(UserRole.Facility)) {
            when (role) {
                UserRole.Facility -> MobileFacilitySettingsAddMemberScreen(
                    goToBack = { navController.popBackStack() },
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.ManageTrainers> {
        RequireRole(role, listOf(UserRole.Facility)) {
            when (role) {
                UserRole.Facility -> MobileFacilitySettingsManageTrainersScreen(
                    goToBack = { navController.popBackStack() },
                    goToAddTrainer = {
                        navController.navigate(SettingsRoute.AddTrainers)
                    }
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.AddTrainers> {
        RequireRole(role, listOf(UserRole.Facility)) {
            when (role) {
                UserRole.Facility -> MobileFacilitySettingsAddTrainerScreen(
                    goToBack = { navController.popBackStack() },
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }

    composable<SettingsRoute.ManageBranches> {
        RequireRole(role, listOf(UserRole.Facility)) {
            when (role) {
                UserRole.Facility -> MobileFacilitySettingsManageBranchesScreen(
                    goToBack = { navController.popBackStack() },
                )

                else -> UnauthorizedAccessScreen()
            }
        }
    }
}
