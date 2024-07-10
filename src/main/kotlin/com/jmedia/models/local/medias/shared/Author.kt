package com.jmedia.models.local.medias.shared

data class Author(
    val name: String,
    val role: AuthorRole = AuthorRole.All
)

enum class AuthorRole {
    Scriptwriter,
    Designer,
    All
}