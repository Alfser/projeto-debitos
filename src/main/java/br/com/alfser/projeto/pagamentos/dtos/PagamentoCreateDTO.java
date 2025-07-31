package br.com.alfser.projeto.pagamentos.dtos;

import br.com.alfser.projeto.pagamentos.annotations.ValidPagamentoCreateDTO;
import br.com.alfser.projeto.pagamentos.common.MetodoPagamento;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


@ValidPagamentoCreateDTO
public class PagamentoCreateDTO {

    @NotBlank
    private String cpfCnpj;

    @NotNull
    private MetodoPagamento metodoPagamento;

    private String numeroCartao;

    @NotNull
    @Positive
    private BigDecimal valor;

    public PagamentoCreateDTO(String cpfCnpj, MetodoPagamento metodoPagamento, String numeroCartao, BigDecimal valor) {
        this.cpfCnpj = cpfCnpj;
        this.metodoPagamento = metodoPagamento;
        this.numeroCartao = numeroCartao;
        this.valor = valor;
    }

    public PagamentoCreateDTO() {
    }

    public Pagamento toPagamentoModel() {
        Pagamento pagamento = new Pagamento();
        pagamento.setCpfCnpj(this.cpfCnpj);
        pagamento.setMetodoPagamento(this.metodoPagamento);
        pagamento.setNumeroCartao(this.numeroCartao);
        pagamento.setValor(this.valor);
        return pagamento;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "PagamentoCreateDTO{" +
                "cpfCnpj='" + cpfCnpj + '\'' +
                ", metodoPagamento=" + metodoPagamento +
                ", numeroCartao='" + numeroCartao + '\'' +
                ", valor=" + valor +
                '}';
    }
}