package com.jmedia.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * Configures the content negotiation for the application.
 * This function installs the ContentNegotiation plugin and sets up JSON serialization using kotlinx.serialization.
 * It allows the application to automatically serialize and deserialize JSON data in HTTP requests and responses.
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}