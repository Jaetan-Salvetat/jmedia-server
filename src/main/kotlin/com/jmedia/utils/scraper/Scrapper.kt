package com.jmedia.utils.scraper

import com.jmedia.models.local.medias.IMedia

abstract class Scrapper<T : IMedia> {
    /**
     * Search a [T]
     *
     * @param query [String]: Search query
     * @param limit [Int]: Limit of search result
     * @return
     */
    abstract suspend fun search(query: String, limit: Int): Set<T>

    protected abstract fun getUrl(path: String): String

    open fun String.toQuery(): String = replace(" ", "+")

    protected fun String.getNautiljonIdFromUrl(): String = substringAfterLast("/")
        .substringBefore(".")

    protected fun String.getNautiljonRating(): Float? = substringBefore("/")
        .toFloatOrNull()
}