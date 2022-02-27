package com.techandteach.curatorship.application

import com.techandteach.curatorship.infrastructure.AirlineDAO
import com.techandteach.curatorship.model.Airline
import com.techandteach.curatorship.model.AirlineRepository
import java.util.*

class AirlineRepositoryImpl(private val airlineDAO: AirlineDAO) : AirlineRepository {
    override fun add(entity: Airline): Airline {
        return airlineDAO.upsert(entity)
    }

    override fun findById(id: UUID): Airline? {
        return airlineDAO.find(id)
    }

    override fun remove(entity: Airline): Airline? {
        return removeById(entity.id)
    }

    override fun removeById(id: UUID): Airline? {
        return airlineDAO.delete(id)
    }
}