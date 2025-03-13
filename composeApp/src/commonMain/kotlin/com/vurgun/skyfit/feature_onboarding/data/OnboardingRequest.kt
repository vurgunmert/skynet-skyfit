package com.vurgun.skyfit.feature_onboarding.data

class OnboardingRequest(
    val userType: Int,
    val characterId: Int? = null,
    val birthdate: String? = null,
    val gender: Int? = null,
    val weight: Int? = null,
    val weightUnit: Int? = null,
    val height: Int? = null,
    val heightUnit: Int? = null,
    val bodyTypeId: Int? = null,
    val name: String? = null,
    val surname: String? = null,
    val gymName: String? = null,
    val gymAddress: String? = null,
    val bio: String? = null,
    val backgroundImage: ByteArray? = null,
    val profileTags: List<Int>? = null,
    val goals: List<Int>? = null
)