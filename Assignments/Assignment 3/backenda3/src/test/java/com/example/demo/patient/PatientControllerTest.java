package com.example.demo.patient;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.patient.model.Patient;
import com.example.demo.patient.model.dto.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static com.example.demo.TestCreationFactory.randomLong;
import static com.example.demo.TestCreationFactory.randomString;
import static com.example.demo.UrlMapping.ENTITY;
import static com.example.demo.UrlMapping.PATIENTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class PatientControllerTest extends BaseControllerTest {
    @InjectMocks
    private PatientController controller;

    @Mock
    private PatientService patientService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allItems() throws Exception {
        List<PatientDTO> items = TestCreationFactory.listOf(Patient.class);
        when(patientService.findAll()).thenReturn(items);

        ResultActions response = mockMvc.perform(get(PATIENTS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }

    @Test
    void create() throws Exception {
        PatientDTO reqItem = PatientDTO.builder()
                .id(1L)
                .name("patient1")
                .numberId("1234")
                .pnc("12345")
                .birthDate(LocalDate.now())
                .address("address1")
                .build();

        when(patientService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(PATIENTS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }


    @Test
    void edit() throws Exception {
        long id = randomLong();
        PatientDTO reqItem = PatientDTO.builder()
                .id(1L)
                .name("patient1")
                .numberId("1234")
                .pnc("12345")
                .birthDate(LocalDate.now())
                .address("address1")
                .build();

        when(patientService.edit(reqItem)).thenReturn(reqItem);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(PATIENTS, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void getItem() throws Exception {
        long id = randomLong();
        PatientDTO reqItem = PatientDTO.builder()
                .id(1L)
                .name("patient1")
                .numberId("1234")
                .pnc("12345")
                .birthDate(LocalDate.now())
                .address("address1")
                .build();
        when(patientService.get(id)).thenReturn(reqItem);

        ResultActions result = performGetWithPathVariable(PATIENTS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(patientService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(PATIENTS + ENTITY, id);
        verify(patientService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }

}