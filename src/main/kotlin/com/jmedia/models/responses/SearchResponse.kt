package com.jmedia.models.responses

import com.jmedia.models.local.medias.Anime
import com.jmedia.models.local.medias.Manga
import com.jmedia.models.local.medias.Status
import io.ktor.server.http.*
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    var mangas: Set<SmallMangaResponse> = setOf(),
    var animes: Set<SmallAnimeResponse> = setOf()
)

@Serializable
data class SmallMangaResponse(
    val id: String?,
    val title: String?,
    val description: String?,
    val image: String?,
    val type: String?,
    val vfTomesNumber: Int,
    val voTomesNumber: Int,
    val rating: Float?
)

@Serializable
data class SmallAnimeResponse(
    val id: String?,
    val title: String?,
    val description: String?,
    val image: String?,
    val format: String?,
    val status: Status,
    val totalEpisodes: Int?,
    val startDate: String?,
    val endDate: String?,
    val rating: Float?
)

private fun Manga.toSmallMangaResponse() = SmallMangaResponse(
    id = id,
    title = title,
    description = description,
    image = image,
    type = type,
    vfTomesNumber = vfTomesNumber,
    voTomesNumber = voTomesNumber,
    rating = rating
)
fun Set<Manga>.toSmallMangaResponse(): Set<SmallMangaResponse> = map { it.toSmallMangaResponse() }.toSet()

private fun Anime.toSmallAnimeResponse() = SmallAnimeResponse(
    id = id,
    title = title,
    description = description,
    image = image,
    format = format.name,
    status = status,
    totalEpisodes = totalEpisodes,
    startDate = startDate?.toHttpDateString(),
    endDate = endDate?.toHttpDateString(),
    rating = rating
)
fun Set<Anime>.toSmallAnimeResponse(): Set<SmallAnimeResponse> = map { it.toSmallAnimeResponse() }.toSet()