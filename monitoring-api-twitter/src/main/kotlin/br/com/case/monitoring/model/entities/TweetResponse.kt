package br.com.case.monitoring.model.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("tweets")
class TweetResponse(
    @JsonProperty("created_at")
    var createdAt: String? = null,
    var date: LocalDateTime = LocalDateTime.parse(createdAt, DateTimeFormatter.ofPattern(("E MMM dd HH:mm:ss ZZ yyyy"))),
    val text: String? = null,
    val user: User? = null,
    val lang: String? = null,
    @Indexed
    var hashtag: String? = null
)
