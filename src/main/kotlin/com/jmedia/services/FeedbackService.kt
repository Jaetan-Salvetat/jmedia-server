package com.jmedia.services

import com.jmedia.models.local.Feedback
import com.jmedia.models.local.FeedbackType
import com.jmedia.repositories.FeedbackRepository

class FeedbackService {
    private val feedbackRepository = FeedbackRepository()

    suspend fun getAll(): List<Feedback> = feedbackRepository.getAll()

    suspend fun create(title: String, description: String, typeString: String): FeedbackResult {
        val type = FeedbackType.fromString(typeString)

        return when {
            title.isBlank() -> FeedbackResult.TitleNotFound
            description.isBlank() -> FeedbackResult.DescriptionNotFound
            typeString.isBlank() -> FeedbackResult.TypeNotFound
            feedbackRepository.exist(title, type) -> FeedbackResult.AlreadyExist
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
    data object AlreadyExist: FeedbackResult("feedback_already_exist")
    data object UnknownError: FeedbackResult("serverside_error")
    data class Success(val feedback: Feedback): FeedbackResult("")
}