package br.com.alfser.projeto.pagamentos.dtos;

import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusDTO {

    @NotNull
    private Long idPagamento;

    @NotNull
    private StatusPagamento status;

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
}
