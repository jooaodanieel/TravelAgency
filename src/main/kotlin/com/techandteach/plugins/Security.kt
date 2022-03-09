package com.techandteach.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Application.configureSecurity() {
    
    authentication {
            jwt {
                val jwtAudience = environment.config.property("jwt.audience").getString()
                val jwtIssuer = environment.config.property("jwt.domain").getString()
                val accessTokenSecret = environment.config.property("jwt.access_secret").getString()

                realm = environment.config.property("jwt.realm").getString()

                verifier(
                    JWT
                        .require(Algorithm.HMAC256(accessTokenSecret))
                        .withAudience(jwtAudience)
                        .withIssuer(jwtIssuer)
                        .build()
                )

                validate { credential ->
                    if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
                }
            }
        }

}
