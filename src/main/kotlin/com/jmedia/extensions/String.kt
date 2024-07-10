package com.jmedia.extensions

import com.jmedia.models.local.MediaType
import com.jmedia.models.local.MediaTypeResult

fun String.getMediaTypes(): MediaTypeResult {
    val mediaTypes = mutableSetOf<MediaType>()

    split(",").forEach {
        val type = MediaType.fromString(it)
            ?: return MediaTypeResult.UnknownType
        mediaTypes.add(type)
    }

    return MediaTypeResult.Success(mediaTypes)
}

fun String.toIntOrDefault(default: Int): Int = toIntOrNull() ?: default