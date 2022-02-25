package com.techandteach.curatorship.model

import com.techandteach.utils.types.Name
import org.junit.Test
import java.util.*
import kotlin.test.assertTrue

class AirlineTest {

    @Test
    fun airlineCreateSetsID() {
        val airline = generateValidAirline()

        assertTrue { airline.id is UUID }
    }

    @Test
    fun airlineHasName() {
        val name = Name.fromString("latin")
        val airline = generateValidAirline( name = name )

        assertTrue { airline.name is Name }
        assertTrue { airline.name == name }
    }

    private fun generateValidAirline(
        name: Name = Name.fromString("default name")
    ): Airline {
        return Airline.create(name)
    }
}