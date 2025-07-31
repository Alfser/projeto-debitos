package br.com.alfser.projeto.pagamentos;

import br.com.alfser.projeto.pagamentos.common.MetodoPagamento;
import br.com.alfser.projeto.pagamentos.common.StatusPagamento;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoDTO;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PagamentoDTOTest {

    private static final String ID_VALIDO = "507f1f77bcf86cd799439011";
    private static final Long ID_PAGAMENTO_VALIDO = 12345L;
    private static final String CPF_VALIDO = "123.456.789-09";
    private static final String CNPJ_VALIDO = "12.345.678/0001-99";
    private static final MetodoPagamento METODO_VALIDO = MetodoPagamento.CARTAO_CREDITO;
    private static final String NUMERO_CARTAO_VALIDO = "4111111111111111";
    private static final BigDecimal VALOR_VALIDO = new BigDecimal("100.50");
    private static final StatusPagamento STATUS_VALIDO = StatusPagamento.PENDENTE_PROCESSAMENTO;

    @Test
    public void deveCriarDtoVazio() {
        PagamentoDTO dto = new PagamentoDTO();

        Assertions.assertAll(
                () -> Assertions.assertNull(dto.getId()),
                () -> Assertions.assertNull(dto.getIdPagamento()),
                () -> Assertions.assertNull(dto.getCpfCnpj()),
                () -> Assertions.assertNull(dto.getMetodoPagamento()),
                () -> Assertions.assertNull(dto.getNumeroCartao()),
                () -> Assertions.assertNull(dto.getValor()),
                () -> Assertions.assertNull(dto.getStatus()),
                () -> Assertions.assertFalse(dto.isAtivo())
        );
    }

    @Test
    public void deveCriarDtoComTodosCampos() {
        PagamentoDTO dto = new PagamentoDTO(
                ID_VALIDO, ID_PAGAMENTO_VALIDO, CPF_VALIDO,
                METODO_VALIDO, NUMERO_CARTAO_VALIDO, VALOR_VALIDO,
                STATUS_VALIDO, true
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(ID_VALIDO, dto.getId()),
                () -> Assertions.assertEquals(ID_PAGAMENTO_VALIDO, dto.getIdPagamento()),
                () -> Assertions.assertEquals(CPF_VALIDO, dto.getCpfCnpj()),
                () -> Assertions.assertEquals(METODO_VALIDO, dto.getMetodoPagamento()),
                () -> Assertions.assertEquals(NUMERO_CARTAO_VALIDO, dto.getNumeroCartao()),
                () -> Assertions.assertEquals(VALOR_VALIDO, dto.getValor()),
                () -> Assertions.assertEquals(STATUS_VALIDO, dto.getStatus()),
                () ->Assertions.assertTrue(dto.isAtivo())
        );
    }

    @Test
    public void deveConverterParaModelCorretamente() {
        PagamentoDTO dto = new PagamentoDTO(
                ID_VALIDO, ID_PAGAMENTO_VALIDO, CNPJ_VALIDO,
                METODO_VALIDO, NUMERO_CARTAO_VALIDO, VALOR_VALIDO,
                STATUS_VALIDO, true
        );

        Pagamento model = dto.toPagamentoModel();

        Assertions.assertAll(
                () -> Assertions.assertEquals(new ObjectId(ID_VALIDO), model.getId()),
                () -> Assertions.assertEquals(ID_PAGAMENTO_VALIDO, model.getIdPagamento()),
                () -> Assertions.assertEquals(CNPJ_VALIDO, model.getCpfCnpj()),
                () -> Assertions.assertEquals(METODO_VALIDO, model.getMetodoPagamento()),
                () -> Assertions.assertEquals(NUMERO_CARTAO_VALIDO, model.getNumeroCartao()),
                () -> Assertions.assertEquals(VALOR_VALIDO, model.getValor()),
                () -> Assertions.assertEquals(STATUS_VALIDO, model.getStatus()),
                () -> Assertions.assertTrue(model.isAtivo())
        );
    }

    @Test
    public void deveLidarComIdNuloAoConverterParaModel() {
        PagamentoDTO dto = new PagamentoDTO(
                null, ID_PAGAMENTO_VALIDO, CPF_VALIDO,
                METODO_VALIDO, NUMERO_CARTAO_VALIDO, VALOR_VALIDO,
                STATUS_VALIDO, true
        );

        Pagamento model = dto.toPagamentoModel();

        Assertions.assertNull(model.getId());
    }

    @Test
    public void deveConverterDeModelCorretamente() {
        Pagamento model = mock(Pagamento.class);
        when(model.getId()).thenReturn(new ObjectId(ID_VALIDO));
        when(model.getIdPagamento()).thenReturn(ID_PAGAMENTO_VALIDO);
        when(model.getCpfCnpj()).thenReturn(CPF_VALIDO);
        when(model.getMetodoPagamento()).thenReturn(METODO_VALIDO);
        when(model.getNumeroCartao()).thenReturn(NUMERO_CARTAO_VALIDO);
        when(model.getValor()).thenReturn(VALOR_VALIDO);
        when(model.getStatus()).thenReturn(STATUS_VALIDO);
        when(model.isAtivo()).thenReturn(true);

        PagamentoDTO dto = PagamentoDTO.fromPagamentoModel(model);

        Assertions.assertAll(
                () -> Assertions.assertEquals(ID_VALIDO, dto.getId()),
                () -> Assertions.assertEquals(ID_PAGAMENTO_VALIDO, dto.getIdPagamento()),
                () -> Assertions.assertEquals(CPF_VALIDO, dto.getCpfCnpj()),
                () -> Assertions.assertEquals(METODO_VALIDO, dto.getMetodoPagamento()),
                () -> Assertions.assertEquals(NUMERO_CARTAO_VALIDO, dto.getNumeroCartao()),
                () -> Assertions.assertEquals(VALOR_VALIDO, dto.getValor()),
                () -> Assertions.assertEquals(STATUS_VALIDO, dto.getStatus()),
                () -> Assertions.assertTrue(dto.isAtivo(), "isAtivo deve retornar 'true'")
        );

        verify(model, times(2)).getId();
        verify(model, times(1)).getIdPagamento();
        verify(model, times(1)).getMetodoPagamento();
        verify(model, times(1)).getStatus();
        verify(model, times(1)).getValor();

    }

    @Test
    public void deveLidarComModelNuloAoConverterDeModel() {
        PagamentoDTO dto = PagamentoDTO.fromPagamentoModel(null);
        Assertions.assertNull(dto);
    }

    @Test
    public void deveSerIgualQuandoValoresSaoIguais() {
        PagamentoDTO dto1 = new PagamentoDTO(
                ID_VALIDO, ID_PAGAMENTO_VALIDO, CPF_VALIDO,
                METODO_VALIDO, NUMERO_CARTAO_VALIDO, VALOR_VALIDO,
                STATUS_VALIDO, true
        );

        PagamentoDTO dto2 = new PagamentoDTO(
                ID_VALIDO, ID_PAGAMENTO_VALIDO, CPF_VALIDO,
                METODO_VALIDO, NUMERO_CARTAO_VALIDO, VALOR_VALIDO,
                STATUS_VALIDO, true
        );

        Assertions.assertEquals(dto1, dto2);
        Assertions.assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void naoDeveSerIgualQuandoValoresSaoDiferentes() {
        PagamentoDTO dto1 = new PagamentoDTO(
                ID_VALIDO, ID_PAGAMENTO_VALIDO, CPF_VALIDO,
                METODO_VALIDO, NUMERO_CARTAO_VALIDO, VALOR_VALIDO,
                STATUS_VALIDO, true
        );

        PagamentoDTO dto2 = new PagamentoDTO(
                ID_VALIDO, 99999L, CPF_VALIDO,  // ID diferente
                METODO_VALIDO, NUMERO_CARTAO_VALIDO, VALOR_VALIDO,
                STATUS_VALIDO, true
        );

        Assertions.assertNotEquals(dto1, dto2);
        Assertions.assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void deveIncluirTodosCamposNoToString() {
        PagamentoDTO dto = new PagamentoDTO(
                ID_VALIDO, ID_PAGAMENTO_VALIDO, CPF_VALIDO,
                METODO_VALIDO, NUMERO_CARTAO_VALIDO, VALOR_VALIDO,
                STATUS_VALIDO, true
        );

        String toString = dto.toString();

        Assertions.assertAll(
                () -> Assertions.assertTrue(toString.contains(ID_VALIDO)),
                () -> Assertions.assertTrue(toString.contains(ID_PAGAMENTO_VALIDO.toString())),
                () -> Assertions.assertTrue(toString.contains(CPF_VALIDO)),
                () -> Assertions.assertTrue(toString.contains(METODO_VALIDO.toString())),
                () -> Assertions.assertTrue(toString.contains(NUMERO_CARTAO_VALIDO)),
                () -> Assertions.assertTrue(toString.contains(VALOR_VALIDO.toString())),
                () -> Assertions.assertTrue(toString.contains(STATUS_VALIDO.toString())),
                () -> Assertions.assertTrue(toString.contains("true"))
        );
    }

    @Test
    public void deveTestarGettersESetters() {
        PagamentoDTO dto = new PagamentoDTO();

        dto.setId(ID_VALIDO);
        dto.setIdPagamento(ID_PAGAMENTO_VALIDO);
        dto.setCpfCnpj(CPF_VALIDO);
        dto.setMetodoPagamento(METODO_VALIDO);
        dto.setNumeroCartao(NUMERO_CARTAO_VALIDO);
        dto.setValor(VALOR_VALIDO);
        dto.setStatus(STATUS_VALIDO);
        dto.setAtivo(true);

        Assertions.assertAll(
                () -> Assertions.assertEquals(ID_VALIDO, dto.getId()),
                () -> Assertions.assertEquals(ID_PAGAMENTO_VALIDO, dto.getIdPagamento()),
                () -> Assertions.assertEquals(CPF_VALIDO, dto.getCpfCnpj()),
                () -> Assertions.assertEquals(METODO_VALIDO, dto.getMetodoPagamento()),
                () -> Assertions.assertEquals(NUMERO_CARTAO_VALIDO, dto.getNumeroCartao()),
                () -> Assertions.assertEquals(VALOR_VALIDO, dto.getValor()),
                () -> Assertions.assertEquals(STATUS_VALIDO, dto.getStatus()),
                () -> Assertions.assertTrue(dto.isAtivo())
        );
    }
}