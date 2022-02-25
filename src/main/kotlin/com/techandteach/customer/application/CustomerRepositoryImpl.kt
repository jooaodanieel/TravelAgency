package com.techandteach.customer.application

import com.techandteach.customer.infrastructure.CreditCardDAO
import com.techandteach.customer.infrastructure.CustomerDAO
import com.techandteach.customer.model.Customer
import com.techandteach.customer.model.CustomerRepository
import java.util.*

class CustomerRepositoryImpl(
    private val customerDAO: CustomerDAO,
    private val creditCardDAO: CreditCardDAO
): CustomerRepository {
    override fun isNameTaken(name: String): Boolean {
        val n = customerDAO.countByName(name)

        return n >  0
    }

    override fun isEmailTaken(email: String): Boolean {
        val n = customerDAO.countByEmail(email)

        return n >  0
    }

    override fun add(customer: Customer): Customer {
        customerDAO.upsert(customer)!!

        for (card in customer.getCreditCards()) {
            creditCardDAO.upsert(card, customer)
        }

        return customer
    }

    override fun findById(id: UUID): Customer? {
        val customer = customerDAO.find(id) ?: return null

        val cards = creditCardDAO.findByCustomerId(id)

        for (card in cards) {
            customer.addCreditCard(card)
        }

        return customer
    }

    override fun remove(customer: Customer): Customer? {
        return removeById(customer.id)
    }

    override fun removeById(id: UUID): Customer? {
        val cards = creditCardDAO.deleteAllFromCustomer(customerId = id)

        val customer = customerDAO.delete(id) ?: return null

        for (card in cards) {
            customer.addCreditCard(card)
        }

        return customer
    }
}