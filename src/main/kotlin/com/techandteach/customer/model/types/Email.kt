package com.techandteach.customer.model.types

data class Email private constructor(private val value: String) {
    companion object {
        fun fromString(value: String?): Email {
            if (value == null) throw IllegalArgumentException("email must be provided")
            if (value.isEmpty()) throw IllegalArgumentException("email cannot be empty")
            // email regex

            return Email(value)
        }
    }

    override fun toString(): String {
        return value
    }
}
