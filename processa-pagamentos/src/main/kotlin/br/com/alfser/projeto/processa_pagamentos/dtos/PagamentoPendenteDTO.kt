package br.com.alfser.projeto.processa_pagamentos.dtos

import br.com.alfser.projeto.processa_pagamentos.common.StatusPagamento
import br.com.alfser.projeto.processa_pagamentos.models.Pagamento


data class PagamentoPendenteDTO(
    val idPagamento: Long,
    val status: StatusPagamento,
){

    fun toPagamento(): Pagamento{
        return Pagamento(
            idPagamento = idPagamento,
            status = status
        )
    }
}