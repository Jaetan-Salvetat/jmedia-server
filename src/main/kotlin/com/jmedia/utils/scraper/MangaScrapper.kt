package com.jmedia.utils.scraper

import com.jmedia.extensions.getOrCreate
import com.jmedia.extensions.replaceAt
import com.jmedia.extensions.toIntOrDefault
import com.jmedia.models.local.medias.Manga
import it.skrape.core.htmlDocument
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.a
import it.skrape.selects.html5.td

class MangaScrapper : Scrapper<Manga>() {
    private val searchQueries = "?types_exclude%5B%5D=4&edition_sup=2&nb_vol_min=0&nb_chapitres_min=0&titre_alternatif=1&titre_alternatif_suite=1&titre_original_latin=1&titre_original=1&has=&tri=0"
    private val baseUrl = "https://www.nautiljon.com"
    
    override fun getUrl(url: String) = "$baseUrl/mangas/$url"

    override suspend fun search(query: String): Set<Manga> {
        var mangas = mutableSetOf<Manga>()

        return skrape(AsyncFetcher) {
            request {
                url = getUrl(searchQueries + "&q=${query.toQuery()}")
            }
            response {
                htmlDocument {
                    // Title + id
                    "a.sim img" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val manga = mangas.getOrCreate(index)

                                mangas = mangas.replaceAt(
                                    index = index,
                                    newValue = manga.copy(image = baseUrl + docElement.attribute("src"))
                                )
                            }
                        }
                    }

                    // Title + id
                    a(".eTitre") {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val manga = mangas.getOrCreate(index)

                                mangas = mangas.replaceAt(
                                    index = index,
                                    newValue = manga.copy(
                                        id = docElement.attribute("href").getNautiljonIdFromUrl(),
                                        title = docElement.ownText
                                    )
                                )
                            }
                        }
                    }

                    // Description
                    td(".left.vtop p") {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val manga = mangas.getOrCreate(index)

                                mangas = mangas.replaceAt(
                                    index = index,
                                    newValue = manga.copy(description = docElement.ownText.replace("...", ""))
                                )
                            }
                        }
                    }

                    // Type
                    "td:nth-child(3)" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val manga = mangas.getOrCreate(index)

                                mangas = mangas.replaceAt(
                                    index = index,
                                    newValue = manga.copy(type = docElement.ownText)
                                )
                            }
                        }
                    }

                    // V0 number of volumes
                    "td:nth-child(4)" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val manga = mangas.getOrCreate(index)

                                mangas = mangas.replaceAt(
                                    index = index,
                                    newValue = manga.copy(voTomesNumber = docElement.ownText.toIntOrDefault(0))
                                )
                            }
                        }
                    }

                    // VF number of volumes
                    "td:nth-child(5)" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val manga = mangas.getOrCreate(index)

                                mangas = mangas.replaceAt(
                                    index = index,
                                    newValue = manga.copy(vfTomesNumber = docElement.ownText.toIntOrDefault(0))
                                )
                            }
                        }
                    }

                    // Rating
                    "td:nth-child(9)" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val manga = mangas.getOrCreate(index)

                                mangas = mangas.replaceAt(
                                    index = index,
                                    newValue = manga.copy(
                                        rating = docElement.ownText.getNautiljonRating()
                                    )
                                )
                            }
                        }
                    }

                    mangas.chunked(10).first().toSet()
                }
            }
        }
    }
}