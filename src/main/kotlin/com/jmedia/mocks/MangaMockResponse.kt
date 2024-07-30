package com.jmedia.mocks

import com.jmedia.models.local.medias.Manga

object MangaMockResponse {
    private val one = Manga(
        id = "id",
        title = "Title",
        description = "Description, Description, Description, Description, Description, Description, Description",
        image = "my image",
        type = "Seinen",
        voTomesNumber = 14,
        vfTomesNumber = 12,
        rating = 9.99f
    )

    val set = buildSet<Manga>(50) { one }
}