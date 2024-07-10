package com.jmedia.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * Configure serialization
 *
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}