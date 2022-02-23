package com.techandteach.customer.model

@kotlinx.serialization.Serializable
data class BillingAddress(
    var street: String,
    var number: String,
    var zipcode: String,
    var neighborhood: String,
    var city: String,
    var state: String,
    var country: String,
    var complement: String = ""
)
