package com.techandteach.curatorship.infrastructure

import com.techandteach.curatorship.model.Airline
import com.techandteach.framework.database.tables.Airlines
import com.techandteach.utils.types.Name
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.postgresql.util.PSQLException
import java.util.UUID

class AirlineDAO(private val db: Database) {
    fun delete(id: UUID): Airline? {
        val airline: Airline = find(id) ?: return null

        db.delete(Airlines) { Airlines.id eq id.toString() }

        return airline
    }

    fun find(id: UUID): Airline? {
        val results = db.from(Airlines)
            .select()
            .where { Airlines.id eq id.toString() }

        var airline: Airline? = null
        for (row in results) {
            val id = row[Airlines.id] ?: break
            val name = Name.fromString(row[Airlines.name])

            airline = Airline.hydrate(UUID.fromString(id), name)
            break
        }

        return airline
    }

    fun upsert(airline: Airline): Airline {
        val exists = find(airline.id) != null

        if (exists) {
            try {
                update(airline)
            } catch (e: PSQLException) {
                throw IllegalArgumentException("Constraint violation on update")
            }
        } else {
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