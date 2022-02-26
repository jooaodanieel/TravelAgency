package com.techandteach.curatorship.model

import com.techandteach.utils.types.Name
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.*

class AirlineTest {

    @Test
    fun airlineCreateSetsID() {
        val airline = generateValidAirline()


        assertTrue(airline.id is UUID)
    }

    @Test
    fun airlineHasName() {
        val name = Name.fromString("latin")
        val airline = generateValidAirline( name = name )

        assertTrue(airline.name is Name)
        assertTrue(airline.name == name)
    }

    @Test
    fun airlineSupportsHydration() {
        val existingId = UUID.randomUUID()
        val hydratedAirline = Airline.hydrate(existingId, Name.fromString("existing name"))

        assertEquals(existingId, hydratedAirline.id)
    }

    private fun generateValidAirline(
        name: Name = Name.fromString("default name")
    ): Airline {
        return Airline.create(name)
    }
}