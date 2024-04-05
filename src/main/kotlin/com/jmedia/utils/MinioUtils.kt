package com.jmedia.utils

import io.ktor.server.config.*
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.UploadObjectArgs
import java.io.File

object MinioUtils {
    private lateinit var minio: MinioClient

    fun initialize(config: ApplicationConfig) {
        minio = MinioClient.builder()
            .endpoint(config.property("minio.endpoint").getString())
            .credentials(
                config.property("minio.accessKey").getString(),
                config.property("minio.secretKey").getString()
            )
            .build()

        Bucket.entries.forEach { createBucketIfNotExist(it) }
    }

    fun uploadImages(bucket: Bucket, files: List<File>) {
        files.forEach { uploadImage(bucket, it) }
    }

    private fun uploadImage(bucket: Bucket, file: File) {
        minio.uploadObject(
            UploadObjectArgs.builder()
                .bucket(bucket.label)
                .`object`(file.name)
                .filename(file.absolutePath)
                .build()
        )
    }

    private fun createBucketIfNotExist(bucket: Bucket) {
        val exist = minio.bucketExists(BucketExistsArgs.builder().bucket(bucket.label).build())

        if (!exist) {
            minio.makeBucket(MakeBucketArgs.builder().bucket(bucket.label).build())
        }
    }
}

enum class Bucket {
    Feedback;

    val label: String
        get() = name.lowercase()

    companion object {
        fun toPathList(bucket: Bucket, files: List<File>): List<String> = files.map {
            toPath(bucket, it)
        }

        fun toPath(bucket: Bucket, file: File): String = "${bucket.label}/${file.name}"
    }
}