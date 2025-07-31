package br.com.alfser.projeto.pagamentos.dtos;

import br.com.alfser.projeto.pagamentos.common.MetodoPagamento;
import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import lombok.Data;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@Data
public class PagamentoDTO {
    private String id;
    private Long idPagamento;
    private String cpfCnpj;
    private MetodoPagamento metodoPagamento;
    private String numeroCartao;
    private BigDecimal valor;
    private StatusPagamento status;
    private boolean ativo;

    public PagamentoDTO() {
    }

    public PagamentoDTO(String id, Long idPagamento, String cpfCnpj,
                        MetodoPagamento metodoPagamento,
                        String numeroCartao, BigDecimal valor,
                        StatusPagamento status, boolean ativo) {
        this.id = id;
        this.idPagamento = idPagamento;
        this.cpfCnpj = cpfCnpj;
        this.metodoPagamento = metodoPagamento;
        this.numeroCartao = numeroCartao;
        this.valor = valor;
        this.status = status;
        this.ativo = ativo;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Pagamento toPagamentoModel() {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(this.id != null ? new ObjectId(this.id) : null);
        pagamento.setIdPagamento(this.idPagamento);
        pagamento.setCpfCnpj(this.cpfCnpj);
        pagamento.setMetodoPagamento(this.metodoPagamento);
        pagamento.setNumeroCartao(this.numeroCartao);
        pagamento.setValor(this.valor);
        pagamento.setStatus(this.status);
        pagamento.setAtivo(this.ativo);
        return pagamento;
    }

    public static PagamentoDTO fromPagamentoModel(Pagamento pagamento) {
        if(pagamento == null){
            return null;
        }
        PagamentoDTO dto = new PagamentoDTO();
        dto.setId(pagamento.getId() != null ? pagamento.getId().toString() : null);
        dto.setIdPagamento(pagamento.getIdPagamento());
        dto.setCpfCnpj(pagamento.getCpfCnpj());
        dto.setMetodoPagamento(pagamento.getMetodoPagamento());
        dto.setNumeroCartao(pagamento.getNumeroCartao());
        dto.setValor(pagamento.getValor());
        dto.setStatus(pagamento.getStatus());
        dto.setAtivo(pagamento.isAtivo());
        return dto;
    }
}