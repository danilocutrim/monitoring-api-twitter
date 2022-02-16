package br.com.case.monitoring.model.entities

import java.time.LocalDateTime

data class ResultAggByDate(
    val date: LocalDateTime? = null,
    val count: Long? = null,
    val day: String? = null,
    val hour: String? = null
)