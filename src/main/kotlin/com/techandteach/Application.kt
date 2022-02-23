package com.techandteach

import com.techandteach.customer.application.CustomerRepositoryImpl
import com.techandteach.customer.application.services.CreateCustomer
import com.techandteach.customer.application.services.DeleteCustomer
import com.techandteach.customer.application.services.FetchCustomers
import com.techandteach.framework.database.databaseConnection
import com.techandteach.plugins.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*

fun main() {
    val db = databaseConnection()

    val repo = CustomerRepositoryImpl(db)

    val createCustomer = CreateCustomer(repo)
    val deleteCustomer = DeleteCustomer(repo)
    val fetchCustomers = FetchCustomers(repo)

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }
        configureRouting(
            createCustomer,
            deleteCustomer,
            fetchCustomers
        )
    }.start(wait = true)
}
