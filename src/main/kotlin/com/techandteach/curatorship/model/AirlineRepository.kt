package com.techandteach.curatorship.model

import com.techandteach.utils.Repository

interface AirlineRepository : Repository<Airline> {
    fun isNameTaken(name: String): Boolean
}