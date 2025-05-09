package com.vurgun.skyfit.feature.dashboard.dashboard

import cafe.adriel.voyager.core.model.ScreenModel
import com.vurgun.skyfit.core.data.domain.repository.UserManager

class DashboardViewModel(userManager: UserManager) : ScreenModel {

    val userRole = userManager.userRole
}