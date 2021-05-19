package com.example.demo.patient;

import com.example.demo.patient.model.Patient;
import com.example.demo.patient.model.dto.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.ENTITY;
import static com.example.demo.UrlMapping.PATIENTS;

@RestController
@RequestMapping(PATIENTS)
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> allPatients(){return patientService.findAll();}

    @PostMapping
    private PatientDTO create(@RequestBody PatientDTO patient){
        return patientService.create(patient);
    }

    @PatchMapping
    private PatientDTO edit(@RequestBody PatientDTO patient){
        return patientService.edit(patient);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        patientService.delete(id);
    }

    @GetMapping(ENTITY)
    public PatientDTO getPatient(@PathVariable Long id){
        return patientService.get(id);
    }
}
