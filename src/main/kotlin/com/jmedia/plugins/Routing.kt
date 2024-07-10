package com.jmedia.plugins

import com.jmedia.routes.searchRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/api") {
            route("/v1") {
                searchRouting()
            }
        }
    }
}