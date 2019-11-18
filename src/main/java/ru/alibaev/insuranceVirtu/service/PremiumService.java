package ru.alibaev.insuranceVirtu.service;

import org.springframework.stereotype.Service;
import ru.alibaev.insuranceVirtu.dto.PremiumCalculateDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

@Service
public class PremiumService {

    public Double calculatePremium(PremiumCalculateDTO premiumCalculateDTO) {
        long insuranceDays = ChronoUnit.DAYS.between(premiumCalculateDTO.getStartValidity(), premiumCalculateDTO.getEndValidity());
        double insuranceAmount = premiumCalculateDTO.getInsuranceAmount();
        double areaCoefficient;
        double buildingYearCoefficient;

        if (premiumCalculateDTO.getRealtyArea() < 50) {
            areaCoefficient = 1.2;
        } else if (premiumCalculateDTO.getRealtyArea() < 100) {
            areaCoefficient = 1.5;
        } else {
            areaCoefficient = 2;
        }

        if (premiumCalculateDTO.getBuildingYear() < 2000) {
            buildingYearCoefficient = 1.3;
        } else if (premiumCalculateDTO.getBuildingYear() <= 2014) {
            buildingYearCoefficient = 1.6;
        } else {
            buildingYearCoefficient = 2;
        }

        return round (insuranceAmount/insuranceDays*areaCoefficient*buildingYearCoefficient*premiumCalculateDTO.getRealtyCoefficient(), 2);
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
