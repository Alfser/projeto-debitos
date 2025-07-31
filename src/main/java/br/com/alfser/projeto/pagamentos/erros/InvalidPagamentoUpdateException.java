package br.com.alfser.projeto.pagamentos.erros;

public class InvalidPagamentoUpdateException extends RuntimeException{
    public InvalidPagamentoUpdateException(String message) {
        super(message);
    }

    public InvalidPagamentoUpdateException(){
        super("Transição de status do pagamento não permitida");
    }
}
