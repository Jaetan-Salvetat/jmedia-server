package com.jmedia.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val details: String = "",
    override val success: Boolean = false
) : IResponse()

object DefaultResponses {
    fun missingQueryParameter(parameterName: String) = ErrorResponse(
        message = "missing_query_parameter",
        details = "Missing query parameter: $parameterName"
    )
}