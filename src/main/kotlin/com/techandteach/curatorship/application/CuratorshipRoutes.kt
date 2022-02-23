package com.techandteach.curatorship.application

import com.techandteach.curatorship.application.dtos.CreateAirlineDTO
import com.techandteach.curatorship.application.services.CreateAirline
import com.techandteach.curatorship.model.Airline
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.curatorshipRouting(
    createAirline: CreateAirline
) {
    route("/airlines") {
        post {
            val ( name ) = call.receive<CreateAirlineDTO>()

            val airlineOrErrorMessage = try {
                createAirline.create(name)
            } catch (e: java.lang.IllegalArgumentException) {
                e.message
            }

            if (airlineOrErrorMessage is Airline) {
                val airline: Airline = airlineOrErrorMessage
                return@post call.respond(
                    message = airline,
                    status = HttpStatusCode.Created
                )
            }

            return@post call.respond(
                message = mapOf("error" to airlineOrErrorMessage),
                status = HttpStatusCode.BadRequest
            )
        }
    }
}