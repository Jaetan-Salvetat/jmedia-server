package com.jmedia.models.local

enum class MediaType {
    Manga,
    Anime;

    companion object {
        fun fromString(str: String): MediaType? = MediaType.entries.find {
            it.toString().lowercase() == str.lowercase()
        }
    }
}

sealed class MediaTypeResult {
    data class Success(val types: Set<MediaType>) : MediaTypeResult()
    data object UnknownType : MediaTypeResult()
}