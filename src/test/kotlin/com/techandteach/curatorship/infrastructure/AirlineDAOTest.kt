package com.techandteach.curatorship.infrastructure

import com.techandteach.curatorship.model.Airline
import com.techandteach.framework.database.databaseConnection
import com.techandteach.framework.database.tables.Airlines
import com.techandteach.utils.types.Name
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.ktorm.database.Database
import org.ktorm.dsl.*
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AirlineDAOTest {

    private val existingId: UUID = UUID.randomUUID()
    private val existingName: Name = Name.fromString("Airline name")

    private val existingIdTwo: UUID = UUID.randomUUID()
    private val existingNameTwo: Name = Name.fromString("Airline other name")

    private lateinit var db: Database
    private lateinit var underTest: AirlineDAO


    @Test
    fun upsertReturnsTheAirline() {
        val airline = Airline.create(Name.fromString("random non-existent name"))
        val returnedAirline = underTest.upsert(airline)

        assertEquals(airline, returnedAirline)
    }

    @Test
    fun upsertInsertsWhenNoSuchIdFound() {
        val initialCount = countStoredAirlines()

        val newAirline = Airline.create(Name.fromString("valid name"))

        underTest.upsert(newAirline)

        val finalCount = countStoredAirlines()

        assertEquals(initialCount + 1, finalCount)
    }

    @Test
    fun upsertUpdatesWhenIdExists() {
        val initialCount = countStoredAirlines()

        val hydratedAirline = Airline.hydrate(existingId, Name.fromString("any name shall do"))

        underTest.upsert(hydratedAirline)

        val finalCount = countStoredAirlines()

        assertEquals(initialCount, finalCount)
    }

    private fun countStoredAirlines(): Int {
        val query = db.from(Airlines).select(Airlines.id)
        return query.map { it[Airlines.id].toString() }.size
    }

    @Test
    fun upsertThrowsIllegalArgumentWhenInsertingWithConstraintConflicts() {
        val duppedNameNewAirline = Airline.create(existingName)
        assertThrows<java.lang.IllegalArgumentException> { underTest.upsert(duppedNameNewAirline) }
    }

    @Test
    fun upsertThrowsIllegalArgumentWhenUpdatingWithConstraintConflicts() {
        val duppedNameHydratedAirline = Airline.hydrate(existingIdTwo, existingName)
        assertThrows<java.lang.IllegalArgumentException> { underTest.upsert(duppedNameHydratedAirline) }
    }

    @Test
    fun findReturnsAirlineWhenMatch() {
        val airline = underTest.find(existingId)

        assertTrue(airline is Airline)
    }

    @Test
    fun findReturnsNullWhenNoMatch() {
        val airline = underTest.find(UUID.randomUUID())

        assertNull(airline)
    }

    @Test
    fun deleteReturnsNullWhenNoMatch() {
        val result = underTest.delete(UUID.randomUUID())

        assertNull(result)
    }

    @Test
    fun deleteReturnsAirlineWhenMatch() {
        val result = underTest.delete(existingId)

        assertTrue(result is Airline)
    }

    @BeforeAll
    internal fun setUpSuite() {
        db = databaseConnection("travelagencytest")
        underTest = AirlineDAO(db)
    }

    @AfterAll
    internal fun tearDownSuite() {
        db.useConnection { it.close() }
    }

    @BeforeEach
    internal fun setUp() {
        db.insert(Airlines) {
            set(it.id, existingId.toString())
            set(it.name, existingName.toString())
        }

        db.insert(Airlines) {
            set(it.id, existingIdTwo.toString())
            set(it.name, existingNameTwo.toString())
        }
    }

    @AfterEach
    internal fun tearDown() {
        val result = db.from(Airlines).select(Airlines.id)
        for (row in result) {
            val id = row[Airlines.id]!!
            db.delete(Airlines) { it.id eq  id }
        }
    }
}