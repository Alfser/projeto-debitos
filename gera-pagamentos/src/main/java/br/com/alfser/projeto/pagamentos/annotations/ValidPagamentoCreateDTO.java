package br.com.alfser.projeto.pagamentos.annotations;

import br.com.alfser.projeto.pagamentos.validators.PagamentoCreateDTOValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PagamentoCreateDTOValidator.class)
public @interface ValidPagamentoCreateDTO {
    String message() default "Pagamento inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}