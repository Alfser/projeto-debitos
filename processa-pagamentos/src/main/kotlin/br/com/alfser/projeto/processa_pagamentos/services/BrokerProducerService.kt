package br.com.alfser.projeto.processa_pagamentos.services

import br.com.alfser.projeto.processa_pagamentos.common.BrokerTopic
import br.com.alfser.projeto.processa_pagamentos.dtos.PagamentoDTO
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class BrokerProducerService (
    private val kafkaTemplate: KafkaTemplate<String?, String?>,
    private val objectMapper: ObjectMapper
){

    @Throws(JsonProcessingException::class)
    fun sendMessage(dto: PagamentoDTO?) {
        val json = objectMapper.writeValueAsString(dto)
        println("PRODUCE MESSAGE: " + json)
        kafkaTemplate.send(BrokerTopic.PAGAMENTO_STATUS, json)
    }
}