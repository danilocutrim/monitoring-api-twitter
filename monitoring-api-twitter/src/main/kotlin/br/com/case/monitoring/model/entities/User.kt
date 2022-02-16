package br.com.case.monitoring.model.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    val id: Long? = null,
    val name: String? = null,
    @JsonProperty("followers_count")
    val followersCount: Long? = null,
    val location: String? = null

)