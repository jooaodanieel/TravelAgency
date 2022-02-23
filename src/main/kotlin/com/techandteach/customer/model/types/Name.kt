package com.techandteach.customer.model.types

data class Name private constructor(private val value: String) {
    companion object {
        fun fromString(value: String?): Name {
            if (value == null) throw IllegalArgumentException("name must be provided")
            if (value.isEmpty()) throw IllegalArgumentException("name cannot be empty")

            return Name(value)
        }
    }

    override fun toString(): String {
        return value
    }
}