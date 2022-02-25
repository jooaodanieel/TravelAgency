package com.techandteach.customer.model.types

import kotlinx.serialization.Serializable

@Serializable(with = CreditCardNumberSerializer::class)
data class CreditCardNumber private constructor(private val value: List<String>) {
    companion object {
        private const val NUMBER_OF_DIGITS: Int = 16

        private fun fromCleanedString(value: String): CreditCardNumber {
            if (value.length != CreditCardNumber.NUMBER_OF_DIGITS) throw IllegalArgumentException("Credit card number must have 12 digits")

            return CreditCardNumber(value.split(""))
        }

        fun fromString(value: String?): CreditCardNumber {
            if (value == null) throw IllegalArgumentException("Credit card number must be provided")
            val digits = value.replace("[- .]".toRegex(), "")
            return fromCleanedString(digits)
        }

        fun fromLong(value: Long?): CreditCardNumber {
            return CreditCardNumber.fromCleanedString(value.toString())
        }
    }

    override fun toString(): String {
        return value.reduceIndexed { i: Int, str: String, d: String -> "$str${if (i < value.size - 1 && i % 4 == 0) "-" else ""}$d" }
    }
}
