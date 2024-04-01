package com.jmedia.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    override val success: Boolean = false
): IResponse()
