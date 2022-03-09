package com.techandteach.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

@kotlinx.serialization.Serializable
data class LoginUsername(val username: String)

@kotlinx.serialization.Serializable
data class RefreshToken(val token: String)

fun Application.configureRouting() {

    val audience = environment.config.property("jwt.audience").getString()
    val issuer = environment.config.property("jwt.domain").getString()
    val accessTokenSecret = environment.config.property("jwt.access_secret").getString()
    val refreshTokenSecret = environment.config.property("jwt.refresh_secret").getString()

    val refreshTokens = mutableSetOf<String>()

    fun generateAccessToken(username: String): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", username)
            .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 15))
            .sign(Algorithm.HMAC256(accessTokenSecret))
    }

    fun generateRefreshToken(username: String): String {
        val refreshToken = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", username)
            .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .sign(Algorithm.HMAC256(refreshTokenSecret))

        refreshTokens.add(refreshToken)

        return refreshToken
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/login") {
            val username = call.receive<LoginUsername>().username

            val accessToken = generateAccessToken(username)
            val refreshToken = generateRefreshToken(username)

            call.respond(mapOf("access_token" to accessToken, "refresh_token" to refreshToken))
        }

        post("/token") {
            val refreshToken = call.receive<RefreshToken>().token

            if (refreshToken == null) {
                call.respond(status = HttpStatusCode.Forbidden, "")
                println("token must be provided")
                return@post
            }

            if (refreshToken !in refreshTokens) {
                call.respond(status = HttpStatusCode.Unauthorized, "")
                println("token not found")
                return@post
            }

            val jwtVerifier = JWT
                .require(Algorithm.HMAC256(refreshTokenSecret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build()

            try {
                val username = jwtVerifier.verify(refreshToken).getClaim("username").asString()
                val accessToken = generateAccessToken(username)
                call.respond(mapOf("access_token" to accessToken))
            } catch (e: JWTVerificationException) {
                println("token verification failed")
                println(e.message)
                call.respond(status = HttpStatusCode.Unauthorized, "")
            }

        }



        authenticate {
            get("/foo") {
                call.respondText("I see you're logged")
            }

            delete("/logout") {
                val refreshToken = call.receive<RefreshToken>().token
                refreshTokens.remove(refreshToken)

                call.respond(status = HttpStatusCode.NoContent, "")
            }
        }
    }
}
