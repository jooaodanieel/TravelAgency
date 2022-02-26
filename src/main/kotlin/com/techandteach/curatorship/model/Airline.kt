package com.techandteach.curatorship.model

import com.techandteach.utils.types.Name
import java.util.*

class Airline private constructor(val name: Name) {
    companion object {
        fun create(name: Name): Airline {
            val id = UUID.randomUUID()
            return instantiateWithGivenId(id, name)
        }

        fun hydrate(id: UUID, name: Name): Airline {
            return instantiateWithGivenId(id, name)
        }

        private fun instantiateWithGivenId(id: UUID, name: Name): Airline {
            val airline = Airline(name)
            airline.id = id
            return airline
        }
    }

    lateinit var id: UUID
}