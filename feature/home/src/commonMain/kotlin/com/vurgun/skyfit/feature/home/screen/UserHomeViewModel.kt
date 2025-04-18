package com.vurgun.skyfit.feature.home.screen

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.feature.home.component.HomeAppointmentItemViewData
import kotlinx.coroutines.flow.map

class UserHomeViewModel(
    private val userManager: UserManager
) : ViewModel() {

    private val user: UserDetail
        get() = userManager.user.value as? UserDetail
            ?: error("❌ current account is not user")

    val characterType = user.characterType

    val appointments: List<HomeAppointmentItemViewData> = emptyList()
}