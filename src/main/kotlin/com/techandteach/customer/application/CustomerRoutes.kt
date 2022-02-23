package com.techandteach.customer.application

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class Cust(val foo: String)

val customers = mutableListOf<Cust>(Cust("baz"))

fun Route.customerRouting() {
    route("/customers") {
        get {
            if (customers.isEmpty()) {
                call.respondText("No customers found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(customers)
            }
        }
    }
}