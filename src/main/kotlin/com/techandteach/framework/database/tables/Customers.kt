package com.techandteach.framework.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object Customers : Table<Nothing>("customers") {
    val id = varchar("id").primaryKey()
    val name = varchar("name")
    val email = varchar("email")
    val password = varchar("password")
}