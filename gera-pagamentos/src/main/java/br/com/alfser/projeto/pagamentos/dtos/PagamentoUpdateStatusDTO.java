package br.com.alfser.projeto.pagamentos.dtos;

import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class PagamentoUpdateStatusDTO {

    @NotNull
    private Long idPagamento;

    @NotNull
    private StatusPagamento status;

    public PagamentoUpdateStatusDTO(Long idPagamento, StatusPagamento status) {
        this.idPagamento = idPagamento;
        this.status = status;
    }

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PagamentoUpdateStatusDTO that = (PagamentoUpdateStatusDTO) o;
        return Objects.equals(idPagamento, that.idPagamento) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPagamento, status);
    }

    @Override
    public String toString() {
        return "PagamentoUpdateStatusDTO{" +
                "idPagamento=" + idPagamento +
                ", status=" + status +
                '}';
    }

    public static PagamentoUpdateStatusDTO from(Pagamento pagamento){
        return new PagamentoUpdateStatusDTO(
                pagamento.getIdPagamento(),
                pagamento.getStatus()
        );
    }
}
