package com.jmedia.routes

import com.jmedia.models.local.medias.Manga
import com.jmedia.models.responses.SearchResponse
import com.jmedia.models.responses.toSmallMangaResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.Thread.sleep

fun Route.utilsRouting() {
    route("utils") {
        get("ping") { call.respondText("pong!") }
        get("get-big-data") {
            sleep(200)
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

            call.respond(
                status = HttpStatusCode.OK,
                message = SearchResponse(
                    mangas = (0..25).map { manga.copy(id = "id+$it") }.toSet().toSmallMangaResponse()
                )
            )
        }
    }
}