package com.jmedia.routes

import com.jmedia.models.local.FeedbackType
import com.jmedia.models.local.toFullFeedbackResponse
import com.jmedia.models.local.toFullFeedbackResponseList
import com.jmedia.models.request.FeedbackRequest
import com.jmedia.models.responses.ErrorResponse
import com.jmedia.services.FeedbackResult
import com.jmedia.services.FeedbackService
import com.jmedia.utils.getFilesFromPartData
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.feedback() {
    val feedbackService = FeedbackService()

    route("feedback") {
        get {
            call.respond(
                status = HttpStatusCode.OK,
                message = feedbackService.getAll().toFullFeedbackResponseList()
            )
        }

        post {
            val feedback = call.receive<FeedbackRequest>()
            val result = feedbackService.create(
                title = feedback.title,
                description = feedback.description,
                type = FeedbackType.fromString(feedback.type)
            )

            when (result) {
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

        post("/upload/{id}") {
            val multipart = call.receiveMultipart()
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw Exception("Id not found")
            val file = getFilesFromPartData(multipart).firstOrNull()
                ?: throw Exception("File is invalid")

            when (val result = feedbackService.uploadFile(id, file)) {
                is FeedbackResult.Success -> call.respond(
                    status = HttpStatusCode.OK,
                    message = result.feedback.toFullFeedbackResponse()
                )
                is FeedbackResult.DoesNotExist -> call.respond(
                    status = HttpStatusCode.fromValue(404),
                    message = ErrorResponse(result.message)
                )
                else -> call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = ErrorResponse(result.message)
                )
            }
        }
    }
}