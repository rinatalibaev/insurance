package ru.alibaev.insuranceVirtu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.alibaev.insuranceVirtu.annotation.PremiumCalculateDTOConstraint;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@PremiumCalculateDTOConstraint
public class PremiumCalculateDTO {
    private double realtyCoefficient;
    private double realtyArea;
    private int buildingYear;
    private LocalDate startValidity;
    private LocalDate endValidity;
    private double insuranceAmount;
}
