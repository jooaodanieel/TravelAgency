package com.techandteach.framework.database

import org.ktorm.database.Database

fun databaseConnection(databaseName: String = "travelagency"): Database {
    return Database.connect(
        url = "jdbc:postgresql://localhost:5432/$databaseName",
        driver = "org.postgresql.Driver",
        user = "root",
        password = "secret"
    )
}