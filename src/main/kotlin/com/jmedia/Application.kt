package com.jmedia

import com.jmedia.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    val config = environment.config

    Constants.initialize(config)
    configureSerialization()
    configureDatabase()
    configureMinio()
    configureHTTP()
    configureRouting()
}