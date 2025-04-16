package com.vurgun.skyfit.feature.home.screen

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.feature.home.component.HomeAppointmentItemViewData
import kotlinx.coroutines.flow.map

class UserHomeViewModel(
    private val userManager: UserManager
) : ViewModel() {

    val user = userManager.user
    val characterType = user.map { it?.characterType }

    val appointments: List<HomeAppointmentItemViewData> = emptyList()
}