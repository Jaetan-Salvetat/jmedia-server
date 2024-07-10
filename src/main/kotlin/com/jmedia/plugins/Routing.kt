package com.jmedia.plugins

import com.jmedia.routes.searchRouting
import com.jmedia.routes.utilsRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

/**
 * Configure routing
 *
 */
fun Application.configureRouting() {
    routing {
        utilsRouting()

        route("/api") {
            route("/v1") {
                searchRouting()
            }
        }
    }
}