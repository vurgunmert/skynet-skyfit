package com.vurgun.skyfit.core.data

import com.vurgun.skyfit.core.network.BASE_IMAGE_URL

fun serverImageFromPath(path: String?): String =
    if (path.isNullOrBlank()) "" else BASE_IMAGE_URL + path