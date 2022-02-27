package com.techandteach.curatorship.application

import com.techandteach.curatorship.infrastructure.AirlineDAO
import com.techandteach.curatorship.model.Airline
import com.techandteach.curatorship.model.AirlineRepository
import com.techandteach.utils.types.Name
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class AirlineRepositoryImplTest {

    @MockK
    lateinit var airlineDAO: AirlineDAO

    private lateinit var underTest: AirlineRepository

    @BeforeAll
    fun setUpSuite() {
        underTest = AirlineRepositoryImpl(airlineDAO)
    }

    @Test
    fun findByIdReturnsAirlineWhenMatch() {
        // given
        val mockedId = UUID.randomUUID()
        val mockedName = Name.fromString("whatever")
        every { airlineDAO.find(any()) } returns Airline.hydrate(mockedId, mockedName)

        // when
        val airline = underTest.findById(mockedId)

        // then
        assertNotNull(airline)
        assertEquals(airline!!.id, mockedId)
    }

    @Test
    fun findByIdReturnsNullWhenNoMatch() {
        // given
        every { airlineDAO.find(any()) } returns null

        // when
        val airline = underTest.findById(UUID.randomUUID())

        // then
        assertNull(airline)
    }

    @Test
    fun removeByIdReturnsAirlineWhenMatch() {
        // given
        val id = UUID.randomUUID()
        val mockAirline = Airline.hydrate(id, Name.fromString("random name"))
        every { airlineDAO.delete(any()) } returns mockAirline

        // when
        val removedAirline = underTest.removeById(id)

        // then
        assertTrue(removedAirline is Airline)
        assertEquals(mockAirline, removedAirline)
    }

    @Test
    fun removeByIdReturnsNullWhenNoMatch() {
        // given
        every { airlineDAO.delete(any()) } returns null

        // when
        val removedAirline = underTest.removeById(UUID.randomUUID())

        // then
        assertNull(removedAirline)
    }

    @Test
    fun removeReturnsAirlineWhenMatch() {
        // given
        val id = UUID.randomUUID()
        val mockAirline = Airline.hydrate(id, Name.fromString("random name"))
        every { airlineDAO.delete(any()) } returns mockAirline

        // when
        val removedAirline = underTest.remove(mockAirline)

        // then
        assertTrue(removedAirline is Airline)
        assertEquals(mockAirline, removedAirline)
    }

    @Test
    fun removeReturnsNullWhenNoMatch() {
        // given
        val mockAirline = Airline.hydrate(UUID.randomUUID(), Name.fromString("whatever"))
        every { airlineDAO.delete(any()) } returns null

        // when
        val removedAirline = underTest.remove(mockAirline)

        // then
        assertNull(removedAirline)
    }

    @Test
    fun addThrowsIllegalArgumentWhenConstraintViolation() {
        // given
        val newAirline = Airline.create(Name.fromString("When does it matter?"))
        every { airlineDAO.upsert(any()) } throws IllegalArgumentException("violation of constraint")

        // when & then
        assertThrows<java.lang.IllegalArgumentException> { underTest.add(newAirline) }
    }

    @Test
    fun addReturnsAirlineWhenNoConstraintViolation() {
        // given
        val newAirline = Airline.create(Name.fromString("airline airlines"))
        every { airlineDAO.upsert(any()) } returns newAirline

        // when
        val addedAirline = underTest.add(newAirline)

        // then
        assertEquals(addedAirline, newAirline)
    }
}