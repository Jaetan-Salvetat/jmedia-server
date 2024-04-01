package com.jmedia.plugins

import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val config = environment.config
    println(config.property("database.url").getString())

    try {
        DatabaseUtils.initialize(config)
        DatabaseUtils.createSchemas()
    } catch (_: Exception) {}
}

private object DatabaseUtils {
    private val tables = listOf(
        """CREATE TABLE IF NOT EXISTS feedback (
                id SERIAL PRIMARY KEY,
                title VARCHAR(255),
                description TEXT,
                type VARCHAR(255)
        )"""
    )

    fun initialize(config: ApplicationConfig) {
        Database.connect(
            url = config.property("database.url").getString(),
            driver = config.property("database.driver").getString(),
            user = config.property("database.user").getString(),
            password = config.property("database.password").getString()
        )
    }

    fun createSchemas() {
        transaction {
            tables.forEach {
                exec(it)
            }
        }
    }
}