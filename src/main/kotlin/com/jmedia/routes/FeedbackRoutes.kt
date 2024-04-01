package com.jmedia.routes

import com.jmedia.models.local.toFullFeedbackResponse
import com.jmedia.models.local.toFullFeedbackResponseList
import com.jmedia.models.request.FeedbackRequest
import com.jmedia.models.responses.ErrorResponse
import com.jmedia.services.FeedbackResult
import com.jmedia.services.FeedbackService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.feedback() {
    val feedbackService = FeedbackService()

    route("feedback") {
        get {
            call.respond(HttpStatusCode.OK, feedbackService.getAll().toFullFeedbackResponseList())
        }

        post {
            val body = call.receive<FeedbackRequest>()

            when (val result = feedbackService.create(body.title, body.description, body.type)) {
                is FeedbackResult.Success -> call.respond(
                    status = HttpStatusCode.Created,
                    message = result.feedback.toFullFeedbackResponse()
                )
                is FeedbackResult.UnknownError -> call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = ErrorResponse(result.message)
                )
                is FeedbackResult.AlreadyExist -> call.respond(
                    status = HttpStatusCode.Conflict,
                    message = ErrorResponse(result.message)
                )
                else -> call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(result.message)
                )
            }
        }
    }
}