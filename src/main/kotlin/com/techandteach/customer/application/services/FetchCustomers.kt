package com.techandteach.customer.application.services

import com.techandteach.customer.model.Customer
import com.techandteach.customer.model.CustomerRepository
import java.util.*

class FetchCustomers(private val customerRepository: CustomerRepository) {
    fun fetchOne(maybeId: String?): Customer {
        val uuid = UUID.fromString(maybeId)
        return customerRepository.findById(uuid) ?: throw IllegalArgumentException("customer id=$maybeId not found")
    }
}