package com.techandteach

import AirlineRepositoryImpl
import com.techandteach.curatorship.application.services.CreateAirline
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

    val customerRepository = CustomerRepositoryImpl(db)
    val airlineRepository = AirlineRepositoryImpl(db)

    val createCustomer = CreateCustomer(customerRepository)
    val deleteCustomer = DeleteCustomer(customerRepository)
    val fetchCustomers = FetchCustomers(customerRepository)

    val createAirline = CreateAirline(airlineRepository)

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }
        configureRouting(
            createCustomer,
            deleteCustomer,
            fetchCustomers,
            createAirline
        )
    }.start(wait = true)
}
