package br.com.alfser.projeto.processa_pagamentos.erros

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime


@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int = 0,
    val error: String? = null,
    val message: String? = null,
    val path: String? = null,
    var details: MutableList<String?>? = null
)