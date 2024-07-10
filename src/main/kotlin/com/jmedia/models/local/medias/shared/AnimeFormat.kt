package com.jmedia.models.local.medias.shared

enum class AnimeFormat {
    TV,
    Movie,
    OAV,
    Unknown;

    companion object {
        fun fromString(str: String): AnimeFormat = when (str.lowercase()) {
            "série" -> TV
            "film" -> Movie
            "oav" -> OAV
            else -> Unknown
        }
    }
}