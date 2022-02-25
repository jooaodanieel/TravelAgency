package com.techandteach.customer.application.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AddCreditCardDTO(
    val provider: String,
    val ownerName: String,
    val number: String,
    val cvc: String,
    val expiration: String
)
