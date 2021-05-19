package com.example.demo.consultation;

import com.example.demo.consultation.model.Consultation;
import com.example.demo.consultation.model.dto.ConsultationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {
    @Mappings({
            @Mapping(target = "patientId", source = "consultation.patient.id"),
            @Mapping(target = "doctorId", source = "consultation.doctor.id"),
            @Mapping(target="date", source = "consultation.date", dateFormat = "yyyy-MM-dd HH")
    })
    ConsultationDTO toDto(Consultation consultation);

    //Consultation fromDto(ConsultationDTO consultationDTO);
}
