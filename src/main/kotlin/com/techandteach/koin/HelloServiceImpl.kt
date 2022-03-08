package com.techandteach.koin

class HelloServiceImpl(
    private val helloMessageData: HelloMessageData
) : HelloService {
    override fun hello(): String = "Hey, ${helloMessageData.message}"
}