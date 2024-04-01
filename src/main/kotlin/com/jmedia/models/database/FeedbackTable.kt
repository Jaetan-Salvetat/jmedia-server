package com.jmedia.models.database

import org.jetbrains.exposed.sql.Table

object FeedbackTable: Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val description = text("description")
    val type = varchar("type", 255)

    override val primaryKey = PrimaryKey(id)
}