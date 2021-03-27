package ru.alibaev.insuranceVirtu.annotation;

import ru.alibaev.insuranceVirtu.validator.MaxValidityDurationValidator;
import ru.alibaev.insuranceVirtu.validator.ScaleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ScaleValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scale {

    int value();

    String message() default "Invalid validity duration";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
