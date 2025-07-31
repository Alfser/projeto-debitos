package br.com.alfser.projeto.pagamentos.repositories;

import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PagamentoRepository extends MongoRepository<Pagamento, ObjectId> {
    boolean existsByIdAndStatus(ObjectId id, StatusPagamento status);
}
