package com.vurgun.skyfit.feature.settings.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.settings.account.MobileSettingsManageAccountsScreen
import com.vurgun.skyfit.feature.settings.changepassword.SettingsChangePasswordScreen
import com.vurgun.skyfit.ui.core.utils.RequireRole
import com.vurgun.skyfit.feature.settings.facility.MobileFacilitySettingsHomeScreen
import com.vurgun.skyfit.feature.settings.facility.branch.MobileFacilitySettingsManageBranchesScreen
import com.vurgun.skyfit.feature.settings.facility.member.MobileFacilityAddMemberScreen
import com.vurgun.skyfit.feature.settings.facility.member.MobileFacilityManageMembersScreen
import com.vurgun.skyfit.feature.settings.facility.notification.MobileFacilitySettingsNotificationsScreen
import com.vurgun.skyfit.feature.settings.facility.payment.MobileFacilitySettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature.settings.facility.account.MobileFacilitySettingsAccountScreen
import com.vurgun.skyfit.feature.settings.facility.profile.MobileFacilitySettingsEditProfileScreen
import com.vurgun.skyfit.feature.settings.facility.trainer.MobileFacilityAddTrainerScreen
import com.vurgun.skyfit.feature.settings.facility.trainer.MobileFacilityManageTrainersScreen
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
import org.koin.compose.koinInject

sealed interface SettingsRoute {
    val route: String

    @Serializable
    data object Main : SettingsRoute {
        override val route: String = "settings/main"
    }

    @Serializable
    data object Account : SettingsRoute {
        override val route: String = "settings/account"
    }

    @Serializable
    data object EditProfile : SettingsRoute {
        override val route: String = "settings/edit_profile"
    }

    @Serializable
    data object ChangePassword : SettingsRoute {
        override val route: String = "settings/change_password"
    }

    @Serializable
    data object PaymentHistory : SettingsRoute {
        override val route: String = "settings/payment_history"
    }

    @Serializable
    data object Notifications : SettingsRoute {
        override val route: String = "settings/notifications"
    }

    @Serializable
    data object Help : SettingsRoute {
        override val route: String = "settings/help"
    }

    @Serializable
    data object ManageAccounts : SettingsRoute {
        override val route: String = "settings/manage_accounts"
    }

    @Serializable
    data object AddAccount : SettingsRoute {
        override val route: String = "settings/add_account"
    }

    @Serializable
    data object ManageMembers : SettingsRoute {
        override val route: String = "settings/manage_members"
    }

    @Serializable
    data object AddMembers : SettingsRoute {
        override val route: String = "settings/add_members"
    }

    @Serializable
    data object ManageTrainers : SettingsRoute {
        override val route: String = "settings/manage_trainers"
    }

    @Serializable
    data object AddTrainers : SettingsRoute {
        override val route: String = "settings/add_trainers"
    }

    @Serializable
    data object ManageBranches : SettingsRoute {
        override val route: String = "settings/manage_branches"
    }
}

fun NavGraphBuilder.settingsRoutes(
    onExitSettings: () -> Unit,
    goToLogin: () -> Unit,
    goToAddAccount: () -> Unit
) {
    composable<SettingsRoute.Main> {
        SettingsRootGraph(
            onExitSettings = onExitSettings,
            goToLogin = goToLogin,
            goToAddAccount = goToAddAccount
        )
    }
}

