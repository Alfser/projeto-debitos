package br.com.alfser.projeto.processa_pagamentos.dtos

import br.com.alfser.projeto.processa_pagamentos.common.StatusPagamento
import br.com.alfser.projeto.processa_pagamentos.models.Pagamento


data class PagamentoDTO(
    val id : String,
    val idPagamento: Long,
    val status: StatusPagamento,
    val isProcessado: Boolean
){
    companion object{
        fun fromPagamentoModel(pagamento: Pagamento): PagamentoDTO {
            return PagamentoDTO(
                id = pagamento.id.toString(),
                idPagamento = pagamento.idPagamento,
                status = pagamento.status,
                isProcessado = pagamento.isProcessado
            )
        }
    }
}