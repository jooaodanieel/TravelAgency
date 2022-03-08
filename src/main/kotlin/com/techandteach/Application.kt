package com.techandteach

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

    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSecurity()
    configureSerialization()
    configureHTTP()
    configureRouting()
}
