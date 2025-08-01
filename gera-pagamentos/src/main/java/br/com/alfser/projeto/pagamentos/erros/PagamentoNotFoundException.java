package br.com.alfser.projeto.pagamentos.erros;

public class PagamentoNotFoundException extends RuntimeException{

    public PagamentoNotFoundException(String message) {
        super(message);
    }

    public PagamentoNotFoundException() {
        super("Pagamento não encontrado");
    }
}
