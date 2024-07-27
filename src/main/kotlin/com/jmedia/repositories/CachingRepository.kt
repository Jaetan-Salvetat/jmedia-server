package com.jmedia.repositories

import com.github.benmanes.caffeine.cache.Caffeine
import com.jmedia.models.responses.SearchResponse
import java.time.Duration

object CachingRepository {
    val search = Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofHours(24))
        .maximumSize(10_000)
        .build<String, SearchResponse>()
}