package com.techandteach.plugins

import com.auth0.jwt.exceptions.JWTVerificationException
import com.techandteach.domain.security.TokenManager
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@kotlinx.serialization.Serializable
data class LoginUsername(val username: String)

@kotlinx.serialization.Serializable
data class RefreshToken(val token: String)

fun Application.configureRouting(manager: TokenManager) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/login") {
            val username = call.receive<LoginUsername>().username

            val accessToken = manager.generateAccessToken(username)
            val refreshToken = manager.generateRefreshToken(username)

            call.respond(mapOf("access_token" to accessToken, "refresh_token" to refreshToken))
        }

        post("/token") {
            val refreshToken = call.receive<RefreshToken>().token

            try {
                val username = manager
                    .validateRefresh(refreshToken)
                    .getClaim("username")
                    .asString()

                val newAccessToken = manager.generateAccessToken(username)

                call.respond(mapOf("access_token" to newAccessToken))
            } catch (e: JWTVerificationException) {
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = mapOf("error" to e.message)
                )
            }
        }

        authenticate {
            get("/foo") {
                call.respondText("I see you're logged")
            }

            delete("/logout") {
                val token = call.receive<RefreshToken>().token
                manager.cancelRefreshToken(token)
                call.respond(status = HttpStatusCode.NoContent, "")
            }
        }
    }
}
