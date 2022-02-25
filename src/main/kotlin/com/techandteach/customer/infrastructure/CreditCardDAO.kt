package com.techandteach.customer.infrastructure

import com.techandteach.customer.model.CreditCard
import com.techandteach.customer.model.Customer
import com.techandteach.customer.model.types.CreditCardNumber
import com.techandteach.customer.model.types.ExpirationDate
import com.techandteach.framework.database.tables.CreditCards
import com.techandteach.utils.types.Name
import org.ktorm.database.Database
import org.ktorm.dsl.*
import java.util.UUID

class CreditCardDAO(private val db: Database) {
    private fun update(card: CreditCard, customer: Customer): CreditCard? {
        val affectedRecords = db.update(CreditCards) {
            set(it.provider, card.provider.toString())
            set(it.owner, card.ownerName.toString())
            set(it.number, card.number.toString())
            set(it.cvc, card.cvc)
            set(it.expirationDate, card.expiration.toString())
            where { CreditCards.customerId eq customer.id.toString() }
        }

        if (affectedRecords == 0) return null

        return card
    }

    private fun insert(card: CreditCard, customer: Customer): CreditCard? {
        val affectedRecords = db.insert(CreditCards) {
            set(it.provider, card.provider.toString())
            set(it.owner, card.ownerName.toString())
            set(it.number, card.number.toString())
            set(it.cvc, card.cvc)
            set(it.expirationDate, card.expiration.toString())
            set(it.customerId, customer.id.toString())
        }

        if (affectedRecords == 0) return null

        return card
    }

    fun upsert(card: CreditCard, customer: Customer): CreditCard? {
        return update(card, customer) ?: insert(card, customer)
    }

    fun findByCustomerId(customerId: UUID): List<CreditCard> {
        val result = db.from(CreditCards)
            .select()
            .where { CreditCards.customerId eq customerId.toString() }

        val cards: MutableList<CreditCard> = mutableListOf()

        for (row in result) {
            val id: Long = row[CreditCards.id] ?: break

            val provider = row[CreditCards.provider]
            val owner = row[CreditCards.owner]
            val number = row[CreditCards.number]
            val cvc = row[CreditCards.cvc]!!
            val expirationDate = row[CreditCards.expirationDate]

            val card = CreditCard.hydrate(
                provider = Name.fromString(provider),
                ownerName = Name.fromString(owner),
                number = CreditCardNumber.fromString(number),
                cvc =  cvc,
                expiration = ExpirationDate.fromString(expirationDate)
            )

            cards.add(card)
        }

        return cards
    }

    fun deleteAllFromCustomer(customerId: UUID): List<CreditCard> {
        val cards = findByCustomerId(customerId)

        db.delete(CreditCards) { it.customerId eq customerId.toString() }

        return cards
    }
}