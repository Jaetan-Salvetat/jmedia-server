package com.jmedia.utils.scraper

import com.jmedia.extensions.fromString
import com.jmedia.extensions.getOrCreate
import com.jmedia.extensions.replaceAt
import com.jmedia.extensions.toIntOrDefault
import com.jmedia.models.local.medias.Anime
import com.jmedia.models.local.medias.Status
import com.jmedia.models.local.medias.shared.AnimeFormat
import it.skrape.core.htmlDocument
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.a
import it.skrape.selects.html5.td
import java.time.LocalDate

class AnimeScrapper : Scrapper<Anime>() {
    private val searchQueries = "?types_exclude%5B%5D=4&edition_sup=2&nb_vol_min=0&nb_chapitres_min=0&titre_alternatif=1&titre_alternatif_suite=1&titre_original_latin=1&titre_original=1&has=&tri=0"
    private val baseUrl = "https://www.nautiljon.com"

    override fun getUrl(path: String) = "$baseUrl/animes/$path"

    override suspend fun search(query: String, limit: Int): Set<Anime> {
        var animes = mutableSetOf<Anime>()

        skrape(AsyncFetcher) {
            request {
                url = getUrl(searchQueries + "&q=${query.toQuery()}")
            }

            response {
                htmlDocument {
                    // Image
                    "a.sim img" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val anime = animes.getOrCreate(index)

                                animes = animes.replaceAt(
                                    index = index,
                                    newValue = anime.copy(image = baseUrl + docElement.attribute("src"))
                                )
                            }
                        }
                    }

                    // Title + id
                    a(".eTitre") {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val anime = animes.getOrCreate(index)

                                animes = animes.replaceAt(
                                    index = index,
                                    newValue = anime.copy(
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
                                val anime = animes.getOrCreate(index)

                                animes = animes.replaceAt(
                                    index = index,
                                    newValue = anime.copy(description = docElement.ownText.replace("...", ""))
                                )
                            }
                        }
                    }

                    // Format
                    "td:nth-child(3)" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val anime = animes.getOrCreate(index)

                                animes = animes.replaceAt(
                                    index = index,
                                    newValue = anime.copy(format = AnimeFormat.fromString(docElement.ownText))
                                )
                            }
                        }
                    }

                    // Status
                    "td:nth-child(4)" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val anime = animes.getOrCreate(index)

                                animes = animes.replaceAt(
                                    index = index,
                                    newValue = anime.copy(status = Status.fromString(docElement.ownText))
                                )
                            }
                        }
                    }

                    // Total episodes
                    "td:nth-child(6)" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val anime = animes.getOrCreate(index)

                                animes = animes.replaceAt(
                                    index = index,
                                    newValue = anime.copy(totalEpisodes = docElement.ownText.toIntOrDefault(0))
                                )
                            }
                        }
                    }

                    // Start date
                    "td:nth-child(7)" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val anime = animes.getOrCreate(index)

                                animes = animes.replaceAt(
                                    index = index,
                                    newValue = anime.copy(
                                        startDate = LocalDate::class.fromString(docElement.ownText)
                                    )
                                )
                            }
                        }
                    }

                    // End date
                    "td:nth-child(8)" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val anime = animes.getOrCreate(index)

                                animes = animes.replaceAt(
                                    index = index,
                                    newValue = anime.copy(
                                        endDate = LocalDate::class.fromString(docElement.ownText)
                                    )
                                )
                            }
                        }
                    }

                    // Rating
                    "td:nth-child(9)" {
                        findAll {
                            forEachIndexed { index, docElement ->
                                val anime = animes.getOrCreate(index)

                                animes = animes.replaceAt(
                                    index = index,
                                    newValue = anime.copy(
                                        rating = docElement.ownText.getNautiljonRating()
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        return animes.chunked(limit).first().toSet()
    }
}