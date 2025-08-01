package br.com.alfser.projeto.pagamentos.dtos;

import br.com.alfser.projeto.pagamentos.common.StatusPagamento;

public class PagamentoFilterDTO {
    private Long idPagamento;
    private String cpfCnpj;
    private StatusPagamento status;
    private Boolean ativo;

    public PagamentoFilterDTO(Long idPagamento, String cpfCnpj, StatusPagamento status, Boolean ativo) {
        this.idPagamento = idPagamento;
        this.cpfCnpj = cpfCnpj;
        this.status = status;
        this.ativo = ativo;
    }

    public PagamentoFilterDTO(Long idPagamento, String cpfCnpj, StatusPagamento status) {
        this.idPagamento = idPagamento;
        this.cpfCnpj = cpfCnpj;
        this.status = status;
    }

    public PagamentoFilterDTO(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public PagamentoFilterDTO(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public PagamentoFilterDTO(StatusPagamento status) {
        this.status = status;
    }

    public PagamentoFilterDTO(Boolean ativo) {
        this.ativo = ativo;
    }

    public PagamentoFilterDTO() {
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

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
