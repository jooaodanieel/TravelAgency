package com.techandteach.customer.application.services

import com.techandteach.customer.model.*
import com.techandteach.customer.model.types.Email
import com.techandteach.utils.types.Name
import com.techandteach.customer.model.types.Password

class CreateCustomer(private val customerRepository: CustomerRepository) {
    private fun validate(name: Name, email: Email) {
        if (customerRepository.isNameTaken(name.toString())) throw IllegalArgumentException("Customer name \"$name\" already in use")
        if (customerRepository.isEmailTaken(email.toString())) throw IllegalArgumentException("Customer email \"$email\" already in use")
    }

    fun create(maybeName: String, maybeEmail: String, maybePassword: String): Customer {
        val name = Name.fromString(maybeName)
        val email = Email.fromString(maybeEmail)
        val password = Password.fromString(maybePassword)

        validate(name, email)

        val customer = Customer.create(name, email, password)
        return customerRepository.add(customer)
    }
}