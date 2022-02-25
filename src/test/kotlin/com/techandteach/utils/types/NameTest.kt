package com.techandteach.utils.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

internal class NameTest {
    @Test
    fun cannotCreateFromNullString() {
        assertFails { Name.fromString(null) }
    }

    @Test
    fun throwsIllegalArgumentWhenNullProvided() {
        assertFailsWith(
            exceptionClass = IllegalArgumentException::class
        ) { Name.fromString(null) }
    }

    @Test
    fun cannotCreateFromEmptyString() {
        assertFails { Name.fromString("") }
    }

    @Test
    fun throwsIllegalArgumentWhenEmptyProvided() {
        assertFailsWith(
            exceptionClass = IllegalArgumentException::class
        ) { Name.fromString("") }
    }

    @Test
    fun correctlyParsesBackToString() {
        val raw = "not an empty string"
        val name = Name.fromString(raw)

        assertEquals(name.toString(), raw)
    }
}