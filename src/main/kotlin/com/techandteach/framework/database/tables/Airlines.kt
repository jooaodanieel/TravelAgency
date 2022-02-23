package com.techandteach.framework.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object Airlines : Table<Nothing>("airlines") {
    val id = varchar("id").primaryKey()
    val name = varchar("name")
}