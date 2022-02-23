package com.techandteach.plugins

import com.techandteach.curatorship.application.curatorshipRouting
import com.techandteach.curatorship.application.services.CreateAirline
import com.techandteach.customer.application.customerRouting
import com.techandteach.customer.application.services.CreateCustomer
import com.techandteach.customer.application.services.DeleteCustomer
import com.techandteach.customer.application.services.FetchCustomers
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting(
    createCustomer: CreateCustomer,
    deleteCustomer: DeleteCustomer,
    fetchCustomers: FetchCustomers,

    createAirline: CreateAirline
) {

    routing {
        customerRouting(
            createCustomer,
            deleteCustomer,
            fetchCustomers
        )

        curatorshipRouting(
            createAirline
        )
    }
}
