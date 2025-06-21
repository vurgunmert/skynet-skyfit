package com.vurgun.skyfit.core.data.v1.data.support.model

data class SupportRequestDTO(val typeId: Int, val email: String, val description: String, val file: ByteArray? = null) {
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SupportRequestDTO

        if (typeId != other.typeId) return false
        if (email != other.email) return false
        if (description != other.description) return false
        if (!file.contentEquals(other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = typeId.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (file?.contentHashCode() ?: 0)
        return result
    }
}