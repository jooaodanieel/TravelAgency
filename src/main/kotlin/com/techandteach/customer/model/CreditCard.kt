package com.techandteach.customer.model

data class CreditCard(
    var provider: String,
    var ownerName: String,
    var number: String,
    var cvc: String,
    var expiration: String
)
