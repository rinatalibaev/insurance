package ru.alibaev.insuranceVirtu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.alibaev.insuranceVirtu.annotation.MaxValidityDuration;
import ru.alibaev.insuranceVirtu.annotation.Scale;
//import ru.alibaev.insuranceVirtu.annotation.PremiumCalculateDTOConstraint;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.Year;

@Data
@NoArgsConstructor
@Accessors(chain = true)
//@PremiumCalculateDTOConstraint
@MaxValidityDuration(365)
public class PremiumCalculateDTO {
    @Positive
    private double realtyCoefficient;
    @Positive
    @Scale(1)
    private double realtyArea;
    @PastOrPresent
    private Year buildingYear;
    @FutureOrPresent
    private LocalDate startValidity;
    @FutureOrPresent
    private LocalDate endValidity;
    @Positive
    private double insuranceAmount;
}
