package com.example.demo.consultation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationDTO {
    private Long id;
    private String date;
    private Long patientId;
    private Long doctorId;
    private String details;

}
