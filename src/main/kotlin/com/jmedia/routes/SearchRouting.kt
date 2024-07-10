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
                    message = DefaultResponses.missingQueryParameter
                )
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: Int.MAX_VALUE

            when (val typesResult = call.request.queryParameters["types"]?.getMediaTypes()) {
                is MediaTypeResult.Success -> call.respond(
                    status = HttpStatusCode.OK,
                    message = searchService.search(query, typesResult.types, limit)
                )
                is MediaTypeResult.UnknownType -> return@get call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse("unknown_media_type")
                )
                null -> return@get call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = DefaultResponses.missingQueryParameter
                )
            }
        }
    }
}