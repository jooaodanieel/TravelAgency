package com.techandteach.framework.database

import org.ktorm.database.Database

fun databaseConnection(): Database {
    return Database.connect(
        url = "jdbc:postgresql://localhost:5432/travelagency",
        driver = "org.postgresql.Driver",
        user = "root",
        password = "secret"
    )
}