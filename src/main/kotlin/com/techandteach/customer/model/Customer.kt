package com.techandteach.customer.model

import com.techandteach.customer.model.types.Email
import com.techandteach.customer.model.types.Name
import com.techandteach.customer.model.types.Password
import com.techandteach.customer.model.types.UUIDSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
class Customer(
    var name: Name,
    var email: Email,
    var password: Password
) {
    companion object {
        fun create(name: Name, email: Email, password: Password): Customer {
            val uuid = UUID.randomUUID()
            return instantiateWithId(uuid, name, email, password)
        }

        fun hydrate(id: UUID, name: Name, email: Email, password: Password): Customer {
            return instantiateWithId(id, name, email, password)
        }

        private fun instantiateWithId(uuid: UUID, name: Name, email: Email, password: Password): Customer {
            val customer = Customer(name, email, password)
            customer.id = uuid
            return customer
        }
    }

    @Serializable(with = UUIDSerializer::class)
    lateinit var id: UUID

    private val billingAddresses: MutableList<BillingAddress> = mutableListOf()
    private val creditCards: MutableList<CreditCard> = mutableListOf()

    fun addBillingAddress(billingAddress: BillingAddress) {
        billingAddresses.add(billingAddress)
    }

    fun addCreditCard(creditCard: CreditCard) {
        creditCards.add(creditCard)
    }

    fun removeBillingAddress(billingAddress: BillingAddress) {
        billingAddresses.remove(billingAddress)
    }

    fun removeCreditCard(creditCard: CreditCard) {
        creditCards.remove(creditCard)
    }

    fun getBillingAddresses(): List<BillingAddress> {
        return billingAddresses
    }

    fun getCreditCards(): List<CreditCard> {
        return creditCards
    }
}