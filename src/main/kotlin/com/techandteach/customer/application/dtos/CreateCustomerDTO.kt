package com.techandteach.customer.application.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreateCustomerDTO(
    val name: String,
    val email: String,
    val password: String
)
