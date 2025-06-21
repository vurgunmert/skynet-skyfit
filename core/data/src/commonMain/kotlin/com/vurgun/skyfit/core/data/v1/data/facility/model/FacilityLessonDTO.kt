package com.vurgun.skyfit.core.data.v1.data.facility.model

import kotlinx.serialization.Serializable

@Serializable
internal data class GetFacilityLessonsRequestDTO(
    val gymId: Int,
    val startFilterDate: String? = null, // datetime türünde gönderilecek
    val endFilterDate: String? = null
)

@Serializable
internal data class GetUpcomingFacilityLessonsRequestDTO(
    val gymId: Int,
    val limit: Int
)

@Serializable
internal data class CreateLessonRequestDTO(
    val gymId: Int,
    val iconId: Int,
    val title: String,
    val trainerNote: String?,
    val trainerId: Int,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String,
    val repetitionType: Int, // 1 - her gün tekrar eden, 2- Haftanın belirli günlerinde tekrar eden, 3- Ayda Bir Kez, Belirli Bir Günde Tekrar Eden, 4- Tekrar Etmeyen (Tek Seferlik)
    val repetition: List<Int>, //* 1 pazar, 2, pazartesi 3 salı, 4 çarşamba, 5 perşembe,6 cuma, 7 cumartesi
    val quota: Int,  // kontenjan
    val lastCancelTime: Int, // 24 saat
    val isRequiredAppointment: Boolean, // zorunlu randevu alımı
    val price: Int, // fiyat yok ise 0 gönder
    val participantType: Int, //1 herkes, 2 üyeler, 3 takipçiler
    val categories: List<Int>
)

@Serializable
internal data class UpdateLessonRequest(
    val lessonId: Int,
    val iconId: Int,
    val trainerNote: String?,
    val trainerId: Int,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String,
    val quota: Int,
    val lastCancelTime: Int,
    val isRequiredAppointment: Boolean,
    val price: Int,
    val participantType: Int,
    val participants: List<Int>,
)

@Serializable
internal data class DeleteLessonRequest(
    val lessonId: Int
)

@Serializable
internal data class ActivateLessonRequest(
    val lessonId: Int
)

@Serializable
internal data class DeactivateLessonRequest(
    val lessonId: Int
)

