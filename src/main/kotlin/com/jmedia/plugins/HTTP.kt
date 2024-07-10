package com.jmedia.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

/**
 * Configure HTTP CORS
 *
 */
fun Application.configureHTTP() {
    install(CORS) {
        anyHost()
    }
}