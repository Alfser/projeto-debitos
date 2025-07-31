package br.com.alfser.projeto.pagamentos.models;

import br.com.alfser.projeto.pagamentos.common.MetodoPagamento;
import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "pagamentos")
public class Pagamento {

    @Id
    ObjectId id = new ObjectId();

    @Indexed(unique = true)
    private Long idPagamento = (long) new ObjectId().getTimestamp();
    private String cpfCnpj;
    private MetodoPagamento metodoPagamento;
    private String numeroCartao;
    private BigDecimal valor;
    private StatusPagamento status = StatusPagamento.PENDENTE_PROCESSAMENTO;
    private boolean ativo = true;

    // Constructors
    public Pagamento() {
    }

    public Pagamento(Long idPagamento, String cpfCnpj, MetodoPagamento metodoPagamento,
                     BigDecimal valor, StatusPagamento status) {
        this.idPagamento = idPagamento;
        this.cpfCnpj = cpfCnpj;
        this.metodoPagamento = metodoPagamento;
        this.valor = valor;
        this.status = status;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
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

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}