package br.com.alfser.projeto.processa_pagamentos.controllers

import br.com.alfser.projeto.processa_pagamentos.dtos.PagamentoDTO
import br.com.alfser.projeto.processa_pagamentos.dtos.ProcessarPagamentoDTO
import br.com.alfser.projeto.processa_pagamentos.services.PagamentoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@Tag(name = "processa-pagamento")
@RestController
@RequestMapping
class PagamentoController (val pagamentoService: PagamentoService){


    /**
     * Lista todos os pagamentos pendentes
     * **/
    @GetMapping("/pagamentos-pendentes")
    @ResponseStatus(HttpStatus.OK)
    @Operation(operationId = "listar-pagamentos-pendentes")
    fun listarPagamentos() = pagamentoService.finAllPagamentosPendentes().map { pagamento -> PagamentoDTO.fromPagamentoModel(pagamento) }

    /**
     * Processa todos os pagamentos que ainda estão pendentes
     * **/
    @PostMapping("/processa-pagamentos-pententes")
    @ResponseStatus(HttpStatus.OK)
    @Operation(operationId = "processar-pagamentos-pendentes")
    fun processarPagamentosPendentes() = pagamentoService.processarPagamentosPendentes()

    /**
     * Processa o pagamento identificado no idPagamento, se existir
     * **/
    @PostMapping("processa-pagamentos-via-id")
    @ResponseStatus(HttpStatus.OK)
    @Operation(operationId = "processar-via-id-pagamento")
    fun processarPagamentoByIdPagamento(@RequestBody dto: ProcessarPagamentoDTO) = pagamentoService.processarByIdPagamento(dto.idPagamento)
}