package com.jmedia.services

import com.jmedia.models.local.MediaType
import com.jmedia.models.responses.SearchResponse
import com.jmedia.models.responses.toSmallAnimeResponse
import com.jmedia.models.responses.toSmallMangaResponse
import com.jmedia.repositories.CachingRepository
import com.jmedia.utils.scraper.AnimeScrapper
import com.jmedia.utils.scraper.MangaScrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchService {
    private val mangaScraper = MangaScrapper()
    private val animeScraper = AnimeScrapper()

    /**
     * Start a search
     *
     * @param query [String]
     * @param types [Set] of [MediaType]
     * @param limit [Int]: Limit of search results
     * @return [SearchResponse]
     */
    suspend fun search(query: String, types: Set<MediaType>, limit: Int): SearchResponse {
        CachingRepository.search.getIfPresent(query)?.let {
            return it
        }

        val result = SearchResponse()

        CoroutineScope(Dispatchers.IO).async {
            types.forEach { type ->
                launch(coroutineContext) {
                    when (type) {
                        MediaType.Manga -> {
                            result.mangas = mangaScraper
                                .search(query, limit)
                                .toSmallMangaResponse()
                        }
                        MediaType.Anime -> {
                            result.animes = animeScraper
                                .search(query, limit)
                                .toSmallAnimeResponse()
                        }
                    }
                }
            }
        }.await()

        CachingRepository.search.put(query, result)
        return result
    }
}