package br.com.alfser.projeto.pagamentos;

import br.com.alfser.projeto.pagamentos.common.MetodoPagamento;
import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import br.com.alfser.projeto.pagamentos.services.PagamentoService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@SpringJUnitConfig
@Import(TestcontainersConfiguration.class)
public class PagamentoServiceIntegrationTest {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoService pagamentoService;

    @Test
    public void criar_DeveSalvarPagamentoNoBanco_QuandoDadosValidos() {

        Pagamento pagamento = new Pagamento();
        pagamento.setValor(BigDecimal.valueOf(100.50));
        pagamento.setCpfCnpj("123.456.789-09");
        pagamento.setMetodoPagamento(MetodoPagamento.PIX);

        Pagamento resultado = pagamentoService.criar(pagamento);

        Assertions.assertNotNull(resultado.getId());
        Assertions.assertEquals(StatusPagamento.PENDENTE_PROCESSAMENTO, resultado.getStatus());
        Assertions.assertEquals(BigDecimal.valueOf(100.50), resultado.getValor());

        Pagamento pagamentoDoBanco = pagamentoService.listar().stream()
                .filter(p -> p.getId().equals(resultado.getId()))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(pagamentoDoBanco);
        Assertions.assertEquals(pagamento.getCpfCnpj(), pagamentoDoBanco.getCpfCnpj());
    }

    @Test
    public void listar_DeveRetornarPagamentosSalvos_QuandoExistiremNoBanco() {
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setValor(BigDecimal.valueOf(50.00));
        pagamentoService.criar(pagamento1);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setValor(BigDecimal.valueOf(150.00));
        pagamentoService.criar(pagamento2);

        List<Pagamento> resultados = pagamentoService.listar();

        Assertions.assertEquals(2, resultados.size());
        Assertions.assertTrue(resultados.stream().anyMatch(p -> p.getValor().equals(BigDecimal.valueOf(50.00))));
        Assertions.assertTrue(resultados.stream().anyMatch(p -> p.getValor().equals(BigDecimal.valueOf(150.00))));
    }
}