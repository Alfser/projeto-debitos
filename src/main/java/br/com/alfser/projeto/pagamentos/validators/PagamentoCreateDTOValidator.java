package br.com.alfser.projeto.pagamentos.validators;

import br.com.alfser.projeto.pagamentos.annotations.ValidPagamentoCreateDTO;
import br.com.alfser.projeto.pagamentos.dtos.PagamentoCreateDTO;
import br.com.alfser.projeto.pagamentos.services.PagamentoService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PagamentoCreateDTOValidator implements
        ConstraintValidator<ValidPagamentoCreateDTO, PagamentoCreateDTO> {

    @Autowired
    PagamentoService pagamentoService;

    @Override
    public boolean isValid(PagamentoCreateDTO dto, ConstraintValidatorContext context) {
        boolean isValid = true;
        context.disableDefaultConstraintViolation();
        if (!isValidNumeroCartao(dto, context)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isValidNumeroCartao(PagamentoCreateDTO dto, ConstraintValidatorContext context) {
        if (pagamentoService.isPagamentoComCartao(dto.getMetodoPagamento())) {
            if (dto.getNumeroCartao() == null || dto.getNumeroCartao().trim().isEmpty()) {
                context.buildConstraintViolationWithTemplate("Número do cartão é obrigatório para pagamento com cartão")
                        .addPropertyNode("numeroCartao")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}