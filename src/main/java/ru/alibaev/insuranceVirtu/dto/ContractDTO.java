package ru.alibaev.insuranceVirtu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.alibaev.insuranceVirtu.enumeration.RealtyTypeEnum;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ContractDTO {
    private Long id;
    private Double insuranceAmount;
    private RealtyTypeEnum realtyType;
    private int buildYear;
    private Double area;
    private LocalDate startValidity;
    private LocalDate endValidity;
    private LocalDate calculationDate;
    private Double premium;
    private String contractNumber;
    private LocalDate endConclusionDate;
    private String policyholderSurname;
    private String policyholderName;
    private String policyholderPatronymic;
    private String policyholderBirthday;
    private String policyholderPassportSeries;
    private String policyholderPassportNumber;
    private int realtyIndex;
    private String realtyState;
    private String realtyRegion;
    private String realtyDistrict;
    private String realtyLocality;
    private String realtyStreet;
    private String realtyHouse;
    private String realtyHousing;
    private String realtyBuilding;
    private String realtyApartment;
    private String insuranceComment;
}
