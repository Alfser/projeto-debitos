package br.com.alfser.projeto.pagamentos.dtos;

import br.com.alfser.projeto.pagamentos.annotations.CondicionalMetodoPagamento;
import br.com.alfser.projeto.pagamentos.common.MetodoPagamento;
import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import lombok.Data;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@Data
public class PagamentoCreateDTO {
    private String cpfCnpj;
    private MetodoPagamento metodoPagamento;

    @CondicionalMetodoPagamento(
            paymentMethods = {MetodoPagamento.CARTAO_CREDITO, MetodoPagamento.CARTAO_DEBITO},
            message = "Número do cartão é obrigatório para pagamentos com cartão"
    )
    private String numeroCartao;
    private BigDecimal valor;

    public PagamentoCreateDTO() {
    }

    public PagamentoCreateDTO(String cpfCnpj,
                              MetodoPagamento metodoPagamento,
                              String numeroCartao, BigDecimal valor) {
        this.cpfCnpj = cpfCnpj;
        this.metodoPagamento = metodoPagamento;
        this.numeroCartao = numeroCartao;
        this.valor = valor;
    }

    public Pagamento toPagamentoModel() {
        Pagamento pagamento = new Pagamento();
        pagamento.setCpfCnpj(this.cpfCnpj);
        pagamento.setMetodoPagamento(this.metodoPagamento);
        pagamento.setNumeroCartao(this.numeroCartao);
        pagamento.setValor(this.valor);
        return pagamento;
    }
}