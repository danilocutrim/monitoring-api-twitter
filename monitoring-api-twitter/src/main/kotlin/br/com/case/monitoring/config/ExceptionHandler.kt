package br.com.case.monitoring.config

import br.com.case.monitoring.exception.NotFoundException
import br.com.case.monitoring.model.errors.BaseExceptionBody
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNotFoundException(
        ex: NotFoundException,
        request: WebRequest
    ): ResponseEntity<BaseExceptionBody> {
        val ourError = BaseExceptionBody(
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            message = ex.localizedMessage,
            status = HttpStatus.NOT_FOUND.value(),
        )
        return ResponseEntity(ourError, HttpHeaders(), HttpStatus.NOT_FOUND)
    }

}
