package br.com.case.monitoring.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("tweets")
data class TweetResponse(
    @JsonProperty("created_at")
    @DateTimeFormat
    val createdAt: String? = null,
    val text: String? = null,
    val user: User? = null,
    val lang: String? = null,
    @Indexed
    var hashtag: String? = null
)