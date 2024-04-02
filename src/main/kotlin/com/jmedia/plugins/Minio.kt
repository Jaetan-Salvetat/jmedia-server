package com.jmedia.plugins

import com.jmedia.Bucket
import com.jmedia.MinioUtils
import io.ktor.server.application.*

fun Application.configureMinio() {
    val config = environment.config

    MinioUtils.initialize(config)
    MinioUtils.uploadImage(Bucket.Feedback)
}