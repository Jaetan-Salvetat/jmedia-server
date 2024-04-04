package com.jmedia.services

import com.jmedia.models.local.Feedback
import com.jmedia.models.local.FeedbackType
import com.jmedia.repositories.FeedbackRepository
import com.jmedia.utils.Bucket
import com.jmedia.utils.MinioUtils
import java.io.File

class FeedbackService {
    private val feedbackRepository = FeedbackRepository()

    suspend fun getAll(): List<Feedback> = feedbackRepository.getAll()

    suspend fun create(title: String, description: String, type: FeedbackType): FeedbackResult {
        return when {
            title.isBlank() -> FeedbackResult.TitleNotFound
            description.isBlank() -> FeedbackResult.DescriptionNotFound
            type == FeedbackType.Unknown -> FeedbackResult.TypeNotFound
            feedbackRepository.exist(title, type) -> FeedbackResult.AlreadyExist
            else -> {
                val feedback = feedbackRepository.create(title, description, type)
                    ?: return FeedbackResult.UnknownError

                FeedbackResult.Success(feedback)
            }
        }
    }

    suspend fun uploadFiles(id: Int, files: List<File>): FeedbackResult {
        if (!feedbackRepository.exist(id)) {
            return FeedbackResult.DoesNotExist
        }

        MinioUtils.uploadImages(Bucket.Feedback, files)
        val feedback = feedbackRepository.uploadFiles(id, files)
            ?: return FeedbackResult.UnknownError
        return FeedbackResult.Success(feedback)
    }
}

sealed class FeedbackResult(val message: String) {
    data object TitleNotFound : FeedbackResult("title_not_found")
    data object DescriptionNotFound : FeedbackResult("description_not_found")
    data object TypeNotFound : FeedbackResult("type_not_found")
    data object AlreadyExist : FeedbackResult("feedback_already_exist")
    data object DoesNotExist : FeedbackResult("feedback_does_not_exist")
    data object UnknownError : FeedbackResult("serverside_error")
    data class Success(val feedback: Feedback) : FeedbackResult("")
}