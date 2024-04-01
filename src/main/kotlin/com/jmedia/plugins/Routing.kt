package com.jmedia.plugins

import com.jmedia.routes.feedback
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        feedback()
    }
}