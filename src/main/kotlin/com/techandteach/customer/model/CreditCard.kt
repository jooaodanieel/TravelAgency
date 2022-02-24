package com.techandteach.customer.model

import com.techandteach.customer.model.types.ExpirationDate
import com.techandteach.customer.model.types.CreditCardNumber
import com.techandteach.utils.types.Name
import kotlinx.serialization.Serializable

@Serializable
data class CreditCard(
    var provider: Name,
    var ownerName: Name,
    var number: CreditCardNumber,
    var cvc: String,
    var expiration: ExpirationDate
)
