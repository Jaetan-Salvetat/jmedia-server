package com.jmedia.plugins

import io.ktor.server.application.*
import io.ktor.server.config.*
import org.intellij.lang.annotations.Language
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    DatabaseUtils.initialize(environment.config)
}

object DatabaseUtils {
    @Language("SQL")
    private val cacheTables = listOf(
        """""".trimMargin()
    )
    lateinit var cacheDB: Database

    fun initialize(config: ApplicationConfig) {
        println("Initialize database...")

        cacheDB = Database.connect(
            url = config.property("database.cache.url").getString(),
            driver = config.property("database.driver").getString(),
            user = config.property("database.cache.user").getString(),
            password = config.property("database.cache.password").getString()
        )

        createCacheSchemas()

        println("Database initialized 💪")
    }

    private fun createCacheSchemas() {
        println("Create tables...")
        transaction(cacheDB) {
            cacheTables.forEach {
                exec(it)
            }
        }
        println("Cache tables created 💪")
    }
}