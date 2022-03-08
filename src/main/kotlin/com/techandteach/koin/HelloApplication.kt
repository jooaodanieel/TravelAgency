package com.techandteach.koin

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HelloApplication : KoinComponent {
    val helloService by inject<HelloService>()

    fun sayHello() = println(helloService.hello())
}