package com.jmedia.utils

import io.ktor.http.content.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.io.File
import java.util.*

suspend fun <T> suspendedTransaction(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }

suspend fun getFilesFromPartData(data: MultiPartData): List<File> {
    val files = mutableListOf<File>()

    data.forEachPart {
        if (it is PartData.FileItem) {
            val fileName = UUID.randomUUID().toString()
            val fileExtension = it.originalFileName?.split(".")?.last()
            val bytes = it.streamProvider().readBytes()
            val file = File.createTempFile("", "$fileName.$fileExtension")

            file.writeBytes(bytes)
            files.add(file)
        }
    }

    return files
}