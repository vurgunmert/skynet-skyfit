package com.vurgun.skyfit.core.data.persona.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SelectUserTypeRequest(val usertypeId: Int)

@Serializable
data class SelectUserTypeResponse(val token: String)

