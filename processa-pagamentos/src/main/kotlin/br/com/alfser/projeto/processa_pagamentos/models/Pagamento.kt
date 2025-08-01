package br.com.alfser.projeto.processa_pagamentos.models

import br.com.alfser.projeto.processa_pagamentos.common.StatusPagamento
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal


@Document(collection = "pagamentos")
data class Pagamento (
    @Id
    val id: ObjectId = ObjectId(),
    @Indexed(unique = true) val idPagamento: Long,
    val status: StatusPagamento,
    val isProcessado: Boolean = false
)