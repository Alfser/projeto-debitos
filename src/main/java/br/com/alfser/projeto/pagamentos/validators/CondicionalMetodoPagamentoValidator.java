package br.com.alfser.projeto.pagamentos.validators;

import br.com.alfser.projeto.pagamentos.annotations.CondicionalMetodoPagamento;
import br.com.alfser.projeto.pagamentos.common.MetodoPagamento;
import br.com.alfser.projeto.pagamentos.models.Pagamento;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CondicionalMetodoPagamentoValidator implements ConstraintValidator<CondicionalMetodoPagamento, Object> {

    private MetodoPagamento[] requiredPaymentMethods;

    @Override
    public void initialize(CondicionalMetodoPagamento constraintAnnotation) {
        this.requiredPaymentMethods = constraintAnnotation.paymentMethods();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // Obtém o objeto root (Pagamento) sendo validado
        Object root = null;
        if (context instanceof HibernateConstraintValidatorContext) {
            root = ((HibernateConstraintValidatorContext) context)
                    .getConstraintValidatorPayload(Object.class);
        }
        if (root == null) {
            try {
                Field field = context.getClass().getDeclaredField("validatedValue");
                field.setAccessible(true);
                root = field.get(context);
            } catch (Exception e) {
                return true;
            }
        }

        if (root instanceof Pagamento) {
            Pagamento pagamento = (Pagamento) root;

            // Verifica se o método de pagamento está na lista de requeridos
            boolean paymentMethodRequiresField = Arrays.stream(requiredPaymentMethods)
                    .anyMatch(method -> method == pagamento.getMetodoPagamento());

            if (paymentMethodRequiresField) {
                return value != null && !value.toString().trim().isEmpty();
            }
        }
        return true;
    }
}
