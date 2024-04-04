package com.jmedia.repositories

import com.jmedia.models.database.FeedbackTable
import com.jmedia.models.local.Feedback
import com.jmedia.models.local.FeedbackType
import com.jmedia.models.local.toFeedback
import com.jmedia.utils.suspendedTransaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.io.File

class FeedbackRepository {
    suspend fun getAll(): List<Feedback> = suspendedTransaction {
        FeedbackTable
            .selectAll()
            .map { it.toFeedback() }
    }

    suspend fun create(title: String, description: String, type: FeedbackType): Feedback? = suspendedTransaction {
        val query = FeedbackTable.insert {
            it[FeedbackTable.title] = title
            it[FeedbackTable.description] = description
            it[FeedbackTable.type] = type.name
        }

        query.resultedValues?.firstOrNull()?.toFeedback()
    }

    suspend fun uploadFile(id: Int, file: File): Feedback? = suspendedTransaction {
        FeedbackTable.update(
            where = { FeedbackTable.id eq id },
            body = { it[filePath] = "/feedback/${file.name}" }
        )

        FeedbackTable.select { FeedbackTable.id eq id }
            .firstOrNull()
            ?.toFeedback()
    }

    suspend fun exist(id: Int): Boolean = suspendedTransaction {
        FeedbackTable
            .select { FeedbackTable.id eq id }
            .toList()
            .isNotEmpty()
    }

    suspend fun exist(title: String, type: FeedbackType): Boolean = suspendedTransaction {
        FeedbackTable
            .select {
                FeedbackTable.title eq title
                FeedbackTable.type eq type.name
            }
            .toList()
            .isNotEmpty()
    }
}