@Composable
private fun SettingsRootGraph(
    onExitSettings: () -> Unit,
    goToLogin: () -> Unit,
    goToAddAccount: () -> Unit
) {
    val settingsNavController = rememberNavController()

    val userManager: UserManager = koinInject()
    val role by userManager.userRole.collectAsState(UserRole.Guest)
    val previousRole = remember { mutableStateOf(role) }

    LaunchedEffect(role) {
        if (previousRole.value != role) {
            settingsNavController.popBackStack(SettingsRoute.Main, inclusive = false)
            previousRole.value = role
        }
    }

    NavHost(
        navController = settingsNavController,
        startDestination = SettingsRoute.Main
    ) {
        composable<SettingsRoute.Main> {
            RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
                when (role) {
                    UserRole.Facility -> MobileFacilitySettingsHomeScreen(
                        goToBack = onExitSettings,
                        goToLogin = goToLogin,
                        goToAccountSettings = {
                            settingsNavController.navigate(SettingsRoute.Account)
                        },
                        goToPaymentHistory = {
                            settingsNavController.navigate(SettingsRoute.PaymentHistory)
                        },
                        goToNotifications = {
                            settingsNavController.navigate(SettingsRoute.Notifications)
                        },
                        goToManageTrainers = {
                            settingsNavController.navigate(SettingsRoute.ManageTrainers)
                        },
                        goToManageMembers = {
                            settingsNavController.navigate(SettingsRoute.ManageMembers)
                        },
                        goToManageBranches = {
                            settingsNavController.navigate(SettingsRoute.ManageBranches)
                        },
                        goToSupport = {
                            settingsNavController.navigate(SettingsRoute.Help)
                        }
                    )

                    UserRole.Trainer -> MobileTrainerSettingsHomeScreen(
                        goToBack = onExitSettings,
                        goToLogin = goToLogin,
                        goToAccount = {
                            settingsNavController.navigate(SettingsRoute.Account)
                        },
                        goToPaymentHistory = {
                            settingsNavController.navigate(SettingsRoute.PaymentHistory)
                        },
                        goToMembers = {
                            settingsNavController.navigate(SettingsRoute.ManageMembers)
                        },
                        goToNotifications = {
                            settingsNavController.navigate(SettingsRoute.Notifications)
                        },
                        goToHelp = {
                            settingsNavController.navigate(SettingsRoute.Help)
                        }
                    )

                    UserRole.User -> MobileUserSettingsHomeScreen(
                        goToBack = onExitSettings,
                        goToLogin = goToLogin,
                        goToAccount = {
                            settingsNavController.navigate(SettingsRoute.Account)
                        },
                        goToPaymentHistory = {
                            settingsNavController.navigate(SettingsRoute.PaymentHistory)
                        },
                        goToNotifications = {
                            settingsNavController.navigate(SettingsRoute.Notifications)
                        },
                        goToHelp = {
                            settingsNavController.navigate(SettingsRoute.Help)
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
                        goToBack = { settingsNavController.popBackStack() },
                        goToEditProfile = {
                            settingsNavController.navigate(SettingsRoute.EditProfile)
                        },
                        goToChangePassword = {
                            settingsNavController.navigate(SettingsRoute.ChangePassword)
                        },
                        goToAddAccount = goToAddAccount,
                        goToManageAccounts = {
                            settingsNavController.navigate(SettingsRoute.ManageAccounts)
                        }
                    )

                    UserRole.Trainer -> MobileTrainerSettingsAccountScreen(
                        goToBack = { settingsNavController.popBackStack() },
                        goToEditProfile = {
                            settingsNavController.navigate(SettingsRoute.EditProfile)
                        },
                        goToChangePassword = {
                            settingsNavController.navigate(SettingsRoute.ChangePassword)
                        },
                        goToAddAccount = goToAddAccount,
                        goToManageAccounts = {
                            settingsNavController.navigate(SettingsRoute.ManageAccounts)
                        }
                    )

                    UserRole.User -> MobileUserSettingsAccountScreen(
                        goToBack = { settingsNavController.popBackStack() },
                        goToEditProfile = {
                            settingsNavController.navigate(SettingsRoute.EditProfile)
                        },
                        goToChangePassword = {
                            settingsNavController.navigate(SettingsRoute.ChangePassword)
                        },
                        goToAddAccount = goToAddAccount,
                        goToManageAccounts = {
                            settingsNavController.navigate(SettingsRoute.ManageAccounts)
                        }
                    )

                    else -> UnauthorizedAccessScreen()
                }
            }
        }

        composable<SettingsRoute.EditProfile> {
            RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
                when (role) {
                    UserRole.Facility -> MobileFacilitySettingsEditProfileScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    UserRole.Trainer -> MobileTrainerSettingsEditProfileScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    UserRole.User -> MobileUserSettingsEditProfileScreen(
                        goToBack = { settingsNavController.popBackStack() },
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
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    else -> UnauthorizedAccessScreen()
                }
            }
        }

        composable<SettingsRoute.PaymentHistory> {
            RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
                when (role) {
                    UserRole.Facility -> MobileFacilitySettingsPaymentHistoryScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    UserRole.Trainer -> MobileTrainerSettingsPaymentHistoryScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    UserRole.User -> MobileUserSettingsPaymentHistoryScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    else -> UnauthorizedAccessScreen()
                }
            }
        }

        composable<SettingsRoute.Notifications> {
            RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
                when (role) {
                    UserRole.Facility -> MobileFacilitySettingsNotificationsScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    UserRole.Trainer -> MobileTrainerSettingsNotificationsScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    UserRole.User -> MobileUserSettingsNotificationsScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    else -> UnauthorizedAccessScreen()
                }
            }
        }

        composable<SettingsRoute.Help> {
            RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
                MobileSettingsSupportHelpScreen(
                    goToBack = { settingsNavController.popBackStack() },
                )
            }
        }

        composable<SettingsRoute.ManageAccounts> {
            RequireRole(role, listOf(UserRole.User, UserRole.Trainer, UserRole.Facility)) {
                when (role) {
                    UserRole.User,
                    UserRole.Facility,
                    UserRole.Trainer -> MobileSettingsManageAccountsScreen(
                        goToBack = { settingsNavController.popBackStack() },
                        goToAddAccount = goToAddAccount,
                    )

                    else -> UnauthorizedAccessScreen()
                }
            }
        }

        composable<SettingsRoute.ManageMembers> {
            RequireRole(role, listOf(UserRole.Trainer, UserRole.Facility)) {
                when (role) {
                    UserRole.Facility -> MobileFacilityManageMembersScreen(
                        goToBack = { settingsNavController.popBackStack() },
                        goToAddMember = {
                            settingsNavController.navigate(SettingsRoute.AddMembers)
                        }
                    )

                    else -> UnauthorizedAccessScreen()
                }
            }
        }

        composable<SettingsRoute.AddMembers> {
            RequireRole(role, listOf(UserRole.Facility)) {
                when (role) {
                    UserRole.Facility -> MobileFacilityAddMemberScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    else -> UnauthorizedAccessScreen()
                }
            }
        }

        composable<SettingsRoute.ManageTrainers> {
            RequireRole(role, listOf(UserRole.Facility)) {
                when (role) {
                    UserRole.Facility -> MobileFacilityManageTrainersScreen(
                        goToBack = { settingsNavController.popBackStack() },
                        goToAddTrainer = {
                            settingsNavController.navigate(SettingsRoute.AddTrainers)
                        }
                    )

                    else -> UnauthorizedAccessScreen()
                }
            }
        }

        composable<SettingsRoute.AddTrainers> {
            RequireRole(role, listOf(UserRole.Facility)) {
                when (role) {
                    UserRole.Facility -> MobileFacilityAddTrainerScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    else -> UnauthorizedAccessScreen()
                }
            }
        }

        composable<SettingsRoute.ManageBranches> {
            RequireRole(role, listOf(UserRole.Facility)) {
                when (role) {
                    UserRole.Facility -> MobileFacilitySettingsManageBranchesScreen(
                        goToBack = { settingsNavController.popBackStack() },
                    )

                    else -> UnauthorizedAccessScreen()
                }
            }
        }
    }
}
