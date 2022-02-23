package com.techandteach.customer.model

import com.techandteach.utils.Repository

interface CustomerRepository : Repository<Customer> {

    fun isNameTaken(name: String): Boolean

    fun isEmailTaken(email: String): Boolean

}