package ru.alibaev.insuranceVirtu.annotation;

import ru.alibaev.insuranceVirtu.validator.MaxValidityDurationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxValidityDurationValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxValidityDuration {
    long value();

    String message() default "Invalid scale of floating point value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}