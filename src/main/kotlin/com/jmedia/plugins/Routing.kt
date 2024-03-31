package com.jmedia.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        feedback()
    }
}

private fun Routing.feedback() = route("/") {
    get("/") {
        call.respondText("Hello World!")
    }
}