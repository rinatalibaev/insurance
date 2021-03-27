package ru.alibaev.insuranceVirtu.validator;

import ru.alibaev.insuranceVirtu.annotation.PremiumCalculateDTOConstraint;
import ru.alibaev.insuranceVirtu.dto.PremiumCalculateDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class PremiumCalculateDTOValidator implements
        ConstraintValidator<PremiumCalculateDTOConstraint, Object> {

    @Override
    public void initialize(PremiumCalculateDTOConstraint premiumCalculateDTOConstraint) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        PremiumCalculateDTO premiumCalculateDTO = (PremiumCalculateDTO) value;
        String doubleString = String.valueOf(premiumCalculateDTO.getRealtyArea());
        int indexOfDecimal = doubleString.indexOf(".");
        try {
            return premiumCalculateDTO.getRealtyCoefficient() != 0 &&
                    premiumCalculateDTO.getBuildingYear().getValue() != 0 &&
                    premiumCalculateDTO.getBuildingYear().getValue() <= LocalDate.now().getYear() &&
                    String.valueOf(premiumCalculateDTO.getBuildingYear()).length() == 4 &&
                    premiumCalculateDTO.getInsuranceAmount() != 0 &&
                    premiumCalculateDTO.getRealtyArea() != 0 &&
                    doubleString.substring(indexOfDecimal + 1).length() < 2 &&
                    (premiumCalculateDTO.getStartValidity().isAfter(LocalDate.now()) || premiumCalculateDTO.getStartValidity().isEqual(LocalDate.now())) &&
                    premiumCalculateDTO.getStartValidity().isBefore(premiumCalculateDTO.getEndValidity()) &&
                    !premiumCalculateDTO.getEndValidity().minusYears(1).plusDays(1).isAfter(premiumCalculateDTO.getStartValidity());
        } catch (NullPointerException npe) {
            return false;
        }

    }

}
