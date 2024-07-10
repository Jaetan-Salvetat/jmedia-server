package com.jmedia.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    override val success: Boolean = false
) : IResponse()

object DefaultResponses {
    val missingQueryParameter = ErrorResponse("missing_query_parameter")
}