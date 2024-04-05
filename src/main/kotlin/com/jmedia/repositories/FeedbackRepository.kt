package com.jmedia.repositories

import com.jmedia.models.database.FeedbackTable
import com.jmedia.models.local.Feedback
import com.jmedia.models.local.FeedbackType
import com.jmedia.models.local.toFeedback
import com.jmedia.utils.Bucket
import com.jmedia.utils.suspendedTransaction
import org.jetbrains.exposed.sql.*
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

    suspend fun uploadFiles(id: Int, file: List<File>): Feedback? = suspendedTransaction {
        FeedbackTable.update(where = { FeedbackTable.id eq id }) {
            it[filePath] = Bucket.toPathList(Bucket.Feedback, file).joinToString(separator = "|")
        }

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
            .select { FeedbackTable.title eq title and (FeedbackTable.type eq type.name) }
            .toList()
            .isNotEmpty()
    }
}