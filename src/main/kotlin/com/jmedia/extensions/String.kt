package com.jmedia.extensions

import com.jmedia.models.local.MediaType
import com.jmedia.models.local.MediaTypeResult

/**
 * Convert a [String] to a [MediaTypeResult].
 * The string need to have this format: **type1,type2**
 *
 * @return [MediaTypeResult]
 */
fun String.getMediaTypes(): MediaTypeResult {
    val mediaTypes = mutableSetOf<MediaType>()

    split(",").forEach {
        val type = MediaType.fromString(it.trim())
            ?: return MediaTypeResult.UnknownType(it.trim())
        mediaTypes.add(type)
    }

    return MediaTypeResult.Success(mediaTypes)
}

/**
 * Convert a [String] to an [Int].
 * Return the default value if the cast fail.
 *
 * @param default [Int]
 * @return [Int]
 */
fun String.toIntOrDefault(default: Int = 0): Int = toIntOrNull() ?: default