package com.techandteach

import com.techandteach.db.conn
import com.techandteach.domain.security.TokenManager
import com.techandteach.koin.HelloApplication
import com.techandteach.koin.helloModule
import io.ktor.application.*
import com.techandteach.plugins.*
import org.koin.core.context.startKoin

fun main(args: Array<String>) {

    startKoin {
        printLogger()

        modules(helloModule)
    }

    HelloApplication().sayHello()

    conn()

    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val issuer = environment.config.property("jwt.domain").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val realm = environment.config.property("jwt.realm").getString()
    val accessSecret = environment.config.property("jwt.access_secret").getString()
    val refreshSecret = environment.config.property("jwt.refresh_secret").getString()

    val tokenManager = TokenManager(issuer, audience, realm, accessSecret, refreshSecret)

    configureSecurity(tokenManager)
    configureSerialization()
    configureHTTP()
    configureRouting(tokenManager)
}
