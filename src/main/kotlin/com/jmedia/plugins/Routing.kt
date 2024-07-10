package com.jmedia.plugins

import com.jmedia.routes.searchRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Configure routing
 *
 */
fun Application.configureRouting() {
    routing {
        get("/ping") {
            call.respondText("pong")
        }

        route("/api") {
            route("/v1") {
                searchRouting()
            }
        }
    }
}