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
    val toReturn = environment?.config?.propertyOrNull("sample.helloWorld")?.getString() ?: "null"

    get("/") {
        call.respondText(toReturn)
    }
}