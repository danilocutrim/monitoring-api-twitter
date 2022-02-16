package br.com.case.monitoring.model.request

import java.time.LocalDateTime

data class Result(
    val date: LocalDateTime? = null,
    val total: Long? = null,
    val day: String? = null,
    val hour: String? = null
)