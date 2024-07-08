package com.jmedia.services

import com.jmedia.models.local.MediaType
import com.jmedia.models.responses.SearchResponse
import com.jmedia.models.responses.toSmallAnimeResponse
import com.jmedia.models.responses.toSmallMangaResponse
import com.jmedia.utils.scraper.AnimeScrapper
import com.jmedia.utils.scraper.MangaScrapper

class SearchService {
    private val mangaScraper = MangaScrapper()
    private val animeScraper = AnimeScrapper()

    suspend fun search(query: String, types: Set<MediaType>): SearchResponse {
        val result = SearchResponse()

        types.forEach { type ->
            when (type) {
                MediaType.Manga -> result.mangas = mangaScraper.search(query).toSmallMangaResponse()
                MediaType.Anime -> result.animes = animeScraper.search(query).toSmallAnimeResponse()
            }
        }

        return result
    }
}
