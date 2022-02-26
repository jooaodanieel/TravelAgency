package com.techandteach.curatorship.infrastructure

import com.techandteach.curatorship.model.Airline
import com.techandteach.framework.database.tables.Airlines
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.postgresql.util.PSQLException

class AirlineDAO(private val db: Database) {
    fun upsert(airline: Airline): Airline {
        val affectedRecords: Int = try {
            update(airline)
        } catch (e: PSQLException) {
            throw IllegalArgumentException("Constraint violation on update")
        }

        if (affectedRecords == 0) {
            try {
                insert(airline)
            } catch (e: PSQLException) {
                throw IllegalArgumentException("Constraint violation on insertion")
            }
        }

        return airline
    }

    private fun update(airline: Airline): Int {
        return db.update(Airlines) {
            set(it.name, airline.name.toString())
            where { it.id eq airline.id.toString() }
        }
    }

    private fun insert(airline: Airline): Int {
        return db.insert(Airlines) {
            set(it.id, airline.id.toString())
            set(it.name, airline.name.toString())
        }
    }
}