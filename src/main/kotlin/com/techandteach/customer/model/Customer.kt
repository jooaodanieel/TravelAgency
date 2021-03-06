package com.techandteach.customer.model

import com.techandteach.customer.model.types.Email
import com.techandteach.utils.types.Name
import com.techandteach.customer.model.types.Password
import com.techandteach.utils.UUIDSerializer
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

    private val creditCards: MutableList<CreditCard> = mutableListOf()


    fun addCreditCard(creditCard: CreditCard) {
        creditCards.add(creditCard)
    }

    fun removeCreditCard(creditCard: CreditCard) {
        creditCards.remove(creditCard)
    }

    fun getCreditCards(): List<CreditCard> {
        return creditCards
    }
}