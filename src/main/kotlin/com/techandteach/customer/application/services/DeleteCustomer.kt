package com.techandteach.customer.application.services

import com.techandteach.customer.model.Customer
import com.techandteach.customer.model.CustomerRepository
import java.util.*

class DeleteCustomer(private val customerRepository: CustomerRepository) {
    fun delete(maybeId: String?): Customer {
        val uuid = UUID.fromString(maybeId)

        return customerRepository.removeById(uuid)
            ?: throw IllegalArgumentException("Customer with id=$uuid not found")
    }
}