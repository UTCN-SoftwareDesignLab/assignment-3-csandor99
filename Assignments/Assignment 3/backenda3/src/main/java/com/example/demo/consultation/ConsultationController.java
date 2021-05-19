package com.example.demo.consultation;

import com.example.demo.consultation.model.Consultation;
import com.example.demo.consultation.model.dto.ConsultationDTO;
import com.example.demo.patient.model.dto.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(CONSULTATIONS)
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationService consultationService;

    @GetMapping(ENTITY)
    public ConsultationDTO getConsultation(@PathVariable Long id){
        return consultationService.get(id);
    }

    @GetMapping
    public List<ConsultationDTO> allConsultations(){
        return consultationService.findAll();
    }

    @GetMapping(NAME)
    public List<ConsultationDTO> allDoctorConsultations(@PathVariable String name){return consultationService.findAllFromDoctor(name);}

    @PostMapping
    public boolean create(@RequestBody ConsultationDTO consultation){
        return consultationService.create(consultation);
    }

    @PatchMapping
    public void edit(@RequestBody ConsultationDTO consultationDTO){
        consultationService.edit(consultationDTO);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        consultationService.delete(id);
    }

    @PostMapping(CHECKIN)
    public void checkin(@RequestBody ConsultationDTO consultation){
        consultationService.checkin(consultation);
    }
}
