package br.com.alfser.projeto.pagamentos;

import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import br.com.alfser.projeto.pagamentos.services.PagamentoService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

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
        // Arrange
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setId(new ObjectId());
        pagamento1.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId(new ObjectId());
        pagamento2.setStatus(StatusPagamento.PROCESSADO_SUCESSO);

        List<Pagamento> pagamentos = Arrays.asList(pagamento1, pagamento2);

        when(pagamentoRepository.findAll()).thenReturn(pagamentos);

        // Act
        List<Pagamento> resultado = pagamentoService.listar();

        // Assert
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertNotNull(resultado.get(0).getId());
        Assertions.assertNotNull(resultado.get(1).getId());
        verify(pagamentoRepository, times(1)).findAll();
    }

    @Test
    public void listar_DeveRetornarListaVazia_QuandoNaoExistiremRegistros() {
        // Arrange
        when(pagamentoRepository.findAll()).thenReturn(List.of());

        // Act
        List<Pagamento> resultado = pagamentoService.listar();

        // Assert
        Assertions.assertTrue(resultado.isEmpty());
        verify(pagamentoRepository, times(1)).findAll();
    }
}