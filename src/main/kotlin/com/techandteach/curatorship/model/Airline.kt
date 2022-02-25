package com.techandteach.curatorship.model

import com.techandteach.utils.types.Name
import java.util.*

class Airline private constructor(val name: Name) {
    companion object {
        fun create(name: Name): Airline {
            val airline = Airline(name)
            airline.id = UUID.randomUUID()
            return airline
        }
    }

    lateinit var id: UUID
}