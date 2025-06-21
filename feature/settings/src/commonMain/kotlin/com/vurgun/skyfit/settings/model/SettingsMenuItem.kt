package com.vurgun.skyfit.settings.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import skyfit.core.ui.generated.resources.*

sealed class SettingsMenuItem(
    val iconRes: DrawableResource,
    val titleRes: StringResource,
    val destination: SettingsDestination,
) {
    data object Account : SettingsMenuItem(
        iconRes = Res.drawable.ic_profile,
        titleRes = Res.string.settings_account,
        destination = SettingsDestination.Account
    )

    data object Notifications : SettingsMenuItem(
        iconRes = Res.drawable.ic_bell,
        titleRes = Res.string.settings_notifications,
        destination = SettingsDestination.Notifications
    )

    data object Payment : SettingsMenuItem(
        iconRes = Res.drawable.ic_credit_card,
        titleRes = Res.string.settings_payment,
        destination = SettingsDestination.Payment
    )

    data object Support : SettingsMenuItem(
        iconRes = Res.drawable.ic_question_circle,
        titleRes = Res.string.settings_support,
        destination = SettingsDestination.Support
    )

    // Facility-only
    data object Members : SettingsMenuItem(
        iconRes = Res.drawable.ic_posture_fill,
        titleRes = Res.string.settings_members,
        destination = SettingsDestination.Members
    )

    data object Trainers : SettingsMenuItem(
        Res.drawable.ic_athletic_performance,
        Res.string.settings_trainers,
        SettingsDestination.Trainers
    )

    data object Branches : SettingsMenuItem(
        iconRes = Res.drawable.ic_building,
        titleRes = Res.string.settings_branches,
        destination = SettingsDestination.Branches
    )

    data object LessonPackages : SettingsMenuItem(
        Res.drawable.ic_package,
        Res.string.settings_lesson_packages,
        SettingsDestination.LessonPackages
    )
}

