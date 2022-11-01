package ru.alibaev.insuranceVirtu.validator;

import ru.alibaev.insuranceVirtu.annotation.Scale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class ScaleValidator implements ConstraintValidator<Scale, Object> {

    private int value;

    @Override
    public void initialize(Scale scale) {
        this.value = scale.value();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        double number = (double) o;
        int check = BigDecimal.valueOf(number).scale();
        return BigDecimal.valueOf(number).scale() <= value;
    }
}
