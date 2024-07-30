package com.jmedia.mocks

import com.jmedia.models.responses.SearchResponse
import com.jmedia.models.responses.toSmallMangaResponse

object SearchMockResponse {
    val mangas = SearchResponse(
        mangas = MangaMockResponse.set.toSmallMangaResponse()
    )
}