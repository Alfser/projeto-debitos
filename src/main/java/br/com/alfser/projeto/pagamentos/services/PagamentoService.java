package br.com.alfser.projeto.pagamentos.services;

import br.com.alfser.projeto.pagamentos.PagamentoRepository;
import br.com.alfser.projeto.pagamentos.common.MetodoPagamento;
import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    public PagamentoService(PagamentoRepository pagamentoRepository){
        this.pagamentoRepository = pagamentoRepository;
    }
    public Pagamento criar(Pagamento pagamento) throws IllegalArgumentException{
        if(pagamento == null){
            throw new IllegalArgumentException("Não é permitido model pagamento nulla no sercice de criação de pagamento");
        }
        pagamento.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> listar(){
        return pagamentoRepository.findAll();
    }

    public boolean isPagamentoComCartao(MetodoPagamento metodo) {
        return metodo == MetodoPagamento.CARTAO_CREDITO || metodo == MetodoPagamento.CARTAO_DEBITO;
    }

}
