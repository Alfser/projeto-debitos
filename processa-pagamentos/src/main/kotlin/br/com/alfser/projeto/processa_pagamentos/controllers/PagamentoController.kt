package br.com.alfser.projeto.processa_pagamentos.controllers

import br.com.alfser.projeto.processa_pagamentos.dtos.PagamentoDTO
import br.com.alfser.projeto.processa_pagamentos.dtos.ProcessarPagamentoDTO
import br.com.alfser.projeto.processa_pagamentos.services.PagamentoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping
class PagamentoController (val pagamentoService: PagamentoService){


    /**
     * Lista todos os pagamentos pendentes
     * **/
    @GetMapping("/pagamentos-pendentes")
    @ResponseStatus(HttpStatus.OK)
    fun listarPagamentos() = pagamentoService.finAllPagamentosPendentes().map { pagamento -> PagamentoDTO.fromPagamentoModel(pagamento) }

    /**
     * Processa todos os pagamentos que ainda estão pendentes
     * **/
    @PostMapping("/processa-pagamentos-pententes")
    @ResponseStatus(HttpStatus.OK)
    fun processarPagamentosPendentes() = pagamentoService.processarPagamentosPendentes()

    /**
     * Processa o pagamento identificado no idPagamento, se existir
     * **/
    @PostMapping("processa-pagamentos-via-id")
    @ResponseStatus(HttpStatus.OK)
    fun processarPagamentoByIdPagamento(@RequestBody dto: ProcessarPagamentoDTO) = pagamentoService.processarByIdPagamento(dto.idPagamento)
}