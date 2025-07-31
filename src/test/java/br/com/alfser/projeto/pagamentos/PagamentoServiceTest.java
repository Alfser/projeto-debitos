package br.com.alfser.projeto.pagamentos;

import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoFilterDTO;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import br.com.alfser.projeto.pagamentos.repositories.PagamentoRepository;
import br.com.alfser.projeto.pagamentos.services.PagamentoService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {PagamentoService.class})
public class PagamentoServiceTest {

    @MockitoBean
    private PagamentoRepository pagamentoRepository;

    @MockitoBean
    private  MongoTemplate mongoTemplate;

    @InjectMocks
    private PagamentoService pagamentoService;

    @Test
    public void criar_DeveLancarExcecao_QuandoPagamentoForNulo() {
            // Arrange & Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> pagamentoService.criar(null),
                    "Deveria lançar IllegalArgumentException quando pagamento for nulo");
        }

    @Test
    public void listar_DeveRetornarListaDePagamentos_QuandoExistiremRegistros() {
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setId(new ObjectId());
        pagamento1.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId(new ObjectId());
        pagamento2.setStatus(StatusPagamento.PROCESSADO_SUCESSO);

        List<Pagamento> pagamentos = Arrays.asList(pagamento1, pagamento2);
        long totalCount = 2L;

        // Mock the query execution
        when(mongoTemplate.find(any(Query.class), eq(Pagamento.class)))
                .thenReturn(pagamentos);

        // Mock the count operation
        when(mongoTemplate.count(any(Query.class), eq(Pagamento.class)))
                .thenReturn(totalCount);

        // Act
        Page<Pagamento> resultPage = pagamentoService.listar(
                new PagamentoFilterDTO(),
                PageRequest.of(0, 10)
        );

        // Assert
        Assertions.assertEquals(2, resultPage.getContent().size());
        Assertions.assertEquals(2, resultPage.getTotalElements());

        // Verify mongoTemplate interactions
        verify(mongoTemplate, times(1))
                .find(any(Query.class), eq(Pagamento.class));
        verify(mongoTemplate, times(1))
                .count(any(Query.class), eq(Pagamento.class));
    }

    @Test
    public void listar_DeveRetornarListaVazia_QuandoNaoExistiremRegistros() {
        when(pagamentoRepository.findAll()).thenReturn(List.of());
        List<Pagamento> resultado = pagamentoService.listar(new PagamentoFilterDTO(), PageRequest.of(0, 10)).stream().toList();
        Assertions.assertTrue(resultado.isEmpty());
        verify(pagamentoRepository, times(1)).findAll();
    }
}