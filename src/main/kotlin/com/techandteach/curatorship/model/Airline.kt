package com.techandteach.curatorship.model

import com.techandteach.utils.types.Name
import com.techandteach.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
class Airline(
    val name: Name
) {
    companion object {
        fun create(name: Name): Airline {
            val id = UUID.randomUUID()
            return instantiateWithId(id, name)
        }

        fun hydrate(id: UUID, name: Name): Airline {
            return instantiateWithId(id, name)
        }

        private fun instantiateWithId(id: UUID, name: Name): Airline {
            val airline = Airline(name)
            airline.id = id
            return airline
        }
    }

    @Serializable(with = UUIDSerializer::class)
    lateinit var id: UUID
}