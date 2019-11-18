package ru.alibaev.insuranceVirtu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private long insuranceAmount;
    @NotNull
    private String realtyType;
    @NotNull
    private int buildingYear;
    @NotNull
    private double realtyArea;
    @NotNull
    private LocalDate startValidity;
    @NotNull
    private LocalDate endValidity;
    @NotNull
    private LocalDate calculateDate;
    @NotNull
    private double premiumAmount;
    @NotNull
    private String contractNumber;
    @NotNull
    private LocalDate conclusionDate;
    @NotNull
    private String clientFio;
    @NotNull
    private LocalDate clientBirthday;
    @NotNull
    private int passportSeries;
    @NotNull
    private int passportNumber;
    @NotNull
    private String realtyState;
    private String realtyIndex;
    @NotNull
    private String realtyRegion;
    private String realtyDistrict;
    @NotNull
    private String realtyLocality;
    @NotNull
    private String realtyStreet;
    private int realtyHouse;
    private String realtyHousing;
    private String realtyBuilding;
    @NotNull
    private int realtyApartment;
    private String contractCommentText;
}
