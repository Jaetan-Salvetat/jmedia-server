package com.jmedia.routes

import com.jmedia.extensions.getMediaTypes
import com.jmedia.models.local.MediaTypeResult
import com.jmedia.models.responses.DefaultResponses
import com.jmedia.models.responses.ErrorResponse
import com.jmedia.services.SearchService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Search routing
 *
 */
fun Route.searchRouting() {
    val searchService = SearchService()

    route("search") {
        get {
            val query = call.request.queryParameters["q"]
                ?: return@get call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = DefaultResponses.missingQueryParameter("q"),
                )
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 20

            when (val typesResult = call.request.queryParameters["type"]?.getMediaTypes()) {
                is MediaTypeResult.Success -> call.respond(
                    status = HttpStatusCode.OK,
                    message = searchService.search(query, typesResult.types, limit)
                )
                is MediaTypeResult.UnknownType -> return@get call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(
                        message = "unknown_media_type",
                        details = "Unknown media type: ${typesResult.type}")
                )
                null -> return@get call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = DefaultResponses.missingQueryParameter("type")
                )
            }
        }
    }
}