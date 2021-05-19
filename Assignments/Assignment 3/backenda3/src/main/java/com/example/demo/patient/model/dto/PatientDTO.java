package com.example.demo.patient.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {
    private Long id;
    private String name;
    private String numberId;
    private String pnc;
    private LocalDate birthDate;
    private String address;
}
