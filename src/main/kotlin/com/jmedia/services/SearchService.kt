package com.jmedia.services

import com.jmedia.models.local.MediaType
import com.jmedia.models.responses.SearchResponse
import com.jmedia.models.responses.toSmallAnimeResponse
import com.jmedia.models.responses.toSmallMangaResponse
import com.jmedia.utils.scraper.AnimeScrapper
import com.jmedia.utils.scraper.MangaScrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class SearchService {
    private val mangaScraper = MangaScrapper()
    private val animeScraper = AnimeScrapper()

    /**
     * Start a search
     *
     * @param query [String]
     * @param forType [MediaType]
     * @param limit [Int]: Limit of search results
     * @return [SearchResponse]
     */
    suspend fun search(query: String, forType: MediaType, limit: Int) = CoroutineScope(Dispatchers.IO).async {
        when (forType) {
            MediaType.Manga -> {
                SearchResponse(
                    mangas = mangaScraper
                        .search(query, limit)
                        .toSmallMangaResponse()
                )
            }
            MediaType.Anime -> {
                SearchResponse(
                    animes = animeScraper
                        .search(query, limit)
                        .toSmallAnimeResponse()
                )
            }
        }
    }.await()
}