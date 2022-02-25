package com.techandteach.plugins

import com.techandteach.customer.application.customerRouting
import com.techandteach.customer.application.services.CreateCustomer
import com.techandteach.customer.application.services.DeleteCustomer
import com.techandteach.customer.application.services.FetchCustomers
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    createCustomer: CreateCustomer,
    deleteCustomer: DeleteCustomer,
    fetchCustomers: FetchCustomers,
) {

    routing {
        customerRouting(
            createCustomer,
            deleteCustomer,
            fetchCustomers
        )
    }
}
