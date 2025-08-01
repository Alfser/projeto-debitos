package br.com.alfser.projeto.processa_pagamentos.erros

class PagamentoPendenteNotFoundException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor() : super("Pagamento pendente não encontrado")
}
