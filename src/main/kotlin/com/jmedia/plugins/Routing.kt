package com.jmedia.plugins

import com.jmedia.routes.feedback
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        swaggerUI("doc", "openapi/documentation.yaml")

        route("/api/v1") {
            feedback()
        }
    }
}