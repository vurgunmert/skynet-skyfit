package com.vurgun.skyfit.presentation.shared.viewmodel

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.network.models.BodyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SkyFitUserAccountSettingsViewModel : ViewModel() {

    // StateFlows for individual user fields
     val _userName = MutableStateFlow<String?>(null)
     val _fullName = MutableStateFlow<String?>(null)
     val _email = MutableStateFlow<String?>(null)
     val _height = MutableStateFlow<Int?>(null)
     val _heightUnit = MutableStateFlow<String?>(null)
     val _weight = MutableStateFlow<Int?>(null)
     val _weightUnit = MutableStateFlow<String?>(null)
     val _bodyType = MutableStateFlow<BodyType?>(null)
     val _profileImageUrl = MutableStateFlow<String?>(null)
     val _backgroundImageUrl = MutableStateFlow<String?>(null)

    // Baseline data for comparison
    private var initialUserName: String? = null
    private var initialFullName: String? = null
    private var initialEmail: String? = null
    private var initialHeight: Int? = null
    private var initialWeight: Int? = null
    private var initialBodyType: BodyType? = null
    private var initialProfileImageUrl: String? = null
    private var initialBackgroundImageUrl: String? = null

    // Public StateFlows for UI consumption
    val userName: StateFlow<String?> = _userName
    val fullName: StateFlow<String?> = _fullName
    val email: StateFlow<String?> = _email
    val height: StateFlow<Int?> = _height
    val heightUnit: StateFlow<String?> = _heightUnit
    val weightUnit: StateFlow<String?> = _weightUnit
    val weight: StateFlow<Int?> = _weight
    val bodyType: StateFlow<BodyType?> = _bodyType
    val profileImageUrl: StateFlow<String?> = _profileImageUrl
    val backgroundImageUrl: StateFlow<String?> = _backgroundImageUrl

    // Derived StateFlow to check if any value has been updated
    private val _isAnyUpdated = MutableStateFlow(false)
    val isAnyUpdated: StateFlow<Boolean> = _isAnyUpdated

    fun loadData() {

    }

    // Update state when any value changes
    private fun updateIsAnyUpdated() {
        _isAnyUpdated.value = _userName.value != initialUserName ||
                _fullName.value != initialFullName ||
                _email.value != initialEmail ||
                _height.value != initialHeight ||
                _weight.value != initialWeight ||
                _bodyType.value != initialBodyType ||
                _profileImageUrl.value != initialProfileImageUrl ||
                _backgroundImageUrl.value != initialBackgroundImageUrl
    }

    // Update methods for state management
    fun updateUserName(value: String) {
        _userName.value = value
        updateIsAnyUpdated()
    }

    fun updateFullName(value: String) {
        _fullName.value = value
        updateIsAnyUpdated()
    }

    fun updateEmail(value: String) {
        _email.value = value
        updateIsAnyUpdated()
    }

    fun updateHeight(value: String) {
        _height.value = value.toIntOrNull()
        updateIsAnyUpdated()
    }

    fun updateWeight(value: String) {
        _weight.value = value.toIntOrNull()
        updateIsAnyUpdated()
    }

    fun updateBodyType(value: BodyType) {
        _bodyType.value = value
        updateIsAnyUpdated()
    }


    fun deleteAccount() {
//        TODO("Not yet implemented")
    }

}

