package com.techandteach.customer.application

import com.techandteach.customer.application.dtos.CreateCustomerDTO
import com.techandteach.customer.application.services.CreateCustomer
import com.techandteach.customer.application.services.DeleteCustomer
import com.techandteach.customer.application.services.FetchCustomers
import com.techandteach.customer.model.Customer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting(
    createCustomer: CreateCustomer,
    deleteCustomer: DeleteCustomer,
    fetchCustomers: FetchCustomers
) {
    route("/customers") {
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                message = mapOf("error" to "Missing or malformed parameter \"id\""),
                status = HttpStatusCode.BadRequest
            )

            val customerOrErrorMessage = try {
                fetchCustomers.fetchOne(id)
            } catch (e: java.lang.IllegalArgumentException) {
                e.message
            }

            if (customerOrErrorMessage is Customer) {
                val customer: Customer = customerOrErrorMessage
                return@get call.respond(customer)
            }

            return@get call.respond(
                message = mapOf("error" to customerOrErrorMessage),
                status = HttpStatusCode.BadRequest
            )
        }

        post {
            val (name, email, password) = call.receive<CreateCustomerDTO>()

            val customerOrErrorMessage = try {
                createCustomer.create(name, email, password)
            } catch (e: IllegalArgumentException) {
                e.message
            }

            if (customerOrErrorMessage is Customer) {
                val customer: Customer = customerOrErrorMessage
                return@post call.respond(
                    message = customer,
                    status = HttpStatusCode.Created
                )
            }

            return@post call.respond(
                message = mapOf("error" to customerOrErrorMessage),
                status = HttpStatusCode.BadRequest
            )
        }

        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(
                message = mapOf("error" to "Missing or malformed parameter \"id\""),
                status = HttpStatusCode.BadRequest
            )

            val customerOrErrorMessage = try {
                deleteCustomer.delete(id)
            } catch (e: java.lang.IllegalArgumentException) {
                e.message
            }

            if (customerOrErrorMessage is Customer) {
                val customer: Customer = customerOrErrorMessage
                return@delete call.respond(customer)
            }

            return@delete call.respond(
                message = mapOf("error" to customerOrErrorMessage),
                status = HttpStatusCode.BadRequest
            )
        }
    }
}