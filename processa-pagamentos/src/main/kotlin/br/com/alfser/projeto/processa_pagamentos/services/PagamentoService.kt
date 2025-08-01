package br.com.alfser.projeto.processa_pagamentos.services

import br.com.alfser.projeto.processa_pagamentos.models.Pagamento
import br.com.alfser.projeto.processa_pagamentos.repositories.PagamentoRepository
import org.springframework.stereotype.Service

@Service
class PagamentoService (val pagamentoRepository: PagamentoRepository){

    fun finAllPagamentosPendentes() = pagamentoRepository.findAllByIsProcessadoFalse()
    fun processarPagamentosPendentes(){
        println("Processar pagamentos")
    }
    fun processarByIdPagamento(idPagamento: Long){
        println("Processar pagamento via idPagamento")
    }
    fun salvarPagamento(pagamento: Pagamento) = pagamentoRepository.save(pagamento)
}