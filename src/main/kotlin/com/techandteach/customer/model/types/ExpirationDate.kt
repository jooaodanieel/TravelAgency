package com.techandteach.customer.model.types

import java.text.DecimalFormat
import java.time.LocalDate

import kotlinx.serialization.Serializable

@Serializable(with = ExpirationDateSerializer::class)
data class ExpirationDate(
    private val month: Int,
    private val year: Int
) {
    companion object {
        fun fromString(value: String?): ExpirationDate {
            if (value == null) throw IllegalArgumentException("Expiration date must be provided")
            val rgx = """^(\d{2})/(\d{2})$""".toRegex()
            val matched = rgx.find(value) ?: throw IllegalArgumentException("Expiration date must follow the format mm/yy")
            val month = matched.groupValues?.get(1).toInt()
            val year = matched.groupValues?.get(2).toInt()

            if ((month !in 1 until 13) || year < LocalDate.now().year % 100) throw IllegalArgumentException("Expiration date must be a valid pair month/year")

            return ExpirationDate(month, year)
        }
    }

    override fun toString(): String {
        val format = DecimalFormat("00")
        val monthString = format.format(month)
        val yearString = format.format(year)
        return "$monthString/$yearString"
    }
}