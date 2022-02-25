package com.techandteach.framework.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object CreditCards : Table<Nothing>("credit_cards") {
    val id = long("id").primaryKey()
    val provider = varchar("provider")
    val owner = varchar("owner")
    val number = varchar("number")
    val cvc = varchar("cvc")
    val expirationDate = varchar("expiration")
    val customerId = varchar("customer_id")
}