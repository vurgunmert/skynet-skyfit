package com.vurgun.skyfit.core.data

import com.vurgun.skyfit.core.network.BASE_IMAGE_URL

class CoreDataProvider(

) {
    private val globalApiService = Any()
    private val accountApiService = Any()
    private val profileApiService = Any()
    private val lessonApiService = Any()
    private val appointmentApiService = Any()
    private val facilityApiService = Any()
    private val socialApiService = Any()
    private val favoritesApiService = Any()
    private val chatbotApiService = Any()
    private val postureApiService = Any()
    private val notificationApiService = Any()
    private val exploreApiService = Any()
    private val nutritionApiService = Any()
    private val exerciseApiService = Any()
}

fun serverImageFromPath(path: String?): String =
    if (path.isNullOrBlank()) "" else BASE_IMAGE_URL + path