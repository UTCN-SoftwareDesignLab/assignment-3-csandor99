package com.example.demo.patient;

import com.example.demo.TestCreationFactory;
import com.example.demo.patient.model.Patient;
import com.example.demo.patient.model.dto.PatientDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;


class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp(){
        patientRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        patientRepository.saveAll(patients);

        List<PatientDTO> all = patientService.findAll();

        Assertions.assertEquals(patients.size(), all.size());
    }

    @Test
    void create() {
        Patient patient = Patient.builder()
                .id(1L)
                .name("patient1")
                .numberId("1234")
                .pnc("12345")
                .birthDate(LocalDate.now())
                .address("address1")
                .build();

        patientRepository.save(patient);
        PatientDTO patientDto = PatientDTO.builder()
                .id(1L)
                .name("patient1")
                .numberId("1234")
                .pnc("12345")
                .birthDate(LocalDate.now())
                .address("address1")
                .build();

        Assertions.assertNotNull(patientService.create(patientDto));
    }

    @Test
    void edit() {
        PatientDTO patientDto = PatientDTO.builder()
                .id(1L)
                .name("patient1")
                .numberId("1234")
                .pnc("12345")
                .birthDate(LocalDate.now())
                .address("address1")
                .build();
        patientDto = patientService.create(patientDto);
        Assertions.assertEquals(patientDto.getId(), patientService.edit(patientDto).getId());
    }

    @Test
    void delete() {
        PatientDTO patientDto = PatientDTO.builder()
                .id(1L)
                .name("patient1")
                .numberId("1234")
                .pnc("12345")
                .birthDate(LocalDate.now())
                .address("address1")
                .build();
        patientDto = patientService.create(patientDto);
        patientService.delete(patientDto.getId());
    }
}