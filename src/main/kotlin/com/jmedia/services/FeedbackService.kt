package com.jmedia.services

import com.jmedia.models.local.Feedback
import com.jmedia.repositories.FeedbackRepository

class FeedbackService {
    private val feedbackRepository = FeedbackRepository()

    suspend fun create(title: String, description: String, type: String): FeedbackResult {
        return when {
            title.isBlank() -> FeedbackResult.TitleNotFound
            description.isBlank() -> FeedbackResult.DescriptionNotFound
            type.isBlank() -> FeedbackResult.TypeNotFound
            else -> {
                val feedback = feedbackRepository.create(title, description, type)
                    ?: return FeedbackResult.UnknownError

                FeedbackResult.Success(feedback)
            }
        }
    }
}

sealed class FeedbackResult(val message: String) {
    data object TitleNotFound: FeedbackResult("title_not_found")
    data object DescriptionNotFound: FeedbackResult("description_not_found")
    data object TypeNotFound: FeedbackResult("type_not_found")
    data object UnknownError: FeedbackResult("serverside_error")
    data class Success(val feedback: Feedback): FeedbackResult("")
}