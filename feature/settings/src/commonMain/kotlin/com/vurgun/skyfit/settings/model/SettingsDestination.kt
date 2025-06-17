package com.vurgun.skyfit.settings.model

sealed class SettingsDestination(val route: String) {
    data object Account : SettingsDestination("account")
    data object Notifications : SettingsDestination("notifications")
    data object Payment : SettingsDestination("payment")
    data object Support : SettingsDestination("support")
    data object Members : SettingsDestination("members")
    data object Trainers : SettingsDestination("trainers")
    data object Branches : SettingsDestination("branches")
    data object LessonPackages : SettingsDestination("lesson_packages")
}
