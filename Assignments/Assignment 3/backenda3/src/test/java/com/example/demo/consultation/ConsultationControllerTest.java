package com.example.demo.consultation;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.consultation.model.Consultation;
import com.example.demo.consultation.model.dto.ConsultationDTO;
import com.example.demo.patient.model.Patient;
import com.example.demo.patient.model.dto.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.demo.TestCreationFactory.randomLong;
import static com.example.demo.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsultationControllerTest extends BaseControllerTest {

    @InjectMocks
    private ConsultationController controller;

    @Mock
    private ConsultationService consultationService;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        controller = new ConsultationController(consultationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allConsultations() throws Exception{
        List<ConsultationDTO> items = TestCreationFactory.listOf(Consultation.class);
        when(consultationService.findAll()).thenReturn(items);

        ResultActions response = mockMvc.perform(get(CONSULTATIONS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));
    }

    @Test
    void allDoctorConsultations() throws Exception{
        String str = TestCreationFactory.randomString();
        List<ConsultationDTO> items = TestCreationFactory.listOf(Consultation.class);
        when(consultationService.findAllFromDoctor(str)).thenReturn(items);

        ResultActions response = mockMvc.perform(get(CONSULTATIONS + "/doctor/" + str));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));
    }

    @Test
    void create() throws Exception{
        ConsultationDTO reqItem = ConsultationDTO.builder()
                .id(1L)
                .date(LocalDateTime.now().toString())
                .patientId(2L)
                .doctorId(4L)
                .details("detalii")
                .build();

        when(consultationService.create(reqItem)).thenReturn(true);

        ResultActions result = performPostWithRequestBody(CONSULTATIONS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void edit() throws Exception{
        long id = randomLong();
        ConsultationDTO reqItem = ConsultationDTO.builder()
                .id(1L)
                .date(LocalDateTime.now().toString())
                .patientId(2L)
                .doctorId(4L)
                .details("detalii")
                .build();

        ResultActions result = performPatchWithRequestBodyAndPathVariable(CONSULTATIONS, reqItem,"");
        result.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception{
        ConsultationDTO reqItem = ConsultationDTO.builder()
                .id(1L)
                .date(LocalDateTime.now().toString())
                .patientId(2L)
                .doctorId(4L)
                .details("detalii")
                .build();
        ResultActions result = performDeleteWIthPathVariable(CONSULTATIONS + ENTITY, reqItem.getId().toString());
        result.andExpect(status().isOk());
    }

    @Test
    void checkin() {
    }
}