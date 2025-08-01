package br.com.alfser.projeto.pagamentos.specification;

import br.com.alfser.projeto.pagamentos.dtos.PagamentoFilterDTO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PagamentoCriteriaQuery {

    public static Query withFilters(
            PagamentoFilterDTO filters
    ) {
        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();

        if (filters.getIdPagamento() != null) {
            criteria.add(Criteria.where("idPagamento").is(filters.getIdPagamento()));
        }

        if (filters.getCpfCnpj() != null) {
            criteria.add(Criteria.where("cpfCnpj").regex(filters.getCpfCnpj(), "i"));
        }

        if (filters.getStatus() != null) {
            criteria.add(Criteria.where("status").is(filters.getStatus()));
        }

        if (filters.getAtivo() != null) {
            criteria.add(Criteria.where("ativo").is(filters.getAtivo()));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return query;
    }
}
