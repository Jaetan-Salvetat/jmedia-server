package com.jmedia.models.local.medias

import com.jmedia.models.local.medias.shared.Author

data class Anime(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val image: String? = null,
    val format: String? = null,
    val status: Status = Status.Unknown,
    val totalEpisodes: Int? = null,
    val rating: Float? = null,
    val author: Set<Author>? = null,
    val publisher: String? = null
) : IMedia

enum class Status {
    InProgress,
    Ended,
    BeComing,
    Unknown;

    companion object {
        fun fromInt(status: String): Status = when (status) {
            "Terminée" -> Ended
            "À venir" -> BeComing
            "En cours" -> InProgress
            else -> {
                println("SERVER_ERROR: Unknown status $status")
                Unknown
            }
        }
    }
}