package com.jmedia.utils.scraper

import com.jmedia.models.local.medias.Anime

class AnimeScrapper : Scrapper<Anime>() {
    override fun getUrl(url: String) = "https://www.nautiljon.com/animes"

    override suspend fun search(query: String): Set<Anime> = setOf()
}