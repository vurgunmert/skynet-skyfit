package com.vurgun.skyfit.core.ui.model

data class ServicePackageUiData(
    val id: Int,
    val title: String,
    val courseList: List<String>,
    val memberCount: Int,
    val duration: Int,
    val price: Float,
    val onMembersClick: () -> Unit,
    val onEditClick: () -> Unit,
    val onDeleteClick: () -> Unit
)