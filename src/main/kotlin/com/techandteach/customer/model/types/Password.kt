package com.techandteach.customer.model.types

@kotlinx.serialization.Serializable
class Password private constructor(val value: String) {
    companion object {
        fun fromString(value: String?): Password {
            if (value == null) throw IllegalArgumentException("password must be provided")
            if (value.length !in 6..20) throw IllegalArgumentException("password length must be between 6 and 20")

            return Password(value)
        }
    }

    private fun hashed(): String {
        return "=.=$value=.="
    }

    override fun toString(): String {
        return hashed()
    }
}