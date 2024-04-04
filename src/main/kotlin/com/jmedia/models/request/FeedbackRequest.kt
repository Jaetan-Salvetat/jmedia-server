package com.jmedia.models.request

import kotlinx.serialization.Serializable

@Serializable
data class FeedbackRequest(
    val title: String,
    val description: String,
    val type: String
)