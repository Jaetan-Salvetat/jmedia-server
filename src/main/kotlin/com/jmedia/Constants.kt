package com.jmedia

import io.ktor.server.config.*

object Constants {
    lateinit var minioBaseUrl: String

    fun initialize(config: ApplicationConfig) {
        minioBaseUrl = config.property("minio.endpoint").getString()
    }
}