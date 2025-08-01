package br.com.alfser.projeto.processa_pagamentos.services

import br.com.alfser.projeto.processa_pagamentos.common.BrokerTopic
import br.com.alfser.projeto.processa_pagamentos.dtos.PagamentoDTO
import br.com.alfser.projeto.processa_pagamentos.dtos.PagamentoPendenteDTO
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class BrokerConsumerService(
    private val kafkaTemplate: KafkaTemplate<String?, String?>,
    private val objectMapper: ObjectMapper,
    private val pagamentoService: PagamentoService
) {
    @KafkaListener(topics = [BrokerTopic.PAGAMENTO_PENDENTE])
    @Throws(JsonProcessingException::class)
    fun processMessage(json: String?) {
        val dto: PagamentoPendenteDTO =
            objectMapper.readValue(json, PagamentoPendenteDTO::class.java)
        pagamentoService.salvarPagamento(dto.toPagamento())
        println("CONSUME MESSAGE: " + json)
        println("MESSAGE OBJECT: " + dto.toString())
    }
}