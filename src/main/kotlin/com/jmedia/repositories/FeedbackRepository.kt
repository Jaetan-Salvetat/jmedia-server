package com.jmedia.repositories

import com.jmedia.models.database.FeedbackTable
import com.jmedia.models.local.Feedback
import com.jmedia.models.local.toFeedback
import com.jmedia.suspendedTransaction
import org.jetbrains.exposed.sql.insert

class FeedbackRepository {
    suspend fun create(title: String, description: String, type: String): Feedback? = suspendedTransaction {
        val query = FeedbackTable.insert {
            it[FeedbackTable.title] = title
            it[FeedbackTable.description] = description
            it[FeedbackTable.type] = type
        }

        query.resultedValues?.firstOrNull()?.toFeedback()
    }
}