package com.jmedia.models.local

import com.jmedia.Constants
import com.jmedia.models.database.FeedbackTable
import com.jmedia.models.responses.FullFeedbackResponse
import org.jetbrains.exposed.sql.ResultRow

enum class FeedbackType {
    Unknown,
    Bug,
    Feature;

    companion object {
        fun fromString(name: String) = when (name.lowercase()) {
            "bug" -> Bug
            "feature" -> Feature
            else -> Unknown
        }
    }
}

data class Feedback(
    val id: Int = -1,
    val title: String,
    val description: String,
    val type: FeedbackType,
    val filesPath: List<String> = listOf()
)

fun Feedback.toFullFeedbackResponse() = FullFeedbackResponse(
    id = id,
    title = title,
    description = description,
    type = type.name.lowercase(),
    filesUrl = filesPath.map { "${Constants.minioBaseUrl}/$it" }
)

fun List<Feedback>.toFullFeedbackResponseList() = map { it.toFullFeedbackResponse() }

fun ResultRow.toFeedback() = Feedback(
    id = this[FeedbackTable.id],
    title = this[FeedbackTable.title],
    description = this[FeedbackTable.description],
    type = FeedbackType.fromString(this[FeedbackTable.type]),
    filesPath = this[FeedbackTable.filePath]
        ?.split("|")
        ?.map { it }
        ?: listOf()
)