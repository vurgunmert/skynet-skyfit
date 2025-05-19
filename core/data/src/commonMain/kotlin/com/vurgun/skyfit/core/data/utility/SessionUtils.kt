package com.vurgun.skyfit.core.data.utility

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
var appSessionId = Uuid.random().toString()
    private set


@OptIn(ExperimentalUuidApi::class)
fun randomUUID(): String {
    return  Uuid.random().toString()
}