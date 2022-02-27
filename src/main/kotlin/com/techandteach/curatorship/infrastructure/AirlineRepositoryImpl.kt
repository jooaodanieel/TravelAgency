package com.techandteach.curatorship.infrastructure

import com.techandteach.curatorship.model.Airline
import com.techandteach.curatorship.model.AirlineRepository
import java.util.*

class AirlineRepositoryImpl(private val airlineDAO: AirlineDAO) : AirlineRepository {
    override fun add(airline: Airline): Airline {
        return airlineDAO.upsert(airline)
    }

    override fun findById(id: UUID): Airline? {
        return airlineDAO.find(id)
    }

    override fun remove(airline: Airline): Airline? {
        return removeById(airline.id)
    }

    override fun removeById(id: UUID): Airline? {
        return airlineDAO.delete(id)
    }
}