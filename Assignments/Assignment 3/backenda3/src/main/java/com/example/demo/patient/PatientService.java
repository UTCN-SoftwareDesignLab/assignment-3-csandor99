package com.example.demo.patient;

import com.example.demo.patient.model.Patient;
import com.example.demo.patient.model.dto.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public Patient findById(Long id){
        return patientRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Patient not found! Id: " + id));
    }

    public List<PatientDTO> findAll(){
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatientDTO create(PatientDTO patientDTO){
        return patientMapper.toDto(patientRepository.save(
                patientMapper.fromDto(patientDTO)
        ));
    }

    public PatientDTO edit(PatientDTO patientDTO){
        Patient patient = findById(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setNumberId(patientDTO.getNumberId());
        patient.setPnc(patientDTO.getPnc());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setAddress(patientDTO.getAddress());
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public PatientDTO get(Long id){
        return patientMapper.toDto(findById(id));
    }

    public void delete(Long id){
        patientRepository.deleteById(id);
    }
}
