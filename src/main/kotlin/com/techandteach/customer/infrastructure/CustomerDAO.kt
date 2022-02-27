package com.techandteach.customer.infrastructure

import com.techandteach.customer.model.Customer
import com.techandteach.customer.model.types.Email
import com.techandteach.customer.model.types.Password
import com.techandteach.framework.database.tables.Customers
import com.techandteach.utils.types.Name
import org.ktorm.database.Database
import org.ktorm.dsl.*
import java.util.*

class CustomerDAO(private val db: Database) {
    private fun update(customer: Customer): Customer? {
        val affectedRecords = db.update(Customers) {
            set(it.name, customer.name.toString())
            set(it.email, customer.email.toString())
            set(it.password, customer.password.toString())
            where { it.id eq customer.id.toString() }
        }

        if (affectedRecords == 0) return null

        return customer
    }

    private fun insert(customer: Customer): Customer? {
        val affectedRecords = db.insert(Customers) {
            set(it.id, customer.id.toString())
            set(it.name, customer.name.toString())
            set(it.email, customer.email.toString())
            set(it.password, customer.password.toString())
        }

        if (affectedRecords == 0) return null

        return customer
    }

    fun upsert(customer: Customer): Customer? {
        return update(customer) ?: insert(customer)
    }

    fun find(id: UUID): Customer? {
        val customerRecord = db.from(Customers)
            .select()
            .where { Customers.id eq id.toString() }

        var customer: Customer? = null

        for (row in customerRecord) {
            val rowId: String = row[Customers.id] ?: break

            val name: Name = Name.fromString(row[Customers.name])
            val email: Email = Email.fromString(row[Customers.email])
            val password: Password = Password.fromString(row[Customers.password])

            customer = Customer.hydrate(UUID.fromString(rowId), name, email, password)

            break
        }

        return customer
    }

    fun countByName(name: String): Int {
        val query = db.from(Customers)
            .select(Customers.name)
            .where { Customers.name eq name }

        var n = 0
        for (row in query) {
            n++
        }

        return n
    }

    fun countByEmail(email: String): Int {
        val query = db.from(Customers)
            .select(Customers.email)
            .where { Customers.email eq email }

        var n = 0
        for (row in query) {
            n++
        }

        return n
    }

    fun delete(id: UUID): Customer? {
        val customer = find(id)

        db.delete(Customers) { it.id eq id.toString() }

        return customer
    }
}