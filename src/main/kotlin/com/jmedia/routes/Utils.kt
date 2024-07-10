package com.jmedia.routes

import com.jmedia.models.local.medias.Anime
import com.jmedia.models.local.medias.Manga
import com.jmedia.models.responses.SearchResponse
import com.jmedia.models.responses.toSmallAnimeResponse
import com.jmedia.models.responses.toSmallMangaResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.Thread.sleep
import java.time.LocalDate

fun Route.utilsRouting() {
    route("utils") {
        get("ping") { call.respondText("pong!") }
        get("get-big-data") {
            sleep(300)
            val manga = Manga(
                id = "id",
                title = "Title",
                description = "Description, Description, Description, Description, Description, Description, Description",
                image = "my image",
                type = "Seinen",
                voTomesNumber = 14,
                vfTomesNumber = 12,
                rating = 9.99f
            )
            val anime = Anime(
                id = "id",
                title = "Title",
                description = "Description, Description, Description, Description, Description, Description",
                image = "my image",
                totalEpisodes = 15,
                startDate = LocalDate.EPOCH,
                endDate = LocalDate.EPOCH,
                rating = 9.99f
            )

            call.respond(
                status = HttpStatusCode.OK,
                message = SearchResponse(
                    animes = (0..25).map { anime.copy(id = "id+$it") }.toSet().toSmallAnimeResponse(),
                    mangas = (0..25).map { manga.copy(id = "id+$it") }.toSet().toSmallMangaResponse()
                )
            )
        }
    }
}