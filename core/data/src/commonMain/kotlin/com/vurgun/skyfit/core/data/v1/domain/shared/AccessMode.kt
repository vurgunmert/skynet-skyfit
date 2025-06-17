package com.vurgun.skyfit.core.data.v1.domain.shared

sealed interface AccessMode {
    data object Owner : AccessMode
    data object Visitor : AccessMode
}