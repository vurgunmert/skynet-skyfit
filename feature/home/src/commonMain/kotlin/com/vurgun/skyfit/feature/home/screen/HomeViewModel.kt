package com.vurgun.skyfit.feature.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.manager.UserManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val userManager: UserManager
) : ViewModel() {

    val user = userManager.user
    val characterType = user.map { it?.characterType }
}