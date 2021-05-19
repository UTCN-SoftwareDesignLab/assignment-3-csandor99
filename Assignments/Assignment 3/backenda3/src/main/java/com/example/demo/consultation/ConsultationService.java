package com.example.demo.consultation;

import com.example.demo.consultation.model.Consultation;
import com.example.demo.consultation.model.dto.ConsultationDTO;
import com.example.demo.patient.PatientRepository;
import com.example.demo.patient.model.Patient;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import com.example.demo.websocket.MessageHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final SimpMessageSendingOperations template;

    private Consultation findById(Long id){
        return consultationRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Consultation not found! Id: " + id));
    }

    public ConsultationDTO get(Long id){
        return consultationMapper.toDto(findById(id));
    }

    public List<ConsultationDTO> findAll(){
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ConsultationDTO> findAllFromDoctor(String name){
        User doctor = userRepository.findByUsername(name)
                .orElseThrow(()->new EntityNotFoundException("Doctor not found! name: " + name));

        return consultationRepository.findAllByDoctorEquals(doctor).stream()
                .map(consultationMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean create(ConsultationDTO consultationDTO){
        Patient patient = patientRepository.findById(consultationDTO.getPatientId())
                .orElseThrow(()->new EntityNotFoundException("Patient not found! Id: " + consultationDTO.getPatientId()));

        User doctor = userRepository.findById(consultationDTO.getDoctorId())
                .orElseThrow(()->new EntityNotFoundException("Doctor not found! Id: " + consultationDTO.getDoctorId()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime dateTime = LocalDateTime.parse(consultationDTO.getDate(), formatter);

        if(consultationRepository.findAllByDateEqualsAndDoctorEquals(dateTime,doctor).isEmpty()){
            consultationRepository.save(Consultation.builder()
                    .date(dateTime)
                    .details(consultationDTO.getDetails())
                    .doctor(doctor)
                    .patient(patient)
                    .build());
            template.convertAndSend("/topic/messages/" + doctor.getUsername(),new MessageHolder("New consultation on: " + consultationDTO.getDate() + "\nPatient: " + patient.getName()).getMessage());
            return true;
        }

        return false;
    }

    public void edit(ConsultationDTO consultationDTO){
        Consultation consultation = findById(consultationDTO.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime dateTime = LocalDateTime.parse(consultationDTO.getDate(), formatter);

        User doctor = userRepository.findById(consultationDTO.getDoctorId())
                .orElseThrow(()->new EntityNotFoundException("Doctor not found! Id: " + consultationDTO.getDoctorId()));

        if(consultationRepository.findAllByDateEqualsAndDoctorEquals(dateTime,doctor).isEmpty()){
            consultation.setDate(dateTime);
        }
        consultation.setDetails(consultationDTO.getDetails());
        consultationRepository.save(consultation);
    }

    public void delete(Long id){
        consultationRepository.deleteById(id);
    }

    public void checkin(ConsultationDTO consultationDTO){
        Patient patient = patientRepository.findById(consultationDTO.getPatientId())
                .orElseThrow(()->new EntityNotFoundException("Patient not found! Id: " + consultationDTO.getPatientId()));

        User doctor = userRepository.findById(consultationDTO.getDoctorId())
                .orElseThrow(()->new EntityNotFoundException("Doctor not found! Id: " + consultationDTO.getDoctorId()));

        template.convertAndSend("/topic/checkin/" + doctor.getUsername(),new MessageHolder("Patient " + patient.getName()+ " arrived for the consultation scheduled at: " + consultationDTO.getDate()).getMessage());
    }

}
