package com.jmedia.extensions

import io.ktor.http.content.*
import java.io.File
import java.util.*

suspend fun MultiPartData.getFiles(): List<File> {
    val files = mutableListOf<File>()

    forEachPart {
        if (it is PartData.FileItem) {
            println("filename: ${it.originalFileName}")

            val fileName = UUID.randomUUID().toString()
            val bytes = it.streamProvider().readBytes()
            val file = File("upload", fileName)

            file.writeBytes(bytes)
            files.add(file)
        }
    }

    println("files length: ${files.size}")

    return  files
}