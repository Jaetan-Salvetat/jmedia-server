package com.jmedia.utils.scraper

import com.jmedia.models.local.medias.IMedia

abstract class Scrapper<T : IMedia> {
    abstract fun getUrl(path: String): String
    abstract suspend fun search(query: String, limit: Int): Set<T>

    open fun String.toQuery(): String = replace(" ", "+")

    protected fun String.getNautiljonIdFromUrl(): String = substringAfterLast("/")
        .substringBefore(".")

    protected fun String.getNautiljonRating(): Float? = substringBefore("/")
        .toFloatOrNull()
}