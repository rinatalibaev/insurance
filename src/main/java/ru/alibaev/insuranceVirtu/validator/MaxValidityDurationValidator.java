package ru.alibaev.insuranceVirtu.validator;

import ru.alibaev.insuranceVirtu.annotation.MaxValidityDuration;
import ru.alibaev.insuranceVirtu.dto.PremiumCalculateDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.temporal.ChronoUnit;

public class MaxValidityDurationValidator implements ConstraintValidator<MaxValidityDuration, Object> {

    private long value;

    @Override
    public void initialize(MaxValidityDuration daysUntilLessThan) {
        this.value = daysUntilLessThan.value();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        PremiumCalculateDTO premiumCalculateDTO = (PremiumCalculateDTO) o;
        long daysBetween = premiumCalculateDTO.getStartValidity().until(premiumCalculateDTO.getEndValidity(), ChronoUnit.DAYS);
        return (daysBetween > 0 ) && (daysBetween < value);
    }
}
