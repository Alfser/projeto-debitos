package br.com.alfser.projeto.pagamentos.erros;

public class PagamentoNotAvailableChangeException extends RuntimeException{

    public PagamentoNotAvailableChangeException(String message) {
        super(message);
    }

    public PagamentoNotAvailableChangeException() {
        super("Pagamento processado com sucesso não pode ser modificado");
    }
}
