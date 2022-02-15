package br.com.case.monitoring.model.errors

import java.time.LocalDateTime

data class BaseExceptionBody(
    val timeStamp: LocalDateTime = LocalDateTime.now(),
    val status: Int? = null,
    val error: String? = null,
    val message: String? = null,
)
