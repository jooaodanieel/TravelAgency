package com.techandteach.customer.application.services

import com.techandteach.customer.model.*
import com.techandteach.customer.model.types.CreditCardNumber
import com.techandteach.customer.model.types.Email
import com.techandteach.customer.model.types.ExpirationDate
import com.techandteach.utils.types.Name
import com.techandteach.customer.model.types.Password
import java.util.*

class CreateCustomer(private val customerRepository: CustomerRepository) {
    private fun validateRegistration(name: Name, email: Email) {
        if (customerRepository.isNameTaken(name.toString())) throw IllegalArgumentException("Customer name \"$name\" already in use")
        if (customerRepository.isEmailTaken(email.toString())) throw IllegalArgumentException("Customer email \"$email\" already in use")
    }

    private fun validateCustomer(id: UUID?) {
        if (id == null) throw IllegalArgumentException("Customer id must be provided")

        customerRepository.findById(id) ?: throw IllegalArgumentException("Customer id=$id doesn't exist")
    }

    fun registerCustomer(maybeName: String, maybeEmail: String, maybePassword: String): Customer {
        val name = Name.fromString(maybeName)
        val email = Email.fromString(maybeEmail)
        val password = Password.fromString(maybePassword)

        validateRegistration(name, email)

        val customer = Customer.create(name, email, password)
        return customerRepository.add(customer)
    }

    fun addCreditCardInformation(
        maybeCustomerId: String?,
        maybeProvider: String,
        maybeOwnerName: String,
        maybeNumber: String,
        cvc: String,
        maybeExpiration: String
    ): Customer {
        val customerId: UUID = UUID.fromString(maybeCustomerId)

        validateCustomer(customerId)

        val provider = Name.fromString(maybeProvider)
        val ownerName = Name.fromString(maybeOwnerName)
        val number = CreditCardNumber.fromString(maybeNumber)
        val expirationDate = ExpirationDate.fromString(maybeExpiration)

        val card = CreditCard.create(provider, ownerName, number, cvc, expirationDate)

        val customer = customerRepository.findById(customerId)!!

        customer.addCreditCard(card)

        return customerRepository.add(customer)
    }
}