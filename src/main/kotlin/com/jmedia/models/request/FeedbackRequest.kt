package com.jmedia.models.request

import com.jmedia.models.local.FeedbackType
import io.ktor.http.content.*

data class FeedbackRequest(
    val title: String,
    val description: String,
    val type: FeedbackType
) { companion object }

suspend fun FeedbackRequest.Companion.fromFormItem(data: MultiPartData): FeedbackRequest {
    var title = ""
    var description = ""
    var type = ""

    data.forEachPart {
        if (it is PartData.FormItem) {
            when (it.name) {
                "title" -> title = it.value
                "description" -> description = it.value
                "type" -> type = it.value
            }
        }
    }

    return FeedbackRequest(
        title = title,
        description = description,
        type = FeedbackType.fromString(type)
    )
}