package com.jmedia.models.local.medias

import com.jmedia.models.local.medias.shared.Author

data class Manga(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val image: String? = null,
    val type: String? = null,
    val vfTomesNumber: Int = 0,
    val voTomesNumber: Int = 0,
    val rating: Float? = null,
    val author: Set<Author>? = null,
    val publisher: String? = null
) : IMedia