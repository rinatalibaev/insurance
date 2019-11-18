package ru.alibaev.insuranceVirtu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surname;
    private String firstname;
    private String patronymic;
    private LocalDate birthday;
    private String passportSeries;
    private String passportNumber;
}
