package com.vurgun.skyfit.core.data.v1.data.facility.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject

object FacilityMemberPackageNullableDeserializer :
    KSerializer<FacilityMemberPackageDTO?> {

    override val descriptor: SerialDescriptor =
        FacilityMemberPackageDTO.serializer().descriptor

    override fun deserialize(decoder: Decoder): FacilityMemberPackageDTO? {
        val input = decoder as? JsonDecoder
            ?: throw SerializationException("Expected JsonDecoder")

        val element = input.decodeJsonElement()

        // Return null if empty object
        if (element is JsonObject && element.isEmpty()) {
            return null
        }

        return input.json.decodeFromJsonElement(FacilityMemberPackageDTO.serializer(), element)
    }

    override fun serialize(encoder: Encoder, value: FacilityMemberPackageDTO?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            encoder.encodeSerializableValue(FacilityMemberPackageDTO.serializer(), value)
        }
    }
}

@Serializable
data class FacilityMemberDTO(
    val userId: Int,
    @SerialName("profilePhoto")
    val profilePhotoPath: String? = null,
    val username: String,
    val name: String,
    val surname: String,
    val status: Int,
    val statusName: String,
    @Serializable(with = FacilityMemberPackageNullableDeserializer::class)
    val membershipPackage: FacilityMemberPackageDTO? = null,
    val usedLessonCount: Int? = null
)

@Serializable
data class AddGymMemberRequest(val gymId: Int, val userId: Int)

@Serializable
data class GetGymMembersRequest(val gymId: Int)

@Serializable
data class GetPlatformMembersRequest(val gymId: Int)

@Serializable
data class DeleteGymMemberRequest(val gymId: Int, val userId: Int)


@Serializable
data class AddGymTrainerRequest(val gymId: Int, val userId: Int)

@Serializable
data class GetGymTrainersRequest(val gymId: Int)

@Serializable
data class DeleteGymTrainerRequest(val gymId: Int, val userId: Int)

@Serializable
data class GetPlatformTrainersRequest(val gymId: Int)

@Serializable
data class CreatePackageDTO(
    val gymId: Int,
    val title: String,
    val contents: List<Int>,
    val description: String,
    val lessonCount: Int,
    val duration: Int,
    val price: Float
)

@Serializable
data class AddPackageToMember(
    val userId: Int,
    val packageId: Int,
    val startDate: String, //"2025-06-19"
    val endDate: String? = null, //"2025-06-19"
    val lessonCount: Int? = null,
)

@Serializable
data class DeleteMemberPackage(
    val userId: Int,
    val packageId: Int
)

@Serializable
data class UpdateMemberPackage(
    val userId: Int,
    val gymId: Int,
    val packageId: Int)