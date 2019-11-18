package ru.alibaev.insuranceVirtu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ClientDTO {
    private Long id;
    private String surname;
    private String firstname;
    private String patronymic;
    private LocalDate birthday;
    private String passportSeries;
    private String passportNumber;
}
