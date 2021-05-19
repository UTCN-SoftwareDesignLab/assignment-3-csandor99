package com.example.demo.consultation;

import com.example.demo.TestCreationFactory;
import com.example.demo.consultation.model.Consultation;
import com.example.demo.consultation.model.dto.ConsultationDTO;
import com.example.demo.patient.PatientRepository;
import com.example.demo.patient.PatientService;
import com.example.demo.patient.model.Patient;
import com.example.demo.patient.model.dto.PatientDTO;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationServiceTest {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private ConsultationRepository consultationRepository;

    @BeforeEach
    void setUp(){
        consultationRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Consultation> consultations = TestCreationFactory.listOf(Patient.class);
        consultationRepository.saveAll(consultations);

        List<ConsultationDTO> all = consultationService.findAll();

        Assertions.assertEquals(consultations.size(), all.size());
    }

    @Test
    void findAllFromDoctor() {
        List<Consultation> consultations = TestCreationFactory.listOf(Patient.class);
        String name = consultations.get(0).getDoctor().getUsername();
        consultationRepository.saveAll(consultations);

        List<ConsultationDTO> all = consultationService.findAllFromDoctor(name);

        Assertions.assertNotEquals(consultations.size(), all.size());
    }

    @Test
    void create() {
        Consultation consultation = Consultation.builder()
                .id(1L)
                .date(LocalDateTime.now())
                .patient(Patient.builder()
                        .id(1L)
                        .name("nume")
                        .pnc("1245")
                        .birthDate(LocalDate.now())
                        .address("strada").build())
                .doctor(User.builder()
                        .id(2L)
                        .username("domnu")
                        .email("domnu@doctor.com")
                        .password("doctor")
                        .build())
                .details("detalii")
                .build();

        consultationRepository.save(consultation);
        ConsultationDTO reqItem = ConsultationDTO.builder()
                .id(1L)
                .date(LocalDateTime.now().toString())
                .patientId(2L)
                .doctorId(4L)
                .details("detalii")
                .build();

        Assertions.assertTrue(consultationService.create(reqItem));
    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
        ConsultationDTO reqItem = ConsultationDTO.builder()
                .id(1L)
                .date(LocalDateTime.now().toString())
                .patientId(2L)
                .doctorId(4L)
                .details("detalii")
                .build();
        consultationService.create(reqItem);
        consultationService.delete(reqItem.getId());
    }
}