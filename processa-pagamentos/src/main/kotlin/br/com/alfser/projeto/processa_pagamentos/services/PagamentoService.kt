package br.com.alfser.projeto.processa_pagamentos.services

import br.com.alfser.projeto.processa_pagamentos.common.StatusPagamento
import br.com.alfser.projeto.processa_pagamentos.dtos.PagamentoPendenteDTO
import br.com.alfser.projeto.processa_pagamentos.erros.PagamentoPendenteNotFoundException
import br.com.alfser.projeto.processa_pagamentos.models.Pagamento
import br.com.alfser.projeto.processa_pagamentos.repositories.PagamentoRepository
import com.mongodb.client.result.UpdateResult
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class PagamentoService (private val pagamentoRepository: PagamentoRepository, private val mongoTemplate: MongoTemplate, private val brokerProducerService: BrokerProducerService){

    fun finAllPagamentosPendentes() = pagamentoRepository.findAllByIsProcessadoFalse()
    fun processarPagamentosPendentes(){
        println("Processar pagamentos")
    }
    fun processarByIdPagamento(idPagamento: Long){
        val query = Query.query(Criteria.where("idPagamento").`is`(idPagamento))
        query.addCriteria(Criteria.where("isProcessado").`is`(false))

        val pagamento: Pagamento? = mongoTemplate.findOne(query, Pagamento::class.java)
        if(pagamento == null){
            throw PagamentoPendenteNotFoundException()
        }
        val update: Update = Update()
        update.set("isProcessado", true)
        update.set("status", StatusPagamento.PROCESSADO_SUCESSO)
        val result: UpdateResult = mongoTemplate.updateFirst(query, update, Pagamento::class.java)
        if(result.modifiedCount > 0L){
            brokerProducerService.sendMessage(PagamentoPendenteDTO(pagamento.idPagamento, StatusPagamento.PROCESSADO_SUCESSO))
        }else{
            brokerProducerService.sendMessage(PagamentoPendenteDTO(pagamento.idPagamento, StatusPagamento.PROCESSADO_FALHA))
        }

    }
    fun salvarPagamento(pagamento: Pagamento) = pagamentoRepository.save(pagamento)
}