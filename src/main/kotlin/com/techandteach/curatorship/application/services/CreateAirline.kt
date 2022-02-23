package com.techandteach.curatorship.application.services

import com.techandteach.curatorship.model.Airline
import com.techandteach.curatorship.model.AirlineRepository
import com.techandteach.utils.types.Name

class CreateAirline(
    private val airlineRepository: AirlineRepository
) {
    private fun validate(name: Name) {
        if (airlineRepository.isNameTaken(name.toString())) throw IllegalArgumentException("Airline name $name is already taken")
    }

    fun create(maybeName: String?): Airline {
        val name = Name.fromString(maybeName)
        validate(name)

        val airline = Airline.create(name)
        return airlineRepository.add(airline)
    }
}