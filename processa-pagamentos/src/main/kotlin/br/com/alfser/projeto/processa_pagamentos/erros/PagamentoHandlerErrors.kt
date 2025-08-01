package br.com.alfser.projeto.processa_pagamentos.erros

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.List

@RestControllerAdvice
class PagamentoHandlerErrors {

    @ExceptionHandler(PagamentoPendenteNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlePagamentoPendenteNotFoundException(
        ex: PagamentoPendenteNotFoundException,
        request: HttpServletRequest
    ): ErrorResponse? {
        return ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.getReasonPhrase(),
            message = "Pagamento não encontrado",
            path = request.getRequestURI(),
            details = List.of(ex.message)
        )
    }
}