package com.vurgun.skyfit.feature.dashboard.screen

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.user.repository.UserManager

class DashboardViewModel(userManager: UserManager) : ViewModel() {

    val userRole = userManager.userRole
}