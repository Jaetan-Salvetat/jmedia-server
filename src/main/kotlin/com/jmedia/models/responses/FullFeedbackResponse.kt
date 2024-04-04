package com.jmedia.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class FullFeedbackResponse(
    val id: Int,
    val title: String,
    val description: String,
    val type: String,
    val filePath: String?
)
