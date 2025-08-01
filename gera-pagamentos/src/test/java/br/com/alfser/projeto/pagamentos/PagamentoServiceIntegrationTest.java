package br.com.alfser.projeto.pagamentos;

import br.com.alfser.projeto.pagamentos.common.MetodoPagamento;
import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoFilterDTO;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import br.com.alfser.projeto.pagamentos.repositories.PagamentoRepository;
import br.com.alfser.projeto.pagamentos.services.PagamentoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Import({TestcontainersConfiguration.class})
public class PagamentoServiceIntegrationTest {

    private static final String CNPJ_VALIDO = "12.345.678/0001-99";
    private static final Long ID_PAGAMENTO_VALIDO = 12345L;
    private static final StatusPagamento STATUS_VALIDO = StatusPagamento.PENDENTE_PROCESSAMENTO;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoService pagamentoService;

    @BeforeEach
    public void SetUp(){
        pagamentoRepository.deleteAll();
    }

    @Test
    public void criar_DeveSalvarPagamentoComStatusPendenteProcessmentoNoBanco_QuandoDadosValidos() {

        Pagamento pagamento = new Pagamento();
        pagamento.setValor(BigDecimal.valueOf(100.50));
        pagamento.setCpfCnpj("123.456.789-09");
        pagamento.setMetodoPagamento(MetodoPagamento.PIX);

        Pagamento resultado = pagamentoService.criar(pagamento);

        Assertions.assertInstanceOf(Pagamento.class, resultado);
        Assertions.assertNotNull(resultado.getId());
        Assertions.assertEquals(StatusPagamento.PENDENTE_PROCESSAMENTO, resultado.getStatus());
        Assertions.assertEquals(BigDecimal.valueOf(100.50), resultado.getValor());

        Pagamento pagamentoDoBanco = pagamentoService.listar(new PagamentoFilterDTO(),PageRequest.of(0, 1)).stream()
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

        List<Pagamento> resultados = pagamentoService.listar(new PagamentoFilterDTO(), PageRequest.of(0, 10)).stream().toList();

        Assertions.assertEquals(2, resultados.size());
        Assertions.assertTrue(resultados.stream().anyMatch(p -> p.getValor().equals(BigDecimal.valueOf(50.00))));
        Assertions.assertTrue(resultados.stream().anyMatch(p -> p.getValor().equals(BigDecimal.valueOf(150.00))));
    }

    @Test
    public void listar_DeveRetornarPagamentoFiltradoPorCpfCnnj_QuandoEnvidoValorNoFiltro(){
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setValor(BigDecimal.valueOf(50.00));
        pagamento1.setCpfCnpj(CNPJ_VALIDO);
        pagamentoService.criar(pagamento1);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setValor(BigDecimal.valueOf(150.00));
        pagamentoService.criar(pagamento2);

        List<Pagamento> resultados = pagamentoService.listar(
                new PagamentoFilterDTO(CNPJ_VALIDO),
                PageRequest.of(0, 10)).stream().toList();

        Assertions.assertEquals(1, resultados.size());
        Assertions.assertEquals(CNPJ_VALIDO, resultados.get(0).getCpfCnpj());
    }

    @Test
    public void listar_DeveRetornarPagamentoFiltradoPorIdPagamento_QuandoEnvidoValorNoFiltro(){
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setValor(BigDecimal.valueOf(50.00));
        pagamento1.setIdPagamento(ID_PAGAMENTO_VALIDO);
        pagamentoService.criar(pagamento1);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setValor(BigDecimal.valueOf(150.00));
        pagamento2.setIdPagamento(222L);
        pagamentoService.criar(pagamento2);

        List<Pagamento> resultados = pagamentoService.listar(
                new PagamentoFilterDTO(ID_PAGAMENTO_VALIDO),
                PageRequest.of(0, 10)).stream().toList();

        Assertions.assertEquals(1, resultados.size());
        Assertions.assertEquals(ID_PAGAMENTO_VALIDO, resultados.get(0).getIdPagamento());
    }

    @Test
    public void listar_DeveRetornarPagamentoFiltradoPorStatusPagamento_QuandoEnvidoValorNoFiltro(){
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setValor(BigDecimal.valueOf(50.00));
        pagamentoService.criar(pagamento1);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setValor(BigDecimal.valueOf(150.00));
        pagamentoService.criar(pagamento2);

        List<Pagamento> resultados = pagamentoService.listar(
                new PagamentoFilterDTO(StatusPagamento.PENDENTE_PROCESSAMENTO),
                PageRequest.of(0, 10)).stream().toList();

        Assertions.assertEquals(2, resultados.size());
        Assertions.assertEquals(StatusPagamento.PENDENTE_PROCESSAMENTO, resultados.get(0).getStatus());
    }

    @Test
    public void listar_DeveRetornarPagamentoAtivo_QuandoFiltradoPorAtivoTrue(){
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setValor(BigDecimal.valueOf(50.00));
        pagamentoService.criar(pagamento1);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setValor(BigDecimal.valueOf(150.00));
        pagamentoService.criar(pagamento2);
        pagamentoService.desativarPagamento(pagamento2.getId());

        List<Pagamento> resultados = pagamentoService.listar(
                new PagamentoFilterDTO(true),
                PageRequest.of(0, 10)).stream().toList();

        Assertions.assertEquals(1, resultados.size());
        Assertions.assertTrue(resultados.get(0).isAtivo());
        Assertions.assertEquals(StatusPagamento.PENDENTE_PROCESSAMENTO, resultados.get(0).getStatus());
    }

    @Test
    public void listar_DeveRetornarPagamentoAtivoInativo_QuandoFiltroAtivoNulo(){
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setValor(BigDecimal.valueOf(50.00));
        pagamentoService.criar(pagamento1);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setValor(BigDecimal.valueOf(150.00));
        pagamento2.setAtivo(false);
        pagamentoService.criar(pagamento2);

        List<Pagamento> resultados = pagamentoService.listar(
                new PagamentoFilterDTO(),
                PageRequest.of(0, 10)).stream().toList();

        Assertions.assertEquals(2, resultados.size());
        Assertions.assertTrue(resultados.stream().anyMatch(Pagamento::isAtivo));
        Assertions.assertTrue(resultados.stream().anyMatch(pagamento -> !pagamento.isAtivo()));
    }
}