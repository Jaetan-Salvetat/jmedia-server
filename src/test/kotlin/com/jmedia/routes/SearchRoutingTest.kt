package com.jmedia.routes

import com.jmedia.mocks.MangaMockResponse
import com.jmedia.utils.scraper.MangaScrapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.test.BeforeTest

class SearchRoutingTest : BaseApiTest() {
    private val mangaScrapper = mockk<MangaScrapper>()

    @BeforeTest
    fun initialize() {
        coEvery { mangaScrapper.search(SEARCH_QUERY, SMALL_LIMIT) } returns MangaMockResponse.set
    }

    companion object {
        const val BASE_URL = "http://0.0.0.0:8080/api/v1/search"
        const val SEARCH_QUERY = "oshi no"
        const val SMALL_LIMIT = 10
    }
}