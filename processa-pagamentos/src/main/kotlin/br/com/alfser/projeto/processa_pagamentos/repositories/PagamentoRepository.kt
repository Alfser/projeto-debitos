package br.com.alfser.projeto.processa_pagamentos.repositories

import br.com.alfser.projeto.processa_pagamentos.models.Pagamento
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PagamentoRepository : MongoRepository<Pagamento, ObjectId>{
    fun findAllByIsProcessadoFalse() : List<Pagamento>
}