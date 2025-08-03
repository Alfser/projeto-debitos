package br.com.alfser.projeto.pagamentos.services;

import br.com.alfser.projeto.pagamentos.dtos.PagamentoUpdateStatusDTO;
import br.com.alfser.projeto.pagamentos.erros.InvalidPagamentoUpdateException;
import br.com.alfser.projeto.pagamentos.erros.PagamentoNotAvailableChangeException;
import br.com.alfser.projeto.pagamentos.erros.PagamentoNotFoundException;
import br.com.alfser.projeto.pagamentos.repositories.PagamentoRepository;
import br.com.alfser.projeto.pagamentos.common.MetodoPagamento;
import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoFilterDTO;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import br.com.alfser.projeto.pagamentos.specification.PagamentoCriteriaQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final BrokerProducerService messageBrokerService;
    private final MongoTemplate mongoTemplate;

    public PagamentoService(PagamentoRepository pagamentoRepository, MongoTemplate mongoTemplate, BrokerProducerService messageBrokerService){
        this.pagamentoRepository = pagamentoRepository;
        this.messageBrokerService = messageBrokerService;
        this.mongoTemplate = mongoTemplate;
    }
    public Pagamento criar(Pagamento pagamento) throws IllegalArgumentException{
        if(pagamento == null){
            throw new IllegalArgumentException("Não é permitido model pagamento nulla no sercice de criação de pagamento");
        }
        pagamento.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);
        Pagamento pagamentoCreated = pagamentoRepository.save(pagamento);
        try {
            messageBrokerService.sendMessage(PagamentoUpdateStatusDTO.from(pagamentoCreated));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return pagamentoCreated;
    }

    public Page<Pagamento> listar(PagamentoFilterDTO filters, Pageable pageable){
        Query query = PagamentoCriteriaQuery.withFilters(filters).with(pageable);
        List<Pagamento> results = mongoTemplate.find(query, Pagamento.class);
        Query countQuery = PagamentoCriteriaQuery.withFilters(filters).skip(-1).limit(-1);
        long total = mongoTemplate.count(countQuery, Pagamento.class);
        return PageableExecutionUtils.getPage(results, pageable, () -> total);
    }

    public boolean isPagamentoComCartao(MetodoPagamento metodo) {
        return metodo == MetodoPagamento.CARTAO_CREDITO || metodo == MetodoPagamento.CARTAO_DEBITO;
    }

    public void desativarPagamento(ObjectId id) throws PagamentoNotAvailableChangeException{
        if(pagamentoRepository.existsByIdAndStatus(id, StatusPagamento.PENDENTE_PROCESSAMENTO)){
            this.updateAtivoById(id, false);
        }else {
            throw new PagamentoNotAvailableChangeException("Somente pagamentos em processamento podem ser excluídos");
        }
    }

    public void atualizarStatusPagamento(Long idPagamento, StatusPagamento novoStatus) throws InvalidPagamentoUpdateException, PagamentoNotFoundException {
        Query query = Query.query(Criteria.where("idPagamento").is(idPagamento));
        Pagamento pagamento = mongoTemplate.findOne(query, Pagamento.class);
        if (pagamento == null) {
            throw new PagamentoNotFoundException();
        }

        if (!isValidTransition(pagamento.getStatus(), novoStatus)) {
            throw new InvalidPagamentoUpdateException(
                    String.format("Transição inválida de %s para %s",
                            pagamento.getStatus(), novoStatus)
            );
        }

        Update update = new Update()
                .set("status", novoStatus);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Pagamento.class);

        if (result.getModifiedCount() == 0) {
            throw new RuntimeException("Falha ao atualizar status");
        }
    }

    private boolean isValidTransition(StatusPagamento current, StatusPagamento newStatus) {
        if (current == StatusPagamento.PROCESSADO_SUCESSO) {
            return false; // Cannot change after success
        }

        return switch (current) {
            case PENDENTE_PROCESSAMENTO ->
                    newStatus == StatusPagamento.PROCESSADO_SUCESSO ||
                            newStatus == StatusPagamento.PROCESSADO_FALHA;
            case PROCESSADO_FALHA ->
                    newStatus == StatusPagamento.PENDENTE_PROCESSAMENTO;
            default -> false;
        };
    }

    private void updateAtivoById(ObjectId id, boolean ativo) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("ativo", ativo);
        mongoTemplate.updateFirst(query, update, Pagamento.class);
    }
}
