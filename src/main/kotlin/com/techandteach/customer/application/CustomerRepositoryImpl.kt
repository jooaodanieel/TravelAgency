package com.techandteach.customer.application

import com.techandteach.customer.model.Customer
import com.techandteach.customer.model.CustomerRepository
import com.techandteach.customer.model.types.Email
import com.techandteach.customer.model.types.Name
import com.techandteach.customer.model.types.Password
import com.techandteach.framework.database.tables.Customers
import org.ktorm.database.Database
import org.ktorm.dsl.*
import java.util.*

class CustomerRepositoryImpl(private val db: Database): CustomerRepository {
    override fun isNameTaken(name: String): Boolean {
        val query = db.from(Customers)
            .select(Customers.name)
            .where { Customers.name eq name }

        var n = 0
        for (row in query) {
            n++
        }

        return n >  0
    }

    override fun isEmailTaken(email: String): Boolean {
        val query = db.from(Customers)
            .select(Customers.email)
            .where { Customers.email eq email }

        var n = 0
        for (row in query) {
            n++
        }

        return n >  0
    }

    override fun add(customer: Customer): Customer {
        val affectedRecords = db.update(Customers) {
            set(it.name, customer.name.toString())
            set(it.email, customer.email.toString())
            set(it.password, customer.password.toString())
            where { it.id eq customer.id.toString() }
        }

        if (affectedRecords == 1) return customer

        db.insert(Customers) {
            set(it.id, customer.id.toString())
            set(it.name, customer.name.toString())
            set(it.email, customer.email.toString())
            set(it.password, customer.password.toString())
        }

        return customer
    }

    override fun findById(id: UUID): Customer? {
        val customerRecord = db.from(Customers)
            .select()
            .where { Customers.id eq id.toString() }

        var customer: Customer? = null

        for (row in customerRecord) {
            val id: String = row[Customers.id] ?: break

            val name: Name = Name.fromString(row[Customers.name])
            val email: Email = Email.fromString(row[Customers.email])
            val password: Password = Password.fromString(row[Customers.password])

            customer = Customer.hydrate(UUID.fromString(id), name, email, password)
        }

        return customer
    }

    override fun remove(customer: Customer): Customer? {
        return removeById(customer.id)
    }

    override fun removeById(id: UUID): Customer? {
        val customer = findById(id)

        db.delete(Customers) { it.id eq id.toString() }

        return customer
    }
}