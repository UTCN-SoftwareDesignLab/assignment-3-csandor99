package com.example.demo.patient;

import com.example.demo.patient.model.Patient;
import com.example.demo.patient.model.dto.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mappings(
            @Mapping(target = "consultations", ignore = true)
    )
    Patient fromDto(PatientDTO patientDTO);
    PatientDTO toDto(Patient patient);
}
