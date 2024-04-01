package com.jmedia.repositories

import com.jmedia.models.database.FeedbackTable
import com.jmedia.models.local.Feedback
import com.jmedia.models.local.toFeedback
import com.jmedia.suspendedTransaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class FeedbackRepository {
    suspend fun getAll(): List<Feedback> = suspendedTransaction {
        val query = FeedbackTable.selectAll()
        query.map { it.toFeedback() }
    }

    suspend fun create(title: String, description: String, type: String): Feedback? = suspendedTransaction {
        val query = FeedbackTable.insert {
            it[FeedbackTable.title] = title
            it[FeedbackTable.description] = description
            it[FeedbackTable.type] = type
        }

        query.resultedValues?.firstOrNull()?.toFeedback()
    }
}