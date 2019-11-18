package ru.alibaev.insuranceVirtu.annotation;

import ru.alibaev.insuranceVirtu.validator.PremiumCalculateDTOValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PremiumCalculateDTOValidator.class)
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PremiumCalculateDTOConstraint {
    String message() default "Введенные Вами данные неверны";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
